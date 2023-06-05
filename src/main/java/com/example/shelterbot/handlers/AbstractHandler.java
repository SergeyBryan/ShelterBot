package com.example.shelterbot.handlers;

import com.example.shelterbot.message.ShelterMessageImpl;
import com.pengrad.telegrambot.TelegramBot;
import org.springframework.stereotype.Component;


/**
 * Это абстрактный класс, который внедряет интерфейс TelegramHandler
 * Аннотация @Component показывает, что он является компонентом Spring и может автоматически сконфигурирован и внедрён в другие компоненты.
 * Класс содержит два поля для наследников: telegramBot и shelterMessage, которые переданы в конструктор
 * Класс также содержит методы, которые должны быть реализованы в классах-наследниках.
 * Эти методы отвечают за обработку входящих сообщений и отправку ответов.
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
