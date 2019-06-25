package com.fourB.library.Report;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fourB.library.R;
import com.fourB.library.Util.ReportManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ReportDialogActivity {

    private Activity mActivity;
    private Dialog mDialog;

    private Spinner mRoomSpinner, mReasonSpinner;
    private Button mAcceptBtn, mCancelBtn;
    private EditText mSeatEditText, mReasonEditText;
    private String mRoomName, mReason, mSeat, mReasonMsg;

    public ReportDialogActivity(Activity activity) {
        this.mActivity = activity;
    }

    private void initView() {
        mRoomSpinner = mDialog.findViewById(R.id.declareRoomSp);
        mReasonSpinner = mDialog.findViewById(R.id.declareReasonSp);
        mSeatEditText = mDialog.findViewById(R.id.declarePosition);
        mReasonEditText = mDialog.findViewById(R.id.declareContent);
        mAcceptBtn = mDialog.findViewById(R.id.declareButton);
        mCancelBtn = mDialog.findViewById(R.id.declareCancleButton);
    }

    private void initListener() {
        mAcceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setReportData();
                if(!mSeat.equals("")){
                    declareDialog();
                    mDialog.dismiss();
                } else { Toast.makeText(mActivity, mActivity.getString(R.string.report_request_type_seat), Toast.LENGTH_SHORT).show(); }
            }
        });


        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivity, mActivity.getString(R.string.chatbot_report_dialog_toast_no), Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
            }
        });
    }

    public void callFunction() {
        mDialog = new Dialog(mActivity);

        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();

        mDialog.setContentView(R.layout.activity_report);
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        initView();
        initListener();

        mDialog.show();

        ArrayAdapter<CharSequence> roomAdapter = ArrayAdapter.createFromResource(mActivity, R.array.report_rooms, R.layout.item_basic_spinner);
        roomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mRoomSpinner.setAdapter(roomAdapter);

        ArrayAdapter<CharSequence> reasonAdapter = ArrayAdapter.createFromResource(mActivity, R.array.report_reasons, R.layout.item_basic_spinner);
        reasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mReasonSpinner.setAdapter(reasonAdapter);
    }

    private void setReportData(){
        mRoomName = mRoomSpinner.getSelectedItem().toString();
        mReason = mReasonSpinner.getSelectedItem().toString();
        mSeat = mSeatEditText.getText().toString();
        mReasonMsg = mReasonEditText.getText().toString();
    }

    private void declareDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
            builder.setTitle(mActivity.getString(R.string.chatbot_report_dialog_title));
            builder.setMessage(mActivity.getString(R.string.chatbot_report_dialog_content));
            builder.setPositiveButton(mActivity.getString(R.string.chatbot_report_dialog_yes),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            setReportData();
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(mActivity.getApplicationContext(),
                                            mActivity.getString(R.string.chatbot_report_dialog_toast_yes),
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Date now = new Date();
                                    SimpleDateFormat format = new SimpleDateFormat("MM월 dd일 E요일 HH:mm:ss", Locale.KOREA);
                                    ReportManager.report(mRoomName, mSeat, mReason, mReasonMsg, format.format(now));
                                }
                            }).start();

                        }
                    });
            builder.setNegativeButton(mActivity.getString(R.string.chatbot_report_dialog_no),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(mActivity,
                                    mActivity.getString(R.string.chatbot_report_dialog_toast_no),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
            builder.show();
        }
}
