package com.example.shelterbot.service.impl;

import com.example.shelterbot.model.Report;
import com.example.shelterbot.model.User;
import com.example.shelterbot.repository.ReportsRepository;
import com.example.shelterbot.service.FileService;
import com.example.shelterbot.service.ReportsService;
import com.example.shelterbot.service.UserService;
import com.pengrad.telegrambot.model.Message;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Сервис для работы с отчетами.
 */
@Service
public class ReportsServiceImpl implements ReportsService {
    private final ReportsRepository reportsRepository;

    private final FileService fileService;

    private final UserService userService;

    /**
     * Конструктор класса.
     * @param reportsRepository репозиторий отчетов
     * @param fileService сервис для работы с файлами
     * @param userService сервис для работы с пользователями
     */
    public ReportsServiceImpl(ReportsRepository reportsRepository, FileService fileService, UserService userService) {
        this.reportsRepository = reportsRepository;
        this.fileService = fileService;
        this.userService = userService;
    }

    /**
     * Сохраняет отчет в базу данных.
     * @param report отчет
     * @return сохраненный отчет
     */
    @Override
    public Report save(Report report) {
        return reportsRepository.save(report);
    }

    /**
     * Сохраняет отчет на основе сообщения пользователя сприсланным отчетом.
     *
     * @param message сообщение пользователя
     */
    @Override
    public void save(Message message) {
        var chatId = message.chat().id();
        User user = userService.getUserByChatId(chatId);
        String pathToPhoto = null;
        String text = null;

        if (message.text() != null) {
            text = message.text();
        } else if (message.caption() != null) {
            text = message.caption();
        }
        if (message.photo() != null) {
            pathToPhoto = savePhoto(message);
        }

        var report = reportsRepository.getToDayReportByUserChatId(chatId);
        if (report!=null) {
            var photoFromDB = report.getPetPhoto();
            var reportID = report.getId();
            if (photoFromDB == null) {
                reportsRepository.updatePhoto(pathToPhoto, reportID);
            } else {
                pathToPhoto += " " + photoFromDB;
                reportsRepository.updatePhoto(pathToPhoto, reportID);
            }
            if (report.getText() == null) {
                reportsRepository.updateText(text, reportID);
            }
        } else {
            Report reportToSave = new Report(pathToPhoto, text, user, user.getCat(), user.getDog());
            reportToSave.setCreatedTime(LocalDateTime.now());
            save(reportToSave);
        }
    }

    /**
     * Возвращает список всех отчетов.
     * @return список отчетов
     */
    @Override
    public List<Report> getAll() {
        return reportsRepository.findAll();
    }

    /**
     * Возвращает отчет по идентификатору пользователя.
     * @param id идентификатор пользователя
     * @return отчет
     */
    @Override
    public Report getByUserId(Long id) {
        return reportsRepository.getByUserOwnerId(id);
    }

    /**
     * Сохраняет фотографию из сообщения пользователя.
     * @param message сообщение пользователя
     * @return путь к сохраненной фотографии
     */
    @Override
    public String savePhoto(Message message) {
        return fileService.saveImage(message);
    }

    @Override
    public List<Report> getAllUncheckedReports() {
        return reportsRepository.getAllByIsCheckedIsFalse();
    }

    @Override
    public void checkReport(long reportID) {
        reportsRepository.updateIsChecked(reportID);
    }


}
