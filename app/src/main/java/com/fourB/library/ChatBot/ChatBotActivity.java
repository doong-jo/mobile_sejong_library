package com.fourB.library.ChatBot;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.database.DataSetObserver;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import com.fourB.library.R;
import com.fourB.library.ChatBot.async.RequestJavaV2Task;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Objects;

import ai.api.android.AIConfiguration;
import ai.api.android.AIDataService;
import ai.api.model.AIRequest;
import ai.api.model.ResponseMessage;
import ai.api.model.Result;

public class ChatBotActivity extends AppCompatActivity implements ChatBotInterface {
    static final boolean BOT_SIDE = true;
    static final boolean USER_SIDE = false;

    private ChatArrayAdapter mChatArrayAdapter;
    private ListView mListView;
    private EditText mChatText;
    private AppCompatImageButton mButtonSend;

    private ChatBotInterface mThisInterface;
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
    protected void onCreate(Bundle savedInstanceState) {
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

        botSpeechOfString(getString(R.string.chatbot_say_hello));
    }

    private void initAIConfigure() {
        final AIConfiguration config = new AIConfiguration("6d747f5fec06408d87631a072c965fe0",
                AIConfiguration.SupportedLanguages.Korean,
                AIConfiguration.RecognitionEngine.System);

        mAIDataRequset = new AIDataRequest(this, config);
    }

    private void initView(){
        mButtonSend = findViewById(R.id.buttonSend);
        mListView = findViewById(R.id.listView);
        mChatText = findViewById(R.id.chatText);

        mChatArrayAdapter = new ChatArrayAdapter(this, R.layout.layout_chat_bot_msg);
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
        if( msg.equals("") ) { return true; }
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
    public void preBotSpeech() {
        mButtonSend.setEnabled(false);
        mChatArrayAdapter.add(new ChatMessage(BOT_SIDE, "", null));
    }

    @Override
    public void botSpeech(final Result result) {
        final int arrCount = mChatArrayAdapter.getCount();
        if( arrCount != 0 ) {
            mChatArrayAdapter.remove(arrCount - 1);
        }

        mChatArrayAdapter.add(new ChatMessage(BOT_SIDE, "", result));

        mChatArrayAdapter.notifyDataSetChanged();
        mButtonSend.setEnabled(true);
    }

    @Override
    public void botSpeechOfString(String msg) {
        mChatArrayAdapter.add(new ChatMessage(BOT_SIDE, msg, null));

        mChatArrayAdapter.notifyDataSetChanged();
        mButtonSend.setEnabled(true);
    }

    @Override
    public void userSpeech(final String msg) {
        mChatArrayAdapter.add(new ChatMessage(USER_SIDE, mChatText.getText().toString(), null));
        mAIDataRequset.request(msg);
    }
}
