package com.example.shelterbot.handlers;

import com.example.shelterbot.message.ShelterMessageImpl;
import com.example.shelterbot.service.FileService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(4)
public class ReportHandler extends AbstractHandler {

    private final FileService fileService;

    private static final String EXAMPLE_PHOTO = "src/main/resources/files/пример.jpeg";
    private static final String EXAMPLE_DIET = "Рацион животного";
    private static final String EXAMPLE_GENERAL_HEALTH = "Общее самочувствие и привыкание к новому месту";
    private static final String EXAMPLE_BEHAVIOR_CHANGE = "Изменение в поведении: отказ от старых привычек, приобретение новых";

    public ReportHandler(TelegramBot telegramBot, ShelterMessageImpl shelterMessage, FileService fileService) {
        super(telegramBot, shelterMessage);
        this.fileService = fileService;
    }

    @Override
    public boolean appliesTo(Update update) {
        if (update.callbackQuery() != null) {
            return (update.callbackQuery().data().equals("/" + PET_REPORT));
        }
        return false;
    }

    @Override
    public void handleUpdate(Update update) {

        if (update.message() == null) {

            var chatId = update.callbackQuery().from().id();
            SendMessage sendMessage = new SendMessage(chatId,
                    "Важно! Сообщение должно начинаться со слова 'Отчет'\n Пример отчета:\n" +
                            "Отчет:\n" +
                    EXAMPLE_DIET + "\n" +
                            EXAMPLE_GENERAL_HEALTH + "\n" +
                            EXAMPLE_BEHAVIOR_CHANGE);

            byte[] photo = fileService.getImage(EXAMPLE_PHOTO);
            SendPhoto sendPhoto = new SendPhoto(chatId, photo);

            telegramBot.execute(sendMessage);
            telegramBot.execute(sendPhoto);
        }
    }
}
