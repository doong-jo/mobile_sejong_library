package com.fourB.library.ReadingRoom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.fourB.library.R;

public class ReadingRoomWVFragment extends Fragment {
    static final String ROOM_URL = "http://210.107.226.14/seat/roomview5.asp?room_no=";

    private ReadingRoomCallBack callBack;
    private WebView mWebView;
    private Button mBackBtn;
    private String mRoomUrl;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof ReadingRoomCallBack){
            callBack = (ReadingRoomCallBack) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(callBack != null)
            callBack = null;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_reading_room_wv,container,false);

        mWebView = (WebView) rootView.findViewById(R.id.webView_reading_room);
        mBackBtn = (Button) rootView.findViewById(R.id.btn_back_reading_room);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClientClass());


        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.loadUrl(mRoomUrl);

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onCommand(0);
            }
        });
        return rootView;
    }

    public void setRoomUrl(String index) {
        this.mRoomUrl = ROOM_URL + index;
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
