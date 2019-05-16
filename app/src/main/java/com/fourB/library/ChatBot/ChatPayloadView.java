package com.fourB.library.ChatBot;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fourB.library.R;

public class ChatPayloadView extends LinearLayout {

    private String mType;
    private String mText;
    private String mName;

    private ChatBotActivity parentContext;

    static final String DIALOG = "dialog";
    static final String ACTIVITY = "activity";
    static final String DIALOG_REPORT = "report";

    public ChatPayloadView(ChatBotActivity context, String type, String text, String name) {
        super(context);
        mType = type;
        mText = text;
        mName = name;

        parentContext = context;

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
                if( mType.equals(DIALOG) ) {
                    payloadDialog(mName);
                } else if(mType.equals(ACTIVITY)) {
                    Intent res = new Intent();
                    String _Package = getContext().getPackageName();
                    String _Class = mName;
                    res.setClassName(_Package, _Package + _Class);
                    res.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(res);
                }
            }
        });
    }

    private void payloadDialog(String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(parentContext);

        if( name.equals(DIALOG_REPORT) ) {

            builder.setTitle(parentContext.getString(R.string.chatbot_report_dialog_title));
            builder.setMessage(parentContext.getString(R.string.chatbot_report_dialog_content));
            builder.setPositiveButton(parentContext.getString(R.string.chatbot_report_dialog_yes),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(parentContext,
                                    parentContext.getString(R.string.chatbot_report_dialog_toast_yes),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
            builder.setNegativeButton(parentContext.getString(R.string.chatbot_report_dialog_no),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(parentContext,
                                    parentContext.getString(R.string.chatbot_report_dialog_toast_no),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
            builder.show();
        }
    }
}
