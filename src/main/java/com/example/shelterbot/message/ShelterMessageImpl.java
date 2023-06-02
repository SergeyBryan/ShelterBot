package com.example.shelterbot.message;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;

public class ShelterMessageImpl implements ShelterMessage {

    Logger LOGGER;

    public InlineKeyboardMarkup keyboards(String keyboardText) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        return keyboardMarkup.addRow(new InlineKeyboardButton(keyboardText).callbackData("/" + keyboardText));
    }

    public InlineKeyboardMarkup keyboards(String firstKeyboardText, String secondKeyboardText) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        return keyboardMarkup.addRow(
                new InlineKeyboardButton(firstKeyboardText).callbackData("/" + firstKeyboardText),
                new InlineKeyboardButton(secondKeyboardText).callbackData("/" + secondKeyboardText));
    }

    public InlineKeyboardMarkup keyboards(String firstKeyboardText, String secondKeyboardText, String thirdKeyboardText) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        return keyboardMarkup.addRow(
                new InlineKeyboardButton(firstKeyboardText).callbackData("/" + firstKeyboardText),
                new InlineKeyboardButton(secondKeyboardText).callbackData("/" + secondKeyboardText),
                new InlineKeyboardButton(thirdKeyboardText).callbackData("/" + thirdKeyboardText));
    }

    public InlineKeyboardMarkup keyboards(String firstKeyboardText, String secondKeyboardText, String thirdKeyboardText, String fourthKeyboard) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        return keyboardMarkup.addRow(
                new InlineKeyboardButton(firstKeyboardText).callbackData("/" + firstKeyboardText),
                new InlineKeyboardButton(secondKeyboardText).callbackData("/" + secondKeyboardText),
                new InlineKeyboardButton(thirdKeyboardText).callbackData("/" + thirdKeyboardText),
                new InlineKeyboardButton(fourthKeyboard).callbackData("/" + fourthKeyboard));
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
