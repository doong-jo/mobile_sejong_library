package com.fourB.library.Login;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.fourB.library.HttpManager;
import com.fourB.library.R;

import java.io.IOException;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    final String TAG = this.getClass().getName();

    private ScrollView mScroll;
    private Button mLoginBtn;
    private EditText mInputId;
    private EditText mInputPw;

    private Spinner loginUserSpinner;
    private ArrayList<String> loginUserArrayList;
    private ArrayAdapter<String> loginUserArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initKeyboardUtils();
        initListener();
    }

    private void initKeyboardUtils() {
        // Threshold for minimal keyboard height.
        final int MIN_KEYBOARD_HEIGHT_PX = 150;

// Top-level window decor view.
        final View decorView = getWindow().getDecorView();

// Register global layout listener.
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            private final Rect windowVisibleDisplayFrame = new Rect();
            private int lastVisibleDecorViewHeight;

            @Override
            public void onGlobalLayout() {
                // Retrieve visible rectangle inside window.
                decorView.getWindowVisibleDisplayFrame(windowVisibleDisplayFrame);
                final int visibleDecorViewHeight = windowVisibleDisplayFrame.height();

                // Decide whether keyboard is visible from changing decor view height.
                if (lastVisibleDecorViewHeight != 0) {
                    if (lastVisibleDecorViewHeight > visibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX) {
                        // Calculate current keyboard height (this includes also navigation bar height when in fullscreen mode).
                        int currentKeyboardHeight = decorView.getHeight() - windowVisibleDisplayFrame.bottom;
                        // Notify listener about keyboard being shown.
                        onKeyboardShown(currentKeyboardHeight);
                    } else if (lastVisibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX < visibleDecorViewHeight) {
                        // Notify listener about keyboard being hidden.
//                        onKeyboardHidden();
                    }
                }
                // Save current decor view height for the next call.
                lastVisibleDecorViewHeight = visibleDecorViewHeight;
            }
        });
    }

    void onKeyboardShown(int height) {
        mScroll.fullScroll(ScrollView.FOCUS_DOWN);
    }

    private void initView() {
        mScroll = findViewById(R.id.login_scroll);
        mLoginBtn = findViewById(R.id.login_button);
        mInputId = findViewById(R.id.login_student_id);
        mInputPw = findViewById(R.id.login_student_pw);

        loginUserArrayList = new ArrayList<>();
        loginUserArrayList.add(getString(R.string.login_type_student));
        loginUserArrayList.add(getString(R.string.login_type_staff));

        loginUserArrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                loginUserArrayList);

        loginUserSpinner = findViewById(R.id.login_user_spinner);
        loginUserSpinner.setAdapter(loginUserArrayAdapter);
    }

    private void initListener() {
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessLogin(mInputId.getText().toString(), mInputPw.getText().toString());
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);
            }
        });
    }

    private void accessLogin(final String id, final String pw) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String[] query = {"id=" + id, "pw=" + pw };

                    Log.d(TAG, HttpManager.httpRun("user", query));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}

