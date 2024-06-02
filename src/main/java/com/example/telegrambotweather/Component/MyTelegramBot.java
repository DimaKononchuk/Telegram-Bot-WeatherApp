package com.example.telegrambotweather.Component;

import com.example.telegrambotweather.Service.MessageService;
import com.example.telegrambotweather.Service.WeatherService;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class MyTelegramBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.username}")
    private String botUsername;
    @Value("${telegram.bot.token}")
    private String botToken;

    @Autowired
    private WeatherService weatherService;
    @Autowired
    private MessageService messageService;
    private Map<Long, String> pendingFunction = new HashMap<>();
    private boolean isActive;
    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if(messageText.equals("/start")&& !isActive){
                isActive=true;
                SendMessage sendMessage=new SendMessage();
                sendMessage.setText("Hi, Welcom the Weather Bot!\nselect a function:");
                sendMessage.setChatId(String.valueOf(chatId));
                sendMessage.setReplyMarkup(getMainMenuKeyboard());
                messageService.sendMessage(this,sendMessage);


            }else if(messageText.equals("/stop")){
                isActive=false;
                messageService.sendRemoveKeyboard(this, chatId, "Bot Stopped!");
            }else if(messageText.equals("Current Weather")){
                messageService.sendMessage(this,chatId,"Enter the City:");
                weatherService.getWeather(messageText);
            }
        }
    }


    public ReplyKeyboardMarkup getMainMenuKeyboard() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("Current Weather"));
        row1.add(new KeyboardButton("Forecast 4 days"));

        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("Forecast 16 days"));
        row2.add(new KeyboardButton("Forecast 30 days"));

        keyboard.add(row1);
        keyboard.add(row2);

        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        return keyboardMarkup;
    }
}
