package com.example.shelterbot.handlers.text;

public enum InstructionsForDogsText {
    RULES_BEFORE(""),
    DOC_LIST("""
            """),
    RECOMMENDATION_FOR_TRANSPORTATION("""
            """),
    HOUSE_RECOMMENDATION_FOR_PUPPY("""
            """),
    HOUSE_RECOMMENDATION_FOR_ADULT("""
            """),
    HOUSE_RECOMMENDATION_FOR_INVALID(""),
    RESTRICTIONS(""),
    CYNOLOGIST_ADVICE(""),
    CYNOLOGIST_LIST(""),
    BACK_MENU("Вернуться в информационное меню");

    private final String text;

    InstructionsForDogsText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}
