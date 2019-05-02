package com.fourB.library.ReadingRoom;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.fourB.library.PopupActivity;
import com.fourB.library.R;

public class ReadingRoomActivity extends AppCompatActivity implements ReadingRoomCallBack{

    private ReadingRoomFragment readingRoomFragment;
    private ReadingRoomWVFragment readingRoomWVFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_room);

        setTitle(getString(R.string.menu_reading_room));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        allFindViewById();
//
//        mButtonAseat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
//                startActivityForResult(intent, 1);
//            }
//        });
        readingRoomFragment = new ReadingRoomFragment();
        readingRoomWVFragment = new ReadingRoomWVFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.reading_room_cotainer,readingRoomFragment).commit();


    }

//    private void allFindViewById(){
//        mButtonAseat = (Button)findViewById(R.id.btn_reading_room_A);
//        mProgressBarA = (ProgressBar)findViewById(R.id.bar_reading_room_A);
//        mProgressBarA.setMax(228);
//        mProgressBarA.setProgress(130);
//    }

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

    @Override
    public void onCommand(int data) {
        switch (data){
            case 0:
                getSupportFragmentManager().beginTransaction().replace(R.id.reading_room_cotainer,readingRoomFragment).commit();
                break;
            case 1:
                readingRoomWVFragment.setRoomUrl("1");
                getSupportFragmentManager().beginTransaction().replace(R.id.reading_room_cotainer,readingRoomWVFragment).commit();
                break;
            case 2:
                readingRoomWVFragment.setRoomUrl("2");
                getSupportFragmentManager().beginTransaction().replace(R.id.reading_room_cotainer,readingRoomWVFragment).commit();
                break;
            case 3:
                readingRoomWVFragment.setRoomUrl("3");
                getSupportFragmentManager().beginTransaction().replace(R.id.reading_room_cotainer,readingRoomWVFragment).commit();
                break;
            case 4:
                readingRoomWVFragment.setRoomUrl("4");
                getSupportFragmentManager().beginTransaction().replace(R.id.reading_room_cotainer,readingRoomWVFragment).commit();
                break;
            case 5:
                readingRoomWVFragment.setRoomUrl("5");
                getSupportFragmentManager().beginTransaction().replace(R.id.reading_room_cotainer,readingRoomWVFragment).commit();
                break;
            case 6:
                readingRoomWVFragment.setRoomUrl("6");
                getSupportFragmentManager().beginTransaction().replace(R.id.reading_room_cotainer,readingRoomWVFragment).commit();
                break;
        }

    }
}
