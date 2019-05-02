package com.fourB.library;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.fourB.library.async.RequestJavaV2Task;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIDataService;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;

public class ChatBotActivity extends AppCompatActivity {

    private static final String TAG = "ChatActivity";

    private ChatArrayAdapter mChatArrayAdapter;
    private ListView mListView;
    private EditText mChatText;
    private Button mButtonSend;

    Intent intent;
    private boolean side = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        setTitle(getString(R.string.menu_chatbot));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final AIConfiguration config = new AIConfiguration("6d747f5fec06408d87631a072c965fe0",
                AIConfiguration.SupportedLanguages.Korean,
                AIConfiguration.RecognitionEngine.System);

        final AIDataService aiDataService = new AIDataService(this, config);
        final AIRequest aiRequest = new AIRequest();
        aiRequest.setLanguage(getString(R.string.korean_lang_code));
        aiRequest.setQuery("신고");

        RequestJavaV2Task asyncV2 = new RequestJavaV2Task(aiDataService);
        asyncV2.execute(aiRequest);

        allFindViewById();

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

        mChatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                mListView.setSelection(mChatArrayAdapter.getCount() - 1);
            }
        });

    }

    private void allFindViewById(){

        mButtonSend = (Button) findViewById(R.id.buttonSend);
        mListView = (ListView) findViewById(R.id.listView);
        mChatText = (EditText) findViewById(R.id.chatText);

        mChatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.activity_chat_singlemessage);
        mListView.setAdapter(mChatArrayAdapter);

        mListView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        mListView.setAdapter(mChatArrayAdapter);
    }

    private boolean sendChatMessage(){
        mChatArrayAdapter.add(new ChatMessage(side, mChatText.getText().toString()));
        mChatText.setText("");
        side = !side;
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
}
