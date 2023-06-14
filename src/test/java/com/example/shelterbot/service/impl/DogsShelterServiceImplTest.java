package com.example.shelterbot.service.impl;

import com.example.shelterbot.exceptions.NotFoundException;
import com.example.shelterbot.model.DogsShelter;
import com.example.shelterbot.repository.DogsShelterRepository;
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

class DogsShelterServiceImplTest {
    @Mock
    private DogsShelterRepository dogsShelterRepository;

    @InjectMocks
    private DogsShelterServiceImpl dogsShelterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveTest() {
        DogsShelter dogsShelter = new DogsShelter();
        when(dogsShelterRepository.save(dogsShelter)).thenReturn(dogsShelter);
        DogsShelter savedDogsShelter = dogsShelterService.save(dogsShelter);
        Assertions.assertEquals(dogsShelter, savedDogsShelter);
        verify(dogsShelterRepository, times(1)).save(dogsShelter);
    }

    @Test
    void getByIdTest() throws NotFoundException {
        DogsShelter dogsShelter = new DogsShelter();
        dogsShelter.setId(1L);
        Optional<DogsShelter> optionalDogsShelter = Optional.of(dogsShelter);
        when(dogsShelterRepository.findById(1)).thenReturn(optionalDogsShelter);
        DogsShelter foundDogsShelter = dogsShelterService.getById(1);
        Assertions.assertEquals(dogsShelter, foundDogsShelter);
        verify(dogsShelterRepository, times(1)).findById(1);
    }

    @Test
    void getByIdNotFoundExceptionTest() {
        Optional<DogsShelter> optionalDogsShelter = Optional.empty();
        when(dogsShelterRepository.findById(1)).thenReturn(optionalDogsShelter);
        Assertions.assertThrows(NotFoundException.class, () -> dogsShelterService.getById(1));
        verify(dogsShelterRepository, times(1)).findById(1);
    }

    @Test
    void getAllTest() {
        List<DogsShelter> dogsShelterList = new ArrayList<>();
        DogsShelter dogsShelter1 = new DogsShelter();
        dogsShelter1.setId(1L);
        DogsShelter dogsShelter2 = new DogsShelter();
        dogsShelter2.setId(2L);
        dogsShelterList.add(dogsShelter1);
        dogsShelterList.add(dogsShelter2);
        when(dogsShelterRepository.findAll()).thenReturn(dogsShelterList);
        List<DogsShelter> foundDogsShelterList = dogsShelterService.getAll();
        Assertions.assertEquals(dogsShelterList.size(), foundDogsShelterList.size());
        Assertions.assertEquals(dogsShelterList.get(0), foundDogsShelterList.get(0));
        Assertions.assertEquals(dogsShelterList.get(1), foundDogsShelterList.get(1));
        verify(dogsShelterRepository, times(1)).findAll();
    }

}