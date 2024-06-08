package com.example.telegrambotweather;

import com.example.telegrambotweather.Model.WeatherCity;
import com.example.telegrambotweather.Service.WeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static javax.management.Query.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class TelegramBotWeatherApplicationTests {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherService weatherService; // Your service class containing getWeatherDays method

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    public void testGetWeatherDaysCityNotFound() {
        weatherService.getWeatherDays();
    }

    @Test
    public void testGetWeatherDaysOtherException() {


        // Add assertions for the exception message if needed
    }
}
