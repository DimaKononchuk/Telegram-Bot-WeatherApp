package com.example.telegrambotweather.Component;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;


/**
 * Helper class, allows to build keyboards for users
 */
@Component
public class KeyboardHelper {
    public static String  cancel_data="Cancel\uD83D\uDDD9";

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
                new KeyboardButton("3-hour Forecast 5 days"));

        KeyboardRow row1 = new KeyboardRow(buttonsColumn1);


        return ReplyKeyboardMarkup.builder()
                .keyboard(List.of(row1))
                .selective(true)
                .resizeKeyboard(true)
                .oneTimeKeyboard(true)
                .build();

    }
    public ReplyKeyboardMarkup buildMenuWithCancel(LinkedHashSet<String> list) {
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(cancel_data);
        KeyboardRow keyboardRow1 = new KeyboardRow();
        List<String> list1;
        for (String city:list){
            keyboardRow1.add(city);

        }
        return ReplyKeyboardMarkup.builder()
                .keyboard(List.of(keyboardRow1,keyboardRow))
                .selective(true)
                .resizeKeyboard(true)
                .oneTimeKeyboard(false)
                .build();
    }
    public ReplyKeyboardMarkup buildMenuWithCancel() {
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(cancel_data);

        return ReplyKeyboardMarkup.builder()
                .keyboard(List.of(keyboardRow))
                .selective(true)
                .resizeKeyboard(true)
                .oneTimeKeyboard(false)
                .build();
    }
    public InlineKeyboardMarkup buildMenuListDate(List<LocalDate> dateSet) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (LocalDate date : dateSet) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(date.toString());
            button.setCallbackData(date.toString());

            List<InlineKeyboardButton> rowInline = Collections.singletonList(button);
            rowsInline.add(rowInline);
        }

        return InlineKeyboardMarkup.builder()
                .keyboard(rowsInline)
                .build();
    }
    public ReplyKeyboardMarkup ReplybuildMenuListDate(List<LocalDate> dateSet) {
        List<KeyboardRow> keyboard = new ArrayList<>();

        for (LocalDate date : dateSet) {
            KeyboardRow row = new KeyboardRow();
            KeyboardButton button = new KeyboardButton(date.toString());
            row.add(button);
            keyboard.add(row);
        }
        KeyboardRow row = new KeyboardRow();
        KeyboardButton button = new KeyboardButton(cancel_data);
        row.add(button);
        keyboard.add(row);
        return ReplyKeyboardMarkup.builder()
                .keyboard(keyboard)
                .selective(true)
                .resizeKeyboard(true)
                .oneTimeKeyboard(false)
                .build();
    }
    public ReplyKeyboardMarkup ReplybuildMenuListTime(List<LocalTime> timeSet) {
        List<KeyboardRow> keyboard = new ArrayList<>();

        for (LocalTime date : timeSet) {
            KeyboardRow row = new KeyboardRow();
            KeyboardButton button = new KeyboardButton(date.toString());
            row.add(button);
            keyboard.add(row);
        }
        KeyboardRow row = new KeyboardRow();
        KeyboardButton button = new KeyboardButton(cancel_data);
        row.add(button);
        keyboard.add(row);
        return ReplyKeyboardMarkup.builder()
                .keyboard(keyboard)
                .selective(true)
                .resizeKeyboard(true)
                .oneTimeKeyboard(false)
                .build();
    }
    public InlineKeyboardMarkup buildMenuListTime(List<LocalDate> timeSet) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (LocalDate date : timeSet) {
            InlineKeyboardButton button = new InlineKeyboardButton(date.toString());
            button.setCallbackData("date.toString()");

            List<InlineKeyboardButton> rowInline = (List<InlineKeyboardButton>) button;
            rowsInline.add(rowInline);

        }
        KeyboardRow row=new KeyboardRow();
        InlineKeyboardButton inlineKeyboardButton=new InlineKeyboardButton();
        inlineKeyboardButton.setText("fd");
        inlineKeyboardButton.setCallbackData("gd");
        row.add(String.valueOf(inlineKeyboardButton));
        return InlineKeyboardMarkup.builder()
                .keyboard((Collection<? extends List<InlineKeyboardButton>>) inlineKeyboardButton)
                .build();
    }
}
