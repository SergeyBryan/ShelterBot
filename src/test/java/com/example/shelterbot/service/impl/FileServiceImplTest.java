package com.example.shelterbot.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.response.GetFileResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

public class FileServiceImplTest {

    private final TelegramBot telegramBot = mock(TelegramBot.class);
    private final FileServiceImpl fileService = new FileServiceImpl(telegramBot);

    @Value("${path.to.file.folder}")
    private static String filePath;

    @BeforeEach
    void setUp() throws IOException {
        when(telegramBot.getFileContent(mock(File.class))).thenReturn("Test image content".getBytes());
    }

    @Test
    void testSaveImage() {
        Message message = mock(Message.class);
        File file = mock(File.class);
        when(file.fileUniqueId()).thenReturn("test_file_id");
        when(message.photo()).thenReturn(new com.pengrad.telegrambot.model.PhotoSize[]{mock(com.pengrad.telegrambot.model.PhotoSize.class)});
        GetFileResponse getFileResponse = mock(GetFileResponse.class);
        when(getFileResponse.file()).thenReturn(file);
        when(telegramBot.execute(new GetFile("test_file_id"))).thenReturn(getFileResponse);
        String imagePath = fileService.saveImage(message);
        assertEquals(Path.of(filePath, "test_file_id").toString(), imagePath);
    }

    @Test
    void testGetImage() throws IOException {
        Path tempFilePath = Files.createTempFile("test_image", ".jpg");
        byte[] imageBytes = Files.readAllBytes(tempFilePath);
        byte[] resultBytes = fileService.getImage(tempFilePath.toString());
        Assertions.assertArrayEquals(imageBytes, resultBytes);
    }
}