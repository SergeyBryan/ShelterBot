package com.example.shelterbot.handlers;

import com.example.shelterbot.handlers.text.InstructionsForCatsText;
import com.example.shelterbot.message.ShelterMessageImpl;
import com.example.shelterbot.model.enums.PetType;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(7)
@Slf4j
public class InstructionHandler extends AbstractHandler {

    private final ChatHandler chatHandler;
    private final MenuHandler menuHandler;
    private boolean isFirstMenuSelection;
    private boolean ifSecondMenuSelection;

    public InstructionHandler(TelegramBot telegramBot, ShelterMessageImpl shelterMessage, ChatHandler chatHandler, MenuHandler menuHandler) {
        super(telegramBot, shelterMessage);
        this.chatHandler = chatHandler;
        this.menuHandler = menuHandler;
    }

    @Override
    public boolean appliesTo(Update update) {
        log.info("Processing appliesTo InstructionHandler: {}", update);
        if (update.callbackQuery() != null) {
            String data = update.callbackQuery().data();
            isFirstMenuSelection = data.equals("/" + HOW_TO_TAKE_A_PET);
            ifSecondMenuSelection = INSTRUCTION_LIST.stream()
                    .map(s -> "/" + s)
                    .anyMatch(s -> s.equals(data));

            return isFirstMenuSelection || ifSecondMenuSelection;
        }
        return false;
    }

    @Override
    public void handleUpdate(Update update) {
        String data = update.callbackQuery().data();
        long chatId = update.callbackQuery().message().chat().id();

        if (isFirstMenuSelection) {
            firstMenuSelection(chatId);
        } else if (ifSecondMenuSelection) {
            secondSelectionMenu(update, data, chatId);
        }
    }

    private void secondSelectionMenu(Update update, String data, long chatId) {
        InlineKeyboardMarkup backMenu = shelterMessage.keyboards(BACK_MENU);
        PetType flag = menuHandler.flag.get(chatId);
        if (flag == PetType.CAT) {
            switch (data) {
                case "/" + INSTRUCTION_MEETING ->
                        shelterMessage.sendButtonMessage(chatId, telegramBot, InstructionsForCatsText.RULES_BEFORE.getText(), backMenu);
                case "/" + DOC_LIST ->
                        shelterMessage.sendButtonMessage(chatId, telegramBot, InstructionsForCatsText.DOC_LIST.getText(), backMenu);
                case "/" + TRANSPORTATION ->
                        shelterMessage.sendButtonMessage(chatId, telegramBot, InstructionsForCatsText.RECOMMENDATION_FOR_TRANSPORTATION.getText(), backMenu);
                case "/" + HOUSE_RECOM_FOR_KITY ->
                        shelterMessage.sendButtonMessage(chatId, telegramBot, InstructionsForCatsText.HOUSE_RECOMMENDATION_FOR_KITTY.getText(), backMenu);
                case "/" + HOUSE_RECOM_FOR_ADULT ->
                        shelterMessage.sendButtonMessage(chatId, telegramBot, InstructionsForCatsText.HOUSE_RECOMMENDATION_FOR_ADULT.getText(), backMenu);
                case "/" + HOUSE_RECOM_FOR_INVALID ->
                        shelterMessage.sendButtonMessage(chatId, telegramBot, InstructionsForCatsText.HOUSE_RECOMMENDATION_FOR_INVALID.getText(), backMenu);
                case "/" + RESTRICTIONS ->
                        shelterMessage.sendButtonMessage(chatId, telegramBot, InstructionsForCatsText.RESTRICTIONS.getText(), backMenu);
                case "/" + CALL_A_VOLUNTEER ->
                        chatHandler.handleUpdate(update);
                case "/" + BACK ->
                        firstMenuSelection(chatId);
            }
        }
    }

    private void firstMenuSelection(long chatId) {
        InlineKeyboardMarkup dogMenu = shelterMessage.keyboards(INSTRUCTION_MEETING,
                                                                DOC_LIST,
                                                                TRANSPORTATION,
                                                                HOUSE_RECOM_FOR_PUPPY,
                                                                HOUSE_RECOM_FOR_ADULT,
                                                                HOUSE_RECOM_FOR_INVALID,
                                                                RECOM_FROM_CYNOLOGIST,
                                                                CYNOLOGIST_LIST,
                                                                RESTRICTIONS,
                                                                CALL_A_VOLUNTEER,
                                                                BACK);
        InlineKeyboardMarkup catMenu = shelterMessage.keyboards(INSTRUCTION_MEETING,
                                                                DOC_LIST,
                                                                TRANSPORTATION,
                                                                HOUSE_RECOM_FOR_KITY,
                                                                HOUSE_RECOM_FOR_ADULT,
                                                                HOUSE_RECOM_FOR_INVALID,
                                                                RESTRICTIONS,
                                                                CALL_A_VOLUNTEER,
                                                                BACK);
        PetType flag = menuHandler.flag.get(chatId);
        if (flag == PetType.CAT) {
            shelterMessage.sendButtonMessage(chatId, telegramBot, "Здесь вы можете получить инструкции", catMenu);
        } else if (flag == PetType.DOG) {
            shelterMessage.sendButtonMessage(chatId, telegramBot, "Здесь вы можете получить инструкции", dogMenu);
        }

    }
}
