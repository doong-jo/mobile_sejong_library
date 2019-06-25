package com.fourB.library.SearchBook;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.fourB.library.R;
import com.fourB.library.Util.HttpManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class RealSearchBookActivity extends AppCompatActivity {
    private Spinner mCategorySpinner;
    private EditText mEditTextSearch;
    private Button mBtnSearch;
    private InputMethodManager mInputManager;
    private RecyclerView mRecyclerView;
    private RealSearchBookAdapter mSearchBookAdapter;
    private ArrayList<RealSearchBookItem> mDataArrList = new ArrayList<>();
    private CardView[] mCategoryCardViews;
    private boolean[] mIsSelectedCategory;
    private NestedScrollView mNestedView;
    private ProgressBar mLoadingProgress;


    private int mStartPageNum = 1;
    private int PAGE_NUM = 10;
    private int mCurBookArraySize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_search_book);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();
        initListener();

        mInputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this, R.array.real_search_book_sort, R.layout.item_basic_spinner);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCategorySpinner.setAdapter(categoryAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mSearchBookAdapter = new RealSearchBookAdapter(getApplicationContext());
        mRecyclerView.setAdapter(mSearchBookAdapter);

        if(mNestedView != null) {
            mNestedView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                        if(mCurBookArraySize < PAGE_NUM) {
                            mLoadingProgress.setVisibility(View.INVISIBLE);
                        } else if (mCurBookArraySize == PAGE_NUM){
                            mStartPageNum = mStartPageNum + PAGE_NUM;
                            mLoadingProgress.setVisibility(View.VISIBLE);
                            recycleThread();
                        }

                    }
                }
            });
        }

        final String getChatBotAny = getIntent().getStringExtra("text");
        if( getChatBotAny != null && !getChatBotAny.equals("")) {
            mEditTextSearch.setText(getChatBotAny);
            dataSetStart();
        }


        changeCategoryFace();
    }

    private void changeCategoryFace() {
            for (int i = 0; i < mCategoryCardViews.length; i++) {
                final int index = i;
                mCategoryCardViews[i].setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(View v) {
                        mIsSelectedCategory[index] = !mIsSelectedCategory[index];

                        if( mIsSelectedCategory[index] ) {
                            LinearLayout linearLayout = (LinearLayout)mCategoryCardViews[index].getChildAt(0);
                            linearLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                            TextView tv = (TextView) linearLayout.getChildAt(0);
                            tv.setTextColor(getResources().getColor(R.color.white));

                            if( index == 0 ) {
                                for (int j = 1; j < mCategoryCardViews.length; j++) {
                                    LinearLayout _linearLayout = (LinearLayout)mCategoryCardViews[j].getChildAt(0);
                                    _linearLayout.setBackground(getResources().getDrawable(R.drawable.rectangle_primary));

                                    TextView _tv = (TextView) _linearLayout.getChildAt(0);
                                    _tv.setTextColor(getResources().getColor(R.color.black));

                                    mIsSelectedCategory[j] = false;
                                }
                            } else {
                                if( mIsSelectedCategory[0] ) {
                                    LinearLayout _linearLayout = (LinearLayout)mCategoryCardViews[0].getChildAt(0);
                                    _linearLayout.setBackground(getResources().getDrawable(R.drawable.rectangle_primary));

                                    TextView _tv = (TextView) _linearLayout.getChildAt(0);
                                    _tv.setTextColor(getResources().getColor(R.color.black));

                                    mIsSelectedCategory[0] = false;
                                }
                            }
                        } else {
                            LinearLayout linearLayout = (LinearLayout)mCategoryCardViews[index].getChildAt(0);
                            linearLayout.setBackground(getResources().getDrawable(R.drawable.rectangle_primary));

                            TextView tv = (TextView) linearLayout.getChildAt(0);
                            tv.setTextColor(getResources().getColor(R.color.black));
                        }
                        boolean allFalse = true;
                        for (int j = 0; j < mIsSelectedCategory.length; j++) {
                            if( mIsSelectedCategory[j] ) {
                                allFalse = false;
                                break;
                            }
                        }
                        if( allFalse ) {
                            LinearLayout linearLayout = (LinearLayout)mCategoryCardViews[0].getChildAt(0);
                            linearLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                            TextView tv = (TextView) linearLayout.getChildAt(0);
                            tv.setTextColor(getResources().getColor(R.color.white));

                            mIsSelectedCategory[0] = true;
                        }
                    }
                });
            }
        }

    private void initView() {
        mCategorySpinner = findViewById(R.id.spinner_search_book_category);
        mEditTextSearch = findViewById(R.id.editText_search_book);
        mBtnSearch = findViewById(R.id.btn_search_book);
        mRecyclerView = findViewById(R.id.recyclerView_search_book);
        mNestedView = (NestedScrollView)findViewById(R.id.scrollView_search_book);
        mLoadingProgress = (ProgressBar)findViewById(R.id.loading_progress);


        mCategoryCardViews = new CardView[] {
                findViewById(R.id.search_category_all),
                findViewById(R.id.search_category_dan),
                findViewById(R.id.search_category_gan),
                findViewById(R.id.search_category_multi),
                findViewById(R.id.search_category_essay),
                findViewById(R.id.search_category_dan_dong),
                findViewById(R.id.search_category_ej_in),
                findViewById(R.id.search_category_ej_out),
                findViewById(R.id.search_category_eb_ej),
                findViewById(R.id.search_category_eb),
        };

        mIsSelectedCategory = new boolean[mCategoryCardViews.length];
        for (int i=0; i<mIsSelectedCategory.length; i++) {
            mIsSelectedCategory[i] = false;
        }

        mIsSelectedCategory[0] = true;
        LinearLayout linearLayout = (LinearLayout)mCategoryCardViews[0].getChildAt(0);
        linearLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        TextView tv = (TextView) linearLayout.getChildAt(0);
        tv.setTextColor(getResources().getColor(R.color.white));
    }

    private void initListener() {
        mEditTextSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return true;
                }
                return false;
            }
        });

        mEditTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mStartPageNum = 1;
                    dataSetStart();
                    return true;
                }
                return false;
            }
        });

        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStartPageNum = 1;
                dataSetStart();
            }
        });
    }

    private void dataSetStart() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        mDataArrList.clear();
        mSearchBookAdapter.clear();
        mSearchBookAdapter.notifyDataSetChanged();
        recycleThread();

    }

    private void recycleViewDataSetting(RealSearchBookItem[] data){

        ArrayList<RealSearchBookItem> dataArrList = new ArrayList<>(Arrays.asList(data));
//        mDataArrList.addAll(Arrays.asList(data));
        mSearchBookAdapter.addItems(dataArrList);

        mCurBookArraySize = dataArrList.size();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSearchBookAdapter.notifyDataSetChanged();
            }
        });
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

    private void recycleThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < mIsSelectedCategory.length; i++) {
                        if( mIsSelectedCategory[i] && i == 0 ) {
                            recycleViewDataSetting(HttpManager.searchBookRealServer(mEditTextSearch.getText().toString(),
                                    i + 1, mStartPageNum, PAGE_NUM));
                            break;
                        } else if( mIsSelectedCategory[i] ) {
                            recycleViewDataSetting(HttpManager.searchBookRealServer(mEditTextSearch.getText().toString(),
                                    i + 1, mStartPageNum, PAGE_NUM));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
