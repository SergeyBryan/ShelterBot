package com.example.shelterbot.service;

import com.example.shelterbot.exceptions.NotFoundException;
import com.example.shelterbot.model.CatsShelter;

import java.util.List;

public interface CatsShelterService {
    CatsShelter save(CatsShelter catsShelter);

    CatsShelter getById(int id) throws NotFoundException;

    List<CatsShelter> getAll();
}
