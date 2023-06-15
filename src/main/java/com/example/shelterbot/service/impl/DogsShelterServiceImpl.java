package com.example.shelterbot.service.impl;

import com.example.shelterbot.exceptions.NotFoundException;
import com.example.shelterbot.model.DogsShelter;
import com.example.shelterbot.repository.DogsShelterRepository;
import com.example.shelterbot.service.DogsShelterService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DogsShelterServiceImpl implements DogsShelterService {
    private final DogsShelterRepository dogsShelterRepository;

    public DogsShelterServiceImpl(DogsShelterRepository dogsShelterRepository) {
        this.dogsShelterRepository = dogsShelterRepository;
    }

    @Override
    public DogsShelter save(DogsShelter dogsShelter) {
        return dogsShelterRepository.save(dogsShelter);
    }

    @Override
    public DogsShelter getById(int id) throws NotFoundException {
        Optional<DogsShelter> optionalDogsShelter =  dogsShelterRepository.findById(id);
        if (optionalDogsShelter.isPresent()) {
            return optionalDogsShelter.get();
        } else {
            throw new NotFoundException("Волонтер с ID " + id + " не найден");
        }
    }

    @Override
    public List<DogsShelter> getAll() {
        return dogsShelterRepository.findAll();
    }
}
