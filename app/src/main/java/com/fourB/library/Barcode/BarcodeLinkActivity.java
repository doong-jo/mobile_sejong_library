package com.fourB.library.Barcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fourB.library.R;

public class BarcodeLinkActivity extends AppCompatActivity {

    private Button mBorrowBtn;
    private Button mCancelBtn;
    private ImageView mBookImage;
    private TextView mBookTitle;
    private TextView mBookAuthor;
    private String mBarcodeScarNumber;
    private Intent mIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcord_book_borrow);

        setTitle("책 대여");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mIntent = getIntent();
        mBarcodeScarNumber = mIntent.getStringExtra("BarcodeScanNumber");

        if(mBarcodeScarNumber.equals(getString(R.string.barcode_bookBarcode_01))) {
            mBookImage = (ImageView) findViewById(R.id.barcode_bookImage);
            mBookImage.setImageResource(R.drawable.barcode_book_03);

            mBookTitle = (TextView) findViewById(R.id.barcode_bookTitle);
            mBookTitle.setText(R.string.barcode_bookTitle_01);

            mBookAuthor = (TextView) findViewById(R.id.barcode_author);
            mBookAuthor.setText(R.string.barcode_bookAuthor_01);

        }else if(mBarcodeScarNumber.equals(getString(R.string.barcode_bookBarcode_02))){
            mBookImage = (ImageView) findViewById(R.id.barcode_bookImage);
            mBookImage.setImageResource(R.drawable.barcode_book_04);

            mBookTitle = (TextView) findViewById(R.id.barcode_bookTitle);
            mBookTitle.setText(R.string.barcode_bookTitle_02);

            mBookAuthor = (TextView) findViewById(R.id.barcode_author);
            mBookAuthor.setText(R.string.barcode_bookAuthor_02);
        }

        mBorrowBtn = (Button)findViewById(R.id.barcode_borrowBtn);
        mBorrowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.borrow_success, Toast.LENGTH_LONG).show();
                finish();
            }
        });

        mCancelBtn = (Button)findViewById(R.id.barcode_cancelBtn);
        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.borrow_cancle, Toast.LENGTH_LONG).show();
                finish();
            }
        });
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
