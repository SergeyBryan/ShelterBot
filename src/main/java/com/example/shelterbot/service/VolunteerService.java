package com.example.shelterbot.service;

import com.example.shelterbot.exceptions.NotFoundException;
import com.example.shelterbot.model.Cats;
import com.example.shelterbot.model.Dogs;
import com.example.shelterbot.model.Report;
import com.example.shelterbot.model.Volunteer;

import java.util.List;
import java.util.Optional;

public interface VolunteerService {
    Volunteer save(Volunteer volunteer);

    Volunteer getById(int id) throws NotFoundException;

    List<Volunteer> getAll();

    void extendTrialPeriod(int days, int id);

    List<Report> getAllReports();

    Report getReportByUserId(int id);

    Cats addCatInShelter(Cats cats);

    Dogs addDogInShelter(Dogs dogs);
}
