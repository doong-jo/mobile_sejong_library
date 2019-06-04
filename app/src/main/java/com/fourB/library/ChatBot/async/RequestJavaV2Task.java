package com.fourB.library.ChatBot.async;

import android.os.AsyncTask;

import com.fourB.library.ChatBot.ChatBotInterface;
import com.google.gson.JsonObject;

import java.util.List;

import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.ResponseMessage;
import ai.api.model.Result;


public class RequestJavaV2Task extends AsyncTask<AIRequest, Void, AIResponse> {
    private AIDataService mAiDataService;
    private ChatBotInterface mTargetActivity;

    public RequestJavaV2Task(AIDataService aiDataService, ChatBotInterface serviceActivity) {
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

            List<ResponseMessage> responseMessageList = response.getResult().getFulfillment().getMessages();
            JsonObject customPayload = null;

            if( responseMessageList.size() >= 2 ) {
                ResponseMessage.ResponsePayload payloadMsg = (ResponseMessage.ResponsePayload)responseMessageList.get(1);
                customPayload = payloadMsg.getPayload();
            }

//            ResponseMessage.ResponsePayload payloadMsg = (ResponseMessage.ResponsePayload) responseMessageList.get(0);
//            JsonObject json = payloadMsg.getPayload();

//            Log.d("DialogFlow 4", json.getAsString());
//            Log.i("DialogFlow", "Resolved query: " + result.getResolvedQuery());
//            Log.d("DialogFlow", "Result : " + resultString);

            mTargetActivity.botSpeech(resultString, customPayload);
        }
    }

}
