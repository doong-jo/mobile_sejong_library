package com.fourB.library.RequestBook;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fourB.library.R;

import java.util.ArrayList;
import java.util.Arrays;

public class SelfRequestBookFragment extends Fragment {

    private Button mBtnRequest;
    private EditText mBooktitle;
    private Spinner mBookKinds;
    private Context mContext;

    private ArrayList<String> mBookKindsArrayList = new ArrayList<>();
    private ArrayAdapter<String> mBookKindsArrayAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_self_request_book, container, false);

        mBtnRequest = (Button) rootView.findViewById(R.id.self_book_request_button);
        mBooktitle = (EditText) rootView.findViewById(R.id.selfRequestBookTitle);
        mBtnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("희망 도서 신청");
                builder.setMessage( mBooktitle.getText().toString() + " 를 신청하겠습니까?");
                builder.setPositiveButton("아니오",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getContext(),"신청 취소되었습니다.",Toast.LENGTH_LONG).show();
                            }
                        });
                builder.setNegativeButton("예",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getContext(),"신청 완료되었습니다.",Toast.LENGTH_LONG).show();
                            }
                        });
                builder.show();
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
