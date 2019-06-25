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

public class SearchBookAdapter extends RecyclerView.Adapter<SearchBookAdapter.ViewHolder> {
    Context mContext;
    ArrayList<SearchBookItem> mItemData = new ArrayList<SearchBookItem>();

    public SearchBookAdapter(Context context){
        this.mContext = context;
    }

    @Override
    public int getItemCount() {
        return mItemData.size();
    }

    @Override
    public SearchBookAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_search_book, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SearchBookAdapter.ViewHolder viewHolder, int i) {
        final SearchBookItem item = mItemData.get(i);
        viewHolder.setItem(item);
    }

    public void addItem(SearchBookItem item){
        mItemData.add(item);
    }

    public void addItems(ArrayList<SearchBookItem> items){
        this.mItemData.addAll(items);
    }

    public SearchBookItem getItem (int position){
        return mItemData.get(position);
    }

    public void clear() {
        mItemData.clear();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView mCardView;
        private ImageView mBookImg;
        private TextView mBookTitle;
        private TextView mBookAuthor;
        private TextView mBookPublisher;
        private TextView mBookPubDate;

        public ViewHolder(View itemView) {
            super(itemView);
            mCardView = itemView.findViewById(R.id.search_book_card);
            mBookImg = itemView.findViewById(R.id.search_book_img);
            mBookTitle = itemView.findViewById(R.id.search_book_title);
            mBookAuthor = itemView.findViewById(R.id.search_book_author);
            mBookPublisher = itemView.findViewById(R.id.search_book_publisher);
            mBookPubDate = itemView.findViewById(R.id.search_book_publish_date);
        }

        public void setItem(SearchBookItem item){
            Picasso.get().load(item.getImageURL()).into(mBookImg);
            mBookTitle.setText(Html.fromHtml(item.getTitle()));
            mBookAuthor.setText(Html.fromHtml(item.getAuthor()));
            mBookPublisher.setText(Html.fromHtml(item.getPublisher()));
            mBookPubDate.setText(Html.fromHtml(item.getPubdate()));
        }
    }
}
