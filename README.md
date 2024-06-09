# Telegram-Bot-WeatherApp


WeatherApp is a Telegram bot built using Spring Boot that provides weather information using the OpenWeatherMap API.

## Requirements

- Java 21 or higher
- Maven 4.0.0 or higher
- A [Telegram](https://telegram.org) account to create a bot
- An API key from [OpenWeatherMap](https://openweathermap.org/api)

## Installation

1. **Clone the repository:**

    ```bash
    git clone https://github.com/yourusername/WeatherApp.git
    cd Telegram-Bot-WeatherApp
    ```

2. **Configure application properties:**

    Create a file named `application.properties` in the `src/main/resources` directory and add the following configuration:

    ```properties
    telegram.bot.username=YOUT_BOT_NAME
    telegram.bot.token=YOUR_TELEGRAM_TOKEN
    weather.api.key=YOUR_API_KEY
    ```

    Replace `YOUT_BOT_NAME`, `YOUR_TELEGRAM_TOKEN`, and `YOUR_API_KEY` with your actual values.

3. **Build and run the application:**

    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

## Usage

After starting the application, your Telegram bot will be ready to receive requests. Open Telegram and find your bot by username, then send it the name of a city to get the current weather.
## Screenshot
![Screenshot_7](https://github.com/DimaKononchuk/Telegram-Bot-WeatherApp/assets/64828665/5226fa70-fa87-47cb-84de-abadf88e0e91)
![Screenshot_9](https://github.com/DimaKononchuk/Telegram-Bot-WeatherApp/assets/64828665/7180c6f9-68be-441a-8ce5-61a687058c52)

## Project Structure

- **TelegramBotWeatherApplication.java** - The main class of the application.
- **MyTelegramBot.java** - Configuration class for storing the API key and base URL.
- **WeatherService.java** - Service for fetching weather data from OpenWeatherMap.
- **UserRequestHandler.java** - Controller for handling messages from the Telegram bot.

## Example Code

### WeatherConfig.java

```java
@Configuration
public class BotConfig {

    @Bean
    public TelegramBotsApi telegramBotsApi(TelegramLongPollingBot bot) throws TelegramApiException {
        TelegramBotsApi botsApi=new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(bot);

        return botsApi;
    }

}
