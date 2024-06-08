package com.example.telegrambotweather.Service;


import com.example.telegrambotweather.Model.WeatherCity;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private  String weatherApi;

    private final RestTemplate restTemplate=new RestTemplate();



    public String getWeather(String city) throws HttpClientErrorException.NotFound  {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + weatherApi + "&units=metric";
        try {
            JsonNode response = restTemplate.getForObject(url, JsonNode.class);

            if (response != null) {
                WeatherCity weatherCity=new WeatherCity();

//                JsonNode main = response.path("main");
//                String temperature = main.path("temp").asText();
//                String weatherDescription = response.path("weather").get(0).path("description").asText();
//                String country=response.path("sys").path("country").asText();

                return weatherCity.getWeatherCity(response);
            } else {
                return "Could not fetch weather data.";
            }
        } catch (HttpClientErrorException.NotFound e) {
            return "City not found, please try again⤵️";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public WeatherCity getWeatherDays(){

        WeatherCity weatherCity=new WeatherCity();
        String url = "http://api.openweathermap.org/data/2.5/forecast?lat=44.34&lon=10.99&appid="+weatherApi;
        try {
            JsonNode response = new RestTemplate().getForObject(url, JsonNode.class);

            if (response != null) {
                weatherCity.setDateTimeList(response);
                weatherCity.setDateList(weatherCity.getDateList());
                weatherCity.setTimeList(weatherCity.getTimeList());

            }
        } catch (HttpClientErrorException.NotFound e) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(404),"City not found, please try again⤵️");
        }
        return weatherCity;
    }

    public static boolean isValidDate(String dateString) {
        try {
            // Створюємо об'єкт DateTimeFormatter з заданим форматом
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            // Спробуємо розпарсити рядок у LocalDate
            LocalDate date = LocalDate.parse(dateString, formatter);

            // Якщо розпарсувалося успішно, дата відповідає формату
            return true;
        } catch (DateTimeParseException e) {
            // Якщо розпарсування не вдалося, дата не відповідає формату
            return false;
        }
    }
}
