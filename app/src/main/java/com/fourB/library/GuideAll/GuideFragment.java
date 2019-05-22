package com.fourB.library.GuideAll;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fourB.library.R;


@SuppressLint("ValidFragment")
public class GuideFragment extends Fragment {
    int mLayout;

    @SuppressLint("ValidFragment")
    public GuideFragment(int layout) {
        this.mLayout = layout;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup mRootView = (ViewGroup) inflater.inflate(mLayout,container,false);
        return mRootView;
    }
}
