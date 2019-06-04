package com.fourB.library.ChatBot;

import com.google.gson.JsonObject;

public interface ChatBotInterface {
    void preBotSpeech();
    void botSpeech(String result, JsonObject customPayload);
    void userSpeech(String message);
}
