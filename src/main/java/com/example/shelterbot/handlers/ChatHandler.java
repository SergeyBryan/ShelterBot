package com.example.shelterbot.handlers;

import com.example.shelterbot.message.ShelterMessageImpl;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Order(6)
public class ChatHandler extends DefaultHandler{

    static Map<Long, Long> chat = new HashMap<>();
    public ChatHandler(TelegramBot telegramBot, ShelterMessageImpl shelterMessage) {
        super(telegramBot, shelterMessage);
    }

    @Override
    public boolean appliesTo(Update update) {
        boolean isReport = false;
        boolean isCallBackQueryEqualsVolunteer = false;
        if (update.message() != null) {
            isReport = update.message().text().toLowerCase().startsWith("отчет");
        } else if (update.callbackQuery() != null) {
            isCallBackQueryEqualsVolunteer = update.callbackQuery().data().equals("/" + CALL_A_VOLUNTEER);
        }

        return isCallBackQueryEqualsVolunteer || isReport ;
    }

    @Override
    public void handleUpdate(Update update) {
//        Если пользователь нажал позвать волонтера назначаем ему волонтера
//        и записываем связь в Хэшмапу где ключ айди пользователя а значение айди волонтера
        if (update.callbackQuery().data().equals("/" + CALL_A_VOLUNTEER)) {
//            todo добавить назначение свободного волонтера
            var volunteerID = 493667033L;
            var userID = update.callbackQuery().from().id();
            chat.put(userID, volunteerID);
            var userName = update.callbackQuery().from().firstName();

            SendMessage sendMessageToVolunteer = new SendMessage(
                    volunteerID,
                    "Пользователю " + userName + " нужна помощь, напишите приветсвенное сообщения для начала переписки");
            telegramBot.execute(sendMessageToVolunteer);

            SendMessage sendMessageToUser = new SendMessage(userID, "Вам назначен волонтер, скоро он с Вами свяжется");
            telegramBot.execute(sendMessageToUser);

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
                SendMessage sendMessage = new SendMessage(update.message().chat().id(), """
                        Вам не назначен волнтер для связи,
                        нажмите в меню позвать волонтера и после
                        этого сможете переписываться с сотрудниками приюта""");
                telegramBot.execute(sendMessage);
            }
        }
    }
}
