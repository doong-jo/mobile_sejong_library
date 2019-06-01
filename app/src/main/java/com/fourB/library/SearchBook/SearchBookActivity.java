package com.fourB.library.SearchBook;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fourB.library.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class SearchBookActivity extends AppCompatActivity {
    private Spinner mSpinnerCategory;
    private Spinner mSpinnerArrayWay;
    private EditText mEditTextSearch;
    private Button mBtnSearch;
    private AdapterSpinner mCategoryAdapter;
    private AdapterSpinner mArrayWayAdapter;
    private InputMethodManager mInputManager;
    RecyclerView recyclerView;
    SearchBookAdapter mSearchBookAdapter;

    String mSearchText;


    final static int BOOK_NUM =5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book);

        setTitle(getString(R.string.menu_search_book));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSpinnerCategory = (Spinner)findViewById(R.id.spinner_search_book_category);
        mSpinnerArrayWay = (Spinner)findViewById(R.id.spinner_search_book_array_way);

        mEditTextSearch = (EditText)findViewById(R.id.editText_search_book);

        mEditTextSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Enter key Action
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    //Enter키눌렀을떄 처리
                    mSearchText = mEditTextSearch.getText().toString().toLowerCase(Locale.getDefault());
                    mSearchBookAdapter.SearchingBookItem(mSearchText);


                    mEditTextSearch.clearFocus();
                    mInputManager.hideSoftInputFromWindow(v.getWindowToken(),0);

                    return true;
                }
                return false;
            }
        });

        mBtnSearch = (Button)findViewById(R.id.btn_search_book);
        mInputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        spinnerDataSetting();



        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_search_book);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        mSearchBookAdapter = new SearchBookAdapter(getApplicationContext());
        recycleViewDataSetting();
        recyclerView.setAdapter(mSearchBookAdapter);


        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearchText = mEditTextSearch.getText().toString().toLowerCase(Locale.getDefault());
                mSearchBookAdapter.SearchingBookItem(mSearchText);
            }
        });


    }

    public void recycleViewDataSetting(){

        //리사이클러뷰에 데이터를 넣는 함수
        ArrayList<SearchBookItem> items = new ArrayList<>();
        SearchBookItem[] item = new SearchBookItem[BOOK_NUM];
        item[0] = new SearchBookItem("뼈있는 아무말 대잔치","신영","로크미디어",true,"2018","811.3 현79운ㄷ",R.drawable.book_example);
        item[1] = new SearchBookItem("운수 좋은날","현진권","재승출판",true,"2012","811.3 현79운ㄷ",R.drawable.bookimage_04);
        item[2] = new SearchBookItem("미움 받을 용기","기시미 이치로","안견일랑",true,"2014","158.1 안14K전",R.drawable.bookimage_02);
        item[3] = new SearchBookItem("죽은 시인의 사회","N.H클라인바움","서교출판",true,"2004","125.32 건19북",R.drawable.bookimage_05);
        item[4] = new SearchBookItem("해리포터와 불의 잔"," 조앤. K 롤링","문학수첩",true,"2000","811.3 823.914 R884hK김5",R.drawable.bookimage_03);
        for (int i = 0; i < BOOK_NUM; i++) {
            items.add(item[i]);
        }
        mSearchBookAdapter.addItems(items);

    }
    public void spinnerDataSetting(){

        String[] mCategory = getResources().getStringArray(R.array.search_book_category);
        String[] mArrayWay = getResources().getStringArray(R.array.search_book_array_way);

        List<String> mCategoryData = new ArrayList<>(Arrays.asList(mCategory));
        mCategoryAdapter = new AdapterSpinner(this, mCategoryData);
        mSpinnerCategory.setAdapter(mCategoryAdapter);

        List<String> mArrayWayData = new ArrayList<>(Arrays.asList(mArrayWay));
        mArrayWayAdapter = new AdapterSpinner(this, mArrayWayData);
        mSpinnerArrayWay.setAdapter(mArrayWayAdapter);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (mEditTextSearch.isFocused()) {
                Rect outRect = new Rect();
                mEditTextSearch.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    mEditTextSearch.clearFocus();
                    assert v != null;
                    mInputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
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
