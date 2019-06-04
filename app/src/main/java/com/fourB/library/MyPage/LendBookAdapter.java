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

public class LendBookAdapter extends RecyclerView.Adapter<LendBookAdapter.ViewHolder> {

    Context context;
    ArrayList<LendBookItem> items =  new ArrayList<LendBookItem>();

    LayoutInflater inflater;

    public LendBookAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public LendBookAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_lend_book, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LendBookAdapter.ViewHolder holder, final int position) {
        final LendBookItem item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(LendBookItem item){
        items.add(item);
    }

    public void addItems(ArrayList<LendBookItem> items){
        this.items.addAll(items);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView mTitle;
        TextView mStartDate;
        TextView mEndDate;

        public ViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView)itemView.findViewById(R.id.lend_book_record_title);
            mStartDate = (TextView)itemView.findViewById(R.id.lend_book_record_start_date);
            mEndDate = (TextView)itemView.findViewById(R.id.lend_book_record_end_date);
        }

        public void setItem(LendBookItem item){
            mTitle.setText(item.getmTitle());
            mStartDate.setText(String.format("대출일: %s", item.getmStartDate()));
            mEndDate.setText(String.format("반납 예정일: %s",item.getmEndDate()));
        }
    }
}
