package com.fourB.library.Barcode;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.fourB.library.R;

public class BackPressCloseHandler {

    private long backKeyPressedTime = 0;
    private Toast toast;
    private Activity activity;

    public BackPressCloseHandler(Activity context) {
        this.activity = context;
    }

    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + R.integer.barcode_Backable_second) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + R.integer.barcode_Backable_second) {
            activity.finish();
            toast.cancel();
        }
    }
    public void showGuide() {
        toast = Toast.makeText(activity, R.string.backButton_comment, Toast.LENGTH_SHORT); toast.show();
    }
}
