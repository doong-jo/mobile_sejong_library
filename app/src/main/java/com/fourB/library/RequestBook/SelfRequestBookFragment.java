package com.fourB.library.RequestBook;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fourB.library.R;

import java.util.ArrayList;
import java.util.Arrays;

public class SelfRequestBookFragment extends Fragment {

    private Button mBtnRequest;
    private Spinner mBookKinds;

    private ArrayList<String> mBookKindsArrayList = new ArrayList<>();
    private ArrayAdapter<String> mBookKindsArrayAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_self_request_book, container, false);

        mBtnRequest = (Button) rootView.findViewById(R.id.self_book_request_button);
        mBtnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "신청 완료", Toast.LENGTH_SHORT).show();
            }
        });

        String[] bookKinds = getResources().getStringArray(R.array.book_kinds);

        mBookKinds = (Spinner) rootView.findViewById(R.id.book_kinds_spinner);
        mBookKindsArrayList.addAll(Arrays.asList(bookKinds));

        mBookKindsArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.item_basic_spinner, mBookKindsArrayList);
        mBookKinds.setAdapter(mBookKindsArrayAdapter);


        return rootView;
    }

}
