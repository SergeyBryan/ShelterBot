package com.example.shelterbot.service.impl;

import com.example.shelterbot.exceptions.NotFoundException;
import com.example.shelterbot.model.Cats;
import com.example.shelterbot.repository.CatsRepository;
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

public class CatsServiceImplTest {

    @Mock
    private CatsRepository catsRepository;

    @InjectMocks
    private CatsServiceImpl catsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveTest() {
        Cats cats = new Cats();
        when(catsRepository.save(cats)).thenReturn(cats);
        Cats savedCats = catsService.save(cats);
        Assertions.assertEquals(cats, savedCats);
        verify(catsRepository, times(1)).save(cats);
    }

    @Test
    void getByIdTest() throws NotFoundException {
        Cats cats = new Cats();
        cats.setId(1L);
        Optional<Cats> optionalCats = Optional.of(cats);
        when(catsRepository.findById(1L)).thenReturn(optionalCats);
        Cats foundCats = catsService.getById(1L);
        Assertions.assertEquals(cats, foundCats);
        verify(catsRepository, times(1)).findById(1L);
    }

    @Test
    void getByIdNotFoundExceptionTest() {
        Optional<Cats> optionalCats = Optional.empty();
        when(catsRepository.findById(1L)).thenReturn(optionalCats);
        Assertions.assertThrows(NotFoundException.class, () -> catsService.getById(1));
        verify(catsRepository, times(1)).findById(1L);
    }

    @Test
    void getAllTest() {
        List<Cats> catsList = new ArrayList<>();
        Cats cats1 = new Cats();
        cats1.setId(1L);
        Cats cats2 = new Cats();
        cats2.setId(2L);
        catsList.add(cats1);
        catsList.add(cats2);
        when(catsRepository.findAll()).thenReturn(catsList);
        List<Cats> foundCatsList = catsService.getAll();
        Assertions.assertEquals(catsList.size(), foundCatsList.size());
        Assertions.assertEquals(catsList.get(0), foundCatsList.get(0));
        Assertions.assertEquals(catsList.get(1), foundCatsList.get(1));
        verify(catsRepository, times(1)).findAll();
    }
}