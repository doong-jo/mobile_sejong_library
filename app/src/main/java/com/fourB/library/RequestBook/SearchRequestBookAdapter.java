package com.fourB.library.RequestBook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.fourB.library.R;
import com.fourB.library.SearchBook.SearchBookItem;
import com.fourB.library.Util.ReportManager;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SearchRequestBookAdapter extends RecyclerView.Adapter<SearchRequestBookAdapter.ViewHolder> {

    Context mContext;
    ArrayList<SearchBookItem> mItemData = new ArrayList<SearchBookItem>();
    private String mbookTitle;

    public SearchRequestBookAdapter(Context context){
        this.mContext = context;
    }

    @Override
    public int getItemCount() {
        return mItemData.size();
    }

    @Override
    public SearchRequestBookAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_search_request_book, viewGroup, false);
        return new SearchRequestBookAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SearchRequestBookAdapter.ViewHolder viewHolder, int i) {
        final SearchBookItem item = mItemData.get(i);

        viewHolder.setItem(item);

        viewHolder.mRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mbookTitle = viewHolder.mBookTitle.getText().toString();
                show();
            }
        });
    }

    void show()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("희망 도서 신청");
        builder.setMessage( mbookTitle + " 를 신청하겠습니까?");
        builder.setPositiveButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(mContext,"신청 취소되었습니다.",Toast.LENGTH_LONG).show();
                    }
                });
        builder.setNegativeButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(mContext,"신청 완료되었습니다.",Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
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
        private Button mRequestButton;

        public ViewHolder(View itemView) {
            super(itemView);
            mCardView = itemView.findViewById(R.id.search_book_card);
            mBookImg = itemView.findViewById(R.id.search_book_img);
            mBookTitle = itemView.findViewById(R.id.search_book_title);
            mBookAuthor = itemView.findViewById(R.id.search_book_author);
            mBookPublisher = itemView.findViewById(R.id.search_book_publisher);
            mBookPubDate = itemView.findViewById(R.id.search_book_publish_date);
            mRequestButton = itemView.findViewById(R.id.request_button);
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
