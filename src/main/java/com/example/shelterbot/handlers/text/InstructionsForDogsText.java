package com.example.shelterbot.handlers.text;

public enum InstructionsForDogsText {
    RULES_BEFORE("Правила знакомства с животным до того, как забрать его из приюта"),
    DOC_LIST("""
           Список документов, необходимых для того, чтобы взять животное из приюта"""),
    RECOMMENDATION_FOR_TRANSPORTATION("""
            Рекомендаций по транспортировке животного"""),
    HOUSE_RECOMMENDATION_FOR_PUPPY("""
            Рекомендаций по обустройству дома для щенка"""),
    HOUSE_RECOMMENDATION_FOR_ADULT("""
            Рекомендаций по обустройству дома для взрослого животного"""),
    HOUSE_RECOMMENDATION_FOR_INVALID("""
            Рекомендаций по обустройству дома для животного с ограниченными возможностями (зрение, передвижение)"""),
    RESTRICTIONS("Список причин, почему могут отказать и не дать забрать собаку из приюта"),
    CYNOLOGIST_ADVICE("Советы кинолога по первичному общению с собакой"),
    CYNOLOGIST_LIST("Рекомендации по проверенным кинологам для дальнейшего обращения к ним"),
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
