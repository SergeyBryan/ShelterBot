package com.example.shelterbot.handlers;

import com.example.shelterbot.message.ShelterMessageImpl;
import com.example.shelterbot.service.UserService;
import com.pengrad.telegrambot.TelegramBot;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Это абстрактный класс, который внедряет интерфейс TelegramHandler
 * Аннотация @Component показывает, что он является компонентом Spring и может автоматически сконфигурирован и внедрён в другие компоненты.
 * Класс содержит два поля для наследников: telegramBot и shelterMessage, которые переданы в конструктор
 * Класс также содержит методы, которые должны быть реализованы в классах-наследниках.
 * Эти методы отвечают за обработку входящих сообщений и отправку ответов.
 */

@Component
public abstract class AbstractHandler implements TelegramHandler {

    protected static final String CAT_SHELTER = "Приют для кошек";
    protected static final String DOG_SHELTER = "Приют для собак";

    protected static final String MENU = "Выберите Ваш запрос в меню";
    protected static final String INFO = "Узнать информацию о приюте";
    protected static final String HOW_TO_TAKE_A_PET = "Как взять животное из приюта";
    protected static final String PET_REPORT = "Прислать отчет о питомце";
    protected static final String CALL_A_VOLUNTEER = "Позвать волонтера";
    protected static final String BACK = "Назад";
    protected static final List<String> MENU_LIST = (List.of(INFO, HOW_TO_TAKE_A_PET,
//            PET_REPORT,
            CALL_A_VOLUNTEER));

    protected static final String INFORMATION = "Информация о приюте";
    protected static final String BACK_MENU = "Вернуться в информационное меню";
    protected static final String SCHEDULE = "Расписание о приюте";
    protected static final String PASS = "Оформить пропуск на машину";
    protected static final String SECURITY = "Правила безопасности";
    protected static final String PERSONAL_INFO = "Оставить контактные данные";
    protected static final List<String> ASSISTANCE_LIST = List.of(INFORMATION, SCHEDULE, PASS, SECURITY, PERSONAL_INFO, BACK_MENU);


    protected TelegramBot telegramBot;
    protected ShelterMessageImpl shelterMessage;

    public AbstractHandler(TelegramBot telegramBot, ShelterMessageImpl shelterMessage) {
        this.telegramBot = telegramBot;
        this.shelterMessage = shelterMessage;
    }
}
