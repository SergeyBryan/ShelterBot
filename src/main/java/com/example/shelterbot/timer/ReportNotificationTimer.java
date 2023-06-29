package com.example.shelterbot.timer;

import com.example.shelterbot.message.ShelterMessage;
import com.example.shelterbot.model.Report;
import com.example.shelterbot.repository.ReportsRepository;
import com.pengrad.telegrambot.TelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class ReportNotificationTimer {
    private final ReportsRepository repository;
    private final  ShelterMessage shelterMessage;
    private final TelegramBot telegramBot;

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
        List<Report> reportList = repository.getAllByIsCheckedIsFalse();
        for (Report report : reportList) {
            if (report.isChecked()) {
                log.info("Processing checkDate ReportNotificationTimer " + report);
            } else {
                log.info("Отчет не проверен, будет отправлено уведомление пользователю: " + report.getUserOwner());
                shelterMessage.sendMessage(report.getUserOwner().getChatId(), telegramBot, "Вы не отправили отчёт");
            }
        }
    }
}


