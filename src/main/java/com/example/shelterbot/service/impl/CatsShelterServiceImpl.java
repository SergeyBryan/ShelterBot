package com.example.shelterbot.service.impl;

import com.example.shelterbot.exceptions.NotFoundException;
import com.example.shelterbot.model.CatsShelter;
import com.example.shelterbot.repository.CatsShelterRepository;
import com.example.shelterbot.service.CatsShelterService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatsShelterServiceImpl implements CatsShelterService {

    private final CatsShelterRepository catsShelterRepository;

    public CatsShelterServiceImpl(CatsShelterRepository catsShelterRepository) {
        this.catsShelterRepository = catsShelterRepository;
    }

    @Override
    public CatsShelter save(CatsShelter catsShelter) {
        return catsShelterRepository.save(catsShelter);
    }

    @Override
    public CatsShelter getById(int id) throws NotFoundException {
        Optional<CatsShelter> optionalCatsShelter =  catsShelterRepository.findById(id);
        if (optionalCatsShelter.isPresent()) {
            return optionalCatsShelter.get();
        } else {
            throw new NotFoundException("Приют для кошек с ID " + id + " не найден");
        }
    }

    @Override
    public List<CatsShelter> getAll() {
        return catsShelterRepository.findAll();
    }
}
