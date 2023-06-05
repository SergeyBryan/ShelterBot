package com.example.shelterbot.handlers;

import com.example.shelterbot.message.ShelterMessageImpl;
import com.pengrad.telegrambot.TelegramBot;
import org.springframework.stereotype.Component;


/**
 * Класс родитель для хендлеров, используется, чтобы не переопределять базовые аргументы в хендлерах
 */

@Component
public abstract class AbstractHandler implements TelegramHandler {

    protected TelegramBot telegramBot;
    protected ShelterMessageImpl shelterMessage;

    public AbstractHandler(TelegramBot telegramBot, ShelterMessageImpl shelterMessage) {
        this.telegramBot = telegramBot;
        this.shelterMessage = shelterMessage;
    }
}
