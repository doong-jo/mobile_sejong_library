package com.fourB.library.MyPage;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fourB.library.R;

import java.util.ArrayList;

public class LendBookRecordActivity extends AppCompatActivity {

    private RecyclerView mLendRecordRecycle;
    private LendBookRecordAdapter mLendRecordAdapter;
    private final int BOOK_NUM = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lend_book);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mLendRecordRecycle = (RecyclerView)findViewById(R.id.recycler_lend_book);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
        mLendRecordRecycle.setLayoutManager(layoutManager);
        mLendRecordAdapter = new LendBookRecordAdapter(getApplicationContext());
        initData();

        mLendRecordRecycle.setAdapter(mLendRecordAdapter);
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

        ArrayList<LendBookRecordItem> items = new ArrayList<>();
        LendBookRecordItem[] item = new LendBookRecordItem[BOOK_NUM];
        item[0] = new LendBookRecordItem("인생 : 위화 장편소설","19/05/03","19/05/10","812.3 여96K백3");
        item[1] = new LendBookRecordItem("알고리즘 : 문제해결 중심으로","19/05/03","19/05/10","005.1 국94알");
        item[2] = new LendBookRecordItem("미움으로 흘리는 눈물은 없다","19/04/04","19/04/11","811.4 염73미");
        item[3] = new LendBookRecordItem("삶이란 무엇인가","18/06/05","18/06/12","181 R137jK안");
        item[4] = new LendBookRecordItem("이 세상에 그대만큼 사랑하고픈 사람 있을까","18/06/05","18/06/12","811.16 용96이");
        item[5] = new LendBookRecordItem("미생","18/05/03","18/05/10","852.3 밥23K국3");
        item[6] = new LendBookRecordItem("세상의 끝에서 만나는 두 사람","18/05/03","18/05/10","005.1 국94알");
        item[7] = new LendBookRecordItem("더할나위 없었던 당신을 위해","18/04/04","18/04/11","811.4 염73미");
        item[8] = new LendBookRecordItem("천재가 생각하는 사고방식","17/06/05","17/06/12","181 R137jK안");
        item[9] = new LendBookRecordItem("마지막이라는 책글자속","17/06/05","17/06/12","811.16 용96이");

        for (int i = 0; i < BOOK_NUM; i++) {
            items.add(item[i]);
        }
        mLendRecordAdapter.addItems(items);
    }
}
