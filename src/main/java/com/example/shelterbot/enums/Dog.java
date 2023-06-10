package com.example.shelterbot.enums;

public enum Dog {
        GREETING("Бот приветствует пользователя."),
        DATING_RULES("Бот может выдать правила знакомства с животным до того, как забрать его из приюта."),
        LIST_OF_DOCUMENTS("Бот может выдать список документов, необходимых для того, чтобы взять животное из приюта."),
        LIST_OF_RECOMMENDATIONS_FOR_TRANSPORTATION("Бот может  выдать список рекомендаций по транспортировке животного."),
        LIST_OF_RECOMMENDATIONS_FOR_HOME_IMPROVEMENT_FOR_A_PUPPY("Бот может  выдать список рекомендаций по обустройству дома для щенка."),
        LIST_OF_RECOMMENDATIONS_FOR_HOME_IMPROVEMENT_FOR_AN_ADULT_ANIMAL("Бот может  выдать список рекомендаций по обустройству дома для взрослого животного."),
        LIST_OF_RECOMMENDATIONS_FOR_HOME_IMPROVEMENT_FOR_AN_ANIMAL_WITH_DISABILITIES("Бот может  выдать список рекомендаций по обустройству дома для животного с ограниченными возможностями (зрение, передвижение)."),
        TIPS_FROM_A_DOG_HANDLER("Бот может выдать советы кинолога по первичному общению с собакой."),
        RECOMMENDATIONS_FOR_PROVEN_DOG_HANDLERS("Бот может выдать рекомендации по проверенным кинологам для дальнейшего обращения к ним."),
        LIS_OF_REASONS_WHY_THEY_MAY_REFUSE("Бот может выдать список причин, почему могут отказать и не дать забрать собаку из приюта."),
        ACCEPT_AND_RECORD_CONTACT_DETAILS_FOR_COMMUNICATION("Бот может принять и записать контактные данные для связи."),
        CALL_A_VOLUNTEER("Если бот не может ответить на вопросы клиента, то можно позвать волонтера.");
        private String infoDog;

        Dog(String infoDog) {
        }

        public String toString() {
                return "Dog{" +
                        "infoDog='" + infoDog + '\'' +
                        '}';
        }
}
