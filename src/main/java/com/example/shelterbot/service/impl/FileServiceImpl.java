package com.example.shelterbot.service.impl;

import com.example.shelterbot.service.FileService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * Реализация интерфейса FileService для сохранения и загрузки файлов
 */
@Service
public class FileServiceImpl implements FileService {

    /**
     * Путь к папке, в которой будут сохраняться изображения
     */
    @Value("${path.to.file.folder}")
    private String filePath;

    /**
     * Логгер для записи действий и ошибок в лог-файл
     */
    private final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    /**
     * Маркер для обозначения ошибок в лог-файле
     */
    private static final Marker ERROR_MARKER = MarkerFactory.getMarker("ERROR");

    /**
     * Объект бота Telegram для получения содержимого файлов
     */
    private final TelegramBot telegramBot;

    public FileServiceImpl(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    /**
     * Метод сохранения изображения в файловую систему
     * @param message сообщение с изображением
     * @return путь к сохраненному изображению в файловой системе
     */
    @Override
    public String saveImage(Message message) {
        // todo пока обрабатывается только одно фото, необходимо реальзовать возможность принимать альбом
        logger.info("В метод saveImage передано: " + message.photo().length + " фотографий");

        String fileId = message.photo()[message.photo().length - 1].fileId();
        GetFile request = new GetFile(fileId);
        GetFileResponse getFileResponse = telegramBot.execute(request);
        File file = getFileResponse.file();


        String fileName = file.fileUniqueId() + "." + StringUtils.getFilenameExtension(file.filePath());
        Path path = Path.of(filePath, fileName);

        try {
            Files.write(path, telegramBot.getFileContent(file));

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

    /**
     * Метод загрузки изображения из файловой системы
     * @param filePath путь к файлу в файловой системе
     * @return массив байт изображения
     */
    @Override
    public byte[] getImage(String filePath) {
        byte[] result = new byte[0];
        try {
            java.io.File file = new java.io.File(filePath);
            result = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            logger.error(ERROR_MARKER,
                    "Ошибка загрузки изображения из системы"
                            + e.getMessage()
                            + Arrays.toString(e.getStackTrace()),
                    e);
        }
        return result;
    }
}
