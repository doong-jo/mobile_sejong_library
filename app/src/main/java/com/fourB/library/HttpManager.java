package com.fourB.library;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpManager {
    final static private String SERVER_URL = "https://sejong-library.run.goorm.io/";

    private static  OkHttpClient client = new OkHttpClient();

    public static String httpRun(String url, final String[] query, final String jsonBody, final String method) throws IOException {
        url = url + "?";

        String uriQuery = "";
        for (int i = 0; i < query.length; i++) {
            StringBuilder builder = new StringBuilder(uriQuery);
            builder.append(query[i]);
            if( i != query.length - 1) { builder.append('&'); }
            uriQuery = builder.toString();
        }

        StringBuilder builder = new StringBuilder(SERVER_URL);
        builder.append(url);
        builder.append(uriQuery);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonBody);

        Request.Builder reqBuilder = new Request.Builder();
        reqBuilder.url(builder.toString());

        if( method.equals("GET")) {

        } else if ( method.equals("POST") ) {
            reqBuilder.post(body);
        } else if ( method.equals("PUT") ) {
            reqBuilder.put(body);
        } else if ( method.equals("DELETE")) {
            reqBuilder.delete(body);
        }

        final Request request = reqBuilder.build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
