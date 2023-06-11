package com.example.shelterbot.service;

import com.example.shelterbot.exceptions.NotFoundException;
import com.example.shelterbot.model.DogsShelter;

import java.util.List;

public interface DogsShelterService {
    DogsShelter save(DogsShelter dogsShelter);

    DogsShelter getById(int id) throws NotFoundException;

    List<DogsShelter> getAll();
}
