package com.fourB.library.GuideAll;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;



@SuppressLint("ValidFragment")
public class GuideFragment extends Fragment {
    int mLayout;

    @SuppressLint("ValidFragment")
    public GuideFragment(int layout) {
        this.mLayout = layout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup mRootView = (ViewGroup) inflater.inflate(mLayout,container,false);
        return mRootView;
    }
}
