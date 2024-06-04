package com.example.telegrambotweather.handler.handle;

import com.example.telegrambotweather.Component.KeyboardHelper;
import com.example.telegrambotweather.Model.History;
import com.example.telegrambotweather.Model.UserRequest;
import com.example.telegrambotweather.Model.UserSession;
import com.example.telegrambotweather.Service.TelegramService;
import com.example.telegrambotweather.Service.UserSessionService;
import com.example.telegrambotweather.enums.ConversationState;
import com.example.telegrambotweather.handler.UserRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.List;
@Component
public class CurrentCityWeatherHandler extends UserRequestHandler {
    public static String  current_data= "Current Weather Data";
    public static String  cancel_data="Cancel\uD83D\uDDD9";

//    @Autowired
//    public History<String> historyList;

    private final TelegramService telegramService;
    private final KeyboardHelper keyboardHelper;
    private final UserSessionService userSessionService;

    public CurrentCityWeatherHandler(TelegramService telegramService, KeyboardHelper keyboardHelper, UserSessionService userSessionService) {
        this.telegramService = telegramService;
        this.keyboardHelper = keyboardHelper;
        this.userSessionService = userSessionService;
    }

    @Override
    public boolean isApplicable(UserRequest request) {
        return isTextMessage(request.getUpdate(), current_data);
    }

    @Override
    public void handle(UserRequest dispatchRequest) {
        UserSession userSession = dispatchRequest.getUserSession();
        ReplyKeyboardMarkup replyKeyboardMarkup = keyboardHelper.buildMenuWithCancel(userSession.getHistory().getHistory());
        telegramService.sendMessage(dispatchRequest.getChatId(),"Enter the City⤵️",replyKeyboardMarkup);


        userSession.setState(ConversationState.WAITING_FOR_CITY);
        userSessionService.saveSession(userSession.getChatId(), userSession);
        if( dispatchRequest.getUpdate().getMessage().getText().equals(cancel_data)){
            userSession.setState(ConversationState.WAITING_FOR_CLICK_MENU);
            userSessionService.saveSession(userSession.getChatId(), userSession);
        }
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
