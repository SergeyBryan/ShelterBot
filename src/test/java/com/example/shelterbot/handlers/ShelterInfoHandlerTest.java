package com.example.shelterbot.handlers;

import com.example.shelterbot.handlers.text.ShelterInfoEnum;
import com.example.shelterbot.message.ShelterMessageImpl;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendPhoto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ShelterInfoHandlerTest {

    private TelegramBot telegramBot;
    private ShelterMessageImpl shelterMessage;
    private ShelterInfoHandler handler;

    @BeforeEach
    void setUp() {
        telegramBot = Mockito.mock(TelegramBot.class);
        shelterMessage = Mockito.mock(ShelterMessageImpl.class);
        handler = new ShelterInfoHandler(telegramBot, shelterMessage);
    }

    @Test
    void testAppliesToWithCallbackQuery() {
        CallbackQuery callbackQuery = Mockito.mock(CallbackQuery.class);
        Update update = Mockito.mock(Update.class);
        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.data()).thenReturn("/information");
        assertTrue(handler.appliesTo(update));
    }

    @Test
    void testAppliesToWithMessage() {
        Message message = Mockito.mock(Message.class);
        Update update = Mockito.mock(Update.class);
        when(update.message()).thenReturn(message);
        assertFalse(handler.appliesTo(update));
    }

    @Test
    void testHandleUpdateWithBackMenu() {
        CallbackQuery callbackQuery = Mockito.mock(CallbackQuery.class);
        Chat chat = Mockito.mock(Chat.class);
        Message message = Mockito.mock(Message.class);
        Update update = Mockito.mock(Update.class);
        InlineKeyboardMarkup backMenu = Mockito.mock(InlineKeyboardMarkup.class);
        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.data()).thenReturn("/back_menu");
        when(callbackQuery.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(123L);
        when(shelterMessage.keyboards(any())).thenReturn(backMenu);
        handler.handleUpdate(update);
        verify(shelterMessage).sendButtonMessage(123L, telegramBot, ShelterInfoEnum.BACK_MENU.getText(), backMenu);
    }

    @Test
    void testHandleUpdateWithInformation() {
        CallbackQuery callbackQuery = Mockito.mock(CallbackQuery.class);
        Chat chat = Mockito.mock(Chat.class);
        Message message = Mockito.mock(Message.class);
        Update update = Mockito.mock(Update.class);
        InlineKeyboardMarkup backMenu = Mockito.mock(InlineKeyboardMarkup.class);
        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.data()).thenReturn("/information");
        when(callbackQuery.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(123L);
        when(shelterMessage.keyboards(any())).thenReturn(backMenu);
        handler.handleUpdate(update);
        verify(shelterMessage).sendButtonMessage(123L, telegramBot, ShelterInfoEnum.INFORMATION_TEXT.getText(), backMenu);
    }

    @Test
    void testHandleUpdateWithSchedule() {
        CallbackQuery callbackQuery = Mockito.mock(CallbackQuery.class);
        Chat chat = Mockito.mock(Chat.class);
        Message message = Mockito.mock(Message.class);
        Update update = Mockito.mock(Update.class);
        InlineKeyboardMarkup backMenu = Mockito.mock(InlineKeyboardMarkup.class);
        File file = Mockito.mock(File.class);
        SendPhoto sendPhoto = Mockito.mock(SendPhoto.class);
        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.data()).thenReturn("/schedule");
        when(callbackQuery.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(123L);
        when(shelterMessage.keyboards(any())).thenReturn(backMenu);
        when(file.toURI()).thenReturn(Path.of("src/main/resources/photo.jpg").toUri());
        handler.handleUpdate(update);
        verify(telegramBot).execute(sendPhoto);
        verify(shelterMessage).sendButtonMessage(123L, telegramBot, ShelterInfoEnum.SCHEDULE_TEXT.getText(), backMenu);
    }

    @Test
    void testHandleUpdateWithPass() {
        CallbackQuery callbackQuery = Mockito.mock(CallbackQuery.class);
        Chat chat = Mockito.mock(Chat.class);
        Message message = Mockito.mock(Message.class);
        Update update = Mockito.mock(Update.class);
        InlineKeyboardMarkup backMenu = Mockito.mock(InlineKeyboardMarkup.class);
        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.data()).thenReturn("/pass");
        when(callbackQuery.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(123L);
        when(shelterMessage.keyboards(any())).thenReturn(backMenu);
        handler.handleUpdate(update);
        verify(shelterMessage).sendButtonMessage(123L, telegramBot, ShelterInfoEnum.PASS_TEXT.getText(), backMenu);
    }

    @Test
    void testHandleUpdateWithSecurity() {
        CallbackQuery callbackQuery = Mockito.mock(CallbackQuery.class);
        Chat chat = Mockito.mock(Chat.class);
        Message message = Mockito.mock(Message.class);
        Update update = Mockito.mock(Update.class);
        InlineKeyboardMarkup backMenu = Mockito.mock(InlineKeyboardMarkup.class);
        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.data()).thenReturn("/security");
        when(callbackQuery.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(123L);
        when(shelterMessage.keyboards(any())).thenReturn(backMenu);
        handler.handleUpdate(update);
        verify(shelterMessage).sendButtonMessage(123L, telegramBot, ShelterInfoEnum.SECURITY_TEXT.getText(), backMenu);
    }

    @Test
    void testHandleUpdateWithPersonalInfo() {
        CallbackQuery callbackQuery = Mockito.mock(CallbackQuery.class);
        Chat chat = Mockito.mock(Chat.class);
        Message message = Mockito.mock(Message.class);
        Update update = Mockito.mock(Update.class);
        InlineKeyboardMarkup backMenu = Mockito.mock(InlineKeyboardMarkup.class);
        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.data()).thenReturn("/personal_info");
        when(callbackQuery.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(123L);
        when(shelterMessage.keyboards(any())).thenReturn(backMenu);
        handler.handleUpdate(update);
        verify(shelterMessage).sendButtonMessage(123L, telegramBot, ShelterInfoEnum.PERSONAL_INFO_TEXT.getText(), backMenu);
    }
}