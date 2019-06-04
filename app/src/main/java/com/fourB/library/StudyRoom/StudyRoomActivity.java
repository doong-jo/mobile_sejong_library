package com.fourB.library.StudyRoom;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;

import com.fourB.library.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class StudyRoomActivity extends AppCompatActivity {
    public static int REQUEST_CODE_RESERVE_INFO = 0;

    private Spinner mUseTimeSpinner, mUserNumSpinner;
    private EditText mUsingDayText, mUsingTimeText;
    private DatePickerDialog mDatePickerDlg;
    private TimePickerDialog mTimePickerDlg;
    private Button mSearchBtn;

    private ArrayList<String> mUseTimeArrayList = new ArrayList<>();
    private ArrayList<String> mUserNumArrayList = new ArrayList<>();
    private ArrayAdapter<String> mUseTimeArrayAdapter, mUserNumArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_room_check);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();
        initListener();

        // set time -> time spinner
        String[] availableHours = getResources().getStringArray(R.array.study_room_available_hour);
        mUseTimeArrayList.addAll(Arrays.asList(availableHours));

        mUseTimeArrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.item_basic_spinner,
                mUseTimeArrayList);

        // set person -> person spinner
        final int capacityMin = getResources().getInteger(R.integer.study_room_capacity_min);
        final int capacityMax = getResources().getInteger(R.integer.study_room_capacity_max);
        for(int i=capacityMin; i<=capacityMax; ++i) { mUserNumArrayList.add(i+"명 "); }

        mUserNumArrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.item_basic_spinner,
                mUserNumArrayList);

        mUseTimeSpinner.setAdapter(mUseTimeArrayAdapter);
        mUserNumSpinner.setAdapter(mUserNumArrayAdapter);
    }

    private void initView() {
        mUsingDayText = findViewById(R.id.studyroom_txtusingday);
        mUsingTimeText = findViewById(R.id.studyroom_txtusingtime);
        mUsingDayText.setKeyListener(null);
        mUsingTimeText.setKeyListener(null);

        mUseTimeSpinner = findViewById(R.id.usingtimespinner);
        mUserNumSpinner = findViewById(R.id.usercountspinner);
        mSearchBtn = findViewById(R.id.study_room_button_search);

        final Calendar cal = Calendar.getInstance();
        final int min = cal.get(Calendar.MINUTE);
        final int hour = cal.get(Calendar.HOUR_OF_DAY);
        final int day = cal.get(Calendar.DAY_OF_MONTH);
        final int month = cal.get(Calendar.MONTH);
        final int year = cal.get(Calendar.YEAR);

        mUsingDayText.setText(getReserveDate(year, month, day));
        mUsingTimeText.setText(getReserveTime(hour));

        mDatePickerDlg = new DatePickerDialog(mUsingDayText.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                mUsingDayText.setText(getReserveDate(year, month, day));
            }
        }, year, month, day);
        mDatePickerDlg.updateDate(year, month, day);

        mTimePickerDlg = new TimePickerDialog(mUsingTimeText.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute ) {
                mUsingTimeText.setText(getReserveTime(hourOfDay));
            }
        }, hour, 0,false);
        mTimePickerDlg.updateTime(hour, min);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initListener() {
        mUsingDayText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDatePickerDlg.show();
                return true;
            }
        });

        mUsingTimeText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mTimePickerDlg.show();
                return true;
            }
        });

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mSearchBtn.getContext(), StudyRoomViewActivity.class);
                intent.putExtra("date", mUsingDayText.getText().toString());
                intent.putExtra("time", mUsingTimeText.getText().toString().split(" ")[0]);
                intent.putExtra("person", mUserNumSpinner.getSelectedItem().toString());
                intent.putExtra("use", mUseTimeSpinner.getSelectedItem().toString());

                startActivityForResult(intent, REQUEST_CODE_RESERVE_INFO);
            }
        });
    }

    private String getReserveDate(final int year, final int month, final int day) {
        StringBuilder builder = new StringBuilder(16);
        builder.append(year).append("년 ").append(month + 1).append("월 ").append(day).append("일");
        return builder.toString();
    }

    private String getReserveTime(final int hourOfDay) {
        StringBuilder builder = new StringBuilder(32);
        builder.append(hourOfDay).append("시 (시간 단위로 예약 가능)");
        return builder.toString();
    }
}
