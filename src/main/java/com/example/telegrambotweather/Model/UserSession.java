package com.example.telegrambotweather.Model;

import com.example.telegrambotweather.enums.ConversationState;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class UserSession {
    private Long chatId;
    private ConversationState state;
    private String city;
    private String text;
    private History history;
    private LocalDate date;
    private LocalTime time;

    public void addHistory(String city){
        history.add(city);
    }

}
