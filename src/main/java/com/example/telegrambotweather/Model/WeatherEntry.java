package com.example.telegrambotweather.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class WeatherEntry {
    @JsonProperty("dt")
    private long dt;

    @JsonProperty("main")
    private WeatherMain main;

    @JsonProperty("weather")
    private List<Weather> weather;

    @JsonProperty("clouds")
    private Clouds clouds;

    @JsonProperty("wind")
    private Wind wind;

    @JsonProperty("visibility")
    private int visibility;

    @JsonProperty("pop")
    private double pop;

    @JsonProperty("sys")
    private Sys sys;

    @JsonProperty("dt_txt")
    private String dtTxt;

    // Getters and setters
}

