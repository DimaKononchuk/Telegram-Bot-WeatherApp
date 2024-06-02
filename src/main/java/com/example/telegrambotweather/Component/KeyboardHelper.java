package com.example.telegrambotweather.Component;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;


/**
 * Helper class, allows to build keyboards for users
 */
@Component
public class KeyboardHelper {

    public ReplyKeyboardMarkup buildCitiesMenu(List<String> cities) {
        List<KeyboardButton> buttons = List.of(
                new KeyboardButton("Київ"),
                new KeyboardButton("Львів"));
        KeyboardRow row1 = new KeyboardRow(buttons);

        KeyboardRow row2 = new KeyboardRow(List.of(new KeyboardButton()));//BTN_CANCEL

        return ReplyKeyboardMarkup.builder()
                .keyboard(List.of(row1, row2))
                .selective(true)
                .resizeKeyboard(true)
                .oneTimeKeyboard(true)
                .build();
    }

    public ReplyKeyboardMarkup buildMainMenu() {
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add("Current Weather Data");

        return ReplyKeyboardMarkup.builder()
                .keyboard(List.of(keyboardRow))
                .selective(true)
                .resizeKeyboard(true)
                .oneTimeKeyboard(false)
                .build();
    }

    public ReplyKeyboardMarkup buildMenuTypeWeather(){
        List<KeyboardButton> buttonsColumn1 = List.of(
                new KeyboardButton("Current Weather Data"),
                new KeyboardButton("Forecast 4 days"));
        List<KeyboardButton> buttonsColumn2 = List.of(
                new KeyboardButton("Forecast 16 days"),
                new KeyboardButton("Forecast 30 days"));

        KeyboardRow row1 = new KeyboardRow(buttonsColumn1);

        KeyboardRow row2 = new KeyboardRow(buttonsColumn2);//BTN_CANCEL
        return ReplyKeyboardMarkup.builder()
                .keyboard(List.of(row1,row2))
                .selective(true)
                .resizeKeyboard(true)
                .oneTimeKeyboard(false)
                .build();

    }
    public ReplyKeyboardMarkup buildMenuWithCancel() {
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add("BTN_CANCEL");

        return ReplyKeyboardMarkup.builder()
                .keyboard(List.of(keyboardRow))
                .selective(true)
                .resizeKeyboard(true)
                .oneTimeKeyboard(false)
                .build();
    }
}
