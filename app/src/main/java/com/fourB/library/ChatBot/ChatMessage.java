package com.fourB.library.ChatBot;

public class ChatMessage {

    private boolean mLeft;
    private String mMsg;

    public ChatMessage(boolean left, String message) {
        super();
        this.mLeft = left;
        this.mMsg = message;
    }

    public boolean getSide() {
        return mLeft;
    }

    public String getMsg() {
        return mMsg;
    }
}
