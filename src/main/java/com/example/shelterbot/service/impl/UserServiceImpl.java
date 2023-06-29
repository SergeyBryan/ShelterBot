package com.example.shelterbot.service.impl;

import com.example.shelterbot.exceptions.NotFoundException;
import com.example.shelterbot.model.Cats;
import com.example.shelterbot.model.Dogs;
import com.example.shelterbot.model.User;
import com.example.shelterbot.model.enums.PetType;
import com.example.shelterbot.repository.UserRepository;
import com.example.shelterbot.service.CatsService;
import com.example.shelterbot.service.DogsService;
import com.example.shelterbot.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса для работы с пользователями.
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final DogsService dogRepository;
    private final CatsService catRepository;

    /**
     * Конструктор класса.
     *
     * @param userRepository репозиторий пользователей
     * @param dogRepository
     * @param catRepository
     */
    public UserServiceImpl(UserRepository userRepository, DogsService dogRepository, CatsService catRepository) {
        this.userRepository = userRepository;
        this.dogRepository = dogRepository;
        this.catRepository = catRepository;
    }

    /**
     * Сохранение пользователя в базу данных.
     * @param user пользователь
     * @return сохраненный пользователь
     */
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Получение пользователя по его идентификатору.
     * @param id идентификатор пользователя
     * @return пользователь
     * @throws NotFoundException если пользователь не найден
     */
    @Override
    public User getById(long id) throws NotFoundException {
        Optional<User> optionalUser =  userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new NotFoundException("Волонтер с ID " + id + " не найден");
        }
    }

    /**
     * Получение списка всех пользователей.
     * @return список пользователей
     */
    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    /**
     * Продление испытательного периода пользователя на заданное количество дней.
     * @param days количество дней
     * @param id идентификатор пользователя
     * @return true, если операция выполнена успешно, false, если пользователь не найден
     */
    @Override
    public boolean extendTrialPeriod(int days, long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            LocalDateTime newTrialPeriod = user.get().getTrialPeriod().plusDays(days);
            userRepository.extendTrialPeriod(newTrialPeriod, String.valueOf(id));
            return true;
        } else {
            return false;
        }
    }

    /**
     * Получение пользователя по его идентификатору чата.
     * @param chatId идентификатор чата
     * @return пользователь
     */
    @Override
    public User getUserByChatId(long chatId) {
        return userRepository.getUserByChatId(chatId);
    }

    /**
     * Добавление питомца к пользователю.
     *
     * @param petId    идентификатор питомца
     * @param dogOrCat тип питомца (собака или кошка)
     * @param userId   идентификатор пользователя
     * @return true, если операция выполнена успешно
     */
    @Override
    public boolean addPetToOwner(long petId, PetType dogOrCat, long userId) throws NotFoundException {
        if (dogOrCat == PetType.DOG) {
            Dogs dog = dogRepository.getById(petId);
            userRepository.addDogToOwner(dog, userId);
            return true;
        } else if (dogOrCat == PetType.CAT) {
            Cats cat = catRepository.getById(petId);
            userRepository.addCatToOwner(cat, userId);
            return true;
        }
        return false;
    }

    @Override
    public void deleteUser(long userid) {
        userRepository.deleteById(userid);
    }

    @Override
    public List<User> getAllAdoptedPetUser() {
        return userRepository.getAllByCatIsNotNullAndDogIsNotNull();
    }


}
