package com.example.shelterbot.service.impl;

import com.example.shelterbot.exceptions.NotFoundException;
import com.example.shelterbot.model.Dogs;
import com.example.shelterbot.repository.DogsRepository;
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

class DogsServiceImplTest {
    @Mock
    private DogsRepository dogsRepository;

    @InjectMocks
    private DogsServiceImpl dogsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveTest() {
        Dogs dogs = new Dogs();
        when(dogsRepository.save(dogs)).thenReturn(dogs);
        Dogs savedDogs = dogsService.save(dogs);
        Assertions.assertEquals(dogs, savedDogs);
        verify(dogsRepository, times(1)).save(dogs);
    }

    @Test
    void getByIdTest() throws NotFoundException {
        Dogs dogs = new Dogs();
        dogs.setId(1L);
        Optional<Dogs> optionalDogs = Optional.of(dogs);
        when(dogsRepository.findById(1L)).thenReturn(optionalDogs);
        Dogs foundDogs = dogsService.getById(1L);
        Assertions.assertEquals(dogs, foundDogs);
        verify(dogsRepository, times(1)).findById(1L);
    }

    @Test
    void getByIdNotFoundExceptionTest() {
        Optional<Dogs> optionalDogs = Optional.empty();
        when(dogsRepository.findById(1L)).thenReturn(optionalDogs);
        Assertions.assertThrows(NotFoundException.class, () -> dogsService.getById(1));
        verify(dogsRepository, times(1)).findById(1L);
    }

    @Test
    void getAllTest() {
        List<Dogs> dogsList = new ArrayList<>();
        Dogs dogs1 = new Dogs();
        dogs1.setId(1L);
        Dogs dogs2 = new Dogs();
        dogs2.setId(2L);
        dogsList.add(dogs1);
        dogsList.add(dogs2);
        when(dogsRepository.findAll()).thenReturn(dogsList);
        List<Dogs> foundDogsList = dogsService.getAll();
        Assertions.assertEquals(dogsList.size(), foundDogsList.size());
        Assertions.assertEquals(dogsList.get(0), foundDogsList.get(0));
        Assertions.assertEquals(dogsList.get(1), foundDogsList.get(1));
        verify(dogsRepository, times(1)).findAll();
    }

}