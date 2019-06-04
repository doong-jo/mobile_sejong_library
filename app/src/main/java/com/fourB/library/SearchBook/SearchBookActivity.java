package com.fourB.library.SearchBook;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fourB.library.HttpManager;
import com.fourB.library.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SearchBookActivity extends AppCompatActivity {
    private Spinner mSortSpinner;
    private EditText mEditTextSearch;
    private Button mBtnSearch;
    private InputMethodManager mInputManager;
    private RecyclerView mRecyclerView;
    private SearchBookAdapter mSearchBookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();
        initListener();

        ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(this, R.array.search_book_sort, R.layout.item_basic_spinner);
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSortSpinner.setAdapter(sortAdapter);

        mInputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mSearchBookAdapter = new SearchBookAdapter(getApplicationContext());
        mRecyclerView.setAdapter(mSearchBookAdapter);
    }

    private void initView() {
        mSortSpinner = findViewById(R.id.spinner_search_book_sort);
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
                String curSort = mSortSpinner.getSelectedItem().toString();
                final String[] sortArr = getResources().getStringArray(R.array.search_book_sort);
                for(int i=0; i<sortArr.length; i++) {
                    if( curSort.equals(sortArr[i]) ) {
                        switch(i) {
                            case 0: curSort = HttpManager.BOOK_SORT_SIM; break;
                            case 1: curSort = HttpManager.BOOK_SORT_DATE; break;
                            case 2: curSort = HttpManager.BOOK_SORT_COUNT; break;
                            default: break;
                        }
                    }
                }

                final String searchSort = curSort;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            recycleViewDataSetting(HttpManager.searchBookNaverApi(mEditTextSearch.getText().toString(),
                                    10, searchSort));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    public void recycleViewDataSetting(SearchBookItem[] data){
        ArrayList<SearchBookItem> dataArrList = new ArrayList<>();
        dataArrList.addAll(Arrays.asList(data));
        mSearchBookAdapter.addItems(dataArrList);

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
