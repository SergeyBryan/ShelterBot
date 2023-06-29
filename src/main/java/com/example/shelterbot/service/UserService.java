package com.example.shelterbot.service;

import com.example.shelterbot.exceptions.NotFoundException;
import com.example.shelterbot.model.User;
import com.example.shelterbot.model.enums.PetType;

import java.util.List;

public interface UserService {
    User save(User user);

    User getById(long id) throws NotFoundException;

    List<User> getAll();

    boolean extendTrialPeriod(int days, long id);

    User getUserByChatId(long chatId);

    boolean addPetToOwner(long petId, PetType dogOrCat, long userId) throws NotFoundException;

    void deleteUser(long userid);

    List<User> getAllAdoptedPetUser();
}
