package com.example.telegrambotweather.Service;

import com.example.telegrambotweather.sender.WeatherSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

/**
 * This service allows to communicate with Telegram API
 */
@Slf4j
@Component
public class TelegramService {

    private final WeatherSender botSender;

    public TelegramService(WeatherSender botSender) {
        this.botSender = botSender;
    }

    public void sendMessage(Long chatId, String text) {
        sendMessage(chatId, text, null);
    }
    public void sendMessage(Long chatId) {
        SendMessage sendMessage =SendMessage
                .builder()
                .chatId(chatId.toString())

                .build();

        execute(sendMessage);
    }
    public void sendMessage(Long chatId, String text, ReplyKeyboard replyKeyboard) {
        SendMessage sendMessage = SendMessage
                .builder()
                .text(text)
                .chatId(chatId.toString())
                //Other possible parse modes: MARKDOWNV2, MARKDOWN, which allows to make text bold, and all other things

                .replyMarkup(replyKeyboard)
                .build();
        execute(sendMessage);
    }


    private void execute(BotApiMethod botApiMethod) {
        try {
            botSender.execute(botApiMethod);
        } catch (Exception e) {
            log.error("Exception: ", e);
        }
    }

    public void execute(DeleteMessage deleteMessage) {
        try {
            botSender.execute(deleteMessage);
        } catch (Exception e) {
            log.error("Exception: ", e);
        }
    }



}
