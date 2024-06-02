package com.example.telegrambotweather.Model;

import com.example.telegrambotweather.enums.ConversationState;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSession {
    private Long chatId;
    private ConversationState state;
    private String city;
    private String text;
}
