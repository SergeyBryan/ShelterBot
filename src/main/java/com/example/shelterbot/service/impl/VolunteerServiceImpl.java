package com.example.shelterbot.service.impl;

import com.example.shelterbot.exceptions.NotFoundException;
import com.example.shelterbot.model.Volunteer;
import com.example.shelterbot.repository.VolunteerRepository;
import com.example.shelterbot.service.VolunteerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VolunteerServiceImpl implements VolunteerService {

    private final VolunteerRepository volunteerRepository;

    public VolunteerServiceImpl(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    @Override
    public Volunteer save(Volunteer volunteer) {
        volunteerRepository.save(volunteer);
        return volunteer;
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
}
