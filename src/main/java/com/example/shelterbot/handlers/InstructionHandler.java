package com.example.shelterbot.handlers;

import com.example.shelterbot.handlers.text.InstructionsForCatsText;
import com.example.shelterbot.handlers.text.InstructionsForDogsText;
import com.example.shelterbot.message.ShelterMessageImpl;
import com.example.shelterbot.model.enums.PetType;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
@Order(6)
@Slf4j
public class InstructionHandler extends AbstractHandler {

    private final ChatHandler chatHandler;
    private final MenuHandler menuHandler;

    final Map<Long, Boolean> selection = new HashMap<>();

    public InstructionHandler(TelegramBot telegramBot, ShelterMessageImpl shelterMessage, ChatHandler chatHandler, MenuHandler menuHandler) {
        super(telegramBot, shelterMessage);
        this.chatHandler = chatHandler;
        this.menuHandler = menuHandler;
    }

    @Override
    public boolean appliesTo(Update update) {
        if (update.callbackQuery() != null) {
            log.info("Processing appliesTo InstructionHandler: {}", update.callbackQuery().data());
            String data = update.callbackQuery().data();
            boolean isFirstMenuSelection = data.equals("/" + HOW_TO_TAKE_A_PET);
            boolean ifSecondMenuSelection = INSTRUCTION_LIST.stream()
                    .map(s -> "/" + s)
                    .anyMatch(s -> s.equals(data));
            if (isFirstMenuSelection) {
                selection.put(update.callbackQuery().from().id(), true);
            } else if (ifSecondMenuSelection){
                selection.put(update.callbackQuery().from().id(), false);
            }
            log.info("Processing appliesTo InstructionHandler: return true");
            return isFirstMenuSelection || ifSecondMenuSelection;
        }
        log.info("Processing appliesTo InstructionHandler: return false");
        return false;
    }

    @Override
    public void handleUpdate(Update update) {
        String data = update.callbackQuery().data();
        long chatId = update.callbackQuery().from().id();
        boolean isFirstMenuSelection = selection.get(chatId);

        if (isFirstMenuSelection) {
            firstMenuSelection(chatId);
        } else {
            secondSelectionMenu(update, data, chatId);
        }
    }

    private void firstMenuSelection(long chatId) {
        PetType flag = menuHandler.flag.get(chatId);
        if (flag == PetType.CAT) {
            InlineKeyboardMarkup catMenu = shelterMessage.keyboards(
                                                                    INSTRUCTION_MEETING,
                                                                    DOC_LIST,
                                                                    TRANSPORTATION,
                                                                    HOUSE_RECOM_FOR_KITY,
                                                                    HOUSE_RECOM_FOR_ADULT,
                                                                    HOUSE_RECOM_FOR_INVALID,
                                                                    RESTRICTIONS,
                                                                    CALL_A_VOLUNTEER,
                                                                    BACK);
            shelterMessage.sendButtonMessage(chatId, telegramBot, "Здесь вы можете получить инструкции", catMenu);
        } else if (flag == PetType.DOG) {
            InlineKeyboardMarkup dogMenu = shelterMessage.keyboards(
                                                                    INSTRUCTION_MEETING,
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

            shelterMessage.sendButtonMessage(chatId, telegramBot, "Здесь вы можете получить инструкции", dogMenu);
        } else {
            SendMessage sendMessage = new SendMessage(chatId, "Вы не выбрали приют, вернитесь в стартовое меню и выбирете приют");
            telegramBot.execute(sendMessage);
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
        } else if (flag == PetType.DOG) {
            switch (data) {
                case "/" + INSTRUCTION_MEETING ->
                        shelterMessage.sendButtonMessage(chatId, telegramBot, InstructionsForDogsText.RULES_BEFORE.getText(), backMenu);
                case "/" + DOC_LIST ->
                        shelterMessage.sendButtonMessage(chatId, telegramBot, InstructionsForDogsText.DOC_LIST.getText(), backMenu);
                case "/" + TRANSPORTATION ->
                        shelterMessage.sendButtonMessage(chatId, telegramBot, InstructionsForDogsText.RECOMMENDATION_FOR_TRANSPORTATION.getText(), backMenu);
                case "/" + HOUSE_RECOM_FOR_PUPPY ->
                        shelterMessage.sendButtonMessage(chatId, telegramBot, InstructionsForDogsText.HOUSE_RECOMMENDATION_FOR_PUPPY.getText(), backMenu);
                case "/" + HOUSE_RECOM_FOR_ADULT ->
                        shelterMessage.sendButtonMessage(chatId, telegramBot, InstructionsForDogsText.HOUSE_RECOMMENDATION_FOR_ADULT.getText(), backMenu);
                case "/" + HOUSE_RECOM_FOR_INVALID ->
                        shelterMessage.sendButtonMessage(chatId, telegramBot, InstructionsForDogsText.HOUSE_RECOMMENDATION_FOR_INVALID.getText(), backMenu);
                case "/" + RECOM_FROM_CYNOLOGIST ->
                        shelterMessage.sendButtonMessage(chatId, telegramBot, InstructionsForDogsText.CYNOLOGIST_ADVICE.getText(), backMenu);
                case "/" + CYNOLOGIST_LIST ->
                        shelterMessage.sendButtonMessage(chatId, telegramBot, InstructionsForDogsText.CYNOLOGIST_LIST.getText(), backMenu);
                case "/" + RESTRICTIONS ->
                        shelterMessage.sendButtonMessage(chatId, telegramBot, InstructionsForDogsText.RESTRICTIONS.getText(), backMenu);
                case "/" + CALL_A_VOLUNTEER -> chatHandler.handleUpdate(update);
                case "/" + BACK -> firstMenuSelection(chatId);
            }
        } else {
            SendMessage sendMessage = new SendMessage(chatId, "Вы не выбрали приют, вернитесь в стартовое меню и выбирете приют");
            telegramBot.execute(sendMessage);
        }
    }
}
