package com.fourB.library;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.se.omapi.Session;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.fourB.library.async.RequestJavaV2Task;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;
import com.google.cloud.dialogflow.v2.TextInput;

import java.io.InputStream;
import java.util.UUID;

import ai.api.AIListener;
import ai.api.AIServiceException;
import ai.api.android.AIConfiguration;
import ai.api.android.AIDataService;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Result;

public class ChatBotActivity extends AppCompatActivity {
    private SessionsClient mSessionClient;
    private SessionName mSession;
    private String uuid = UUID.randomUUID().toString();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        setTitle(getString(R.string.menu_chatbot));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final AIConfiguration config = new AIConfiguration("CLIENT_ACCESS_TOKEN",
                AIConfiguration.SupportedLanguages.Korean,
                AIConfiguration.RecognitionEngine.System);

        final AIDataService aiDataService = new AIDataService(this, config);
        final AIRequest aiRequest = new AIRequest();
        aiRequest.setLanguage(getString(R.string.korean_lang_code));
        aiRequest.setQuery("신고");

        RequestJavaV2Task asyncV2 = new RequestJavaV2Task(aiDataService);
        asyncV2.execute(aiRequest);
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
