package com.example.shelterbot.handlers;

import com.example.shelterbot.message.ShelterMessageImpl;
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
    protected static final List<String> MENU_LIST = (List.of(INFO, HOW_TO_TAKE_A_PET, PET_REPORT, CALL_A_VOLUNTEER));


    protected TelegramBot telegramBot;
    protected ShelterMessageImpl shelterMessage;

    public AbstractHandler(TelegramBot telegramBot, ShelterMessageImpl shelterMessage) {
        this.telegramBot = telegramBot;
        this.shelterMessage = shelterMessage;
    }
}
