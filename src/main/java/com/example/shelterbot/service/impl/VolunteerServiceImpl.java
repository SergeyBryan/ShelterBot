package com.example.shelterbot.service.impl;

import com.example.shelterbot.exceptions.NotFoundException;
import com.example.shelterbot.model.*;
import com.example.shelterbot.model.enums.PetType;
import com.example.shelterbot.repository.VolunteerRepository;
import com.example.shelterbot.service.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с волонтерами.
 */
@Service
public class VolunteerServiceImpl implements VolunteerService {

    private final VolunteerRepository volunteerRepository;

    private final UserService userService;

    private final ReportsService reportsService;

    private final CatsService catsService;

    private final DogsService dogsService;

    /**
     * Конструктор класса VolunteerServiceImpl.
     * @param volunteerRepository Репозиторий для работы с волонтерами.
     * @param userService Сервис для работы с пользователями.
     * @param reportsService Сервис для работы с отчетами.
     * @param catsService Сервис для работы с котами в приюте.
     * @param dogsService Сервис для работы с собаками в приюте.
     */
    public VolunteerServiceImpl(VolunteerRepository volunteerRepository,
                                UserService userService,
                                ReportsService reportsService,
                                CatsService catsService,
                                DogsService dogsService) {
        this.volunteerRepository = volunteerRepository;
        this.userService = userService;
        this.reportsService = reportsService;
        this.catsService = catsService;
        this.dogsService = dogsService;
    }

    /**
     * Метод для сохранения волонтера.
     * @param volunteer Волонтер для сохранения.
     * @return Сохраненный волонтер.
     */
    @Override
    public Volunteer save(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    /**
     * Метод для получения волонтера по его ID.
     * @param id ID волонтера.
     * @return Волонтер с заданным ID.
     * @throws NotFoundException Если волонтер с заданным ID не найден.
     */
    @Override
    public Volunteer getById(int id) throws NotFoundException {
        Optional<Volunteer> optionalVolunteer =  volunteerRepository.findById(id);
        if (optionalVolunteer.isPresent()) {
            return optionalVolunteer.get();
        } else {
            throw new NotFoundException("Волонтер с ID " + id + " не найден");
        }
    }
    /**
     * Метод для получения списка всех волонтеров.
     * @return Список всех волонтеров.
     */
    @Override
    public List<Volunteer> getAll() {
        return volunteerRepository.findAll();
    }

    /**
     * Метод для продления испытательного срока волонтера.
     * @param days Количество дней для продления.
     * @param id ID пользователя.
     */
    @Override
    public void extendTrialPeriod(int days, long id) {
        userService.extendTrialPeriod(days, id);
    }

    /**
     * Метод для получения списка всех отчетов.
     * @return Список всех отчетов.
     */
    @Override
    public List<Report> getAllReports() {
        return reportsService.getAll();
    }

    /**
     * Метод для получения отчета по ID пользователя.
     * @param id ID пользователя.
     * @return Отчет с заданным ID пользователя.
     */
    @Override
    public Report getReportByUserId(int id) {
        return reportsService.getByUserId((long) id);
    }

    /**
     * Метод для добавления кота в приют.
     * @param cats Кот для добавления.
     * @return Добавленный кот.
     */
    @Override
    public Cats addCatInShelter(Cats cats) {
        return catsService.save(cats);
    }

    /**
     * Метод для добавления собаки в приют.
     * @param dogs Собака для добавления.
     * @return Добавленная собака.
     */
    @Override
    public Dogs addDogInShelter(Dogs dogs) {
        return dogsService.save(dogs);
    }

    /**
     * Добавление питомца к пользователю.
     * @param petId идентификатор питомца
     * @param dogOrCat тип питомца (собака или кошка)
     * @param userId идентификатор пользователя
     * @return true, если операция выполнена успешно
     */
    @Override
    public boolean addPetToOwner(long petId, PetType dogOrCat, long userId) throws NotFoundException {
        userService.addPetToOwner(petId, dogOrCat, userId);
        return true;
    }

    @Override
    public void addVolunteer(long userid) throws NotFoundException {
        User user = userService.getById(userid);
        Volunteer volunteer = new Volunteer(user.getName(), user.getChatId(), user.getPhoneNum());
        volunteerRepository.save(volunteer);
        userService.deleteUser(userid);
    }

    @Override
    public List<Report> getAllUncheckedReports() {
        return reportsService.getAllUncheckedReports();
    }

    @Override
    public void checkReport(long reportID) {
        reportsService.checkReport(reportID);
    }

    @Override
    public Volunteer getVolunteerByChatId(long chatId) {
        return volunteerRepository.getVolunteerByChatId(chatId);
    }

}
