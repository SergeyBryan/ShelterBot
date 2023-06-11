package com.example.shelterbot.service.impl;

import com.example.shelterbot.exceptions.NotFoundException;
import com.example.shelterbot.model.Dogs;
import com.example.shelterbot.repository.DogsRepository;
import com.example.shelterbot.service.DogsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DogsServiceImpl implements DogsService {

    private final DogsRepository dogsRepository;

    public DogsServiceImpl(DogsRepository dogsRepository) {
        this.dogsRepository = dogsRepository;
    }

    @Override
    public Dogs save(Dogs dogs) {
        dogsRepository.save(dogs);
        return dogs;
    }

    @Override
    public Dogs getById(int id) throws NotFoundException {
        Optional<Dogs> optionalDogs =  dogsRepository.findById(id);
        if (optionalDogs.isPresent()) {
            return optionalDogs.get();
        } else {
            throw new NotFoundException("Волонтер с ID " + id + " не найден");
        }
    }

    @Override
    public List<Dogs> getAll() {
        return dogsRepository.findAll();
    }
}
