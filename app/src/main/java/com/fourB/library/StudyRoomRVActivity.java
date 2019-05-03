package com.fourB.library;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class StudyRoomRVActivity extends AppCompatActivity implements View.OnClickListener{

    private Spinner spNum2;
    ArrayList<String> arrayList4;
    ArrayAdapter<String> arrayAdapter4;

    TextView mTv4;
    Button mBtn4;

    private int hour2,minute2;

    Calendar c2;
    TimePickerDialog tpd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_room_rv);

        mBtn4 = (Button) findViewById(R.id.btntime2);

        mTv4 = (TextView) findViewById(R.id.txtView4);


        mBtn4.setOnClickListener(this);

        arrayList4 = new ArrayList<>();

        arrayList4.add("1시간");
        arrayList4.add("2시간");

        arrayAdapter4 = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                arrayList4);

        spNum2= (Spinner)findViewById(R.id.spNum2);
        spNum2.setAdapter(arrayAdapter4);
        spNum2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "이용시간 "+(i+1)+"시간",
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

        c2= Calendar.getInstance();
        hour2=c2.get(Calendar.HOUR_OF_DAY);


        tpd2 = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute ){

                mTv4.setText(hourOfDay+": 00");

            }
        },hour2,minute2,false);
        tpd2.show();

    }
}