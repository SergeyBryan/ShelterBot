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

    protected final String CAT_SHELTER = "Приют для кошек";
    protected final String DOG_SHELTER = "Приют для собак";
    protected final String MENU = "Выберите Ваш запрос в меню";
    protected final String INFO = "Узнать информацию о приюте";
    protected final String HOW_TO_TAKE_A_PET = "Как взять животное из приюта";
    protected final String PET_REPORT = "Прислать отчет о питомце";
    protected final String CALL_A_VOLUNTEER = "Позвать волонтера";
    protected final List<String> menuList = (List.of(INFO, HOW_TO_TAKE_A_PET, PET_REPORT, CALL_A_VOLUNTEER));

    protected TelegramBot telegramBot;
    protected ShelterMessageImpl shelterMessage;

    public AbstractHandler(TelegramBot telegramBot, ShelterMessageImpl shelterMessage) {
        this.telegramBot = telegramBot;
        this.shelterMessage = shelterMessage;
    }
}
