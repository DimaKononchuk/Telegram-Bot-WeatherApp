package com.example.telegrambotweather.handler.handle;

import com.example.telegrambotweather.Component.KeyboardHelper;
import com.example.telegrambotweather.Model.UserRequest;
import com.example.telegrambotweather.Model.UserSession;
import com.example.telegrambotweather.Service.TelegramService;
import com.example.telegrambotweather.Service.UserSessionService;
import com.example.telegrambotweather.Service.WeatherService;
import com.example.telegrambotweather.enums.ConversationState;
import com.example.telegrambotweather.handler.UserRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Component
public class WeatherCityDateTimeHandler extends UserRequestHandler {

    private final TelegramService telegramService;
    private final KeyboardHelper keyboardHelper;
    private final UserSessionService userSessionService;
    //    @Autowired
//    public  History<String> historyList;
    @Autowired
    private WeatherService weatherService;

    public WeatherCityDateTimeHandler(TelegramService telegramService, KeyboardHelper keyboardHelper, UserSessionService userSessionService) {
        this.telegramService = telegramService;
        this.keyboardHelper = keyboardHelper;
        this.userSessionService = userSessionService;

    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        return isTextMessage(userRequest.getUpdate())
                && ConversationState.WAITING_FOR_CITY_DATETIME.equals(userRequest.getUserSession().getState());
    }

    @Override
    public void handle(UserRequest userRequest) {
        String city = userRequest.getUpdate().getMessage().getText();
        UserSession session = userRequest.getUserSession();
        String message=weatherService.getWeatherDateTime(city,session.getDate(),session.getTime());
        ReplyKeyboardMarkup replyKeyboardMarkup = keyboardHelper.buildMenuWithCancel(session.getHistory().getHistory());
        if(!message.equals("City not found, please try again⤵️")){
            session.addHistory(city);

        }

        telegramService.sendMessage(userRequest.getChatId(),message,replyKeyboardMarkup);
        session.setState(ConversationState.WAITING_FOR_CITY_DATETIME);
        userSessionService.saveSession(userRequest.getChatId(), session);

    }

    @Override
    public boolean isGlobal() {
        return true;
    }

}
