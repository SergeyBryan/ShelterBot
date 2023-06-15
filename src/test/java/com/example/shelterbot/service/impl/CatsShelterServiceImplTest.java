package com.example.shelterbot.service.impl;

import com.example.shelterbot.exceptions.NotFoundException;
import com.example.shelterbot.model.CatsShelter;
import com.example.shelterbot.repository.CatsShelterRepository;
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

class CatsShelterServiceImplTest {
    @Mock
    private CatsShelterRepository catsShelterRepository;

    @InjectMocks
    private CatsShelterServiceImpl CatsShelterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveTest() {
        CatsShelter catsShelter = new CatsShelter();
        when(catsShelterRepository.save(catsShelter)).thenReturn(catsShelter);
        CatsShelter savedCatsShelter = CatsShelterService.save(catsShelter);
        Assertions.assertEquals(catsShelter, savedCatsShelter);
        verify(catsShelterRepository, times(1)).save(catsShelter);
    }

    @Test
    void getByIdTest() throws NotFoundException {
        CatsShelter catsShelter = new CatsShelter();
        catsShelter.setId(1L);
        Optional<CatsShelter> optionalCatsShelter = Optional.of(catsShelter);
        when(catsShelterRepository.findById(1)).thenReturn(optionalCatsShelter);
        CatsShelter foundCatsShelter = CatsShelterService.getById(1);
        Assertions.assertEquals(catsShelter, foundCatsShelter);
        verify(catsShelterRepository, times(1)).findById(1);
    }

    @Test
    void getByIdNotFoundExceptionTest() {
        Optional<CatsShelter> optionalCatsShelter = Optional.empty();
        when(catsShelterRepository.findById(1)).thenReturn(optionalCatsShelter);
        Assertions.assertThrows(NotFoundException.class, () -> CatsShelterService.getById(1));
        verify(catsShelterRepository, times(1)).findById(1);
    }

    @Test
    void getAllTest() {
        List<CatsShelter> CatsShelterList = new ArrayList<>();
        CatsShelter catsShelter = new CatsShelter();
        catsShelter.setId(1L);
        CatsShelter catsShelter2 = new CatsShelter();
        catsShelter2.setId(2L);
        CatsShelterList.add(catsShelter);
        CatsShelterList.add(catsShelter2);
        when(catsShelterRepository.findAll()).thenReturn(CatsShelterList);
        List<CatsShelter> foundCatsShelterList = CatsShelterService.getAll();
        Assertions.assertEquals(CatsShelterList.size(), foundCatsShelterList.size());
        Assertions.assertEquals(CatsShelterList.get(0), foundCatsShelterList.get(0));
        Assertions.assertEquals(CatsShelterList.get(1), foundCatsShelterList.get(1));
        verify(catsShelterRepository, times(1)).findAll();
    }
}