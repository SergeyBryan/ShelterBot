package com.example.shelterbot;

public enum Cat {
    GREETING("Бот приветствует пользователя."),
    DATING_RULES("Бот может выдать правила знакомства с животным до того, как забрать его из приюта."),
    LIST_OF_DOCUMENTS("Бот может выдать список документов, необходимых для того, чтобы взять животное из приюта."),
    LIST_OF_RECOMMENDATIONS_FOR_TRANSPORTATION("Бот может  выдать список рекомендаций по транспортировке животного."),
    LIST_OF_RECOMMENDATIONS_FOR_HOME_IMPROVEMENT_FOR_A_KITTEN("Бот может  выдать список рекомендаций по обустройству дома для котенка."),
    LIST_OF_RECOMMENDATIONS_FOR_HOME_IMPROVEMENT_FOR_AN_ADULT_ANIMAL("Бот может  выдать список рекомендаций по обустройству дома для взрослого животного."),
    LIST_OF_RECOMMENDATIONS_FOR_HOME_IMPROVEMENT_FOR_AN_ANIMAL_WITH_DISABILITIES("Бот может  выдать список рекомендаций по обустройству дома для животного с ограниченными возможностями (зрение, передвижение)."),
    ACCEPT_AND_RECORD_CONTACT_DETAILS_FOR_COMMUNICATION("Бот может принять и записать контактные данные для связи."),
    CALL_A_VOLUNTEER("Если бот не может ответить на вопросы клиента, то можно позвать волонтера.");

        private String infoCat;
        Cat(String infoCat) {
        }

        public String toString() {
                return "Cat{" +
                        "infoCat='" + infoCat + '\'' +
                        '}';
        }
}
