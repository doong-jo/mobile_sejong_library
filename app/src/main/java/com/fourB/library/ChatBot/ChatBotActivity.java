package com.fourB.library.ChatBot;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.fourB.library.R;
import com.fourB.library.async.RequestJavaV2Task;

import java.util.Objects;

import ai.api.android.AIConfiguration;
import ai.api.android.AIDataService;
import ai.api.model.AIRequest;

public class ChatBotActivity extends AppCompatActivity implements ChatBotService {
    static final boolean LEFT_SIDE = true;
    static final boolean RIGHT_SIDE = false;

    private ChatArrayAdapter mChatArrayAdapter;
    private ListView mListView;
    private EditText mChatText;
    private Button mButtonSend;

    private ChatBotService mThisInterface;
    private AIDataRequest mAIDataRequset;

    class AIDataRequest {
        private AIDataService mAIDataService;
        private AIRequest mAIRequset;
        private RequestJavaV2Task mAsyncV2;

        public AIDataRequest(Context context, AIConfiguration config) {
            mAIDataService = new AIDataService(context, config);
            mAIRequset = new AIRequest();
            mAIRequset.setLanguage(getString(R.string.korean_lang_code));
        }

        public void request(String requsetMessage) {
            mAIRequset.setQuery(requsetMessage);
            mAsyncV2 = new RequestJavaV2Task(mAIDataService, mThisInterface);
            mAsyncV2.execute(mAIRequset);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mThisInterface = this;

        initAIConfigure();
        initView();
        initListener();

        mChatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                mListView.setSelection(mChatArrayAdapter.getCount() - 1);
            }
        });

    }

    private void initAIConfigure() {
        final AIConfiguration config = new AIConfiguration("6d747f5fec06408d87631a072c965fe0",
                AIConfiguration.SupportedLanguages.Korean,
                AIConfiguration.RecognitionEngine.System);

        mAIDataRequset = new AIDataRequest(this, config);
    }

    private void initView(){
        mButtonSend = (Button) findViewById(R.id.buttonSend);
        mListView = (ListView) findViewById(R.id.listView);
        mChatText = (EditText) findViewById(R.id.chatText);

        mChatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.activity_chat_singlemessage);
        mListView.setAdapter(mChatArrayAdapter);

        mListView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        mListView.setAdapter(mChatArrayAdapter);
    }

    private void initListener() {
        mChatText.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return sendChatMessage();
                }
                return false;
            }
        });

        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sendChatMessage();
            }
        });
    }

    private boolean sendChatMessage(){
        final String msg = mChatText.getText().toString();
        userSpeech(msg);

        mChatText.setText("");
        return true;
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

    @Override
    public void botSpeech(String result) {
        mChatArrayAdapter.add(new ChatMessage(LEFT_SIDE, result));
    }

    @Override
    public void userSpeech(final String msg) {
        mChatArrayAdapter.add(new ChatMessage(RIGHT_SIDE, mChatText.getText().toString()));
        mAIDataRequset.request(msg);
    }
}
