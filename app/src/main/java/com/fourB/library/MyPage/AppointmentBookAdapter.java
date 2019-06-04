package com.fourB.library.MyPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fourB.library.R;

import java.util.ArrayList;

public class AppointmentBookAdapter extends RecyclerView.Adapter<AppointmentBookAdapter.ViewHolder> {

    Context context;
    ArrayList<AppointmentBookItem> items =  new ArrayList<AppointmentBookItem>();

    LayoutInflater inflater;

    public AppointmentBookAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public AppointmentBookAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_appointment_book, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentBookAdapter.ViewHolder holder, final int position) {
        final AppointmentBookItem item = items.get(position);
        holder.setItem(item);

        holder.mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.remove(items.get(position));
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(AppointmentBookItem item){
        items.add(item);
    }

    public void addItems(ArrayList<AppointmentBookItem> items){
        this.items.addAll(items);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView mTitle;
        TextView mDate;
        TextView mRank;
        Button mCancel;

        public ViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView)itemView.findViewById(R.id.appointment_book_title);
            mDate = (TextView)itemView.findViewById(R.id.appointment_book_date);
            mRank = (TextView)itemView.findViewById(R.id.appointment_book_rank);
            mCancel = (Button)itemView.findViewById(R.id.appointment_book_cancel);
        }

        public void setItem(AppointmentBookItem item){
            mTitle.setText(item.getmTitle());
            mDate.setText(String.format("예약일: %s", item.getmStartDate()));
            mRank.setText(String.format("예약순위: %s",item.getmRank()));
        }
    }
}
