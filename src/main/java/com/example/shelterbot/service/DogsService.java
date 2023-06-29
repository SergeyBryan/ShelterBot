package com.example.shelterbot.service;

import com.example.shelterbot.exceptions.NotFoundException;
import com.example.shelterbot.model.Dogs;

import java.util.List;

public interface DogsService {
    Dogs save(Dogs dogs);

    Dogs getById(long id) throws NotFoundException;

    List<Dogs> getAll();
}
