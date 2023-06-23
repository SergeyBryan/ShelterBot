package com.example.shelterbot.handlers;

import com.example.shelterbot.message.ShelterMessageImpl;
import com.example.shelterbot.model.Report;
import com.example.shelterbot.service.ReportsService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Обработчик по умолчанию, который применяется к любому обновлению (Update) в чате.
 * Отправляет сообщение пользователю с предложением вызвать волонтера.
 */
@Component
@Order(100)
public class DefaultHandler extends AbstractHandler {

    /**
     * Создает экземпляр класса DefaultHandler.
     *
     * @param telegramBot    бот, который будет обрабатывать сообщения.
     * @param shelterMessage объект, который предоставляет методы для работы с сообщением ShelterMessage.
     */
    public DefaultHandler(TelegramBot telegramBot, ShelterMessageImpl shelterMessage) {
        super(telegramBot, shelterMessage);
    }

    /**
     * Проверяет, применим ли обработчик к данному обновлению в чате.
     *
     * @param update обновление в чате.
     * @return всегда true, так как обработчик по умолчанию применим к любому обновлению.
     */
    @Override
    public boolean appliesTo(Update update) {
        return true;
    }

    /**
     * Обрабатывает обновление в чате путем отправки пользователю сообщения с предложением вызвать волонтера.
     *
     * @param update обновление в чате.
     */
    @Override
    public void handleUpdate(Update update) {

        telegramBot.execute(
                new SendMessage(
                        update.message().chat().id(), "Позвать волонтера"
                ));
    }
}
