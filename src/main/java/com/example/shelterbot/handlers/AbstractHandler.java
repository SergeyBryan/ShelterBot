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
    protected static final String INFORMATION = "Информация о приюте";
    protected static final String BACK_MENU = "Вернуться в информационное меню";
    protected static final String SCHEDULE = "Расписание о приюте";
    protected static final String PASS = "Оформить пропуск на машину";
    protected static final String SECURITY = "Правила безопасности";
    protected static final String PUT_CONTACTS = "Оставить контактные данные";
    protected static final List<String> ASSISTANCE_LIST = List.of(INFORMATION, SCHEDULE, PASS, SECURITY, PUT_CONTACTS, BACK_MENU);
    protected static final String INSTRUCTION_MEETING = "Правила знакомства с животным";
    protected static final String DOC_LIST = "Список документов";
    protected static final String TRANSPORTATION = "Транспортировка";
    protected static final String HOUSE_RECOM_FOR_PUPPY = "Обустр. дома щенок";
    protected static final String HOUSE_RECOM_FOR_KITY = "Обустр. дома котенок";
    protected static final String HOUSE_RECOM_FOR_ADULT = "Обустр. дома взрослый";
    protected static final String HOUSE_RECOM_FOR_INVALID = "Обустр. дома инвалид";
    protected static final String RECOM_FROM_CYNOLOGIST = "Советы кинолога";
    protected static final String CYNOLOGIST_LIST = "Список кинологов";
    protected static final String RESTRICTIONS = "Список причин отказа";

    protected static final List<String> INSTRUCTION_LIST = List.of( INSTRUCTION_MEETING,
                                                                    DOC_LIST,
                                                                    TRANSPORTATION,
                                                                    HOUSE_RECOM_FOR_PUPPY,
                                                                    HOUSE_RECOM_FOR_KITY,
                                                                    HOUSE_RECOM_FOR_ADULT,
                                                                    HOUSE_RECOM_FOR_INVALID,
                                                                    RECOM_FROM_CYNOLOGIST,
                                                                    CYNOLOGIST_LIST,
                                                                    RESTRICTIONS,
                                                                    BACK_MENU);


    protected TelegramBot telegramBot;
    protected ShelterMessageImpl shelterMessage;

    public AbstractHandler(TelegramBot telegramBot, ShelterMessageImpl shelterMessage) {
        this.telegramBot = telegramBot;
        this.shelterMessage = shelterMessage;
    }
}
