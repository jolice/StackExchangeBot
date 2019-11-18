package io.riguron.stackexchange.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramConfiguration {

    @Value("${bot.token}")
    private String token;

    @Value("${delete.payload}")
    private String deletePayload;

    @Bean
    public TelegramBot telegramBot() {
        TelegramBot telegramBot = new TelegramBot(token);
        telegramBot.setUpdatesListener(this.updatesListener(telegramBot));
        return telegramBot;
    }

    @Bean
    public UpdatesListener updatesListener(TelegramBot telegramBot) {
        return new DeletingUpdatesListener(telegramBot, deletePayload);
    }

}
