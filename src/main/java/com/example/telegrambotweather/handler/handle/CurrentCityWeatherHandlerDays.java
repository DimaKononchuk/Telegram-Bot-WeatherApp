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
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.time.LocalDate;

@Component
public class CurrentCityWeatherHandlerDays extends UserRequestHandler {
    public static String  current_data= "3-hour Forecast 5 days";
    public static String  cancel_data="Cancel\uD83D\uDDD9";
    private final TelegramService telegramService;
    private final KeyboardHelper keyboardHelper;
    private final UserSessionService userSessionService;
    @Autowired
    private WeatherService weatherService;


    public CurrentCityWeatherHandlerDays(TelegramService telegramService, KeyboardHelper keyboardHelper, UserSessionService userSessionService) {
        this.telegramService = telegramService;
        this.keyboardHelper = keyboardHelper;
        this.userSessionService = userSessionService;
    }

    @Override
    public boolean isApplicable(UserRequest request) {
        return isTextMessage(request.getUpdate(), current_data)|| isTextMessage(request.getUpdate(), "Back") ;
    }

    @Override
    public void handle(UserRequest dispatchRequest) {
        UserSession userSession = dispatchRequest.getUserSession();

        ReplyKeyboardMarkup inlineKeyboardMarkup = keyboardHelper.ReplybuildMenuListDate(weatherService.getWeatherDays().getDateList());
        telegramService.sendMessage(dispatchRequest.getChatId(),"Enter the Date⤵️ \\(select from the menu\\)",inlineKeyboardMarkup);

        userSession.setState(ConversationState.WAITING_FOR_INLINE_DATE);
        userSessionService.saveSession(userSession.getChatId(), userSession);



    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
