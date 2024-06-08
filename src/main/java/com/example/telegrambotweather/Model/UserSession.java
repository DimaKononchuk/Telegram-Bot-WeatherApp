package com.example.telegrambotweather.Model;

import com.example.telegrambotweather.enums.ConversationState;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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

    public void setDate(String date) {
        this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public void setTime(String time) {
        this.time = LocalTime.parse(time,DateTimeFormatter.ofPattern("HH:mm"));
    }
}
