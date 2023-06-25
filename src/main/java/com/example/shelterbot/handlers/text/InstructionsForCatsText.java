package com.example.shelterbot.handlers.text;

public enum InstructionsForCatsText {
    RULES_BEFORE(""),
    DOC_LIST("""
            """),
    RECOMMENDATION_FOR_TRANSPORTATION("""
            """),
    HOUSE_RECOMMENDATION_FOR_KITTY("""
            """),
    HOUSE_RECOMMENDATION_FOR_ADULT("""
            """),
    HOUSE_RECOMMENDATION_FOR_INVALID(""),
    RESTRICTIONS(""),
    BACK_MENU("Вернуться в информационное меню");

    private final String text;

    InstructionsForCatsText(String text) {
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
