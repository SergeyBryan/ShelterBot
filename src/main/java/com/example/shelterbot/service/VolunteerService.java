package com.example.shelterbot.service;

import com.example.shelterbot.exceptions.NotFoundException;
import com.example.shelterbot.model.Cats;
import com.example.shelterbot.model.Dogs;
import com.example.shelterbot.model.Report;
import com.example.shelterbot.model.Volunteer;
import com.example.shelterbot.model.enums.PetType;

import java.util.List;

public interface VolunteerService {
    Volunteer save(Volunteer volunteer);

    Volunteer getById(int id) throws NotFoundException;

    List<Volunteer> getAll();

    void extendTrialPeriod(int days, long id);

    List<Report> getAllReports();

    Report getReportByUserId(int id);

    Cats addCatInShelter(Cats cats);

    Dogs addDogInShelter(Dogs dogs);

    boolean addPetToOwner(long petId, PetType dogOrCat, long userId) throws NotFoundException;

    void addVolunteer(long userid) throws NotFoundException;

    List<Report> getAllUncheckedReports();

    void checkReport(long reportID);

    Volunteer getVolunteerByChatId(long chatId);
}
