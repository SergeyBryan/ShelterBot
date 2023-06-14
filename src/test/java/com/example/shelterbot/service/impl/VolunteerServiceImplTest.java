package com.example.shelterbot.service.impl;

import com.example.shelterbot.exceptions.NotFoundException;
import com.example.shelterbot.model.Volunteer;
import com.example.shelterbot.repository.VolunteerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class VolunteerServiceImplTest {
    @Mock
    private VolunteerRepository volunteerRepository;

    @InjectMocks
    private VolunteerServiceImpl volunteerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveTest() {
        Volunteer volunteer = new Volunteer();
        when(volunteerRepository.save(volunteer)).thenReturn(volunteer);
        Volunteer savedVolunteer = volunteerService.save(volunteer);
        Assertions.assertEquals(volunteer, savedVolunteer);
        verify(volunteerRepository, times(1)).save(volunteer);
    }

    @Test
    void getByIdTest() throws NotFoundException {
        Volunteer volunteer = new Volunteer();
        volunteer.setId(1L);
        Optional<Volunteer> optionalVolunteer = Optional.of(volunteer);
        when(volunteerRepository.findById(1)).thenReturn(optionalVolunteer);
        Volunteer foundVolunteer = volunteerService.getById(1);
        Assertions.assertEquals(volunteer, foundVolunteer);
        verify(volunteerRepository, times(1)).findById(1);
    }

    @Test
    void getByIdNotFoundExceptionTest() {
        Optional<Volunteer> optionalVolunteer = Optional.empty();
        when(volunteerRepository.findById(1)).thenReturn(optionalVolunteer);
        Assertions.assertThrows(NotFoundException.class, () -> volunteerService.getById(1));
        verify(volunteerRepository, times(1)).findById(1);
    }

    @Test
    void getAllTest() {
        List<Volunteer> volunteerList = new ArrayList<>();
        Volunteer volunteer1 = new Volunteer();
        volunteer1.setId(1L);
        Volunteer volunteer2 = new Volunteer();
        volunteer2.setId(2L);
        volunteerList.add(volunteer1);
        volunteerList.add(volunteer2);
        when(volunteerRepository.findAll()).thenReturn(volunteerList);
        List<Volunteer> foundVolunteerList = volunteerService.getAll();
        Assertions.assertEquals(volunteerList.size(), foundVolunteerList.size());
        Assertions.assertEquals(volunteerList.get(0), foundVolunteerList.get(0));
        Assertions.assertEquals(volunteerList.get(1), foundVolunteerList.get(1));
        verify(volunteerRepository, times(1)).findAll();
    }

}