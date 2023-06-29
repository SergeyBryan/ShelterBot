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

/**
 * Класс TelegramBotUpdatesListener является компонентом Spring Framework и реализует интерфейс UpdatesListener.
 * Он отвечает за прослушивание обновлений от Telegram Bot API и передачу их на обработку соответствующим обработчикам.
 */
@Component
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final TelegramBot telegramBot;
    /**
     * Список обработчиков, которые будут использоваться для обработки входящих сообщений.
     */
    private final List<TelegramHandler> telegramHandlers;

    /**
     * Конструктор класса TelegramBotUpdatesListener.
     *
     * @param telegramBot      объект, представляющий бота Telegram
     * @param telegramHandlers список обработчиков, которые будут использоваться для обработки входящих сообщений
     */
    public TelegramBotUpdatesListener(TelegramBot telegramBot, List<TelegramHandler> telegramHandlers) {
        this.telegramBot = telegramBot;
        this.telegramHandlers = telegramHandlers;
    }

    /**
     * Метод process вызывается при получении новых обновлений от Telegram Bot API.
     * Метод обрабатывает каждое обновление, фильтруя только те, которые содержат сообщения или callback-запросы,
     * и передает их на обработку методу handleUpdate.
     *
     * @param list список обновлений от Telegram Bot API
     * @return CONFIRMED_UPDATES_ALL, если обновления были успешно обработаны
     */
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

    /**
     * Метод init вызывается после создания объекта и регистрирует текущий объект в качестве слушателя обновлений у объекта telegramBot.
     */
    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     * Метод handleUpdate перебирает список telegramHandlers и вызывает метод appliesTo для каждого обработчика,
     * чтобы определить, какой обработчик должен быть использован для обработки данного обновления.
     * Затем метод вызывает метод handleUpdate для выбранного обработчика.
     *
     * @param update объект, представляющий входящее сообщение
     */
    private void handleUpdate(Update update) {
        logger.info("Processing handleUpdate: {}", update);
        for (TelegramHandler handler : telegramHandlers) {
            if (handler.appliesTo(update)) {
                handler.handleUpdate(update);
                break;
            }
        }
    }


}
