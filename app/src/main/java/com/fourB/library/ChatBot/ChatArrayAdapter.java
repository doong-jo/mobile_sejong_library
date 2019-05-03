package com.fourB.library.ChatBot;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fourB.library.R;

import java.util.ArrayList;

public class ChatArrayAdapter extends ArrayAdapter {

    private TextView mChatText;
    private ArrayList<ChatMessage> mChatMessageList = new ArrayList<ChatMessage>();
    private LinearLayout mSingleMessageContainer;

    public void add(ChatMessage object) {
        super.add(object);
        mChatMessageList.add(object);
    }

    public ChatArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public int getCount() {
        return this.mChatMessageList.size();
    }

    public ChatMessage getItem(int index) {
        return this.mChatMessageList.get(index);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.activity_chat_msg, parent, false);
        }

        mSingleMessageContainer = (LinearLayout) row.findViewById(R.id.msg_container);
        mChatText = (TextView) row.findViewById(R.id.msg_body);

        initChatData(position);

        return row;
    }

    public void initChatData(int position) {
        ChatMessage chatMessageObj = getItem(position);
        final String msg = chatMessageObj.getMsg();
        final boolean side = chatMessageObj.getSide();

        mChatText.setText(msg);
        mChatText.setBackgroundResource(side ? R.drawable.rounded_rectangle_green : R.drawable.rounded_rectangle_orange);
        mSingleMessageContainer.setGravity(side ? Gravity.START : Gravity.END);
    }

}