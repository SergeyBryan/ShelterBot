package com.example.shelterbot.timer;

import com.example.shelterbot.message.ShelterMessage;
import com.example.shelterbot.model.Report;
import com.example.shelterbot.repository.ReportsRepository;
import com.pengrad.telegrambot.TelegramBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class ReportNotificationTimer {
    private final ReportsRepository repository;
    private final ShelterMessage shelterMessage;
    private final TelegramBot telegramBot;
    private final Logger TIME_LOGGER = LoggerFactory.getLogger(ReportNotificationTimer.class);

    public ReportNotificationTimer(ReportsRepository repository, ShelterMessage shelterMessage, TelegramBot telegramBot) {
        this.repository = repository;
        this.shelterMessage = shelterMessage;
        this.telegramBot = telegramBot;
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void notificationToSendReport() {
        checkDate();
    }

    public void checkDate() {
        List<Report> reportList = repository.findAll();
        for (Report report : reportList) {
            if (
                    report.getCreatedTime().toLocalDate().equals(LocalDateTime.now().toLocalDate().minusDays(1))
            ) {
                shelterMessage.sendMessage(Long.parseLong(report.getUserOwner().getChatId()), telegramBot, "Уважаемый клиент нашего приюта,\nмы заметили, что ваш отчёт о питомце не был отправлен.\nОтправьте, пожалуйста, отчёт о питомце как можно скорее");
                TIME_LOGGER.warn("Нашлись неотправленные отчёты, напоминания были отправлены");
            } else {
                TIME_LOGGER.info("Неотправленных отчётов не нашлось");
            }
        }
    }
}


