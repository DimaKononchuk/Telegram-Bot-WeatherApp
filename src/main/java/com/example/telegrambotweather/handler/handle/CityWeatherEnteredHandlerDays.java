package com.example.telegrambotweather.handler.handle;

import com.example.telegrambotweather.Component.KeyboardHelper;
import com.example.telegrambotweather.Model.UserRequest;
import com.example.telegrambotweather.Model.UserSession;
import com.example.telegrambotweather.Model.WeatherCity;
import com.example.telegrambotweather.Service.TelegramService;
import com.example.telegrambotweather.Service.UserSessionService;
import com.example.telegrambotweather.Service.WeatherService;
import com.example.telegrambotweather.enums.ConversationState;
import com.example.telegrambotweather.handler.UserRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class CityWeatherEnteredHandlerDays extends UserRequestHandler {

    private final TelegramService telegramService;
    private final KeyboardHelper keyboardHelper;
    private final UserSessionService userSessionService;
    public static String  cancel_data="Cancel\uD83D\uDDD9";
//    @Autowired
//    public  History<String> historyList;
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private WeatherCity weatherCity;
    public CityWeatherEnteredHandlerDays(TelegramService telegramService, KeyboardHelper keyboardHelper, UserSessionService userSessionService) {
        this.telegramService = telegramService;
        this.keyboardHelper = keyboardHelper;
        this.userSessionService = userSessionService;

    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        return isTextMessage(userRequest.getUpdate())
                && ConversationState.WAITING_FOR_INLINE_DATE.equals(userRequest.getUserSession().getState());
    }

    @Override
    public void handle(UserRequest userRequest) {
        UserSession userSession = userRequest.getUserSession();
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(userRequest.getChatId().toString());
        deleteMessage.setMessageId(userRequest.getUpdate().getMessage().getMessageId());
        System.out.println(userRequest.getUpdate().getMessage().getText());
        System.out.println(weatherCity.getDateList());

            telegramService.execute(deleteMessage);

        userSession.setState(ConversationState.WAITING_FOR_INLINE_DATE);
        userSessionService.saveSession(userRequest.getChatId(), userSession);

    }
    @Override
    public boolean isGlobal() {
        return false;
    }


}
