package com.example.shelterbot.handlers;

import com.example.shelterbot.message.ShelterMessageImpl;
import com.example.shelterbot.model.enums.PetType;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Обработчик меню для Telegram-бота приюта для кошек.
 * Обрабатывает запросы на получение информации о приюте, инструкции по взятию животного из приюта,
 * отправку отчета о питомце и вызов волонтера.
 */
@Component
@Order(2)
public class MenuHandler extends AbstractHandler {

    final Map<Long, PetType> flag = new HashMap<>();


    /**
     * Конструктор класса MenuHandler.
     *
     * @param telegramBot    экземпляр TelegramBot для отправки сообщений
     * @param shelterMessage экземпляр ShelterMessageImpl для формирования сообщений
     */
    public MenuHandler(TelegramBot telegramBot, ShelterMessageImpl shelterMessage) {
        super(telegramBot, shelterMessage);
    }


    /**
     * Проверяет, применим ли данный обработчик к данному обновлению.
     *
     * @param update обновление, которое нужно обработать
     * @return true, если обработчик применим, иначе false
     */
    @Override
    public boolean appliesTo(Update update) {
        if (update.callbackQuery() != null) {
            return (update.callbackQuery().data().equals("/" + BACK)) ||
                    (update.callbackQuery().data().equals("/" + CAT_SHELTER) || update.callbackQuery().data().equals("/" + DOG_SHELTER));
        }
        return false;
    }

    /**
     * Обрабатывает обновление, отправляя пользователю кнопки меню.
     *
     * @param update обновление, которое нужно обработать
     */
    @Override
    public void handleUpdate(Update update) {
        long chatId = update.callbackQuery().message().chat().id();

        if (update.callbackQuery().data().equals("/" + CAT_SHELTER)) {
            flag.put(chatId, PetType.CAT);
        } else if (update.callbackQuery().data().equals("/" + DOG_SHELTER)) {
            flag.put(chatId, PetType.DOG);
        }

        InlineKeyboardMarkup keyboardMarkup = shelterMessage.keyboards(INFO, HOW_TO_TAKE_A_PET, PET_REPORT, CALL_A_VOLUNTEER);
//        if (update.message() == null) {
        shelterMessage.sendButtonMessage(chatId, telegramBot, MENU, keyboardMarkup);
//        }
//        else {
//            chatId = update.message().chat().id();
//            shelterMessage.sendButtonMessage(chatId, telegramBot, MENU, keyboardMarkup);
//        }
    }
}