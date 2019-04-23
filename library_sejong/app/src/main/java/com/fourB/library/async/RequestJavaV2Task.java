package com.fourB.library.async;

import android.os.AsyncTask;
import android.util.Log;

import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Result;

public class RequestJavaV2Task extends AsyncTask<AIRequest, Void, AIResponse> {
    private AIDataService mAiDataService;

    public RequestJavaV2Task(AIDataService aiDataService) {
        mAiDataService = aiDataService;
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
            Log.i("DialogFlow", "Resolved query: " + result.getResolvedQuery());
            Log.d("DialogFlow", "Result : " + response.getResult().getFulfillment().getSpeech());
            // process aiResponse here
        }
    }

}
