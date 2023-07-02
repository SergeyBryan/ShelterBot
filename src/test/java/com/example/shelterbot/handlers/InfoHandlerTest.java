package com.example.shelterbot.handlers;

import com.example.shelterbot.message.ShelterMessageImpl;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.*;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InfoHandlerTest {

    @Mock
    TelegramBot telegramBot;
    @Mock
    ShelterMessageImpl shelterMessage;
    @Mock
    MenuHandler menuHandler;
    @Mock
    Update update;
    @Mock
    CallbackQuery callbackQuery;
    @Mock
    Message message;
    @Mock
    Chat chat;
    @Mock
    User user;

    InfoHandler infoHandler;

    final String INFO = "Узнать информацию о приюте";
    final String HOW_TO_TAKE_A_PET = "Как взять животное из приюта";
    final String PET_REPORT = "Прислать отчет о питомце";
    final String CALL_A_VOLUNTEER = "Позвать волонтера";
    String MENU = "Выберите Ваш запрос в меню";
    List<String> menuList = (List.of(INFO, HOW_TO_TAKE_A_PET, PET_REPORT, CALL_A_VOLUNTEER));

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        infoHandler = new InfoHandler(telegramBot, shelterMessage, menuHandler);
    }

    @Test
    @DisplayName("Тест метода appliesTo с корректным update")
    void testAppliesToWithCorrectUpdate() {
        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.data()).thenReturn("/" + INFO);
        assertTrue(infoHandler.appliesTo(update));
    }

    @Test
    @DisplayName("Тест метода appliesTo с некорректным update")
    void testAppliesToWithIncorrectUpdate() {
        when(update.callbackQuery()).thenReturn(null);
        assertFalse(infoHandler.appliesTo(update));
    }

    @Test
    @DisplayName("Тест метода handleUpdate с запросом информации")
    void testHandleUpdateWithInfoRequest() {
        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(update.callbackQuery().data()).thenReturn("/" + INFO);
        when(update.callbackQuery().message()).thenReturn(message);
        when(update.callbackQuery().message().chat()).thenReturn(chat);
        when(update.callbackQuery().message().chat().id()).thenReturn(123L);
        InlineKeyboardMarkup inlineKeyboardMarkup = shelterMessage.keyboards("Назад");
        infoHandler.handleUpdate(update);
        verify(shelterMessage, times(1)).sendButtonMessage(123L, telegramBot, "Инфо: \n Здесь вы можете узнать как взять питомца из приюта", inlineKeyboardMarkup);
    }
}