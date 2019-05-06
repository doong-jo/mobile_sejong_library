package com.fourB.library.ChatBot;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fourB.library.R;

import java.util.ArrayList;

public class ChatArrayAdapter extends ArrayAdapter {

    private TextView mChatTv;
    private TextView mNameTv;
    private ImageView mProfileImg;
    private ArrayList<ChatMessage> mChatMessageList = new ArrayList<ChatMessage>();
    private LinearLayout mSingleMessageContainer;
    private ProgressBar mProgressBar;

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
            row = inflater.inflate(R.layout.activity_chat_bot_msg, parent, false);
        }

        mSingleMessageContainer = row.findViewById(R.id.msg_container);
        mChatTv = row.findViewById(R.id.msg_body);
        mNameTv = row.findViewById(R.id.msg_name);
        mProfileImg = row.findViewById(R.id.msg_profile);
        mProgressBar = row.findViewById(R.id.msg_loading);

        initChatData(position);

        return row;
    }

    private void initChatData(int position) {
        ChatMessage chatMessageObj = getItem(position);
        final String msg = chatMessageObj.getMsg();
        final boolean side = chatMessageObj.getSide();

        if( side ) {
            mNameTv.setText(getContext().getString(R.string.chatbot_bot_name));
            mProfileImg.setImageResource(R.mipmap.ic_launcher);
        } else {
            mNameTv.setText("");
            mProfileImg.setImageResource(0);
//            mNameTv.setText(getContext().getString(R.string.chatbot_bot_name));
            // mNameTv.setVisibility(GONE);
        }


        mChatTv.setText(msg);
        mChatTv.setBackgroundResource(side ? R.drawable.rounded_rectangle_green : R.drawable.rounded_rectangle_orange);
        mSingleMessageContainer.setGravity(side ? Gravity.START : Gravity.END);
    }

}