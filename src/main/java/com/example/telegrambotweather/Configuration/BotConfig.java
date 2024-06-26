package com.example.telegrambotweather.Configuration;

import com.example.telegrambotweather.Component.MyTelegramBot;
import com.example.telegrambotweather.Model.History;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class BotConfig {

    @Bean
    public TelegramBotsApi telegramBotsApi(TelegramLongPollingBot bot) throws TelegramApiException {
        TelegramBotsApi botsApi=new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(bot);

        return botsApi;
    }
//    @Bean
//    public History<String> historyList() {
//        return new History<>();
//    }
}
