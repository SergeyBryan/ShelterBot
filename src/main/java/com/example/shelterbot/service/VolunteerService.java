package com.example.shelterbot.service;

import com.example.shelterbot.exceptions.NotFoundException;
import com.example.shelterbot.model.Volunteer;

import java.util.List;
import java.util.Optional;

public interface VolunteerService {
    Volunteer save(Volunteer volunteer);

    Volunteer getById(int id) throws NotFoundException;

    List<Volunteer> getAll();
}
