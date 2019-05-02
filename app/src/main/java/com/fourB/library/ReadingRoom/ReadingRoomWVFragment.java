package com.fourB.library.ReadingRoom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.fourB.library.R;

public class ReadingRoomWVFragment extends Fragment {
    ReadingRoomCallBack callBack;
    private WebView mWebView;
    private Button mBackBtn;
    private String roomUrl = "";

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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_reading_room_wv,container,false);

        mWebView = (WebView) rootView.findViewById(R.id.webView_reading_room);
        mBackBtn = (Button) rootView.findViewById(R.id.btn_back_reading_room);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClientClass());

        //mWebView.getSettings().setSupportZoom( true ); //Modify this
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.loadUrl(roomUrl);

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onCommand(0);
            }
        });
        return rootView;
    }

    public void setRoomUrl(String index) {
        String roomUrl = "http://210.107.226.14/seat/roomview5.asp?room_no=";
        this.roomUrl = roomUrl + index;
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("check URL", url);
            view.loadUrl(url);
            return true;
        }
    }
}
