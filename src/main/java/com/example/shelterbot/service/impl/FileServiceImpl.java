package com.example.shelterbot.service.impl;

import com.example.shelterbot.service.FileService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class FileServiceImpl implements FileService {

    @Value("${path.to.file.folder}")
    private String filePath;

    private final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    private static final Marker ERROR_MARKER = MarkerFactory.getMarker("ERROR");

    private final TelegramBot telegramBot;

    public FileServiceImpl(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public String saveImage(Message message) {
        // todo пока обрабатывается только одно фото, необходимо реальзовать возможность принимать альбом
        logger.info("В метод saveImage передано: " + message.photo().length + " фотографий");

        String fileId = message.photo()[0].fileId();
        GetFile request = new GetFile(fileId);
        GetFileResponse getFileResponse = telegramBot.execute(request);
        File file = getFileResponse.file();

        String fileName = file.fileUniqueId();

        java.io.File image = new java.io.File(filePath + "/" + fileName);
        Path path = Path.of(filePath, fileName);

        try (FileOutputStream fos = new FileOutputStream(image)) {
            ByteArrayInputStream bis = new ByteArrayInputStream(telegramBot.getFileContent(file));
            Files.createFile(path);
            IOUtils.copy(bis, fos);
        } catch (IOException e) {
            logger.error(ERROR_MARKER,
                    "Ошибка при сохранении картинки от пользователя"
                            + e.getMessage()
                            + Arrays.toString(e.getStackTrace()),
                    e);
            return "";
        }
        return path.toString();
    }
}
