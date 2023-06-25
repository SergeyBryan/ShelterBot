package com.example.shelterbot.message;


import com.example.shelterbot.listener.TelegramBotUpdatesListener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * Методы для создания кнопок и сообщений для TelegramBot.
 * метод keyboards возможен для использования в двух аргументах:
 * Передавая в аргументы строку, для создания одной кнопки
 * Передавая в аргументы массив строк, создавая больше одной кнопки
 * Метод sendMessage используется для отправки сообщений ботом
 * Метод sendButtonMessage используется для отправки сообщений ботом с кнопкой
 */
@Service
public class ShelterMessageImpl implements ShelterMessage {


    private static final Logger LOGGER = LoggerFactory.getLogger(ShelterMessageImpl.class);




    public InlineKeyboardMarkup keyboards(String... keyboardText) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        for (String text : keyboardText) {
            keyboardMarkup.addRow(new InlineKeyboardButton(text).callbackData("/" + text));
        }
        return keyboardMarkup;
    }

    public void sendMessage(long id, TelegramBot telegramBot, String message) {
        SendMessage sendMessage = new SendMessage(id, message);
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            LOGGER.error("Error during sending message {}", sendResponse.description());
        }
    }

    public void sendButtonMessage(long id, TelegramBot telegramBot, String message, InlineKeyboardMarkup keyboardMarkup) {
        SendMessage sendMessage = new SendMessage(id, message);
        SendResponse sendResponse = telegramBot.execute(sendMessage.replyMarkup(keyboardMarkup));
        if (!sendResponse.isOk()) {
            LOGGER.error("Error during sending message {}", sendResponse.description());
        }
    }

}
