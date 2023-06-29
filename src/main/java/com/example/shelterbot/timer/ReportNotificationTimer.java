package com.example.shelterbot.timer;

import com.example.shelterbot.message.ShelterMessage;
import com.example.shelterbot.model.Report;
import com.example.shelterbot.model.User;
import com.example.shelterbot.repository.ReportsRepository;
import com.example.shelterbot.service.UserService;
import com.pengrad.telegrambot.TelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ReportNotificationTimer {
    private final ReportsRepository reportsRepository;
    private final  ShelterMessage shelterMessage;
    private final TelegramBot telegramBot;
    private final UserService userService;

    public ReportNotificationTimer(ReportsRepository reportsRepository, ShelterMessage shelterMessage, TelegramBot telegramBot, UserService userService) {
        this.reportsRepository = reportsRepository;
        this.shelterMessage = shelterMessage;
        this.telegramBot = telegramBot;
        this.userService = userService;
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void notificationToSendReport() {
        checkDate();
    }

    public void checkDate() {
        var userList = userService.getAllAdoptedPetUser();
        log.info("Из БД получен список пользоателей которые усыновили питомцев " + userList);

        var reportList = reportsRepository.getAllToDayReport();
        log.info("Из БД получен список отчетов которые были отправлены сегодня " + reportList);

        var userFromReport = reportList
                .stream()
                .map(Report::getUserOwner).toList();
        userList.removeIf(userFromReport::contains);
        log.info("После сопоставления получен список пользователей не отправивших отчет " + userList);

        for (User user : userList) {
            shelterMessage.sendMessage(user.getChatId(), telegramBot, "Вы не отправили отчёт");
        }
    }
}


