package com.fourB.library.ChatBot;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fourB.library.Barcode.CustomScannerActivity;
import com.fourB.library.HttpManager;
import com.fourB.library.MainActivity;
import com.fourB.library.R;
import com.fourB.library.ReportManager;
import com.google.gson.JsonObject;
import com.google.zxing.integration.android.IntentIntegrator;

import java.text.SimpleDateFormat;
import java.util.Date;

import ai.api.model.Result;

public class ChatPayloadView extends LinearLayout {

    private String mType;
    private String mText;
    private String mName;

    private Activity parentContext;
    private Result mResponseData;

    static final String DIALOG = "dialog";
    static final String ACTIVITY = "activity";
    static final String DIALOG_REPORT = "report";

    public ChatPayloadView(final Activity context, final JsonObject payloadJson, final Result responseData) {
        super(context);
        mType = payloadJson.get("type").getAsString();
        mText = payloadJson.get("text").getAsString();
        mName = payloadJson.get("name").getAsString();

        parentContext = context;
        mResponseData = responseData;

        initView();
    }

    public ChatPayloadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ChatPayloadView(Context context, AttributeSet attrs, int defStyleAttr) {
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

                    if(mName.equals(".StudyRoom.StudyRoomViewActivity")) {
                        res.putExtra("person", mResponseData.getStringParameter("user"));
                        final String theTime = mResponseData.getStringParameter("TheTime");
                        int time = 0;
                        if( theTime.split(" ")[0].equals("오전") ) {
                            time = Integer.valueOf(theTime.split(" ")[1].split("시")[0]);
                        } else {
                            time = Integer.valueOf(theTime.split(" ")[1].split("시")[0]) + 12;
                        }
                        res.putExtra("time", String.valueOf(time) + "시");
                        res.putExtra("use", "1시간");

                        final Date today = new Date();
                        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
                        res.putExtra("date", sdf.format(today));
                        getContext().startActivity(res);
                    }
                    else if( mName.equals(".ReadingRoom.ReadingRoomActivity")) {
                        final String room = mResponseData.getStringParameter("StudyRoom");
                        res.putExtra("room", room);
                        getContext().startActivity(res);
                    } else if ( mName.equals(".Barcode.CustomScannerActivity")) {
                        IntentIntegrator integrator = new IntentIntegrator(parentContext);
                        integrator.setCaptureActivity(CustomScannerActivity.class);
                        integrator.setOrientationLocked(false);
                        integrator.initiateScan();
                    } else {
                        getContext().startActivity(res);
                    }


                }
            }
        });
    }

    private void payloadDialog(String name) {
        if( name.equals(DIALOG_REPORT) ) {
            AlertDialog.Builder builder = new AlertDialog.Builder(parentContext);
            builder.setTitle(parentContext.getString(R.string.chatbot_report_dialog_title));
            builder.setMessage(parentContext.getString(R.string.chatbot_report_dialog_content));
            builder.setPositiveButton(parentContext.getString(R.string.chatbot_report_dialog_yes),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String room = mResponseData.getStringParameter("StudyRoom");
                                    final String roomNum = mResponseData.getStringParameter("number");
                                    final String reason = mResponseData.getStringParameter("ReportReason");
                                    final String content = mResponseData.getStringParameter("ReportReasonContent");
                                    final Date today = new Date();
                                    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm");

                                    ReportManager.report(room, roomNum, reason, content, sdf.format(today));
                                }
                            }).start();

                            Toast.makeText(parentContext,
                                    parentContext.getString(R.string.chatbot_report_dialog_toast_yes),
                                    Toast.LENGTH_LONG).show();
//                            Toast.makeText(parentContext,
//                                    parentContext.getString(R.string.chatbot_report_dialog_toast_yes),
//                                    Toast.LENGTH_LONG).show();
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
