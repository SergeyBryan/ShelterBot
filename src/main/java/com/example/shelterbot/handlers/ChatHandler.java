package com.example.shelterbot.handlers;

import com.example.shelterbot.message.ShelterMessageImpl;
import com.example.shelterbot.model.Volunteer;
import com.example.shelterbot.service.VolunteerService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Обработчик чата для связи между пользователем и волонтером.
 * Позволяет пользователю вызвать волонтера и начать переписку с ним.
 * Также позволяет волонтеру отправлять сообщения пользователю и наоборот.
 */
@Component
@Order(6)
public class ChatHandler extends DefaultHandler{

    private final VolunteerService volunteerService;

    /**
     * Хэшмапа для хранения связи между пользователем и волонтером.
     * Ключ - айди пользователя, значение - айди волонтера.
     */
    final Map<Long, Long> chat = new HashMap<>();

    static Queue<Volunteer> volunteerQueue = new LinkedList<>();

    /**
     * Конструктор класса.
     *
     * @param telegramBot      объект TelegramBot для отправки сообщений.
     * @param shelterMessage   объект ShelterMessageImpl для работы с сообщениями.
     * @param volunteerService
     */
    public ChatHandler(TelegramBot telegramBot, ShelterMessageImpl shelterMessage, VolunteerService volunteerService) {
        super(telegramBot, shelterMessage);
        this.volunteerService = volunteerService;
    }

    /**
     * Метод для проверки, применим ли данный обработчик к переданному обновлению.
     * @param update объект Update для проверки.
     * @return true, если данный обработчик применим к переданному обновлению, иначе false.
     */
    @Override
    public boolean appliesTo(Update update) {
        boolean isReport = false;
        boolean isCallBackQueryEqualsVolunteer = false;
        if (update.message() != null) {
            isReport = update.message().text().toLowerCase().startsWith("отчет");
        } else if (update.callbackQuery() != null) {
            isCallBackQueryEqualsVolunteer = update.callbackQuery().data().equals("/" + CALL_A_VOLUNTEER);
        }

        return isCallBackQueryEqualsVolunteer || !isReport ;
    }

    /**
     * Метод для обработки переданного обновления.
     * Если пользователь вызвал волонтера, назначается свободный волонтер и записывается связь в хэшмапу.
     * Если пользователь или волонтер отправил текст, сообщение пересылается соответствующему собеседнику.
     * @param update объект Update для обработки.
     */
    @Override
    public void handleUpdate(Update update) {
//        Если пользователь нажал позвать волонтера назначаем ему волонтера
//        и записываем связь в Хэшмапу где ключ айди пользователя а значение айди волонтера
        if (update.callbackQuery()!=null) {
            if (update.callbackQuery().data().equals("/" + CALL_A_VOLUNTEER)){
                var volunteerID = freeVolunteerAppointment();
                var userID = update.callbackQuery().from().id();
                chat.put(userID, volunteerID);
                var userName = update.callbackQuery().from().firstName();

                SendMessage sendMessageToVolunteer = new SendMessage(
                        volunteerID,
                        "Пользователю " + userName + " нужна помощь, напишите приветсвенное сообщения для начала переписки");
                telegramBot.execute(sendMessageToVolunteer);

                SendMessage sendMessageToUser = new SendMessage(userID, "Вам назначен волонтер, скоро он с Вами свяжется");
                telegramBot.execute(sendMessageToUser);
            }

//            Если пользователь отправил текст в бота проверяем есть ли связь с волотером,
//            отправляем сообщение волотеру
        } else if (update.message().text() != null) {
            var userOrVolunteerID = update.message().chat().id();

            if (chat.containsKey(userOrVolunteerID)) {
                var volunteerID = chat.get(userOrVolunteerID);
                var msg = update.message().text();
                SendMessage sendMessage = new SendMessage(volunteerID, msg);
                telegramBot.execute(sendMessage);

//                Если волонтер отправил текст в бота проверяем есть ли связь с пользователем,
//                отправляем сообщение пользователю
            } else if (chat.containsValue(userOrVolunteerID)) {
                var userID = chat.entrySet()
                        .stream()
                        .filter(entry -> userOrVolunteerID.equals(entry.getValue()))
                        .map(Map.Entry::getKey)
                        .findFirst();
                if (userID.isPresent()) {
                    SendMessage sendMessage = new SendMessage(userID.get(), update.message().text());
                    telegramBot.execute(sendMessage);
                }

//                Если в хэшмапе не найдена связь отправляется сообщение
            } else {
                String msg = """
                        Вам не назначен волнтер для связи,
                        нажмите в меню позвать волонтера и после
                        этого сможете переписываться с сотрудниками приюта""";
                InlineKeyboardMarkup menu = shelterMessage.keyboards(CALL_A_VOLUNTEER);
                var chatId = update.message().chat().id();
                shelterMessage.sendButtonMessage(chatId, telegramBot, msg, menu);
            }
        }
    }

    private Long freeVolunteerAppointment() {
        if (volunteerQueue.isEmpty()) {
            List<Volunteer> allVolunteers = volunteerService.getAll();
            Collections.addAll(volunteerQueue, allVolunteers.toArray(new Volunteer[0]));
        }
        return volunteerQueue.poll().getChatId();
    }
}
