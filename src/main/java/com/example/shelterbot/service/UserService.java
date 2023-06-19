package com.example.shelterbot.service;

import com.example.shelterbot.exceptions.NotFoundException;
import com.example.shelterbot.model.User;

import java.util.List;

public interface UserService {
    User save(User user);

    User getById(int id) throws NotFoundException;

    List<User> getAll();

    boolean extendTrialPeriod(int days, int id);

    User getUserByChatId(String chatId);
}
