package com.example.telegrambotweather.handler.handle;

import com.example.telegrambotweather.Component.KeyboardHelper;
import com.example.telegrambotweather.Model.UserRequest;
import com.example.telegrambotweather.Model.UserSession;
import com.example.telegrambotweather.Service.TelegramService;
import com.example.telegrambotweather.Service.UserSessionService;
import com.example.telegrambotweather.enums.ConversationState;
import com.example.telegrambotweather.handler.UserRequestHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Component
public class StartCommandHandler extends UserRequestHandler {

    private static String command = "/start";

    private final TelegramService telegramService;
    private final KeyboardHelper keyboardHelper;
    private final UserSessionService userSessionService;

    public StartCommandHandler(TelegramService telegramService, KeyboardHelper keyboardHelper, UserSessionService userSessionService) {
        this.telegramService = telegramService;
        this.keyboardHelper = keyboardHelper;
        this.userSessionService = userSessionService;
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
                "\uD83D\uDC4BHi\\! Welcome to the Weather Bot\\!");
        telegramService.sendMessage(request.getChatId(),
                "Choose from the menu below ⤵️",replyKeyboard);
        UserSession userSession = request.getUserSession();
        userSession.setState(ConversationState.WAITING_FOR_CLICK_MENU);
        userSessionService.saveSession(userSession.getChatId(), userSession);
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
