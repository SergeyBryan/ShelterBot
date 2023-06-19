package com.example.shelterbot.service.impl;

import com.example.shelterbot.exceptions.NotFoundException;
import com.example.shelterbot.model.Cats;
import com.example.shelterbot.model.Dogs;
import com.example.shelterbot.model.Report;
import com.example.shelterbot.model.Volunteer;
import com.example.shelterbot.repository.VolunteerRepository;
import com.example.shelterbot.service.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VolunteerServiceImpl implements VolunteerService {

    private final VolunteerRepository volunteerRepository;

    private final UserService userService;

    private final ReportsService reportsService;

    private final CatsService catsService;

    private final DogsService dogsService;


    public VolunteerServiceImpl(VolunteerRepository volunteerRepository,
                                UserService userService,
                                ReportsService reportsService,
                                CatsService catsService,
                                DogsService dogsService) {
        this.volunteerRepository = volunteerRepository;
        this.userService = userService;
        this.reportsService = reportsService;
        this.catsService = catsService;
        this.dogsService = dogsService;
    }

    @Override
    public Volunteer save(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    @Override
    public Volunteer getById(int id) throws NotFoundException {
        Optional<Volunteer> optionalVolunteer =  volunteerRepository.findById(id);
        if (optionalVolunteer.isPresent()) {
            return optionalVolunteer.get();
        } else {
            throw new NotFoundException("Волонтер с ID " + id + " не найден");
        }
    }

    @Override
    public List<Volunteer> getAll() {
        return volunteerRepository.findAll();
    }

    @Override
    public void extendTrialPeriod(int days, int id) {
        userService.extendTrialPeriod(days, id);
    }

    @Override
    public List<Report> getAllReports() {
        return reportsService.getAll();
    }

    @Override
    public Report getReportByUserId(int id) {
        return reportsService.getByUserId((long) id);
    }

    @Override
    public Cats addCatInShelter(Cats cats) {
        return catsService.save(cats);
    }

    @Override
    public Dogs addDogInShelter(Dogs dogs) {
        return dogsService.save(dogs);
    }
}
