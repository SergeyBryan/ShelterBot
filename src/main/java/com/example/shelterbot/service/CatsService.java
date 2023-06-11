package com.example.shelterbot.service;

import com.example.shelterbot.exceptions.NotFoundException;
import com.example.shelterbot.model.Cats;

import java.util.List;

public interface CatsService {
    Cats save(Cats cats);

    Cats getById(int id) throws NotFoundException;

    List<Cats> getAll();
}
