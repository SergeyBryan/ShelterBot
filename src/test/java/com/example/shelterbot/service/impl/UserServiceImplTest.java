package com.example.shelterbot.service.impl;

import com.example.shelterbot.exceptions.NotFoundException;
import com.example.shelterbot.model.User;
import com.example.shelterbot.repository.UserRepository;
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

class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveTest() {
        User user = new User();
        when(userRepository.save(user)).thenReturn(user);
        User savedUser = userService.save(user);
        Assertions.assertEquals(user, savedUser);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void getByIdTest() throws NotFoundException {
        User user = new User();
        user.setId(1L);
        Optional<User> optionalUser = Optional.of(user);
        when(userRepository.findById(1L)).thenReturn(optionalUser);
        User foundUser = userService.getById(1);
        Assertions.assertEquals(user, foundUser);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getByIdNotFoundExceptionTest() {
        Optional<User> optionalUser = Optional.empty();
        when(userRepository.findById(1L)).thenReturn(optionalUser);
        Assertions.assertThrows(NotFoundException.class, () -> userService.getById(1));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getAllTest() {
        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);
        userList.add(user1);
        userList.add(user2);
        when(userRepository.findAll()).thenReturn(userList);
        List<User> foundUserList = userService.getAll();
        Assertions.assertEquals(userList.size(), foundUserList.size());
        Assertions.assertEquals(userList.get(0), foundUserList.get(0));
        Assertions.assertEquals(userList.get(1), foundUserList.get(1));
        verify(userRepository, times(1)).findAll();
    }

}