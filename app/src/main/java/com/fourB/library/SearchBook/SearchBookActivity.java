package com.fourB.library.SearchBook;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.fourB.library.R;

import java.util.ArrayList;
import java.util.List;

public class SearchBookActivity extends AppCompatActivity {
    private Spinner mSpinnerCategory;
    private Spinner mSpinnerArrayWay;
    private EditText mEditTextSearch;
    private Button mBtnSearch;
    private AdapterSpinner mCategoryAdapter;
    private AdapterSpinner mArrayWayAdapter;
    private ScrollView mScrollView;
    private InputMethodManager mInputManager;
    RecyclerView recyclerView;
    SearchBookAdapter mSearchBookAdapter;


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
        mBtnSearch = (Button)findViewById(R.id.btn_search_book);
        mScrollView = (ScrollView)findViewById(R.id.scrollView_search_book);
        mInputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        spinnerDataSetting();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_search_book);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        mSearchBookAdapter = new SearchBookAdapter(getApplicationContext());


        recycleViewDataSetting();
        recyclerView.setAdapter(mSearchBookAdapter);
//        mScrollView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //mInputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//                //mInputManager.hideSoftInputFromWindow(mEditTextSearch.getWindowToken(), 0);
//                //mEditTextSearch.clearFocus();
//            }
//        });
//
//        mEditTextSearch.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                mInputManager.showSoftInput(mEditTextSearch, InputMethodManager.SHOW_IMPLICIT);
//            }
//        });
//
//        mEditTextSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                if(b){
//                    mInputManager.showSoftInput(mEditTextSearch, InputMethodManager.SHOW_IMPLICIT);
//                }else {
//                    mInputManager.hideSoftInputFromWindow(mEditTextSearch.getWindowToken(), 0);
//                }
//            }
//        });


    }

    public void recycleViewDataSetting(){
        ArrayList<SearchBookItem> items = new ArrayList<>();
        SearchBookItem[] item = new SearchBookItem[BOOK_NUM];
        item[0] = new SearchBookItem("운수 좋은날","홍길동","좋은나라",true,"2000","811.3 현79운ㄷ",R.drawable.book_example);
        item[1] = new SearchBookItem("성동 좋은날","홍길동","좋은나라",true,"2000","811.3 현79운ㄷ",R.drawable.book_example);
        item[2] = new SearchBookItem("형호 좋은날","홍길동","좋은나라",true,"2000","811.3 현79운ㄷ",R.drawable.book_example);
        item[3] = new SearchBookItem("나도 좋은날","홍길동","좋은나라",true,"2000","811.3 현79운ㄷ",R.drawable.book_example);
        item[4] = new SearchBookItem("승훈 좋은날","홍길동","좋은나라",true,"2000","811.3 현79운ㄷ",R.drawable.book_example);
        for (int i = 0; i < BOOK_NUM; i++) {
            items.add(item[i]);
        }
        mSearchBookAdapter.addItems(items);

    }
    public void spinnerDataSetting(){
        List<String> mCategoryData = new ArrayList<>();
        mCategoryData.add("전체");
        mCategoryData.add("자료명");
        mCategoryData.add("저자");
        mCategoryData.add("출판사");
        mCategoryAdapter = new AdapterSpinner(this, mCategoryData);
        mSpinnerCategory.setAdapter(mCategoryAdapter);

        List<String> mArrayWayData = new ArrayList<>();
        mArrayWayData.add("순서");
        mArrayWayData.add("오름차순");
        mArrayWayData.add("내림차순");
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
                    //
                    // Hide keyboard
                    //
//                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
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
