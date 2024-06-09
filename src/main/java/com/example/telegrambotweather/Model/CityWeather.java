package com.example.telegrambotweather.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CityWeather {

    private final DateTimeFormatter Timeformatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @JsonProperty("weather")
    private List<Weather> weather;

    @JsonProperty("base")
    private String base;

    @JsonProperty("main")
    private WeatherMain main;

    @JsonProperty("visibility")
    private int visibility;

    @JsonProperty("wind")
    private Wind wind;


    @JsonProperty("clouds")
    private Clouds clouds;

    @JsonProperty("dt")
    private long dt;

    @JsonProperty("sys")
    private Sys sys;

    @JsonProperty("timezone")
    private int timezone;

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("cod")
    private int cod;

    // Getters and setters
    public String getWeatherCity(){
        return "City: "+name+";\n"+
                "Country: "+sys.getCountry()+";\n"+
                "Temperature: "+main.getTemp()+"°C;\n"+
                "Humidity: "+main.getHumidity()+"%;\n"+
                "Description: "+weather.get(0).getDescription()+";\n"+
                "Sunset: "+ LocalDateTime.ofInstant(Instant.ofEpochSecond(sys.getSunset()), ZoneId.of(String.format("UTC+%s",timezone/3600))).format(Timeformatter)+";\n"+
                "Sunrise: "+LocalDateTime.ofInstant(Instant.ofEpochSecond(sys.getSunrise()), ZoneId.of(String.format("UTC+%s",timezone/3600))).format(Timeformatter)+";\n";
    }
}
