package com.example.telegrambotweather.handler.handle;

import com.example.telegrambotweather.Component.KeyboardHelper;
import com.example.telegrambotweather.Model.History;
import com.example.telegrambotweather.Model.UserRequest;
import com.example.telegrambotweather.Model.UserSession;
import com.example.telegrambotweather.Service.TelegramService;
import com.example.telegrambotweather.Service.UserSessionService;
import com.example.telegrambotweather.Service.WeatherService;
import com.example.telegrambotweather.enums.ConversationState;
import com.example.telegrambotweather.handler.UserRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.Arrays;
import java.util.List;

@Component
public class CityWeatherEnteredHandler extends UserRequestHandler {

    private final TelegramService telegramService;
    private final KeyboardHelper keyboardHelper;
    private final UserSessionService userSessionService;
//    @Autowired
//    public  History<String> historyList;
    @Autowired
    private WeatherService weatherService;

    public CityWeatherEnteredHandler(TelegramService telegramService, KeyboardHelper keyboardHelper, UserSessionService userSessionService) {
        this.telegramService = telegramService;
        this.keyboardHelper = keyboardHelper;
        this.userSessionService = userSessionService;

    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        return isTextMessage(userRequest.getUpdate())
                && ConversationState.WAITING_FOR_CITY.equals(userRequest.getUserSession().getState());
    }

    @Override
    public void handle(UserRequest userRequest) {
        ReplyKeyboardMarkup replyKeyboardMarkup;
        String city = userRequest.getUpdate().getMessage().getText();
        String message=weatherService.getWeather(city);
        UserSession session = userRequest.getUserSession();
        if(!message.equals("City not found, please try again⤵️")){
            session.addHistory(city);

        }

        replyKeyboardMarkup = keyboardHelper.buildMenuWithCancel(session.getHistory().getHistory());
        telegramService.sendMessage(userRequest.getChatId(),message,replyKeyboardMarkup);



        session.setCity(city);
        session.setState(ConversationState.WAITING_FOR_CITY);
        userSessionService.saveSession(userRequest.getChatId(), session);
    }

    @Override
    public boolean isGlobal() {
        return false;
    }

}
