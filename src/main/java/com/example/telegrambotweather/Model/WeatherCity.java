package com.example.telegrambotweather.Model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Data
@Setter
@Getter
public class WeatherCity {

    private String City;
    private String Country;

    private String time;
    private String utc;

    private int temperature;
    private String weatherDescription;
    private String iconCode;

    private String sunrise;
    private String sunset;

    private final DateTimeFormatter DateTimeformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final DateTimeFormatter Timeformatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    public WeatherCity(JsonNode json){
        City=json.path("name").asText();
        Country=new Locale("",json.path("sys").path("country").asText()).getDisplayCountry(Locale.ENGLISH);
        ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(json.path("timezone").asInt());
        temperature=json.path("main").path("temp").asInt();
        weatherDescription=json.path("weather").get(0).path("description").asText();
        time = LocalDateTime.ofInstant(Instant.ofEpochSecond(json.path("dt").asInt()), ZoneId.of("UTC")).format(DateTimeformatter);
        sunset = LocalDateTime.ofInstant(Instant.ofEpochSecond(json.path("sys").path("sunset").asInt()), ZoneId.of("UTC")).format(Timeformatter);
        sunrise = LocalDateTime.ofInstant(Instant.ofEpochSecond(json.path("sys").path("sunrise").asInt()), ZoneId.of("UTC")).format(Timeformatter);

    }
    @Override
    public String toString(){
        return "City: "+City+";\n"+
                "Country: "+Country+";\n"+
                "Time: "+time+";\n"+
                "Temperature: "+temperature+"°C;\n"+
                "Description: "+weatherDescription+";\n"+
                "Sunset: "+sunset+";\n"+
                "Sunrise: "+sunrise+";\n";
    }
}
