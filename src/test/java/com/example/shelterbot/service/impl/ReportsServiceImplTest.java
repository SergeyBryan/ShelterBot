package com.example.shelterbot.service.impl;

import com.example.shelterbot.model.Report;
import com.example.shelterbot.repository.ReportsRepository;
import com.example.shelterbot.service.FileService;
import com.example.shelterbot.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class ReportsServiceImplTest {


    @Mock
    private ReportsRepository reportsRepository;

    @Mock
    private FileService fileService;

    @Mock
    private UserService userService;
    @InjectMocks
    private ReportsServiceImpl reportsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        reportsService = new ReportsServiceImpl(reportsRepository, fileService, userService);
    }

    @Test
    public void testSave() {
        Report report = new Report();
        when(reportsRepository.save(any())).thenReturn(report);

        Report savedReport = reportsService.save(new Report());

        assertEquals(report, savedReport);
    }

    @Test
    public void testGetAll() {
        List<Report> reports = new ArrayList<>();
        reports.add(new Report());
        reports.add(new Report());
        when(reportsRepository.findAll()).thenReturn(reports);

        List<Report> allReports = reportsService.getAll();

        assertEquals(2, allReports.size());
    }

    @Test
    public void testGetByUserId() {
        Report report = new Report();
        when(reportsRepository.getByUserOwnerId(any())).thenReturn(report);

        Report foundReport = reportsService.getByUserId(1L);

        assertEquals(report, foundReport);
    }

    @Test
    public void testSavePhoto() {
        String path = "path/to/photo";
        when(fileService.saveImage(any())).thenReturn(path);

        String savedPath = reportsService.savePhoto(null);

        assertEquals(path, savedPath);
    }
}