package com.fourB.library.StudyRoom;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fourB.library.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.Collectors;

public class StudyRoomViewActivity extends AppCompatActivity {
    private TextView mStudyRoomInfo;
    private RecyclerView mStudyRoomRecyclVw;
    private StudyRoomAdapter mStudyRoomAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_room_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();
        initIntent(getIntent());

        final String info = mStudyRoomInfo.getText().toString().replaceAll("\n", " ");
        mStudyRoomAdapter.setReservationInfo(info);
    }

    private void initView() {
        mStudyRoomInfo = findViewById(R.id.study_room_reserve_info);
        mStudyRoomRecyclVw = findViewById(R.id.study_room_reserve_result_lv);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
        mStudyRoomRecyclVw.setLayoutManager(layoutManager);
        mStudyRoomAdapter = new StudyRoomAdapter(this);
        recycleViewDataSetting();
        mStudyRoomRecyclVw.setAdapter(mStudyRoomAdapter);
    }

    private void initIntent(Intent data) {
        final String date = data.getStringExtra("date");
        final String time = data.getStringExtra("time");
        final String person = data.getStringExtra("person");
        final String use = data.getStringExtra("use");

        StringBuilder builder = new StringBuilder(64);
        builder.append(date).append("\n").append(time).append("\n").append(person).append("\n").append(use);
        mStudyRoomInfo.setText(builder.toString());
    }


    public void recycleViewDataSetting(){
        ArrayList<StudyRoomItem> items = new ArrayList<>();

        // TODO: 2019-06-04 Join DB and processing (current code is temporary)
        Random generator = new Random();
        int num = generator.nextInt(10) + 1;

        for(int i=0; i<num; i++) {
            int id = generator.nextInt(30);
            int floor = 0;
            if( id >= 14 ) {
                floor = 4;
            } else {
                floor = 7;
            }

            String two_digit_zero = "";
            if( id < 10 ) { two_digit_zero = "0"; }
            items.add(new StudyRoomItem(id, two_digit_zero + id + " 학술(" + floor + "층)"));
        }
        for (int i = 0; i < items.size(); i++) {
            for (int j = 0; j < items.size(); j++) {
                StudyRoomItem itemA = (StudyRoomItem)items.get(i);
                StudyRoomItem itemB = (StudyRoomItem)items.get(j);
                if (i == j) {
                } else if (itemA.equals(itemB)) {
                    items.remove(j);
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            items.sort(new Comparator<StudyRoomItem>() {
                @Override
                public int compare(StudyRoomItem o1, StudyRoomItem o2) {
                    return Integer.compare(o1.getStudyRoomId(), o2.getStudyRoomId());
                }
            });
        }

        mStudyRoomAdapter.addItems(items);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
