package com.fourB.library.StudyRoom;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;

import com.fourB.library.R;

import java.util.ArrayList;
import java.util.Calendar;

public class StudyRoomActivity extends AppCompatActivity {

    private Spinner mUsingTimeSpinner, mUserCountSpinner;
    private ArrayList<String> mUsingTimeArrayList = new ArrayList<>();
    private ArrayList<String> mUserCountArrayList = new ArrayList<>();
    private ArrayAdapter<String> mUsingTimeArrayAdapter, mUserCountArrayAdapter;


    private EditText mUsingDayText, mUsingTimeText;
    private Button mUsingDayButton, mUsingTimeButton;

    Calendar mCalendar;
    DatePickerDialog mStudyRoomdpd;
    TimePickerDialog mStudytpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_room_check);

        mUsingTimeArrayList.add("1시간");
        mUsingTimeArrayList.add("2시간");

        for(int i=2; i<11; i++) { mUserCountArrayList.add(i+"명 "); }

        initView();
        initListener();


        mUsingTimeArrayAdapter= new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                mUsingTimeArrayList);

        mUsingTimeSpinner = findViewById(R.id.usingtimespinner);
        mUsingTimeSpinner.setAdapter(mUsingTimeArrayAdapter);

        mUserCountArrayList = new ArrayList<>();




        mUserCountArrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                mUserCountArrayList);

        mUserCountSpinner = findViewById(R.id.usercountspinner);
        mUserCountSpinner.setAdapter(mUserCountArrayAdapter);
    }

    private void initView() {
        mUsingDayButton = findViewById(R.id.studyroom_btndate);
        mUsingTimeButton = findViewById(R.id.studyroom_btntime);

        mUsingDayText = findViewById(R.id.studyroom_txtusingday);
        mUsingTimeText = findViewById(R.id.studyroom_txtusingtime);
        mUsingDayText.setKeyListener(null);
        mUsingTimeText.setKeyListener(null);
    }

    private void initListener() {
        mUsingDayText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                final int day = cal.get(Calendar.DAY_OF_MONTH);
                final int month = cal.get(Calendar.MONTH);
                final int year = cal.get(Calendar.YEAR);

                mStudyRoomdpd = new DatePickerDialog(mUsingDayButton.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        mUsingDayText.setText(mYear + "/" + (mMonth + 1) + "/" + mDay);
                    }
                }, year, month, day);
                mStudyRoomdpd.show();
            }
        });

        mUsingTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                final int hour = cal.get(Calendar.HOUR_OF_DAY);

                mStudytpd = new TimePickerDialog(mUsingTimeButton.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute ){
                        mUsingTimeText.setText(hourOfDay + ": 00");
                    }
                }, hour, 0,false);
                mStudytpd.show();
            }
        });
    }
}
