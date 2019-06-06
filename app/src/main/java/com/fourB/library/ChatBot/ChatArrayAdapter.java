package com.fourB.library.ChatBot;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fourB.library.R;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import ai.api.model.ResponseMessage;

public class ChatArrayAdapter extends ArrayAdapter {

    private TextView mChatTv;
    private ArrayList<ChatMessage> mChatMessageList = new ArrayList<>();
    private String mUserLastMsg;

    private Activity parentContext;

    public void add(ChatMessage object) {
        super.add(object);
        mChatMessageList.add(object);
    }

    public ChatArrayAdapter(Activity context, int textViewResourceId) {
        super(context, textViewResourceId);
        parentContext = context;
    }

    public void remove(int pos) {
        super.remove(getItem(pos));
        mChatMessageList.remove(pos);
    }

    public int getCount() {
        return this.mChatMessageList.size();
    }

    public ChatMessage getItem(int index) {
        return this.mChatMessageList.get(index);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMessage chatMessageObj = getItem(position);
        String msg = chatMessageObj.getMsg();
        if( msg.equals("") && chatMessageObj.getMsgResult() != null ) { msg = chatMessageObj.getMsgResult().getFulfillment().getSpeech(); }
        final boolean side = chatMessageObj.getSide();

        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row;
        mChatTv = null;
        if( side ) { // BOT
            if( msg.equals("") ) { // LOADING
                row = inflater.inflate(R.layout.layout_chat_bot_loading_msg, parent, false);
            } else {
                row = inflater.inflate(R.layout.layout_chat_bot_msg, parent, false);
                mChatTv = row.findViewById(R.id.msg_body);
            }
        } else { // USER
            row = inflater.inflate(R.layout.layout_chat_user_msg, parent, false);
            mChatTv = row.findViewById(R.id.msg_body);
            mUserLastMsg = msg;
        }

        if( mChatTv != null ) {
            mChatTv.setText(Html.fromHtml(msg));
            mChatTv.setBackgroundResource(side ? R.drawable.rounded_rectangle_green : R.drawable.rounded_rectangle_orange);
        }

        JsonObject customPayload = null;
        if ( chatMessageObj.getMsgResult() != null ) {
            List<ResponseMessage> responseMessageList = chatMessageObj.getMsgResult().getFulfillment().getMessages();

            if (responseMessageList.size() >= 2) {
                ResponseMessage.ResponsePayload payloadMsg = (ResponseMessage.ResponsePayload) responseMessageList.get(1);
                customPayload = payloadMsg.getPayload();
            }
        }

        JsonObject payloadJson = customPayload;
        if( payloadJson == null ) {
            return row;
        } else {
            JsonObject message = payloadJson.getAsJsonObject("message");

            ChatPayloadView payload = new ChatPayloadView(parentContext, message, chatMessageObj.getMsgResult());

            LinearLayout li = new LinearLayout(getContext());
            li.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            li.setOrientation(LinearLayout.VERTICAL);
            li.addView(row);
            li.addView(payload);

            return li;
        }
    }

}