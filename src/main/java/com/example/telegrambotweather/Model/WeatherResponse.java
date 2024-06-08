package com.example.telegrambotweather.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class WeatherResponse {
    @JsonProperty("cod")
    private String cod;

    @JsonProperty("message")
    private int message;

    @JsonProperty("cnt")
    private int cnt;

    @JsonProperty("list")
    private List<WeatherEntry> list;
    // Getters and setters

    @JsonProperty("city")
    private City city;
}

