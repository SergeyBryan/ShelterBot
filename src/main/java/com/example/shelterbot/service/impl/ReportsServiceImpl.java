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
     * @param message сообщение пользователя
     * @return сохраненный отчет
     */
    @Override
    public Report save(Message message) {
        Long chatId = message.chat().id();
        String pathToPhoto = savePhoto(message);
        String text = message.text();

        User user = userService.getUserByChatId(String.valueOf(chatId));
        Report report = new Report(pathToPhoto, text, user, user.getPetID());
        report.setCreatedTime(LocalDateTime.now());
        return save(report);
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
        return reportsRepository.getByUserOwner_Id(id);
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

}
