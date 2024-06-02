package com.example.telegrambotweather.handler.handle;

import com.example.telegrambotweather.Component.KeyboardHelper;
import com.example.telegrambotweather.Model.UserRequest;
import com.example.telegrambotweather.Service.TelegramService;
import com.example.telegrambotweather.handler.UserRequestHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Component
public class StartCommandHandler extends UserRequestHandler {

    private static String command = "/start";

    private final TelegramService telegramService;
    private final KeyboardHelper keyboardHelper;

    public StartCommandHandler(TelegramService telegramService,KeyboardHelper keyboardHelper) {
        this.telegramService = telegramService;
        this.keyboardHelper = keyboardHelper;
    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        return isCommand(userRequest.getUpdate(), command);
    }

    @Override
    public void handle(UserRequest request) {
//        ReplyKeyboard replyKeyboard = keyboardHelper.buildMenuTypeWeather();
        ReplyKeyboard replyKeyboard = keyboardHelper.buildMenuTypeWeather();
        telegramService.sendMessage(request.getChatId(),
                "\uD83D\uDC4BHi! Welcome to the Weather Bot!");
        telegramService.sendMessage(request.getChatId(),
                "Choose from the menu below ⤵️",replyKeyboard);
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
