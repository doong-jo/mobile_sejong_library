package com.fourB.library.Anouncement;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fourB.library.R;

public class AnouncementItemView extends LinearLayout {
    TextView mAnouncement_Title;
    TextView mAnouncement_Update_Date;

    public AnouncementItemView(Context context) {
        super(context);

        init(context);
    }

    public AnouncementItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_anouncement, this, true);
        mAnouncement_Title = (TextView) findViewById(R.id.anouncement_title);
        mAnouncement_Update_Date = (TextView)findViewById(R.id.Anouncement_Update_Date);
    }

    public void setmAnouncement_Title(String anouncement_title){
        mAnouncement_Title.setText(anouncement_title);
    }
    public void setmAnouncement_Update_Date(String anouncement_update_date){
        mAnouncement_Update_Date.setText(anouncement_update_date);
    }

}
