package com.fourB.library.StudyRoom;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.fourB.library.R;

import java.util.ArrayList;
import java.util.Calendar;

public class StudyRoomActivity extends AppCompatActivity implements View.OnClickListener{

    private Spinner mUsingTimeSpinner;
    ArrayList<String> mUsingTimeArrayList;
    ArrayAdapter<String> mUsingTimeArrayAdapter;

    private Spinner mUserCountSpinner;
    private ArrayList<String> mUserCountArrayList;
    private ArrayAdapter<String> mUserCountArrayAdapter;


    private TextView mUsingDayText, mUsingTimeText;
    private Button mUsingDayButton, mUsingTimeButton;

    private int mHour,mMinute;

    Calendar mCalendar;
    DatePickerDialog mStudyRoomdpd;
    TimePickerDialog mStudytpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_room_check);

        mUsingDayButton = (Button) findViewById(R.id.studyroom_btndate);
        mUsingTimeButton = (Button) findViewById(R.id.studyroom_btntime);

        mUsingDayText = (TextView) findViewById(R.id.studyroom_txtusingday);
        mUsingTimeText = (TextView) findViewById(R.id.studyroom_txtusingtime);

        Button button = (Button) findViewById(R.id.reserve);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StudyRoomRVActivity.class);
                startActivity(intent);
            }
        });

        mUsingDayButton.setOnClickListener(this);
        mUsingTimeButton.setOnClickListener(this);

        mUsingTimeArrayList = new ArrayList<>();

        mUsingTimeArrayList.add("1시간");
        mUsingTimeArrayList.add("2시간");

        mUsingTimeArrayAdapter= new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                mUsingTimeArrayList);

        mUsingTimeSpinner = (Spinner)findViewById(R.id.usingtimespinner);
        mUsingTimeSpinner.setAdapter(mUsingTimeArrayAdapter);

//        mUsingTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getApplicationContext(), "이용시간 "+(i+1)+"시간",
//                        Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//            }
//        });

        mUserCountArrayList = new ArrayList<>();


        for(int i=2; i<11; i++)
            mUserCountArrayList.add(i+"명 ");

        mUserCountArrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                mUserCountArrayList);

        mUserCountSpinner = (Spinner)findViewById(R.id.usercountspinner);
        mUserCountSpinner.setAdapter(mUserCountArrayAdapter);

//        mUserCountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getApplicationContext(), "인원 "+(i+2)+"명",
//                        Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//            }
//        });

    }

    @Override
    public void onClick(View v) {
        if (v == mUsingDayButton) {
            mCalendar = Calendar.getInstance();
            int day = mCalendar.get(Calendar.DAY_OF_MONTH);
            int month = mCalendar.get(Calendar.MONTH);
            int year = mCalendar.get(Calendar.YEAR);

            mStudyRoomdpd = new DatePickerDialog(StudyRoomActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                    mUsingDayText.setText(mYear + "/" + (mMonth + 1) + "/" + mDay);
                }
            }, year, month, day);
            mStudyRoomdpd.show();
        }

        if(v == mUsingTimeButton) {
            mCalendar= Calendar.getInstance();
            mHour=mCalendar.get(Calendar.HOUR_OF_DAY);


            mStudytpd = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute ){

                    mUsingTimeText.setText(hourOfDay+": 00");

                }
            },mHour,mMinute,false);
            mStudytpd.show();
        }
    }
}


