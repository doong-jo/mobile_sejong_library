package com.fourB.library;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatArrayAdapter extends ArrayAdapter {

    private TextView mChatText;
    private ArrayList<ChatMessage> mChatMessageList = new ArrayList<ChatMessage>();
    private LinearLayout mSingleMessageContainer;

    public void add(ChatMessage object) {
        mChatMessageList.add(object);
        super.add(object);
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
            row = inflater.inflate(R.layout.activity_chat_singlemessage, parent, false);
        }

        mSingleMessageContainer = (LinearLayout) row.findViewById(R.id.singleMessageContainer);
        ChatMessage chatMessageObj = getItem(position);
        mChatText = (TextView) row.findViewById(R.id.text_message_body);
        mChatText.setText(chatMessageObj.message);
        mChatText.setBackgroundResource(chatMessageObj.left ? R.drawable.rounded_rectangle_green : R.drawable.rounded_rectangle_orange);
        mSingleMessageContainer.setGravity(chatMessageObj.left ? Gravity.LEFT : Gravity.RIGHT);
        return row;
    }

}