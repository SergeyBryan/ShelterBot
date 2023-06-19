package com.example.shelterbot.service.impl;

import com.example.shelterbot.model.Report;
import com.example.shelterbot.repository.ReportsRepository;
import com.example.shelterbot.service.ReportsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportsServiceImpl implements ReportsService {
    private final ReportsRepository reportsRepository;

    public ReportsServiceImpl(ReportsRepository reportsRepository) {
        this.reportsRepository = reportsRepository;
    }

    @Override
    public Report save(Report report) {
        return reportsRepository.save(report);
    }

    @Override
    public List<Report> getAll() {
        return reportsRepository.findAll();
    }

    @Override
    public Report getByUserId(Long id) {
        return reportsRepository.getByUserOwner_Id(id);
    }

}
