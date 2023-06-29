package com.example.shelterbot.handlers;

import com.example.shelterbot.message.ShelterMessageImpl;
import com.example.shelterbot.model.Report;
import com.example.shelterbot.service.FileService;
import com.example.shelterbot.service.ReportsService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

public class ReportHandlerTest {

    @Mock
    ReportsService reportsService;
    @Mock
    TelegramBot telegramBot;
    @Mock
    ShelterMessageImpl shelterMessage;
    @Mock
    FileService fileService;
    ReportHandler reportHandler;

    private static final String EXAMPLE_REPORT = """
            Важно! Сообщение должно начинаться со слова 'Отчет'
            Пример отчета:
            Отчет:
            Рацион животного
            Общее самочувствие и привыкание к новому месту
            Изменение в поведении: отказ от старых привычек, приобретение новых""";

    private static final String RESPONSE = "Отчет принят, после проверки отчета, если будут вопросы с Вами свяжуться";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        reportHandler = new ReportHandler(reportsService, telegramBot, shelterMessage, fileService);
    }

    @Test
    public void appliesTo_withCallbackQueryAndCorrectData_returnsTrue() {
        Update update = mock(Update.class);
        CallbackQuery callbackQuery = mock(CallbackQuery.class);
        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(update.callbackQuery().data()).thenReturn("/Прислать отчет о питомце");

        assertTrue(reportHandler.appliesTo(update));
    }

    @Test
    public void appliesTo_withTextMessageAndCorrectText_returnsTrue() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("Отчет:");

        assertTrue(reportHandler.appliesTo(update));
    }

    @Test
    public void appliesTo_withTextMessageAndIncorrectText_returnsFalse() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("Некорректный текст");

        assertFalse(reportHandler.appliesTo(update));
    }

    @Test
    public void handleUpdate_withCallbackQuery_sendsMessageAndPhoto() throws IOException {
        var userID = 123L;
        Update update = mock(Update.class);
        CallbackQuery callbackQuery = mock(CallbackQuery.class);
        User user = mock(User.class);
        var examplePhoto = getImage();

        when(update.message()).thenReturn(null);
        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(update.callbackQuery().from()).thenReturn(user);
        when(update.callbackQuery().from().id()).thenReturn(userID);
        when(fileService.getImage(anyString())).thenReturn(examplePhoto);

        reportHandler.handleUpdate(update);

        verify(telegramBot).execute(argThat(argument -> {
            Map<String, Object> parameters = argument.getParameters();
            return parameters.containsKey("text")
                    && parameters.get("chat_id").equals(userID)
                    && parameters.get("text").equals(EXAMPLE_REPORT);

        }));
        verify(telegramBot).execute(argThat(argument -> {
            Map<String, Object> parameters = argument.getParameters();
            return parameters.containsKey("photo")
                    && parameters.get("chat_id").equals(userID)
                    && parameters.get("photo").equals(examplePhoto);

        }));


    }

    @Test
    public void handleUpdate_withTextMessage_callsReportsServiceAndSendsMessage() {
        var userID = 123L;
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);

        when(update.message()).thenReturn(message);
        when(update.message().chat()).thenReturn(chat);
        when(update.message().chat().id()).thenReturn(userID);

        reportHandler.handleUpdate(update);

        verify(reportsService, times(1)).save(any(Message.class));
        verify(telegramBot).execute(argThat(argument -> {
            Map<String, Object> parameters = argument.getParameters();
            return parameters.get("chat_id").equals(userID)
                    && parameters.get("text").equals(RESPONSE);

        }));

    }

    private byte[] getImage() throws IOException {
        java.io.File file = new java.io.File("src/main/resources/files/пример.jpeg");
        return Files.readAllBytes(file.toPath());
    }
}