package com.fourB.library.ChatBot;

public interface ChatBotService {
    void preBotSpeech();
    void botSpeech(String result);
    void userSpeech(String message);
}
