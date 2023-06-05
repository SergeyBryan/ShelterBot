package com.example.shelterbot.handlers;

import com.example.shelterbot.message.ShelterMessageImpl;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import lombok.ToString;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Обработчик меню для Telegram-бота приюта для кошек.
 * Обрабатывает запросы на получение информации о приюте, инструкции по взятию животного из приюта,
 * отправку отчета о питомце и вызов волонтера.
 */
@Component
@ToString
@Order
public class MenuHandler extends AbstractHandler {

    final String INFO = "Узнать информацию о приюте";
    final String HOW_TO_TAKE_A_PET = "Как взять животное из приюта";
    final String PET_REPORT = "Прислать отчет о питомце";
    final String CALL_A_VOLUNTEER = "Позвать волонтера";
    String MENU = "Выберите Ваш запрос в меню";

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
            return (update.callbackQuery().data().equals("/Назад"));
        } else {
            return update.message().text().equals("Приют для кошек");
        }
    }

    /**
     * Обрабатывает обновление, отправляя пользователю кнопки меню.
     *
     * @param update обновление, которое нужно обработать
     */
    @Override
    public void handleUpdate(Update update) {
        InlineKeyboardMarkup keyboardMarkup = shelterMessage.keyboards(INFO, HOW_TO_TAKE_A_PET, PET_REPORT, CALL_A_VOLUNTEER);
        if (update.message() == null) {
            long chatId = update.callbackQuery().message().chat().id();
            shelterMessage.sendButtonMessage(chatId, telegramBot, MENU, keyboardMarkup);
        } else {
            long chatId = update.message().chat().id();
            shelterMessage.sendButtonMessage(chatId, telegramBot, MENU, keyboardMarkup);
        }
    }
}