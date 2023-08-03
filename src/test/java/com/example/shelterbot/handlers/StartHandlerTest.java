package com.example.shelterbot.handlers;

import com.example.shelterbot.message.ShelterMessageImpl;
import com.example.shelterbot.service.UserService;
import com.example.shelterbot.service.VolunteerService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class StartHandlerTest {

    @Mock
    private TelegramBot telegramBot;
    @Mock
    private ShelterMessageImpl shelterMessage;
    @Mock
    private UserService userService;
    @Mock
    private VolunteerService volunteerService;
    private StartHandler startHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        startHandler = new StartHandler(telegramBot, shelterMessage, userService,  volunteerService);
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
    void handleUpdate_withStartCommand_shouldSendWelcomeMessageForNewUser() {
        var chatId = 123L;
        String firstName = "John";
        String helloMsg = "Привет " + firstName + " Я бот, который сможет отвечать на популярные вопросы людей о том, что нужно знать и уметь, чтобы забрать животное из приюта.\n" +
        "Также я могу принимать ежедневные отчеты новых хозяев о том, как животное приспосабливается к новой обстановке\n" +
        "А сейчас, я помогу тебе выбрать приют";
        InlineKeyboardMarkup keyboardMarkup = mock(InlineKeyboardMarkup.class);
        Message message = mock(Message.class);
        Update update = mock(Update.class);
        Chat chat = mock(Chat.class);

        when(update.message()).thenReturn(message);
        when(update.message().chat()).thenReturn(chat);
        when(update.message().chat().id()).thenReturn(chatId);
        when(update.message().chat().firstName()).thenReturn(firstName);
        when(update.message().from()).thenReturn(mock(User.class));
        when(update.message().from().firstName()).thenReturn(firstName);
        when(shelterMessage.keyboards(anyString(), anyString())).thenReturn(keyboardMarkup);
        when(userService.getUserByChatId(any())).thenReturn(null);
        startHandler.handleUpdate(update);

        verify(shelterMessage).sendButtonMessage(eq(chatId), eq(telegramBot), eq(helloMsg), eq(keyboardMarkup));
    }

    @Test
    void handleUpdate_withStartCommand_shouldSendWelcomeMessageForUser() {
        var chatId = 123L;
        String firstName = "John";
        String helloMsg = "Привет " + firstName + ". Я помогу тебе выбрать приют";
        InlineKeyboardMarkup keyboardMarkup = mock(InlineKeyboardMarkup.class);
        Message message = mock(Message.class);
        Update update = mock(Update.class);
        Chat chat = mock(Chat.class);

        when(update.message()).thenReturn(message);
        when(update.message().chat()).thenReturn(chat);
        when(update.message().chat().id()).thenReturn(chatId);
        when(update.message().chat().firstName()).thenReturn(firstName);
        when(update.message().from()).thenReturn(mock(User.class));
        when(update.message().from().firstName()).thenReturn(firstName);
        when(shelterMessage.keyboards(anyString(), anyString())).thenReturn(keyboardMarkup);
        when(userService.getUserByChatId(any())).thenReturn(mock(com.example.shelterbot.model.User.class));
        startHandler.handleUpdate(update);

        verify(shelterMessage).sendButtonMessage(eq(chatId), eq(telegramBot), eq(helloMsg), eq(keyboardMarkup));
    }
}