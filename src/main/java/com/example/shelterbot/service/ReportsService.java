package com.example.shelterbot.service;

import com.example.shelterbot.model.Report;
import com.pengrad.telegrambot.model.Message;

import java.util.List;

public interface ReportsService {
    Report save(Report report);

    Report save(Message message);

    List<Report> getAll();

    Report getByUserId(Long id);

    String savePhoto(Message message);
}
