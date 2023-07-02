package com.example.shelterbot.handlers;

import com.example.shelterbot.handlers.text.InstructionsForCatsText;
import com.example.shelterbot.handlers.text.InstructionsForDogsText;
import com.example.shelterbot.message.ShelterMessageImpl;
import com.example.shelterbot.model.enums.PetType;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.shelterbot.handlers.AbstractHandler.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class InstructionHandlerTest {

    @Mock
    private ChatHandler chatHandler;
    private MenuHandler menuHandler;
    @Mock
    private TelegramBot telegramBot;
    @Mock
    private ShelterMessageImpl shelterMessage;
    @Mock
    private Update update;
    @Mock
    private CallbackQuery callbackQuery;
    InstructionHandler instructionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        menuHandler = new MenuHandler(telegramBot, shelterMessage);
        instructionHandler = new InstructionHandler(telegramBot, shelterMessage, chatHandler, menuHandler);
    }

    @Test
    void testWithHOW_TO_TAKE_A_PETShouldReturnTrue() {
        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(update.callbackQuery().data()).thenReturn("/" + AbstractHandler.HOW_TO_TAKE_A_PET);
        User user = Mockito.mock(User.class);
        when(update.callbackQuery().from()).thenReturn(user);
        when(update.callbackQuery().from().id()).thenReturn(123L);
        instructionHandler.appliesTo(update);
        assertTrue(instructionHandler.selection.get(123L));
        assertTrue(instructionHandler.appliesTo(update));
    }

    @Test
    void testWithINSTRUCTION_LISTShouldReturnTrue() {
        when(update.callbackQuery()).thenReturn(callbackQuery);
        User user = Mockito.mock(User.class);
        when(update.callbackQuery().from()).thenReturn(user);
        when(update.callbackQuery().from().id()).thenReturn(123L);
        INSTRUCTION_LIST.forEach(e -> {
            when(update.callbackQuery().data()).thenReturn("/" + e);
            instructionHandler.appliesTo(update);
            assertFalse(instructionHandler.selection.get(123L));
            assertTrue(instructionHandler.appliesTo(update));
        });
    }

    @Test
    void testWithINFORMATIONShouldReturnFalse() {
        when(update.callbackQuery()).thenReturn(callbackQuery);
        User user = Mockito.mock(User.class);
        when(update.callbackQuery().from()).thenReturn(user);
        when(update.callbackQuery().from().id()).thenReturn(123L);
        when(update.callbackQuery().data()).thenReturn("/" + AbstractHandler.INFORMATION);
        instructionHandler.appliesTo(update);
        assertTrue(instructionHandler.selection.isEmpty());
        assertFalse(instructionHandler.appliesTo(update));
    }

    @Test
    void testHandleUpdateWithFirstSelectionAndFlagCat() {
        String[] arrayMenuCat = {
                INSTRUCTION_MEETING,
                DOC_LIST,
                TRANSPORTATION,
                HOUSE_RECOM_FOR_KITY,
                HOUSE_RECOM_FOR_ADULT,
                HOUSE_RECOM_FOR_INVALID,
                RESTRICTIONS,
                BACK_MENU
        };
        instructionHandler.selection.put(123L, true);
        menuHandler.flag.put(123L, PetType.CAT);
        InlineKeyboardMarkup inlineKeyboardMarkup = shelterMessage.keyboards(arrayMenuCat);

        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(update.callbackQuery().from()).thenReturn(mock(User.class));
        when(update.callbackQuery().from().id()).thenReturn(123L);
        instructionHandler.handleUpdate(update);
        verify(shelterMessage).sendButtonMessage(123L, telegramBot, "Здесь вы можете получить инструкции", inlineKeyboardMarkup);
    }

    @Test
    void testHandleUpdateWithFirstSelectionAndFlagDog() {
        String[] arrayMenuDog = {
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
                BACK
        };
        instructionHandler.selection.put(123L, true);
        menuHandler.flag.put(123L, PetType.DOG);
        InlineKeyboardMarkup inlineKeyboardMarkup = shelterMessage.keyboards(arrayMenuDog);

        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(update.callbackQuery().from()).thenReturn(mock(User.class));
        when(update.callbackQuery().from().id()).thenReturn(123L);


        instructionHandler.handleUpdate(update);
        verify(shelterMessage).sendButtonMessage(123L, telegramBot, "Здесь вы можете получить инструкции", inlineKeyboardMarkup);
    }

    @Test
    void testHandleUpdateWithFirstSelectionAndFlagIsNull() {
        long userID = 123L;
        instructionHandler.selection.put(123L, true);
        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(update.callbackQuery().from()).thenReturn(mock(User.class));
        when(update.callbackQuery().from().id()).thenReturn(userID);


        instructionHandler.handleUpdate(update);

        verify(telegramBot).execute(argThat(argument -> {
            Map<String, Object> parameters = argument.getParameters();
            return parameters.get("chat_id").equals(userID)
                    && parameters.get("text").equals("Вы не выбрали приют, вернитесь в стартовое меню и выбирете приют");
        }));
    }

    @Test
    void testHandleUpdateWithSecondSelectionAndFlagCat() {
        List<String> listMenuCat = List.of(
                "/" + INSTRUCTION_MEETING,
                "/" + DOC_LIST,
                "/" + TRANSPORTATION,
                "/" + HOUSE_RECOM_FOR_KITY,
                "/" + HOUSE_RECOM_FOR_ADULT,
                "/" + HOUSE_RECOM_FOR_INVALID,
                "/" + RESTRICTIONS
        );
        Map<String, String> dataMap = feelMapForCat();
        instructionHandler.selection.put(123L, false);
        menuHandler.flag.put(123L, PetType.CAT);
        InlineKeyboardMarkup inlineKeyboardMarkup = shelterMessage.keyboards(BACK_MENU);

        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(update.callbackQuery().from()).thenReturn(mock(User.class));
        when(update.callbackQuery().from().id()).thenReturn(123L);

        listMenuCat.forEach(e -> {
            when(callbackQuery.data()).thenReturn(e);
            instructionHandler.handleUpdate(update);
            verify(shelterMessage).sendButtonMessage(123L, telegramBot, dataMap.get(e), inlineKeyboardMarkup);

        });
    }

    @Test
    void testHandleUpdateWithSecondSelectionAndFlagDog() {
        List<String> listMenuCat = List.of(
                "/" + INSTRUCTION_MEETING,
                "/" + DOC_LIST,
                "/" + TRANSPORTATION,
                "/" + HOUSE_RECOM_FOR_PUPPY,
                "/" + HOUSE_RECOM_FOR_ADULT,
                "/" + HOUSE_RECOM_FOR_INVALID,
                "/" + RECOM_FROM_CYNOLOGIST,
                "/" + CYNOLOGIST_LIST,
                "/" + RESTRICTIONS
        );
        Map<String, String> dataMap = feelMapForDog();
        instructionHandler.selection.put(123L, false);
        menuHandler.flag.put(123L, PetType.DOG);
        InlineKeyboardMarkup inlineKeyboardMarkup = shelterMessage.keyboards(BACK_MENU);

        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(update.callbackQuery().from()).thenReturn(mock(User.class));
        when(update.callbackQuery().from().id()).thenReturn(123L);

        listMenuCat.forEach(e -> {
            when(callbackQuery.data()).thenReturn(e);
            instructionHandler.handleUpdate(update);
            verify(shelterMessage).sendButtonMessage(123L, telegramBot, dataMap.get(e), inlineKeyboardMarkup);

        });
    }

    @Test
    void testHandleUpdateWithSecondSelectionAndFlagIsNull() {
        long userID = 123L;
        instructionHandler.selection.put(123L, false);
        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(update.callbackQuery().from()).thenReturn(mock(User.class));
        when(update.callbackQuery().from().id()).thenReturn(userID);


        instructionHandler.handleUpdate(update);

        verify(telegramBot).execute(argThat(argument -> {
            Map<String, Object> parameters = argument.getParameters();
            return parameters.get("chat_id").equals(userID)
                    && parameters.get("text").equals("Вы не выбрали приют, вернитесь в стартовое меню и выбирете приют");
        }));
    }

    private Map<String, String> feelMapForDog() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("/" + INSTRUCTION_MEETING, InstructionsForDogsText.RULES_BEFORE.getText());
        hashMap.put("/" + DOC_LIST, InstructionsForDogsText.DOC_LIST.getText());
        hashMap.put("/" + TRANSPORTATION, InstructionsForDogsText.RECOMMENDATION_FOR_TRANSPORTATION.getText());
        hashMap.put("/" + HOUSE_RECOM_FOR_PUPPY, InstructionsForDogsText.HOUSE_RECOMMENDATION_FOR_PUPPY.getText());
        hashMap.put("/" + HOUSE_RECOM_FOR_ADULT, InstructionsForDogsText.HOUSE_RECOMMENDATION_FOR_ADULT.getText());
        hashMap.put("/" + HOUSE_RECOM_FOR_INVALID, InstructionsForDogsText.HOUSE_RECOMMENDATION_FOR_INVALID.getText());
        hashMap.put("/" + RECOM_FROM_CYNOLOGIST, InstructionsForDogsText.CYNOLOGIST_ADVICE.getText());
        hashMap.put("/" + CYNOLOGIST_LIST, InstructionsForDogsText.CYNOLOGIST_LIST.getText());
        hashMap.put("/" + RESTRICTIONS, InstructionsForDogsText.RESTRICTIONS.getText());
        return hashMap;
    }

    private static Map<String, String> feelMapForCat() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("/" + INSTRUCTION_MEETING, InstructionsForCatsText.RULES_BEFORE.getText());
        hashMap.put("/" + DOC_LIST, InstructionsForCatsText.DOC_LIST.getText());
        hashMap.put("/" + TRANSPORTATION, InstructionsForCatsText.RECOMMENDATION_FOR_TRANSPORTATION.getText());
        hashMap.put("/" + HOUSE_RECOM_FOR_KITY, InstructionsForCatsText.HOUSE_RECOMMENDATION_FOR_KITTY.getText());
        hashMap.put("/" + HOUSE_RECOM_FOR_ADULT, InstructionsForCatsText.HOUSE_RECOMMENDATION_FOR_ADULT.getText());
        hashMap.put("/" + HOUSE_RECOM_FOR_INVALID, InstructionsForCatsText.HOUSE_RECOMMENDATION_FOR_INVALID.getText());
        hashMap.put("/" + RESTRICTIONS, InstructionsForCatsText.RESTRICTIONS.getText());
        return hashMap;
    }
}