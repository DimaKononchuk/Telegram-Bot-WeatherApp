package com.example.telegrambotweather.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class City {
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;


    @JsonProperty("country")
    private String country;

    @JsonProperty("population")
    private int population;

    @JsonProperty("timezone")
    private int timezone;

    @JsonProperty("sunrise")
    private long sunrise;

    @JsonProperty("sunset")
    private long sunset;

    // Геттери та сеттери
}