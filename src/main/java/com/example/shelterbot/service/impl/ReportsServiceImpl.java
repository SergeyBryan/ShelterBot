package com.example.shelterbot.service.impl;

import com.example.shelterbot.model.Report;
import com.example.shelterbot.model.User;
import com.example.shelterbot.repository.ReportsRepository;
import com.example.shelterbot.service.FileService;
import com.example.shelterbot.service.ReportsService;
import com.example.shelterbot.service.UserService;
import com.pengrad.telegrambot.model.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportsServiceImpl implements ReportsService {
    private final ReportsRepository reportsRepository;

    private final FileService fileService;

    private final UserService userService;

    public ReportsServiceImpl(ReportsRepository reportsRepository, FileService fileService, UserService userService) {
        this.reportsRepository = reportsRepository;
        this.fileService = fileService;
        this.userService = userService;
    }

    @Override
    public Report save(Report report) {
        return reportsRepository.save(report);
    }

    @Override
    public Report save(Message message) {
        Long chatId = message.chat().id();
        String pathToPhoto = savePhoto(message);
        String text = message.text();

        User user = userService.getUserByChatId(String.valueOf(chatId));
        Report report = new Report(pathToPhoto, text, user, user.getPetID());
        return save(report);
    }

    @Override
    public List<Report> getAll() {
        return reportsRepository.findAll();
    }

    @Override
    public Report getByUserId(Long id) {
        return reportsRepository.getByUserOwner_Id(id);
    }

    @Override
    public String savePhoto(Message message) {
        return fileService.saveImage(message);
    }

}
