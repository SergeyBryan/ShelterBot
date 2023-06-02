package com.example.shelterbot.message;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;

public interface ShelterMessage {

    InlineKeyboardMarkup keyboards(String keyboardText);

    InlineKeyboardMarkup keyboards(String firstKeyboardText, String secondKeyboardText);

    InlineKeyboardMarkup keyboards(String firstKeyboardText, String secondKeyboardText, String thirdKeyboardText);

    InlineKeyboardMarkup keyboards(String firstKeyboardText, String secondKeyboardText, String thirdKeyboardText, String fourthKeyboard);

    void sendMessage(long id, TelegramBot telegramBot, String message);

    void sendButtonMessage(long id, TelegramBot telegramBot, String message, InlineKeyboardMarkup keyboardMarkup);

}
