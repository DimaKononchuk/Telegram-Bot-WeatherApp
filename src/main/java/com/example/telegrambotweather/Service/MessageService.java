package com.example.telegrambotweather.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class MessageService extends SendMessage {


    public void sendMessage(AbsSender absSender,long chatId, String text) {
        this.setChatId(String.valueOf(chatId));
       this.setText(text);
        try {
            absSender.execute(this);
        } catch (TelegramApiException e) {
            try {
                throw new TelegramApiException(e);
            } catch (TelegramApiException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void sendMessage(AbsSender absSender,SendMessage message) {
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            try {
                throw new TelegramApiException(e);
            } catch (TelegramApiException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void sendRemoveKeyboard(AbsSender absSender, long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        ReplyKeyboardRemove keyboardRemove = new ReplyKeyboardRemove();
        keyboardRemove.setRemoveKeyboard(true);
        message.setReplyMarkup(keyboardRemove);
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
