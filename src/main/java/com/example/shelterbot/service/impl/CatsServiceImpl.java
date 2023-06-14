package com.example.shelterbot.service.impl;

import com.example.shelterbot.exceptions.NotFoundException;
import com.example.shelterbot.model.Cats;
import com.example.shelterbot.model.Volunteer;
import com.example.shelterbot.repository.CatsRepository;
import com.example.shelterbot.service.CatsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatsServiceImpl implements CatsService {

    private final CatsRepository catsRepository;

    public CatsServiceImpl(CatsRepository catsRepository) {
        this.catsRepository = catsRepository;
    }

    @Override
    public Cats save(Cats cats) {
        return catsRepository.save(cats);
    }

    @Override
    public Cats getById(int id) throws NotFoundException {
        Optional<Cats> optionalCats =  catsRepository.findById(id);
        if (optionalCats.isPresent()) {
            return optionalCats.get();
        } else {
            throw new NotFoundException("Кот с ID " + id + " не найден");
        }
    }

    @Override
    public List<Cats> getAll() {
        return catsRepository.findAll();
    }


}
