package com.example.telegrambotweather.Service;


import com.example.telegrambotweather.Model.WeatherCity;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
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
                WeatherCity weatherCity=new WeatherCity(response);
//                JsonNode main = response.path("main");
//                String temperature = main.path("temp").asText();
//                String weatherDescription = response.path("weather").get(0).path("description").asText();
//                String country=response.path("sys").path("country").asText();
                return weatherCity.toString();
            } else {
                return "Could not fetch weather data.";
            }
        } catch (HttpClientErrorException.NotFound e) {
            return "City not found, please try again⤵️";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }


}
