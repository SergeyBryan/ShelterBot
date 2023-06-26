package com.example.shelterbot.handlers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.example.shelterbot.message.ShelterMessageImpl;
import com.example.shelterbot.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.Map;

class StartHandlerTest {

    @Mock
    private TelegramBot telegramBot;
    @Mock
    private ShelterMessageImpl shelterMessage;

    private StartHandler startHandler;
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startHandler = new StartHandler(userService, telegramBot, shelterMessage);
    }

    @Test
    void appliesTo_withStartCommand_shouldReturnTrue() {
        Message message = mock(Message.class);
        when(message.text()).thenReturn("/start");
        Update update = mock(Update.class);
        when(update.message()).thenReturn(message);

        boolean result = startHandler.appliesTo(update);

        assertTrue(result);
    }

    @Test
    void appliesTo_withoutStartCommand_shouldReturnFalse() {
        Message message = mock(Message.class);
        when(message.text()).thenReturn("Hello");
        Update update = mock(Update.class);
        when(update.message()).thenReturn(message);

        boolean result = startHandler.appliesTo(update);

        assertFalse(result);
    }

    @Test
    void handleUpdate_withStartCommand_shouldSendWelcomeMessage() {
        var chatId = 123L;
        String firstName = "John";
        Chat chat = mock(Chat.class);
        when(chat.id()).thenReturn(chatId);
        when(chat.firstName()).thenReturn(firstName);
        Message message = mock(Message.class);
        when(message.chat()).thenReturn(chat);
        Update update = mock(Update.class);
        when(update.message()).thenReturn(message);

        startHandler.handleUpdate(update);

        verify(telegramBot).execute(argThat(argument -> {
            Map<String, Object> parameters = argument.getParameters();
            return parameters.get("chat_id").equals(chatId) &&
                    parameters.get("text").equals("Привет " + firstName + ". Я помогу тебе выбрать приют");
        }));
    }
}