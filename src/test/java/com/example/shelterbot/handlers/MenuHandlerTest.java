package com.example.shelterbot.handlers;

import com.example.shelterbot.message.ShelterMessageImpl;
import com.example.shelterbot.model.enums.PetType;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static com.example.shelterbot.handlers.AbstractHandler.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuHandlerTest {

    @Mock
    private static TelegramBot telegramBot;
    @Mock
    private static ShelterMessageImpl shelterMessage;

    private MenuHandler menuHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        menuHandler = new MenuHandler(telegramBot, shelterMessage);
    }

    @Test
    @DisplayName("Testing appliesTo method with callbackQuery update data:/Приют для кошек")
    void testAppliesToWithCallbackQueryUpdate() {
        Update update = mock(Update.class);
        CallbackQuery callbackQuery = mock(CallbackQuery.class);

        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(update.callbackQuery().data()).thenReturn("/Приют для кошек");

        assertTrue(menuHandler.appliesTo(update));
    }
    @Test
    @DisplayName("Testing appliesTo method with callbackQuery update data:/Приют для собак")
    void testAppliesToWithCallbackQueryUpdateShouldTrue() {
        Update update = mock(Update.class);
        CallbackQuery callbackQuery = mock(CallbackQuery.class);

        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(update.callbackQuery().data()).thenReturn("/Приют для собак");

        assertTrue(menuHandler.appliesTo(update));
    }

    @Test
    @DisplayName("Testing appliesTo method with callbackQuery = null")
    void testAppliesToWithMessageUpdate() {
        Update update = mock(Update.class);
        assertFalse(menuHandler.appliesTo(update));
    }

    @Test
    @DisplayName("Testing appliesTo method with wrong callbackQuery update")
    void testAppliesToWithWrongCallbackQueryUpdate() {
        Update update = mock(Update.class);
        CallbackQuery callbackQuery = mock(CallbackQuery.class);

        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(update.callbackQuery().data()).thenReturn("/Wrong");

        assertFalse(menuHandler.appliesTo(update));
    }

    @Test
    @DisplayName("Testing handleUpdate method with callbackQuery update data:/Приют для кошек")
    void testHandleUpdateWithCallbackQueryUpdate() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        CallbackQuery callbackQuery = mock(CallbackQuery.class);
        Chat chat = mock(Chat.class);

        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(123L);
        when(callbackQuery.data()).thenReturn("/Приют для кошек");

        menuHandler.handleUpdate(update);

        verify(shelterMessage, times(1)).sendButtonMessage(123L, telegramBot, "Выберите Ваш запрос в меню",
                shelterMessage.keyboards(INFO, HOW_TO_TAKE_A_PET, PET_REPORT, CALL_A_VOLUNTEER));
        assertEquals(PetType.CAT, menuHandler.flag.get(123L));
    }

    @Test
    @DisplayName("Testing handleUpdate method with callbackQuery update data:/Приют для собак")
    void testHandleUpdateWithCallbackQueryUpdateShouldPutInHashMap() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        CallbackQuery callbackQuery = mock(CallbackQuery.class);
        Chat chat = mock(Chat.class);

        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(123L);
        when(callbackQuery.data()).thenReturn("/Приют для собак");

        menuHandler.handleUpdate(update);

        verify(shelterMessage, times(1)).sendButtonMessage(123L, telegramBot, "Выберите Ваш запрос в меню",
                shelterMessage.keyboards(INFO, HOW_TO_TAKE_A_PET, PET_REPORT, CALL_A_VOLUNTEER));
        assertEquals(PetType.DOG, menuHandler.flag.get(123L));
    }

}