package com.example.shelterbot.handlers;

import com.example.shelterbot.handlers.text.ShelterInfoEnum;
import com.example.shelterbot.message.ShelterMessageImpl;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendPhoto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;

/**
 * Обработчик для команд, связанных с информацией о приюте.
 * Реализует абстрактный класс AbstractHandler.
 * Имеет аннотации @Component и @Order(5).
 */
@Component
@Order(5)
@Slf4j
public class ShelterInfoHandler extends AbstractHandler {

    public ShelterInfoHandler(TelegramBot telegramBot, ShelterMessageImpl shelterMessage) {
        super(telegramBot, shelterMessage);
    }

    @Override
    public boolean appliesTo(Update update) {
        if (update.callbackQuery() != null) {
            log.info("Processing appliesTo ShelterInfoHandler: {}", update.callbackQuery().data());
            boolean result = ASSISTANCE_LIST.stream()
                    .map(s -> "/" + s)
                    .anyMatch(s -> s.equals(update.callbackQuery().data()));
            log.info("Processing appliesTo ShelterInfoHandler: return " + result);
            return result;
        }
        return false;
    }


    @Override
    public void handleUpdate(Update update) {
        String data = update.callbackQuery().data();
        long chatId = update.callbackQuery().message().chat().id();
        InlineKeyboardMarkup back_menu = shelterMessage.keyboards(BACK_MENU);
        InlineKeyboardMarkup menu = shelterMessage.keyboards(INFORMATION,
                SCHEDULE, PASS, SECURITY, PUT_CONTACTS, CALL_A_VOLUNTEER, BACK);
        if (data != null) {
            switch (data) {
                case "/" + BACK_MENU ->
                        shelterMessage.sendButtonMessage(chatId, telegramBot, ShelterInfoEnum.BACK_MENU.getText(), menu);
                case "/" + INFORMATION ->
                        shelterMessage.sendButtonMessage(chatId, telegramBot, ShelterInfoEnum.INFORMATION_TEXT.getText(), back_menu);
                case "/" + SCHEDULE -> {
                    sendPhoto(chatId);
                    shelterMessage.sendButtonMessage(chatId, telegramBot, ShelterInfoEnum.SCHEDULE_TEXT.getText(), back_menu);
                }
                case "/" + PASS ->
                        shelterMessage.sendButtonMessage(chatId, telegramBot, ShelterInfoEnum.PASS_TEXT.getText(), back_menu);
                case "/" + SECURITY ->
                        shelterMessage.sendButtonMessage(chatId, telegramBot, ShelterInfoEnum.SECURITY_TEXT.getText(), back_menu);
                case "/" + PUT_CONTACTS ->
                        shelterMessage.sendButtonMessage(chatId, telegramBot, ShelterInfoEnum.PERSONAL_INFO_TEXT.getText(), back_menu);
            }
        }
    }

    /**
     * Метод, который отправляет фото схемы проезда до приюта.
     * @param chatId идентификатор чата, в который нужно отправить фото.
     */
    private void sendPhoto(long chatId) {
        File file = new File(Path.of("src/main/resources/photo.jpg").toUri());
        SendPhoto sendPhoto = new SendPhoto(chatId, file);
        sendPhoto.caption("Схема проезда до нашего приюта");
        telegramBot.execute(sendPhoto);
    }
}


