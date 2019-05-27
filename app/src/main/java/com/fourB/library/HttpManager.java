package com.fourB.library;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpManager {
    final static String TAG = HttpManager.class.getName();
    final static private String SERVER_URL = "https://sejong-library.run.goorm.io/";

    private static  OkHttpClient client = new OkHttpClient();

    public static String httpRun(String url, final String[] query) throws IOException {
        url = url + "?";

        String uriQuery = "";
        for (int i = 0; i < query.length; i++) {
            if( i != query.length - 1) {
                StringBuilder builder = new StringBuilder(uriQuery);
                builder.append(query[i]);
                builder.append('&');
                uriQuery = builder.toString();
            }
        }

        StringBuilder builder = new StringBuilder(SERVER_URL);
        builder.append(url);
        builder.append(uriQuery);

        final Request request = new Request.Builder().url(builder.toString()).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
