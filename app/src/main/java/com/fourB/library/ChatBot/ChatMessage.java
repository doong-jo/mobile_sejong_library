package com.fourB.library.ChatBot;

import com.google.gson.JsonObject;

public class ChatMessage {

    private boolean mLeft;
    private String mMsg;
    private JsonObject mPayload;

    public ChatMessage(boolean left, String message, JsonObject customPayload) {
        super();
        this.mLeft = left;
        this.mMsg = message;
        this.mPayload = customPayload;
    }

    public boolean getSide() {
        return mLeft;
    }

    public String getMsg() {
        return mMsg;
    }

    public JsonObject getPayload() { return mPayload; }
}
