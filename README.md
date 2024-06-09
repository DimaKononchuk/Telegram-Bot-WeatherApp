# Telegram-Bot-WeatherApp


WeatherApp is a Telegram bot built using Spring Boot that provides weather information using the OpenWeatherMap API.

## Requirements

- Java 11 or higher
- Maven 3.6.0 or higher
- A [Telegram](https://telegram.org) account to create a bot
- An API key from [OpenWeatherMap](https://openweathermap.org/api)

## Installation

1. **Clone the repository:**

    ```bash
    git clone https://github.com/yourusername/WeatherApp.git
    cd WeatherApp
    ```

2. **Configure application properties:**

    Create a file named `application.properties` in the `src/main/resources` directory and add the following configuration:

    ```properties
    weather.api.key=YOUR_API_KEY
    weather.api.url=http://api.openweathermap.org/data/2.5/weather
    telegram.bot.username=YOUR_BOT_USERNAME
    telegram.bot.token=YOUR_BOT_TOKEN
    ```

    Replace `YOUR_API_KEY`, `YOUR_BOT_USERNAME`, and `YOUR_BOT_TOKEN` with your actual values.

3. **Build and run the application:**

    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

## Usage

After starting the application, your Telegram bot will be ready to receive requests. Open Telegram and find your bot by username, then send it the name of a city to get the current weather.

## Project Structure

- **WeatherAppApplication.java** - The main class of the application.
- **WeatherConfig.java** - Configuration class for storing the API key and base URL.
- **WeatherService.java** - Service for fetching weather data from OpenWeatherMap.
- **WeatherBot.java** - Controller for handling messages from the Telegram bot.

## Example Code

### WeatherConfig.java

```java
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeatherConfig {
    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    public String getApiKey() {
        return apiKey;
    }

    public String getApiUrl() {
        return apiUrl;
    }
}
