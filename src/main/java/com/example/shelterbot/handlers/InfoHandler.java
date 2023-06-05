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
 * Обработчик запросов на получение информации о приюте для Telegram-бота приюта для кошек.
 * Обрабатывает запросы на получение информации о приюте, инструкции по взятию животного из приюта,
 * отправку отчета о питомце и вызов волонтера.
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

    /**
     * Конструктор класса InfoHandler.
     *
     * @param telegramBot     экземпляр TelegramBot для отправки сообщений
     * @param shelterMessage  экземпляр ShelterMessageImpl для формирования сообщений
     * @param menuHandler     экземпляр MenuHandler для обработки запросов на возврат к меню
     */
    public InfoHandler(TelegramBot telegramBot, ShelterMessageImpl shelterMessage, MenuHandler menuHandler) {
        super(telegramBot, shelterMessage);
        this.secondStepHandler = menuHandler;
    }

    /**
     * Проверяет, применим ли данный обработчик к данному обновлению.
     *
     * @param update  обновление, которое нужно обработать
     * @return true, если обработчик применим, иначе false
     */
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

    /**
     * Обрабатывает обновление, отправляя пользователю запрошенную информацию.
     *
     * @param update  обновление, которое нужно обработать
     */
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
