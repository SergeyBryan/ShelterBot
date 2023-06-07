package com.example.shelterbot.handlers;

import com.example.shelterbot.message.ShelterMessageImpl;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class DefaultHandlerTest {

    @Mock
    private TelegramBot telegramBot;

    @Mock
    private ShelterMessageImpl shelterMessage;

    private DefaultHandler defaultHandler;

    @Test
    void appliesTo_returnsTrue() {
        defaultHandler = new DefaultHandler(telegramBot, shelterMessage);
        Update update = mock(Update.class);
        assert defaultHandler.appliesTo(update);
    }

    @Test
    void handleUpdate_sendsMessageToChat() {
        MockitoAnnotations.openMocks(this);
        defaultHandler = new DefaultHandler(telegramBot, shelterMessage);
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(123456789L);
        defaultHandler.handleUpdate(update);
        verify(telegramBot).execute(new SendMessage(123456789L, "Позвать волонтера"));
    }
}