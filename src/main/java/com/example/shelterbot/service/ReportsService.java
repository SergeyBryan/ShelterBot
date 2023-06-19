package com.example.shelterbot.service;

import com.example.shelterbot.model.Report;

import java.util.List;

public interface ReportsService {
    Report save(Report report);

    List<Report> getAll();

    Report getByUserId(Long id);
}
