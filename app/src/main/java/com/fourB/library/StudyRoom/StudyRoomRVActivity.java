package com.fourB.library.StudyRoom;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.fourB.library.R;

import java.util.ArrayList;
import java.util.Calendar;

public class StudyRoomRVActivity extends AppCompatActivity implements View.OnClickListener{

    private Spinner mUsingTimeSpinnerRv;
    ArrayList<String> mUsingTimeArrayListRv;
    ArrayAdapter<String> mUsingTimeArrayAdapterRv;

    TextView mUsingTimeTextRv;
    Button mUsingTimeRVButton;

    private int mHour,mMinute;

    Calendar mCalendar;
    TimePickerDialog mStudyRoomRVtpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_room_rv);

        mUsingTimeRVButton = findViewById(R.id.studyroomRV_btntime);
        mUsingTimeTextRv = findViewById(R.id.studyroomRV_timetext);


        mUsingTimeRVButton.setOnClickListener(this);

        mUsingTimeArrayListRv = new ArrayList<>();

        mUsingTimeArrayListRv.add("1시간");
        mUsingTimeArrayListRv.add("2시간");

        mUsingTimeArrayAdapterRv = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                mUsingTimeArrayListRv);

        mUsingTimeSpinnerRv= (Spinner)findViewById(R.id.spNum2);
        mUsingTimeSpinnerRv.setAdapter(mUsingTimeArrayAdapterRv);

//        mUsingTimeSpinnerRv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getApplicationContext(), "이용시간 "+(i+1)+"시간",
//                        Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//            }
//        });

    }

    @Override
    public void onClick(View v) {

        mCalendar= Calendar.getInstance();
        mHour=mCalendar.get(Calendar.HOUR_OF_DAY);


        mStudyRoomRVtpd = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute ){

                mUsingTimeTextRv.setText(hourOfDay+": 00");

            }
        },mHour,mMinute,false);
        mStudyRoomRVtpd.show();

    }
}

/*

 */