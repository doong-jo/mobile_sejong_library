package com.fourB.library.MyPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.fourB.library.R;

import java.util.ArrayList;

public class AppointmentBookActivity extends AppCompatActivity {

    private RecyclerView mAppointRecylcerView;
    private AppointmentBookAdapter mAppointAdapter;
    private final int BOOK_NUM = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_book);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mAppointRecylcerView = (RecyclerView)findViewById(R.id.recycler_appointment_book);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
        mAppointRecylcerView.setLayoutManager(layoutManager);
        mAppointAdapter = new AppointmentBookAdapter(getApplicationContext());
        initData();

        mAppointRecylcerView.setAdapter(mAppointAdapter);
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

    private void initData() {

        ArrayList<AppointmentBookItem> items = new ArrayList<>();
        AppointmentBookItem[] item = new AppointmentBookItem[BOOK_NUM];
        item[0] = new AppointmentBookItem("데이터베이스의 정석","19/05/23","4");
        item[1] = new AppointmentBookItem("제2의 기계세계","19/05/25","2");
        item[2] = new AppointmentBookItem("발명 콘서트","19/05/25","3");
        item[3] = new AppointmentBookItem("나미야 잡화점의 기적","19/05/26","1");
        item[4] = new AppointmentBookItem("모모","19/05/28","5");
        for (int i = 0; i < BOOK_NUM; i++) {
            items.add(item[i]);
        }
        mAppointAdapter.addItems(items);
    }
}
