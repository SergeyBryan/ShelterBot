package com.example.shelterbot.handlers;

import com.example.shelterbot.message.ShelterMessageImpl;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.hibernate.annotations.Comment;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Обработчик команды /start, который применяется только к обновлению, содержащему данную команду.
 * Отправляет пользователю приветственное сообщение.
 */
@Component
@Order(1)
public class StartHandler extends AbstractHandler {

    /**
     * Создает экземпляр класса StartHandler.
     *
     * @param telegramBot    бот, который будет обрабатывать сообщения.
     * @param shelterMessage объект, который предоставляет методы для работы с сообщением ShelterMessage.
     */
    public StartHandler(TelegramBot telegramBot, ShelterMessageImpl shelterMessage) {
        super(telegramBot, shelterMessage);
    }

    /**
     * Проверяет, применим ли обработчик к данному обновлению в чате.
     *
     * @param update обновление в чате.
     * @return true, если текст сообщения содержит команду /start, в противном случае - false.
     */
    @Override
    public boolean appliesTo(Update update) {
        if (update.callbackQuery() == null) {
            return update.message().text() != null && update.message().text().equals("/start");
        } else {
            return false;
        }
    }

    /**
     * Обрабатывает обновление в чате путем отправки пользователю приветственного сообщения.
     *
     * @param update обновление в чате.
     */
    @Override
    public void handleUpdate(Update update) {
        long chatId = update.message().chat().id();
        InlineKeyboardMarkup inlineKeyboardMarkup = shelterMessage.keyboards("Приют для кошек", "Приют для собак");
        shelterMessage.sendButtonMessage(chatId, telegramBot, "Привет " + update.message().chat().firstName() + ". Я помогу тебе выбрать приют", inlineKeyboardMarkup);
    }
}
