package com.example.shelterbot.handlers;

import com.example.shelterbot.message.ShelterMessageImpl;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuHandlerTest {

    private static TelegramBot telegramBot;
    private static ShelterMessageImpl shelterMessage;
    private static MenuHandler menuHandler;

    @BeforeAll
    static void setUp() {
        telegramBot = mock(TelegramBot.class);
        shelterMessage = mock(ShelterMessageImpl.class);
        menuHandler = new MenuHandler(telegramBot, shelterMessage);
    }

    @Test
    @DisplayName("Testing appliesTo method with callbackQuery update")
    void testAppliesToWithCallbackQueryUpdate() {
        Update update = mock(Update.class);
        when(update.callbackQuery()).thenReturn(mockedCallbackQuery("/Назад").callbackQuery());
        assertTrue(menuHandler.appliesTo(update));
    }

    @Test
    @DisplayName("Testing appliesTo method with message update")
    void testAppliesToWithMessageUpdate() {
        Update update = mock(Update.class);
        when(update.message()).thenReturn(mockedMessage("Приют для кошек"));
        assertTrue(menuHandler.appliesTo(update));
    }

    @Test
    @DisplayName("Testing appliesTo method with wrong callbackQuery update")
    void testAppliesToWithWrongCallbackQueryUpdate() {
        Update update = mock(Update.class);
        when(update.callbackQuery()).thenReturn(mockedCallbackQuery("/Wrong").callbackQuery());
        assertFalse(menuHandler.appliesTo(update));
    }

    @Test
    @DisplayName("Testing appliesTo method with wrong message update")
    void testAppliesToWithWrongMessageUpdate() {
        Update update = mock(Update.class);
        when(update.message()).thenReturn(mockedMessage("Wrong"));
        assertFalse(menuHandler.appliesTo(update));
    }

    @Test
    @DisplayName("Testing handleUpdate method with message update")
    void testHandleUpdateWithMessageUpdate() {
        Update update = mock(Update.class);
        Message message = mockedMessage("Приют для кошек");
        Chat chat = mock(Chat.class);
        when(chat.id()).thenReturn(123L);
        when(message.chat()).thenReturn(chat);
        when(update.message()).thenReturn(message);
        InlineKeyboardMarkup keyboardMarkup = mock(InlineKeyboardMarkup.class);
        when(shelterMessage.keyboards(anyString(), anyString(), anyString(), anyString())).thenReturn(keyboardMarkup);
        menuHandler.handleUpdate(update);
        verify(shelterMessage).sendButtonMessage(eq(123L), eq(telegramBot), eq("Выберите Ваш запрос в меню"), eq(keyboardMarkup));
    }

    @Test
    @DisplayName("Testing handleUpdate method with callbackQuery update")
    void testHandleUpdateWithCallbackQueryUpdate() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(chat.id()).thenReturn(123L);
        when(message.chat()).thenReturn(chat);
        when(update.callbackQuery()).thenReturn(mockedCallbackQuery("/Назад", message).callbackQuery());
        InlineKeyboardMarkup keyboardMarkup = mock(InlineKeyboardMarkup.class);
        when(shelterMessage.keyboards(anyString(), anyString(), anyString(), anyString())).thenReturn(keyboardMarkup);
        menuHandler.handleUpdate(update);
        verify(shelterMessage).sendButtonMessage(eq(123L), eq(telegramBot), eq("Выберите Ваш запрос в меню"), eq(keyboardMarkup));
    }

    private Message mockedMessage(String text) {
        Message message = mock(Message.class);
        when(message.text()).thenReturn(text);
        return message;
    }

    private Update mockedCallbackQuery(String data, Message message) {
        Update update = mock(Update.class);
        when(update.callbackQuery()).thenReturn(mockedCallbackQuery(data, message).callbackQuery());
        return update;
    }

    private Update mockedCallbackQuery(String data) {
        Update update = mock(Update.class);
        when(update.callbackQuery()).thenReturn(mockedCallbackQuery(data, null).callbackQuery());
        return update;
    }

//    private CallbackQuery mockedCallbackQuery(String data, Message message) {
//        CallbackQuery callbackQuery = mock(CallbackQuery.class);
//        when(callbackQuery.data()).thenReturn(data);
//        when(callbackQuery.message()).thenReturn(message);
//        return callbackQuery;
//    }

}