package com.fourB.library.ChatBot;

import android.content.Context;
import android.support.annotation.Nullable;
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
    private ArrayList<ChatMessage> mChatMessageList = new ArrayList<>();

    public void add(ChatMessage object) {
        super.add(object);
        mChatMessageList.add(object);
    }

    public ChatArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
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
        final String msg = chatMessageObj.getMsg();
        final boolean side = chatMessageObj.getSide();

        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row = side ?
                !msg.equals("") ?
                        inflater.inflate(R.layout.layout_chat_bot_msg, parent, false) :
                        inflater.inflate(R.layout.layout_chat_bot_loading_msg, parent, false) :
            inflater.inflate(R.layout.layout_chat_user_msg, parent, false);

        mChatTv = !side || !msg.equals("") ? (TextView)row.findViewById(R.id.msg_body) : null;
        if( mChatTv != null ) {
            mChatTv.setText(msg);
            mChatTv.setBackgroundResource(side ? R.drawable.rounded_rectangle_green : R.drawable.rounded_rectangle_orange);
        }

        return row;
    }

}