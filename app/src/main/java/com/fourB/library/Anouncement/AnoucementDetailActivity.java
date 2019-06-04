package com.fourB.library.Anouncement;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fourB.library.R;

public class AnoucementDetailActivity extends AppCompatActivity {

    private  WebView mWebView;
    String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        processIntent(intent);

        mWebView=(WebView)findViewById(R.id.activity_main_webview);
        WebSettings mWebSettings=mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLoadWithOverviewMode(true);

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.loadUrl(mUrl);
    }

    public void processIntent(Intent intent){
        if(intent != null){
            mUrl = intent.getStringExtra("Url");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

}

