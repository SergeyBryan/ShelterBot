package com.example.shelterbot.handlers;

import com.example.shelterbot.message.ShelterMessageImpl;
import com.example.shelterbot.service.FileService;
import com.example.shelterbot.service.ReportsService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(4)
@Slf4j
public class ReportHandler extends AbstractHandler {

    private final ReportsService reportsService;

    private final FileService fileService;
    private static final String EXAMPLE_PHOTO = "src/main/resources/files/пример.jpeg";

    private static final String EXAMPLE_REPORT = """
    Важно! Сообщение должно начинаться со слова 'Отчет'
    Пример отчета:
    Отчет:
    Рацион животного
    Общее самочувствие и привыкание к новому месту
    Изменение в поведении: отказ от старых привычек, приобретение новых""";

    public ReportHandler(ReportsService reportsService,
                         TelegramBot telegramBot,
                         ShelterMessageImpl shelterMessage,
                         FileService fileService) {
        super(telegramBot, shelterMessage);
        this.fileService = fileService;
        this.reportsService = reportsService;
    }

    @Override
    public boolean appliesTo(Update update) {
        if (update.callbackQuery() != null) {
            log.info("Processing appliesTo ReportHandler: {}", update.callbackQuery().data());
            boolean result = update
                    .callbackQuery()
                    .data()
                    .equals("/" + PET_REPORT);
            log.info("Processing appliesTo ReportHandler: return " + result);
            return result;

        } else if (update.message() != null) {
            log.info("Processing appliesTo ReportHandler: {}", update.message());
            if (update.message().text() != null) {
                boolean result = update.message().text().toLowerCase().startsWith("отчет");
                log.info("Processing appliesTo ReportHandler: return " + result);
                return result;
            }
            boolean result = update.message().photo() != null;
            log.info("Processing appliesTo ReportHandler: return " + result);
            return result;
        }
        return false;
    }

    @Override
    public void handleUpdate(Update update) {
        if (update.message() == null) {
            var chatId = update.callbackQuery().from().id();
            SendMessage sendMessage = new SendMessage(chatId, EXAMPLE_REPORT);
            byte[] photo = fileService.getImage(EXAMPLE_PHOTO);
            SendPhoto sendPhoto = new SendPhoto(chatId, photo);
            telegramBot.execute(sendMessage);
            telegramBot.execute(sendPhoto);
        } else {
            reportsService.save(update.message());

            SendMessage sendMessage = new SendMessage(
                    update.message().chat().id(),
                    "Отчет принят, после проверки отчета, если будут вопросы с Вами свяжуться");
            telegramBot.execute(sendMessage);
        }
    }
}
