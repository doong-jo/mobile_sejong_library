package com.fourB.library.ChatBot;

import com.google.gson.JsonObject;

import ai.api.model.Result;

public interface ChatBotInterface {
    void preBotSpeech();
    void botSpeech(Result result);
    void botSpeechOfString(String msg);
    void userSpeech(String message);
}
