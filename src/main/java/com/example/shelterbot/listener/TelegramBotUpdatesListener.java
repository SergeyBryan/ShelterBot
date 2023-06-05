package com.example.shelterbot.listener;

import com.example.shelterbot.handlers.TelegramHandler;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final TelegramBot telegramBot;

    private final List<TelegramHandler> telegramHandlers;

    public TelegramBotUpdatesListener(TelegramBot telegramBot, List<TelegramHandler> telegramHandlers) {
        this.telegramBot = telegramBot;
        this.telegramHandlers = telegramHandlers;
    }


    @Override
    public int process(List<Update> list) {
        try {
            list.stream().
                    filter(update -> update.message() != null || update.callbackQuery() != null).
                    forEach(this::handleUpdate);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return CONFIRMED_UPDATES_ALL;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }


    private void handleUpdate(Update update) {
        for (TelegramHandler handler : telegramHandlers) {
            if (handler.appliesTo(update)) {
                handler.handleUpdate(update);
                break;
            }
        }
    }


}
