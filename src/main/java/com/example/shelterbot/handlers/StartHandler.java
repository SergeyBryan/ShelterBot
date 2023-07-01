package com.example.shelterbot.handlers;

import com.example.shelterbot.message.ShelterMessageImpl;
import com.example.shelterbot.model.User;
import com.example.shelterbot.model.Volunteer;
import com.example.shelterbot.service.UserService;
import com.example.shelterbot.service.VolunteerService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Обработчик команды /start, который применяется только к обновлению, содержащему данную команду.
 * Отправляет пользователю приветственное сообщение.
 */
@Component
@Order(1)
@Slf4j
public class StartHandler extends AbstractHandler {

    private final UserService userService;
    private final VolunteerService volunteerService;

    /**
     * Создает экземпляр класса StartHandler.
     *
     * @param telegramBot    бот, который будет обрабатывать сообщения.
     * @param shelterMessage объект, который предоставляет методы для работы с сообщением ShelterMessage.
     * @param userService объект, который предоставляет методы для работы с пользователями.
     * @param volunteerService объект, который предоставляет методы для работы с волонтерами.
     */
    public StartHandler(TelegramBot telegramBot, ShelterMessageImpl shelterMessage, UserService userService, VolunteerService volunteerService) {
        super(telegramBot, shelterMessage);
        this.userService = userService;
        this.volunteerService = volunteerService;
    }

    /**
     * Проверяет, применим ли обработчик к данному обновлению в чате.
     *
     * @param update обновление в чате.
     * @return true, если текст сообщения содержит команду /start, в противном случае - false.
     */
    @Override
    public boolean appliesTo(Update update) {
        if (update.callbackQuery() == null) {
            log.info("Processing appliesTo StartHandler: {}", update.message().text() + " from " + update.message().from());
            boolean result = update.message().text() != null && update.message().text().equals("/start");
            log.info("Processing appliesTo StartHandler: return " + result);
            return result;
        } else {
            return false;
        }
    }

    /**
     * Обрабатывает обновление в чате путем отправки пользователю приветственного сообщения.
     *
     * @param update обновление в чате.
     */
    @Override
    public void handleUpdate(Update update) {
        long chatId = update.message().chat().id();
        var userName = update.message().chat().firstName();
        InlineKeyboardMarkup inlineKeyboardMarkup = shelterMessage.keyboards("Приют для кошек", "Приют для собак");
        User userByChatId = userService.getUserByChatId(chatId);
        Volunteer volunteerByChatId = volunteerService.getVolunteerByChatId(chatId);
        if (userByChatId != null || volunteerByChatId != null) {
            shelterMessage.sendButtonMessage(
                    chatId,
                    telegramBot,
                    "Привет "
                            + userName
                            + ". Я помогу тебе выбрать приют",
                    inlineKeyboardMarkup);
        } else {
            User user = new User();
            user.setName(userName);
            user.setChatId(chatId);
            userService.save(user);

            String helloMessage = """
                     Я бот, который сможет отвечать на популярные вопросы людей о том, что нужно знать и уметь, чтобы забрать животное из приюта.
                    Также я могу принимать ежедневные отчеты новых хозяев о том, как животное приспосабливается к новой обстановке
                    А сейчас, я помогу тебе выбрать приют""";
            shelterMessage.sendButtonMessage(
                    chatId,
                    telegramBot,
                    "Привет "
                            + userName
                            + helloMessage,
                    inlineKeyboardMarkup);
        }
    }
}
