package com.example.shelterbot.handlers;

import com.example.shelterbot.message.ShelterMessageImpl;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import lombok.ToString;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * В этом хендлере находится каждый пункт основного меню и обработка кнопки "Назад"
 * Работоспособность каждого пункта меню сделана через CASE SWITCH.
 */

@Component
@ToString
@Order
public class InfoHandler extends AbstractHandler {

    final String INFO = "Узнать информацию о приюте";
    final String HOW_TO_TAKE_A_PET = "Как взять животное из приюта";
    final String PET_REPORT = "Прислать отчет о питомце";
    final String CALL_A_VOLUNTEER = "Позвать волонтера";
    String MENU = "Выберите Ваш запрос в меню";
    List<String> menuList = (List.of(INFO, HOW_TO_TAKE_A_PET, PET_REPORT, CALL_A_VOLUNTEER));
    MenuHandler secondStepHandler;

    public InfoHandler(TelegramBot telegramBot, ShelterMessageImpl shelterMessage, MenuHandler menuHandler) {
        super(telegramBot, shelterMessage);
        this.secondStepHandler = menuHandler;
    }

    @Override
    public boolean appliesTo(Update update) {
        if (update.callbackQuery() != null) {
            for (String text : menuList) {
                update.callbackQuery().data().equals("/" + text);
                return true;
            }
        }
        return false;
    }

    @Override
    public void handleUpdate(Update update) {
        String data = update.callbackQuery().data();
        long chatId = update.callbackQuery().message().chat().id();
        InlineKeyboardMarkup inlineKeyboardMarkup = shelterMessage.keyboards("Назад");
        if (data != null) {
            switch (data) {
                case "/" + INFO ->
                        shelterMessage.sendButtonMessage(chatId, telegramBot, "Инфо: \n Здесь можно узнать как взять питомца из приюта", inlineKeyboardMarkup);
                case "/" + HOW_TO_TAKE_A_PET ->
                        shelterMessage.sendButtonMessage(chatId, telegramBot, "Как взять: \n Здесь можно узнать в какие дни взять", inlineKeyboardMarkup);
                case "/" + PET_REPORT ->
                        shelterMessage.sendButtonMessage(chatId, telegramBot, "Отчёт о питомце: \n Отчёт о вашем питомце здесь:", inlineKeyboardMarkup);
                case "/" + CALL_A_VOLUNTEER ->
                        shelterMessage.sendButtonMessage(chatId, telegramBot, "Волонтёр", inlineKeyboardMarkup);
                case "/Назад" -> secondStepHandler.handleUpdate(update);
            }
        }
    }
}
