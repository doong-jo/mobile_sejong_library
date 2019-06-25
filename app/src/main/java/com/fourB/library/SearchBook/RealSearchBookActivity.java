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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.fourB.library.R;
import com.fourB.library.Util.HttpManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class RealSearchBookActivity extends AppCompatActivity {
    private EditText mEditTextSearch;
    private Button mBtnSearch;
    private InputMethodManager mInputManager;
    private RecyclerView mRecyclerView;
    private RealSearchBookAdapter mSearchBookAdapter;
    private ArrayList<RealSearchBookItem> mDataArrList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_search_book);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();
        initListener();

        mInputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mSearchBookAdapter = new RealSearchBookAdapter(getApplicationContext());
        mRecyclerView.setAdapter(mSearchBookAdapter);
    }

    private void initView() {
        mEditTextSearch = findViewById(R.id.editText_search_book);
        mBtnSearch = findViewById(R.id.btn_search_book);
        mRecyclerView = findViewById(R.id.recyclerView_search_book);
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

        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataArrList.clear();
                mSearchBookAdapter.clear();
                mSearchBookAdapter.notifyDataSetChanged();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            recycleViewDataSetting(HttpManager.searchBookRealServer(mEditTextSearch.getText().toString(),
                                    1, 1, 10));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    public void recycleViewDataSetting(RealSearchBookItem[] data){
        mDataArrList.addAll(Arrays.asList(data));
        mSearchBookAdapter.addItems(mDataArrList);

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
}
