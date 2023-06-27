package com.example.shelterbot.timer;

import com.example.shelterbot.message.ShelterMessage;
import com.example.shelterbot.model.Report;
import com.example.shelterbot.repository.ReportsRepository;
import com.pengrad.telegrambot.TelegramBot;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class ReportNotificationTimer {
    ReportsRepository repository;
    ShelterMessage shelterMessage;
    TelegramBot telegramBot;

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
                System.out.println("Нашёлся");
                shelterMessage.sendMessage(report.getUserOwner().getChatId(), telegramBot, "Вы не отправили отчёт");
            } else {
                System.out.println("не нашёлся");
            }
        }
    }
}


