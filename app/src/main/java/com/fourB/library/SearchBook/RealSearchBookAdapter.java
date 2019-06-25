package com.fourB.library.SearchBook;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.fourB.library.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RealSearchBookAdapter extends RecyclerView.Adapter<RealSearchBookAdapter.ViewHolder> {
    Context mContext;
    ArrayList<RealSearchBookItem> mItemData = new ArrayList<RealSearchBookItem>();

    public RealSearchBookAdapter(Context context){
        this.mContext = context;
    }

    @Override
    public int getItemCount() {
        return mItemData.size();
    }

    @Override
    public RealSearchBookAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_real_search_book, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RealSearchBookAdapter.ViewHolder viewHolder, int i) {
        final RealSearchBookItem item = mItemData.get(i);
        viewHolder.setItem(item);
    }

    public void addItem(RealSearchBookItem item){
        mItemData.add(item);
    }

    public void addItems(ArrayList<RealSearchBookItem> items){
        this.mItemData.addAll(items);
    }

    public RealSearchBookItem getItem (int position){
        return mItemData.get(position);
    }

    public void clear() {
        mItemData.clear();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView mCardView;
        private TextView mBookTitle;
        private TextView mBookAuthor;
        private TextView mBookPublisher;
        private TextView mBookPubDate;
        private TextView mBookCategory;

        public ViewHolder(View itemView) {
            super(itemView);
            mCardView = itemView.findViewById(R.id.search_book_card);
            mBookTitle = itemView.findViewById(R.id.search_book_title);
            mBookAuthor = itemView.findViewById(R.id.search_book_author);
            mBookPublisher = itemView.findViewById(R.id.search_book_publisher);
            mBookPubDate = itemView.findViewById(R.id.search_book_publish_date);
            mBookCategory = itemView.findViewById(R.id.search_book_category);
        }

        public void setItem(final RealSearchBookItem item){
            mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mCardView.getContext(), RealSearchBookDetailActivity.class);
                    intent.putExtra("id", item.getmId());

                    mCardView.getContext().startActivity(intent);
                }
            });
            mBookTitle.setText(item.getmTitle());
            mBookAuthor.setText("저자 : " + item.getmAuthor());
            mBookPublisher.setText("출판사 : " + item.getmPublisher());
            mBookPubDate.setText("출판년도 : " + item.getmPubdate());
            mBookCategory.setText(item.getmCategory());


            mBookCategory.bringToFront();
        }
    }
}
