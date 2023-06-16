package com.example.shelterbot.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.exceptions.TelegramApiException;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class FileServiceImplTest {

    @Mock
    private TelegramBot telegramBot;

    private FileServiceImpl fileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fileService = new FileServiceImpl(telegramBot);
    }

    @Test
    void saveImage_shouldSaveImageToFileSystem() throws TelegramApiException, IOException {
        Message message = createMessageWithPhoto();
        File file = new File();
        file.setFileId("fileId");
        file.setFileUniqueId("fileUniqueId");
        when(telegramBot.execute(any(GetFile.class))).thenReturn(new GetFileResponse().setFile(file));
        when(telegramBot.getFileContent(file)).thenReturn(new byte[0]);

        String result = fileService.saveImage(message);

        FileTest
        Assertions.assertNotEquals("", result);
        Assertions.assertTrue(Files.exists(Path.of(result)));
    }

    @Test
    void saveImage_shouldReturnEmptyStringOnError() throws TelegramApiException {
        Message message = createMessageWithPhoto();
        when(telegramBot.execute(any(GetFile.class))).thenThrow(new TelegramApiException("Error"));
        String result = fileService.saveImage(message);
        Assertions.assertEquals("", result);
    }

    @Test
    void getImage_shouldReturnByteArray() {
        byte[] expected = new byte[]{1, 2, 3};
        String filePath = "test.txt";
        try {
            Files.write(Path.of(filePath), expected);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] result = fileService.getImage(filePath);
        Assertions.assertArrayEquals(expected, result);
    }

    private Message createMessageWithPhoto() {
        PhotoSize photoSize = new PhotoSize();
        photoSize.setFileId("fileId");
        photoSize.setFileSize(1024);
        photoSize.setHeight(100);
        photoSize.setWidth(100);
        return new Message().setPhoto(new org.telegram.telegrambots.meta.api.objects.PhotoSize[]{photoSize});
    }
}