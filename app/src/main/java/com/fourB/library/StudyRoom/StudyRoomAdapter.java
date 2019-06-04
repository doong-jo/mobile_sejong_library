package com.fourB.library.StudyRoom;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.fourB.library.ParkActivity;
import com.fourB.library.R;
import com.fourB.library.ReportManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class StudyRoomAdapter extends RecyclerView.Adapter<StudyRoomAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<StudyRoomItem> mItems = new ArrayList<StudyRoomItem>();
    private String mReservationInfo;

    public StudyRoomAdapter(Context context){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public void setReservationInfo(final String info) {
        mReservationInfo = info;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_study_room, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final StudyRoomItem item = mItems.get(i);
        viewHolder.setItem(item);
        viewHolder.initListener(mContext, mReservationInfo);
    }

    public void addItem(StudyRoomItem item){
        mItems.add(item);
    }

    public void addItems(ArrayList<StudyRoomItem> items){
        this.mItems.addAll(items);
    }

    public StudyRoomItem getItem (int position){
        return mItems.get(position);
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mStudyRoomNameTv;
        private Button mViewTableBtn;
        private Button mReserveBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            mStudyRoomNameTv = itemView.findViewById(R.id.study_room_name);
            mViewTableBtn = itemView.findViewById(R.id.study_room_reserve_table);
            mReserveBtn = itemView.findViewById(R.id.study_room_reserve_start);
        }

        public void setItem(StudyRoomItem item){
            mStudyRoomNameTv.setText(item.getStudyRoomName());
        }

        public void initListener(final Context context, final String info) {
            mViewTableBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2019-06-03 Start Activity -> study room table
                    Intent intent = new Intent(context, ParkActivity.class);
                    context.startActivity(intent);
                }
            });

            mReserveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2019-06-03 Start Activity -> reserve study room (DB)
                    StringBuilder stringBuilder = new StringBuilder(128);
                    stringBuilder.append("[");
                    stringBuilder.append(mStudyRoomNameTv.getText().toString());
                    stringBuilder.append("]\n[");
                    stringBuilder.append(info);
                    stringBuilder.append("]\n\n");
                    stringBuilder.append(context.getString(R.string.study_room_reserve_dlg_content));


                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(context.getString(R.string.study_room_reserve_dlg_title));
                    builder.setMessage(stringBuilder.toString());
                    builder.setPositiveButton("예",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(context.getApplicationContext(),
                                            "스터디룸이 예약되었습니다.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });

                    builder.setNegativeButton("아니오",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(context.getApplicationContext(),
                                            "스터디룸이 예약이 취소되었습니다.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });

                    builder.show();
                }
            });
        }
    }
}
