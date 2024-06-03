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
public class MainMenuHandle extends UserRequestHandler {

    public static String  cancel_data= "Cancel\uD83D\uDDD9";

    private final TelegramService telegramService;
    private final KeyboardHelper keyboardHelper;
    private final UserSessionService userSessionService;

    public MainMenuHandle(TelegramService telegramService, KeyboardHelper keyboardHelper, UserSessionService userSessionService) {
        this.telegramService = telegramService;
        this.keyboardHelper = keyboardHelper;
        this.userSessionService = userSessionService;
    }

    @Override
    public boolean isApplicable(UserRequest request) {

        return isTextMessage(request.getUpdate(), cancel_data) || request.getUserSession().getState().equals(ConversationState.WAITING_FOR_CLICK_MENU) ;
    }

    @Override
    public void handle(UserRequest dispatchRequest) {
        ReplyKeyboard replyKeyboard = keyboardHelper.buildMenuTypeWeather();
        telegramService.sendMessage(dispatchRequest.getChatId(),
                "Choose from the menu below  ⤵️", replyKeyboard);
        UserSession userSession = dispatchRequest.getUserSession();
        userSession.setState(ConversationState.WAITING_FOR_CLICK_MENU);
        userSessionService.saveSession(userSession.getChatId(), userSession);
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
