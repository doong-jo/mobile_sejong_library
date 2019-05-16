package com.fourB.library.ChatBot;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fourB.library.R;

public class ChatPayloadView extends LinearLayout {

    private String mText;
    private String mActivityName;

    public ChatPayloadView(Context context, String text, String activityName) {
        super(context);
        mText = text;
        mActivityName = activityName;
        initView();
    }

    public ChatPayloadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ChatPayloadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = li.inflate(R.layout.layout_chat_bot_payload, this, false); addView(v);
        TextView btnText = v.findViewById(R.id.button_text);
        btnText.setText(mText);
        btnText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent res = new Intent();
                String _Package = "com.fourB.library.ReadingRoom";
                String _Class = ".ReadingRoomActivity";
                res.setClassName(_Package, _Class);
                getContext().startActivity(res);
            }
        });
    }
}
