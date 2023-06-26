package com.example.shelterbot.handlers;

import com.example.shelterbot.message.ShelterMessageImpl;
import com.example.shelterbot.model.User;
import com.example.shelterbot.service.UserService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Обработчик команды /start, который применяется только к обновлению, содержащему данную команду.
 * Отправляет пользователю приветственное сообщение.
 */
@Component
@Order(1)
public class StartHandler extends AbstractHandler {

    private final UserService userService;

    /**
     * Создает экземпляр класса StartHandler.
     *
     * @param telegramBot    бот, который будет обрабатывать сообщения.
     * @param shelterMessage объект, который предоставляет методы для работы с сообщением ShelterMessage.
     */
    public StartHandler(UserService userService, TelegramBot telegramBot, ShelterMessageImpl shelterMessage) {
        super(telegramBot, shelterMessage);
        this.userService = userService;
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
            return update.message().text() != null && update.message().text().equals("/start");
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
        String strChatId = String.valueOf(chatId);
        var userName = update.message().chat().firstName();
        InlineKeyboardMarkup inlineKeyboardMarkup = shelterMessage.keyboards("Приют для кошек", "Приют для собак");

        User userByChatId = userService.getUserByChatId(strChatId);

        if (userByChatId != null) {
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
            user.setChatId(strChatId);
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
