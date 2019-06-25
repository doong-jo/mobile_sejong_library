package com.fourB.library.SearchBook;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fourB.library.R;
import com.fourB.library.Util.HttpManager;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class RealSearchBookDetailActivity extends AppCompatActivity {

    private ImageView mBookImg;
    private TextView mCategory;
    private TextView mTitle;
    private TextView mAuthor;
    private TextView mPublisher;
    private TextView mLocation;
    private TextView mLocationNumber;
    private TextView mStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_search_book_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final RealSearchBookItem dataItem;
                    dataItem = HttpManager.searchBookDetailRealServer(getIntent().getStringExtra("id"));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setBookData(dataItem);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void initView() {
        mBookImg = findViewById(R.id.imageView_detail_book);

        mCategory = findViewById(R.id.textView_detail_book_type);
        mTitle = findViewById(R.id.textView_detail_book_title);
        mAuthor = findViewById(R.id.textView_detail_book_author);
        mPublisher = findViewById(R.id.textView_detail_book_publish);
        mLocation = findViewById(R.id.textView_detail_book_location);
        mLocationNumber = findViewById(R.id.textView_detail_book_location_num);
        mStatus = findViewById(R.id.textView_detail_book_status);
    }

    private void setBookData(RealSearchBookItem data) {
        Picasso.get().load(data.getmImgURL()).into(mBookImg);

        mCategory.setText("" + data.getmCategory());
        mTitle.setText("" + data.getmTitle());
        mAuthor.setText("" + data.getmAuthor());
        mPublisher.setText("" + data.getmPublisher());
        mLocation.setText("" + data.getmLocation());
        mLocationNumber.setText("" + data.getmLocationNum());
        mStatus.setText("" + data.getmStatus());
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
