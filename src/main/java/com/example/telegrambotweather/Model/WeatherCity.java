package com.example.telegrambotweather.Model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Setter
@Getter
@Component
public class WeatherCity {

    private String City;
    private String Country;

    private int humidity;
    private String utc;

    private int temperature;
    private String weatherDescription;
    private String iconCode;

    private String sunrise;
    private String sunset;

    private List<LocalTime> timeList=new ArrayList<>();
    private List<LocalDate> dateList=new ArrayList<>();

    private final DateTimeFormatter DateTimeformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final DateTimeFormatter Timeformatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public WeatherCity(){};
    public String  getWeatherCity(JsonNode json){
        City=json.path("name").asText();
        Country=new Locale("",json.path("sys").path("country").asText()).getDisplayCountry(Locale.ENGLISH);
        ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(json.path("timezone").asInt());
        temperature=json.path("main").path("temp").asInt();
        humidity=json.path("main").path("humidity").asInt();
        weatherDescription=json.path("weather").get(0).path("description").asText();

        // Отримати зрушення у секундах від UTC
        int timeOffset=json.path("timezone").asInt()/3600;

        System.out.println(timeOffset);
        sunset = LocalDateTime.ofInstant(Instant.ofEpochSecond(json.path("sys").path("sunset").asInt()), ZoneId.of(String.format("UTC+%s",timeOffset))).format(Timeformatter);
        sunrise = LocalDateTime.ofInstant(Instant.ofEpochSecond(json.path("sys").path("sunrise").asInt()), ZoneId.of(String.format("UTC+%s",timeOffset))).format(Timeformatter);
        return "City: "+City+";\n"+
                "Country: "+Country+";\n"+
                "Temperature: "+temperature+"°C;\n"+
                "Humidity: "+humidity+"%;\n"+
                "Description: "+weatherDescription+";\n"+
                "Sunset: "+sunset+";\n"+
                "Sunrise: "+sunrise+";\n";
    }


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
}
