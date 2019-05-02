package com.fourB.library.ReadingRoom;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fourB.library.R;

public class ReadingRoomFragment extends Fragment {
    ReadingRoomCallBack callBack;
    Button[] mBtnArray = new Button[7];
    ViewGroup rootView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ReadingRoomCallBack) {
            callBack = (ReadingRoomCallBack) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (callBack != null)
            callBack = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_reading_room, container, false);
        BtnSetting();
        return rootView;
    }

    public void BtnSetting(){
        mBtnArray[0] = (Button)rootView.findViewById(R.id.btn_reading_room_A);
        mBtnArray[1] = (Button)rootView.findViewById(R.id.btn_reading_room_B);
        mBtnArray[2] = (Button)rootView.findViewById(R.id.btn_reading_room_C);
        mBtnArray[3] = (Button)rootView.findViewById(R.id.btn_reading_room_D_A);
        mBtnArray[4] = (Button)rootView.findViewById(R.id.btn_reading_room_D_B);
        mBtnArray[5] = (Button)rootView.findViewById(R.id.btn_reading_room_3_A);
        mBtnArray[6] = (Button)rootView.findViewById(R.id.btn_reading_room_3_B);
        for(int i=0; i<mBtnArray.length; i++){
            mBtnArray[i].setOnClickListener(btnClickListener);
        }
    }
    private Button.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_reading_room_A:
                    callBack.onCommand(1);
                    break;

                case R.id.btn_reading_room_B:
                    callBack.onCommand(2);
                    break;

                case R.id.btn_reading_room_C:
                    callBack.onCommand(3);
                    break;

                case R.id.btn_reading_room_D_A:
                    callBack.onCommand(4);
                    break;

                case R.id.btn_reading_room_D_B:
                    callBack.onCommand(5);
                    break;

                case R.id.btn_reading_room_3_A:
                    callBack.onCommand(6);
                    break;
                case R.id.btn_reading_room_3_B:
                    callBack.onCommand(7);
                    break;

                    default:
                        break;
            }
        }
    };
}
