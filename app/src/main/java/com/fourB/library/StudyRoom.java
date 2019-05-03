package com.fourB.library;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.fourB.library.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class StudyRoom extends AppCompatActivity implements View.OnClickListener{

    private Spinner spNum;
    ArrayList<String> arrayList2;
    ArrayAdapter<String> arrayAdapter2;


    private Spinner spTime;
    ArrayList<String> arrayList3;
    ArrayAdapter<String> arrayAdapter3;


    TextView mTv, mTv2;
    Button mBtn, mBtn2;

    private int hour,minute;

    Calendar c;
    DatePickerDialog dpd;
    TimePickerDialog tpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_study_room);

        mBtn = (Button) findViewById(R.id.btndate);
        mBtn2 = (Button) findViewById(R.id.btntime);

        mTv = (TextView) findViewById(R.id.txtView);
        mTv2 = (TextView) findViewById(R.id.txtView2);

        Button button = (Button) findViewById(R.id.reserve);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StudyRoomRVActivity.class);
                startActivity(intent);
            }
        });

        mBtn.setOnClickListener(this);
        mBtn2.setOnClickListener(this);

        arrayList2 = new ArrayList<>();

        arrayList2.add("1시간");
        arrayList2.add("2시간");

        arrayAdapter2 = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                arrayList2);

        spNum = (Spinner)findViewById(R.id.spNum);
        spNum.setAdapter(arrayAdapter2);
        spNum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "이용시간 "+(i+1)+"시간",
                        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        arrayList3 = new ArrayList<>();


        for(int i=2; i<11; i++)
            arrayList3.add(i+"명 "); // 띄어쓰기로 arraylist2 랑 크기 위치 같게 만든 것임.

        arrayAdapter3 = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                arrayList3);

        spTime = (Spinner)findViewById(R.id.spTime);
        spTime.setAdapter(arrayAdapter3);
        spTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "인원 "+(i+2)+"명",
                        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    //        mBtn.setOnClickListener(new View.OnClickListener(){
    @Override
    public void onClick(View v) {
        if (v == mBtn) {
            c = Calendar.getInstance();
            int day = c.get(Calendar.DAY_OF_MONTH);
            int month = c.get(Calendar.MONTH);
            int year = c.get(Calendar.YEAR);

            dpd = new DatePickerDialog(StudyRoom.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                    mTv.setText(mYear + "/" + (mMonth + 1) + "/" + mDay);
                }
            }, year, month, day);
            dpd.show();
        }

        if(v == mBtn2) {
            c= Calendar.getInstance();
            hour=c.get(Calendar.HOUR_OF_DAY);


            tpd = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute ){

                    mTv2.setText(hourOfDay+": 00");

                }
            },hour,minute,false);
            tpd.show();
        }
    }
}

