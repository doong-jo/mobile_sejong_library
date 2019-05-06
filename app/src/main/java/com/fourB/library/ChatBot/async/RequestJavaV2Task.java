package com.fourB.library.ChatBot.async;

import android.os.AsyncTask;

import com.fourB.library.ChatBot.ChatBotService;

import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Result;


public class RequestJavaV2Task extends AsyncTask<AIRequest, Void, AIResponse> {
    private AIDataService mAiDataService;
    private ChatBotService mTargetActivity;

    public RequestJavaV2Task(AIDataService aiDataService, ChatBotService serviceActivity) {
        mAiDataService = aiDataService;
        mTargetActivity = serviceActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mTargetActivity.preBotSpeech();
    }

    @Override
    protected AIResponse doInBackground(AIRequest... aiRequests) {
        final AIRequest request = aiRequests[0];
        try {
            final AIResponse response = mAiDataService.request(request);
            return response;
        } catch (AIServiceException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(AIResponse response) {
        if (response != null) {
            final Result result = response.getResult();
            final String resultString = response.getResult().getFulfillment().getSpeech();
//            Log.i("DialogFlow", "Resolved query: " + result.getResolvedQuery());
//            Log.d("DialogFlow", "Result : " + resultString);

            mTargetActivity.botSpeech(resultString);
        }
    }

}
