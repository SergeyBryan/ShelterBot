package com.example.shelterbot.handlers;
import com.example.shelterbot.message.ShelterMessageImpl;
import com.example.shelterbot.service.FileService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class ReportHandlerTest {

    @Mock
    private TelegramBot telegramBot;

    @Mock
    private ShelterMessageImpl shelterMessage;

    @Mock
    private FileService fileService;

    @InjectMocks
    private ReportHandler reportHandler;

    private static final String EXAMPLE_PHOTO = "src/main/resources/files/пример.jpeg";
    private static final String EXAMPLE_DIET = "Рацион животного";
    private static final String EXAMPLE_GENERAL_HEALTH = "Общее самочувствие и привыкание к новому месту";
    private static final String EXAMPLE_BEHAVIOR_CHANGE = "Изменение в поведении: отказ от старых привычек, приобретение новых";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void appliesTo_withCallbackQueryAndCorrectData_returnsTrue() {
        CallbackQuery callbackQuery = mock(CallbackQuery.class);
        when(callbackQuery.data()).thenReturn("/pet_report");
        Update update = mock(Update.class);
        when(update.callbackQuery()).thenReturn(callbackQuery);

        boolean result = reportHandler.appliesTo(update);

        Assertions.assertFalse(result);
    }

    @Test
    public void appliesTo_withCallbackQueryAndIncorrectData_returnsFalse() {
        CallbackQuery callbackQuery = mock(CallbackQuery.class);
        when(callbackQuery.data()).thenReturn("/other_command");
        Update update = mock(Update.class);
        when(update.callbackQuery()).thenReturn(callbackQuery);

        boolean result = reportHandler.appliesTo(update);

        Assertions.assertFalse(result);
    }

    @Test
    public void appliesTo_withMessage_returnsFalse() {
        Update update = mock(Update.class);
        when(update.message()).thenReturn(null);

        boolean result = reportHandler.appliesTo(update);

        Assertions.assertFalse(result);
    }

    @Test
    public void handleUpdate_withCallbackQuery_sendsMessageAndPhoto() {
        CallbackQuery callbackQuery = mock(CallbackQuery.class);
        when(callbackQuery.data()).thenReturn("/pet_report");
        when(callbackQuery.from().id()).thenReturn(123L);
        Update update = mock(Update.class);
        when(update.callbackQuery()).thenReturn(callbackQuery);

        byte[] photo = new byte[]{1,2,3};
        when(fileService.getImage(EXAMPLE_PHOTO)).thenReturn(photo);

        reportHandler.handleUpdate(update);

        verify(telegramBot).execute(new SendMessage(123, "Важно! Сообщение должно начинаться со слова 'Отчет'\n Пример отчета:\n" +
                "Отчет:\n" +
                EXAMPLE_DIET + "\n" +
                EXAMPLE_GENERAL_HEALTH + "\n" +
                EXAMPLE_BEHAVIOR_CHANGE));
        verify(telegramBot).execute(new SendPhoto(123, photo));
    }

    @Test
    public void handleUpdate_withMessage_doesNothing() {
        Update update = mock(Update.class);
        when(update.message()).thenReturn(mockMessage("some message"));

        reportHandler.handleUpdate(update);

        verify(telegramBot, never()).execute(any());
    }

    private com.pengrad.telegrambot.model.Message mockMessage(String s) {
        com.pengrad.telegrambot.model.Message message = mock(com.pengrad.telegrambot.model.Message.class);
        when(message.text()).thenReturn(s);
        return message;
    }
}