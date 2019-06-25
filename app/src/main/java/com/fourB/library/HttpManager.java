package com.fourB.library;


import com.fourB.library.SearchBook.SearchBookItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpManager {
    final static private String SERVER_URL = "https://sejong-library.run.goorm.io/";
    final static private String NAVER_BOOK_API_URL = "https://openapi.naver.com/v1/search/book.json?query=";
    final static private String NAVER_BOOK_DETAIL_API_URL = "https://openapi.naver.com/v1/search/book_adv.xml?query=";
    final static private String NAVER_CLIENT_ID = "s9Q1eIGcTHVFQCFXNv22";
    final static private String NAVER_CLIENT_SECRET = "rpx4xcAiUt";

    final static public String BOOK_SORT_SIM = "sim";
    final static public String BOOK_SORT_DATE = "date";
    final static public String BOOK_SORT_COUNT = "count";

    final static public String BOOK_CATEGORY_TITLE = "d_titl";
    final static public String BOOK_CATEGORY_AUTOR = "d_auth";
    final static public String BOOK_CATEGORY_PUBL = "d_publ";

    final static int STEP_NONE = 0;
    final static int STEP_TITLE = 1;
    final static int STEP_IMAGE = 2;
    final static int STEP_AUTHOR = 3;
    final static int STEP_PUBLISHER = 4;
    final static int STEP_PUBDATE = 5;
    final static int STEP_ISBN = 6;

    static public int mStep = STEP_NONE;



    private static  OkHttpClient client = new OkHttpClient();

    public static SearchBookItem[] searchBookNaverXMLApi(final String searchObject, final int display, final String category) throws IOException, XmlPullParserException {
        String text = URLEncoder.encode(searchObject, "UTF-8");

        String apiURL = NAVER_BOOK_DETAIL_API_URL + text + "&display=" + display + "&start=1" + "&" + category + "=" + text; // xml 결과
        URL url = new URL(apiURL);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("X-Naver-Client-Id", NAVER_CLIENT_ID);
        con.setRequestProperty("X-Naver-Client-Secret", NAVER_CLIENT_SECRET);
        con.connect();

        int responseCode = con.getResponseCode();

        BufferedReader br;
        if(responseCode == 200) { // 정상 호출
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));

        } else {  // 에러 발생
            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }

        StringBuilder searchResult = new StringBuilder();
        String inputLine;
        while ((inputLine = br.readLine()) != null) { searchResult.append(inputLine + "\n"); }

        String result = searchResult.toString();

        XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = parserFactory.newPullParser();

        parser.setInput(new StringReader(result));

        br.close();
        con.disconnect();


        SearchBookItem[] bookItems = new SearchBookItem[display];
        for(int i=0; i<bookItems.length; i++ ) { bookItems[i] = new SearchBookItem(); }

        int eventType = parser.getEventType() ;
        int k = 0;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_DOCUMENT) {
                // XML 데이터 시작
            } else if (eventType == XmlPullParser.START_TAG) {
                String startTag = parser.getName();
                if (startTag.equals("title")) {
                    mStep = STEP_TITLE;
                } else if (startTag.equals("image")) {
                    mStep = STEP_IMAGE;
                } else if (startTag.equals("author")) {
                    mStep = STEP_AUTHOR;
                } else if (startTag.equals("publisher")){
                    mStep = STEP_PUBLISHER;
                } else if(startTag.equals("pubdate")){
                    mStep = STEP_PUBDATE;
                } else if(startTag.equals("isbn")){
                    mStep = STEP_ISBN;
                }
            } else if (eventType == XmlPullParser.END_TAG) {
                String endTag = parser.getName();
                if ((endTag.equals("item")))
                {
                    k++;
                }
                mStep = STEP_NONE;
            } else if (eventType == XmlPullParser.TEXT) {
                String parText = parser.getText();
                if (mStep == STEP_TITLE) {
                    bookItems[k].setTitle(parText);
                } else if (mStep == STEP_IMAGE) {
                    bookItems[k].setImageURL(parText);
                } else if (mStep == STEP_AUTHOR) {
                    bookItems[k].setAuthor(parText);
                } else if (mStep == STEP_PUBLISHER) {
                    bookItems[k].setPublisher(parText);
                } else if (mStep == STEP_PUBDATE){
                    bookItems[k].setPubdate(parText);
                }
            }
            eventType = parser.next();
        }
        return bookItems;
    }

    public static SearchBookItem[] searchBookNaverApi(final String searchObject, final int display, final String sort) throws IOException {
        String text = URLEncoder.encode(searchObject, "UTF-8");

        String apiURL = NAVER_BOOK_API_URL + text + "&display=" + display + "&" + "sort=" + sort; // json 결과

        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("X-Naver-Client-Id", NAVER_CLIENT_ID);
        con.setRequestProperty("X-Naver-Client-Secret", NAVER_CLIENT_SECRET);
        con.connect();

        int responseCode = con.getResponseCode();

        BufferedReader br;
        if(responseCode == 200) { // 정상 호출
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));

        } else {  // 에러 발생
            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }

        StringBuilder searchResult = new StringBuilder();
        String inputLine;
        while ((inputLine = br.readLine()) != null) { searchResult.append(inputLine + "\n"); }

        br.close();
        con.disconnect();

        String data = searchResult.toString();
        String[] dataArr = data.split("\"");
        SearchBookItem[] bookItems = new SearchBookItem[display];
        for(int i=0; i<bookItems.length; i++ ) { bookItems[i] = new SearchBookItem(); }
        int k = 0;
        for (int i = 0; i < dataArr.length - 2; i++) {
            final String pivot = dataArr[i];
            final String value = dataArr[i + 2];
            if(pivot == null || value == null) {
                int a = 1;
            }
//            Log.d("searchBookNaverApi", "k: " + k);
            if (pivot.equals("title")) {
                bookItems[k].setTitle(value);
            }
            if (pivot.equals("image")) bookItems[k].setImageURL(value);
            if (pivot.equals("author")) bookItems[k].setAuthor(value);
            if (pivot.equals("publisher")) bookItems[k].setPublisher(value);
            if (pivot.equals("pubdate")) { bookItems[k].setPubdate(value);
                k++;
            }
        }

        return bookItems;
    }

    public static String internalServerAPI(String url, final String[] query, final String jsonBody, final String method) throws IOException {
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
