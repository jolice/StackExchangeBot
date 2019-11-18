package io.riguron.stackexchange.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.DeleteMessage;

import java.util.List;

public class DeletingUpdatesListener implements UpdatesListener {

    private TelegramBot telegramBot;
    private String deletePayload;

    DeletingUpdatesListener(TelegramBot telegramBot, String deletePayload) {
        this.telegramBot = telegramBot;
        this.deletePayload = deletePayload;
    }

    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            CallbackQuery callbackQuery = update.callbackQuery();
            if (callbackQuery != null && deletePayload.equals(callbackQuery.data())) {
                Message message = callbackQuery.message();
                if (message != null) {
                    telegramBot.execute(new DeleteMessage(message.chat().id(), message.messageId()));
                }
            }
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
