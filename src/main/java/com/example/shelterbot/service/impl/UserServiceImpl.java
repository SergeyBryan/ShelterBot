package com.example.shelterbot.service.impl;

import com.example.shelterbot.exceptions.NotFoundException;
import com.example.shelterbot.model.User;
import com.example.shelterbot.repository.UserRepository;
import com.example.shelterbot.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getById(int id) throws NotFoundException {
        Optional<User> optionalUser =  userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new NotFoundException("Волонтер с ID " + id + " не найден");
        }
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean extendTrialPeriod(int days, int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            LocalDateTime newTrialPeriod = user.get().getTrialPeriod().plusDays(days);
            userRepository.extendTrialPeriod(newTrialPeriod, String.valueOf(id));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User getUserByChatId(String chatId) {
        return userRepository.getUserByChatId(chatId);
    }
}
