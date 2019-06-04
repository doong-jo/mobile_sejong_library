package com.fourB.library.MyPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fourB.library.R;

import java.util.ArrayList;

public class LendBookRecordAdapter extends RecyclerView.Adapter<LendBookRecordAdapter.ViewHolder> {

    Context context;
    ArrayList<LendBookRecordItem> items =  new ArrayList<LendBookRecordItem>();

    LayoutInflater inflater;

    public LendBookRecordAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public LendBookRecordAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_lend_book_record, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LendBookRecordAdapter.ViewHolder holder, final int position) {
        final LendBookRecordItem item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(LendBookRecordItem item){
        items.add(item);
    }

    public void addItems(ArrayList<LendBookRecordItem> items){
        this.items.addAll(items);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView mTitle;
        TextView mStartDate;
        TextView mEndDate;
        TextView mPosition;

        public ViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView)itemView.findViewById(R.id.lend_book_record_title);
            mStartDate = (TextView)itemView.findViewById(R.id.lend_book_record_start_date);
            mEndDate = (TextView)itemView.findViewById(R.id.lend_book_record_end_date);
            mPosition = (TextView)itemView.findViewById(R.id.lend_book_record_position);
        }

        public void setItem(LendBookRecordItem item){
            mTitle.setText(item.getmTitle());
            mStartDate.setText(String.format("대출일: %s", item.getmStartDate()));
            mEndDate.setText(String.format("반납일: %s",item.getmEndDate()));
            mPosition.setText(String.format("소장위치: %s",item.getmPosition()));
        }
    }
}
