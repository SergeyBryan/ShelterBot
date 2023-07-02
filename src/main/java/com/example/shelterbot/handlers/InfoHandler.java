package com.example.shelterbot.handlers;

import com.example.shelterbot.message.ShelterMessageImpl;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * Обработчик запросов на получение информации о приюте для Telegram-бота приюта для кошек.
 * Обрабатывает запросы на получение информации о приюте, инструкции по взятию животного из приюта,
 * отправку отчета о питомце и вызов волонтера.
 */

@Component
@Order(3)
@Slf4j
public class InfoHandler extends AbstractHandler {


    private final MenuHandler menuHandler;

    private final static String INFO_TEXT = "Инфо: \n Здесь вы можете узнать как взять питомца из приюта";
    private final static String HOW_TO_TAKE_A_PET_TEXT = "Как взять: \n Здесь можно узнать в какие дни взять";
    private final static String PET_REPORT_TEXT = "Отчёт о питомце: \n Отчёт о вашем питомце здесь:";

    /**
     * Конструктор класса InfoHandler.
     *
     * @param telegramBot    экземпляр TelegramBot для отправки сообщений
     * @param shelterMessage экземпляр ShelterMessageImpl для формирования сообщений
     * @param menuHandler    экземпляр MenuHandler для обработки запросов на возврат к меню
     */
    public InfoHandler(TelegramBot telegramBot, ShelterMessageImpl shelterMessage, MenuHandler menuHandler) {
        super(telegramBot, shelterMessage);
        this.menuHandler = menuHandler;
    }

    /**
     * Проверяет, применим ли данный обработчик к данному обновлению.
     *
     * @param update обновление, которое нужно обработать
     * @return true, если обработчик применим, иначе false
     */
    @Override
    public boolean appliesTo(Update update) {
        if (update.callbackQuery() != null) {
            log.info("Processing appliesTo InfoHandler: {}", update.callbackQuery().data());
            boolean result = update.callbackQuery().data().equals("/" + INFO);
            log.info("Processing appliesTo InfoHandler: return " + result);
            return result;
        }
        return false;
    }


    /**
     * Обрабатывает обновление, отправляя пользователю запрошенную информацию.
     *
     * @param update обновление, которое нужно обработать
     */
    @Override
    public void handleUpdate(Update update) {
       String data = update.callbackQuery().data();
        long chatId = update.callbackQuery().message().chat().id();
        InlineKeyboardMarkup menu = shelterMessage.keyboards(INFORMATION,
                SCHEDULE, PASS, SECURITY, PUT_CONTACTS, CALL_A_VOLUNTEER, BACK);
        if (data != null) {
            switch (data) {
                case "/" + INFO -> shelterMessage.sendButtonMessage(chatId, telegramBot, INFO_TEXT, menu);
                case "/" + BACK -> menuHandler.handleUpdate(update);
            }
        }
    }
}
