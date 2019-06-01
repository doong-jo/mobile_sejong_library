package com.fourB.library.ReadingRoom;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.fourB.library.R;

public class ReadingRoomFragment extends Fragment {
    private final int ROOM_NUM = 7;
    private final int [] ROOM_ID = {
            R.id.btn_reading_room_A,
            R.id.btn_reading_room_B,
            R.id.btn_reading_room_C,
            R.id.btn_reading_room_D_A,
            R.id.btn_reading_room_D_B,
            R.id.btn_reading_room_3_A,
            R.id.btn_reading_room_3_B
    };
    private ReadingRoomCallBack mCallBack;
    private Button[] mBtnArray = new Button[ROOM_NUM];
    ViewGroup rootView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ReadingRoomCallBack) {
            mCallBack = (ReadingRoomCallBack) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mCallBack != null)
            mCallBack = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_reading_room, container, false);
        BtnSetting();
        return rootView;
    }

    public void BtnSetting(){
        for(int i=0; i< mBtnArray.length; i++){
            mBtnArray[i] = (Button)rootView.findViewById(ROOM_ID[i]);
            mBtnArray[i].setTag(i);
            mBtnArray[i].setOnClickListener(btnClickListener);
        }
    }

    private Button.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mCallBack.onCommand((int)(v.getTag()) + 1);
        }
    };
}
