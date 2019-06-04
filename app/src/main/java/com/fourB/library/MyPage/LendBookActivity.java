package com.fourB.library.MyPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.fourB.library.R;

import java.util.ArrayList;

public class LendBookActivity extends AppCompatActivity {

    private RecyclerView mLendRecylcerView;
    private LendBookAdapter mLendAdapter;
    private final int BOOK_NUM = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lend_book);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mLendRecylcerView = (RecyclerView)findViewById(R.id.recycler_lend_book);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
        mLendRecylcerView.setLayoutManager(layoutManager);
        mLendAdapter = new LendBookAdapter(getApplicationContext());
        initData();

        mLendRecylcerView.setAdapter(mLendAdapter);
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

        ArrayList<LendBookItem> items = new ArrayList<>();
        LendBookItem[] item = new LendBookItem[BOOK_NUM];
        item[0] = new LendBookItem("뼈있는 아무말 대잔치","19/06/03","19/06/10");
        item[1] = new LendBookItem("미움 받을 용기","19/06/03","19/06/10");
        item[2] = new LendBookItem("죽은 시인의 사회","19/06/04","19/06/11");
        item[3] = new LendBookItem("알고리즘의 정석","19/06/05","19/06/12");
        item[4] = new LendBookItem("Java란 무엇인가?","19/06/05","19/06/12");
        for (int i = 0; i < BOOK_NUM; i++) {
            items.add(item[i]);
        }
        mLendAdapter.addItems(items);
    }
}
