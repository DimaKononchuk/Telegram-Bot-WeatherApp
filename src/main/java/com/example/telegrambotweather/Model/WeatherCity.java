package com.example.telegrambotweather.Model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Component
public class WeatherCity {

    private String City;
    private String Country;

    private int humidity;
    private String utc;

    private double temperature;
    private String weatherDescription;
    private String iconCode;

    private String sunrise;
    private String sunset;

    private List<LocalTime> timeList=new ArrayList<>();
    private List<LocalDate> dateList=new ArrayList<>();

    private final DateTimeFormatter DateTimeformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final DateTimeFormatter Timeformatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private String DateTime;

    public WeatherCity(){};

    public void setDateTimeList(JsonNode json) {

        for(JsonNode time:json.path("list")){
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            // Перетворюємо рядок у LocalDateTime
            LocalDateTime localDateTime = LocalDateTime.parse(time.path("dt_txt").asText(), dateTimeFormatter);
            // Витягуємо лише дату
            LocalDate localDate = localDateTime.toLocalDate();
            LocalTime localTime=localDateTime.toLocalTime();
            if(!dateList.contains(localDate)){
                dateList.add(localDate);
            }
            if(!timeList.contains(localTime)){
                timeList.add(localTime);
            }


        }
        System.out.println(dateList);
        System.out.println(timeList);

    }

    public String getWeatherCityDateTime() {
        return "City: "+City+";\n"+
                "Country: "+Country+";\n"+
                "Date Time: "+DateTime+";\n"+
                "Temperature: "+temperature+"°C;\n"+
                "Humidity: "+humidity+"%;\n"+
                "Description: "+weatherDescription+";\n"+
                "Sunset: "+sunset+";\n"+
                "Sunrise: "+sunrise+";\n";
    }

    public void setSunset(long sunset,int timezone) {
        this.sunset = LocalDateTime.ofInstant(Instant.ofEpochSecond(sunset), ZoneId.of(String.format("UTC+%s",timezone/3600))).format(Timeformatter);
    }

    public void setSunrise(long sunrise,int timezone) {
        this.sunrise = LocalDateTime.ofInstant(Instant.ofEpochSecond(sunrise), ZoneId.of(String.format("UTC+%s",timezone/3600))).format(Timeformatter);
    }
}
