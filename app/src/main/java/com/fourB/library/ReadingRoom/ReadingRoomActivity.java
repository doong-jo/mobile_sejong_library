package com.fourB.library.ReadingRoom;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.fourB.library.R;

public class ReadingRoomActivity extends AppCompatActivity implements ReadingRoomCallBack{

    private ReadingRoomFragment readingRoomFragment;
    private ReadingRoomWVFragment readingRoomWVFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_room);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        readingRoomFragment = new ReadingRoomFragment();
        readingRoomWVFragment = new ReadingRoomWVFragment();

        if( getIntent().hasExtra("room") ) {
            final String room = getIntent().getStringExtra("room").split("열람실")[0];
            onCommand(room.charAt(0) - 65 + 1);
        } else {
            onCommand(0);
        }
//        getSupportFragmentManager().beginTransaction().add(R.id.reading_room_container, readingRoomFragment).commit();
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

    @Override
    public void onCommand(int data) {
        if ( data != 0 ) {
            getSupportFragmentManager().beginTransaction().replace(R.id.reading_room_container,readingRoomWVFragment).commit();
            readingRoomWVFragment.setRoomUrl(String.valueOf(data));
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.reading_room_container,readingRoomFragment).commit();
        }
    }
}
