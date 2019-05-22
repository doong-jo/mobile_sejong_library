package com.fourB.library;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class DeclareDialogActivity  {

    private Context context;
    private Spinner mdeclareSpinner;
    private Spinner mReasonSpinner;
    private Button mbuttonSend;
    private Button mbuttonCancle;
    private EditText mdeclarePostion;
    private EditText mdeclareContent;
    private String mRoomNum;
    private String mReason;
    private String mPosition;
    private String declareMsg;

    public DeclareDialogActivity(Context context) {
        this.context = context;
    }

    public void callFunction() {

        final Dialog dlg = new Dialog(context);

    dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
    WindowManager.LayoutParams params = dlg.getWindow().getAttributes();

    dlg.setContentView(R.layout.activity_declare);
    params.width = WindowManager.LayoutParams.MATCH_PARENT;

    dlg.show();

    mdeclareSpinner = (Spinner) dlg.findViewById(R.id.declareRoomSp);
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.Declare_array, R.layout.declare_spinner_item);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    mdeclareSpinner.setAdapter(adapter);

    mReasonSpinner = (Spinner) dlg.findViewById(R.id.declareReasonSp);
    ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(context, R.array.declare_reason_array, R.layout.declare_spinner_item);
    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    mReasonSpinner.setAdapter(adapter1);

    mdeclarePostion = (EditText)dlg.findViewById(R.id.declarePosition);

    mdeclareContent = (EditText)dlg.findViewById(R.id.declareContent);

    mbuttonSend = (Button) dlg.findViewById(R.id.declareButton);
    mbuttonSend.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            getDeclareData();

            if(!mPosition.equals("") && !declareMsg.equals("")){
                declareDialog();
                dlg.dismiss();
            } else if(mPosition.equals("") && !declareMsg.equals("")){
                Toast.makeText(context, "자리 번호를 입력해주세요!", Toast.LENGTH_SHORT).show();
            } else if(!mPosition.equals("") && declareMsg.equals("")){
                Toast.makeText(context, "신고 내용을 입력해주세요!", Toast.LENGTH_SHORT).show();
            }else if(mPosition.equals("") && declareMsg.equals("")){
                Toast.makeText(context, "자리 번호와 신고 내용을 입력해주세요!", Toast.LENGTH_SHORT).show();
            }
        }
    });

    mbuttonCancle = (Button) dlg.findViewById(R.id.declareCancleButton);
    mbuttonCancle.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(context, "취소되었습니다.", Toast.LENGTH_SHORT).show();

            dlg.dismiss();
        }
    });
    }

    private void getDeclareData(){
        mRoomNum = mdeclareSpinner.getSelectedItem().toString();
        mReason = mReasonSpinner.getSelectedItem().toString();
        mPosition = mdeclarePostion.getText().toString();
        declareMsg = mdeclareContent.getText().toString();
    }

    private void declareDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);


            builder.setTitle(context.getString(R.string.chatbot_report_dialog_title));
            builder.setMessage(context.getString(R.string.chatbot_report_dialog_content));
            builder.setPositiveButton(context.getString(R.string.chatbot_report_dialog_yes),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(context,
                                    context.getString(R.string.chatbot_report_dialog_toast_yes),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
            builder.setNegativeButton(context.getString(R.string.chatbot_report_dialog_no),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(context,
                                    context.getString(R.string.chatbot_report_dialog_toast_no),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
            builder.show();
        }
}
