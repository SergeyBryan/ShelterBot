package com.example.shelterbot.message;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ShelterMessageImplTest {

    private ShelterMessageImpl shelterMessage;

    @Mock
    private TelegramBot telegramBot;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        shelterMessage = new ShelterMessageImpl();
        shelterMessage.setLogger(mock(Logger.class));
    }

    @Test
    void testKeyboardsWithSingleText() {
        InlineKeyboardMarkup keyboardMarkup = shelterMessage.keyboards("Test");
        assertEquals(1, keyboardMarkup.inlineKeyboard().length);
        assertEquals("Test", keyboardMarkup.inlineKeyboard()[0][0].text());
    }

    @Test
    void testKeyboardsWithMultipleTexts() {
        InlineKeyboardMarkup keyboardMarkup = shelterMessage.keyboards("Test1", "Test2");
        assertEquals(2, keyboardMarkup.inlineKeyboard().length);
        assertEquals("Test1", keyboardMarkup.inlineKeyboard()[0][0].text());
        assertEquals("Test2", keyboardMarkup.inlineKeyboard()[1][0].text());
    }

    @Test
    void testSendMessage() {
        long id = 12345L;
        String message = "Test message";
        SendResponse sendResponse = mock(SendResponse.class);
        when(telegramBot.execute(any(SendMessage.class))).thenReturn(sendResponse);

        shelterMessage.sendMessage(id, telegramBot, message);

        verify(telegramBot, times(1)).execute(any(SendMessage.class));
        verify(shelterMessage.getLogger(), never()).error(anyString());
    }

    @Test
    void testSendMessageWithError() {
        long id = 12345L;
        String message = "Test message";
        SendResponse sendResponse = mock(SendResponse.class);
        when(telegramBot.execute(any(SendMessage.class))).thenReturn(sendResponse);

        shelterMessage.sendMessage(id, telegramBot, message);

        verify(telegramBot, times(1)).execute(any(SendMessage.class));
        verify(shelterMessage.getLogger(), times(1)).error("Error during sending message {}", sendResponse.description());
    }

    @Test
    void testSendButtonMessage() {
        long id = 12345L;
        String message = "Test message";
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        SendResponse sendResponse = mock(SendResponse.class);
        when(telegramBot.execute(any(SendMessage.class))).thenReturn(sendResponse);

        shelterMessage.sendButtonMessage(id, telegramBot, message, keyboardMarkup);

        verify(telegramBot, times(1)).execute(any(SendMessage.class));
        verify(shelterMessage.getLogger(), never()).error(anyString());
    }

    @Test
    void testSendButtonMessageWithError() {
        long id = 12345L;
        String message = "Test message";
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        SendResponse sendResponse = mock(SendResponse.class);
        when(telegramBot.execute(any(SendMessage.class))).thenReturn(sendResponse);

        shelterMessage.sendButtonMessage(id, telegramBot, message, keyboardMarkup);

        verify(telegramBot, times(1)).execute(any(SendMessage.class));
        verify(shelterMessage.getLogger(), times(1)).error("Error during sending message {}", sendResponse.description());
    }
}