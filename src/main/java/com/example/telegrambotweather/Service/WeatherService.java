package com.example.telegrambotweather.Service;


import com.example.telegrambotweather.Model.WeatherCity;
import com.example.telegrambotweather.Model.WeatherEntry;
import com.example.telegrambotweather.Model.WeatherResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private  String weatherApi;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private WeatherCity weatherCity;
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

    public boolean isValidDate(String dateString) {

        // Додаткова перевірка за допомогою DateTimeFormatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate date = LocalDate.parse(dateString, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public boolean isValidTime(String timeString) {
        // Додаткова перевірка за допомогою DateTimeFormatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            LocalTime date = LocalTime.parse(timeString, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public HashMap<String,Double> getGeocodingAPI(String city){
        HashMap<String,Double> cityList=new HashMap<>();
        String url = "http://api.openweathermap.org/geo/1.0/direct?q="+city+"&appid="+weatherApi;
        JsonNode response = new RestTemplate().getForObject(url, JsonNode.class);
        if(response.size()!=0){
            cityList.put("lat",response.get(0).path("lat").asDouble());
            cityList.put("lon",response.get(0).path("lon").asDouble());
        };

        System.out.println(city+" "+cityList.get("lat")+" "+cityList.get("lon"));
        return cityList;
    }
    public String getWeatherDateTime(String city,LocalDate date,LocalTime time) {
        HashMap<String,Double> cityGeoList=getGeocodingAPI(city);
        if(cityGeoList.size()!=0){
            String url="http://api.openweathermap.org/data/2.5/forecast?lat="+cityGeoList.get("lat")+"&lon="+cityGeoList.get("lon")+"&appid="+weatherApi;
            String DateTime=date.toString()+" "+time.toString()+":00";
            try {
                ObjectMapper mapper = new ObjectMapper();
                String json=restTemplate.getForObject(url, String.class);
                WeatherResponse weatherResponse = mapper.readValue(json, WeatherResponse.class);
                List<WeatherEntry> filteredList = weatherResponse.getList().stream()
                        .filter(entry -> DateTime.equals(entry.getDtTxt()))
                        .collect(Collectors.toList());
                weatherCity.setCity(weatherResponse.getCity().getName());
                weatherCity.setCountry(weatherResponse.getCity().getCountry());
                weatherCity.setDateTime(filteredList.get(0).getDtTxt());
                weatherCity.setTemperature(filteredList.get(0).getMain().getTemp());
                weatherCity.setHumidity(Integer.parseInt(String.valueOf(filteredList.get(0).getMain().getHumidity())));
                weatherCity.setWeatherDescription(filteredList.get(0).getWeather().get(0).getDescription());
                weatherCity.setSunset(weatherResponse.getCity().getSunset(),weatherResponse.getCity().getTimezone());
                weatherCity.setSunrise(weatherResponse.getCity().getSunrise(),weatherResponse.getCity().getTimezone());
                System.out.println("all"+ weatherResponse);
                System.out.println("list"+filteredList);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            return weatherCity.getWeatherCityDateTime();
        }else{
            return "City not found, please try again⤵️";
        }

    }
}
