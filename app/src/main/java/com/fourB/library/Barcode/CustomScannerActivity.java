package com.fourB.library.Barcode;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.fourB.library.R;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

public class CustomScannerActivity extends Activity{

    private CaptureManager mCapture;
    private DecoratedBarcodeView mbarcodeScannerView;
    private BackPressCloseHandler mbackPressCloseHandler;
    public ImageView mcameraIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_camera);

        mbackPressCloseHandler = new BackPressCloseHandler(this);

        mcameraIcon = (ImageView)findViewById(R.id.cameraIcon);
        mcameraIcon.setImageResource(R.drawable.ic_menu_camera);

        mbarcodeScannerView = (DecoratedBarcodeView)findViewById(R.id.zxing_barcode_scanner);
        mCapture = new CaptureManager(this, mbarcodeScannerView);
        mCapture.initializeFromIntent(getIntent(), savedInstanceState);
        mCapture.decode();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCapture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCapture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCapture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mCapture.onSaveInstanceState(outState);
    }

    public void onBackPressed() {
        mbackPressCloseHandler.onBackPressed();
    }

}