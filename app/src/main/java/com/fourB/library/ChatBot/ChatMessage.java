package com.fourB.library.ChatBot;

import ai.api.model.Result;

public class ChatMessage {

    private boolean mLeft;
    private Result msgResult;
    private String mMsg;

    public ChatMessage(boolean left, String defaultMsg, Result result) {
        super();
        this.mLeft = left;
        this.msgResult = result;
        this.mMsg = defaultMsg;
    }

    public boolean getSide() {
        return mLeft;
    }

    public Result getMsgResult() {
        return msgResult;
    }

    public String getMsg() { return mMsg; }
}
