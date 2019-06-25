package com.fourB.library.Util;


import com.fourB.library.SearchBook.SearchBookItem;
import com.fourB.library.SearchBook.RealSearchBookItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
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
import java.util.ArrayList;
import java.util.List;

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

    final static private String RS_URL = "http://mlib.sejong.ac.kr/";
    final static private String RS_BOOK_SEARCH_URL = "http://mlib.sejong.ac.kr/search/Search.Result.List.axa?";
    final static private String RS_BOOK_SEARCH_DETAIL_URL =  "http://mlib.sejong.ac.kr/search/Search.Detail.ax?";
    final static private String RS_BOOK_SEARCH_DETAIL_LIST_URL = "http://mlib.sejong.ac.kr/search/ItemDetail.axa?";

    final static private String RS_STUDYROOM_SEARCH_URL =  "";
    final static private String RS_READINGROOM_SEARCH_URL =  "";

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

    public static RealSearchBookItem searchBookDetailRealServer(final String id) throws IOException {
        //////////////////////////////////////// Detail List ////////////////////////////////////////
        RealSearchBookItem item = new RealSearchBookItem();

        StringBuilder URLbuilder = new StringBuilder(RS_BOOK_SEARCH_DETAIL_URL);
        URLbuilder.append("cid=").append(id);

        URL url = new URL(URLbuilder.toString());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
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

        Document doc = Jsoup.parse(searchResult.toString());
        Elements elements;

        // ImageURL
        elements = doc.select(".book > img");

        List<String> listImgURL = elements.eachAttr("src");
        int a = 1;
        for (int i = 0; i < listImgURL.size(); i++) {
            if( listImgURL.get(i).split("\\.\\./").length == 1 ) {
                continue;
            } else {
                listImgURL.set(i, RS_URL + listImgURL.get(i).split("\\.\\./")[1]);
            }

        }

        // Title
        elements = doc.select(".borrow_d > h2");
        List<String> listTitle = elements.eachText();

        // Author
        elements = doc.select(".borrow_d > h3");
        List<String> listAuthor = elements.eachText();
        for (int i = 0; i < listAuthor.size(); i++) {
            listAuthor.set(i, listAuthor.get(i).split(" / ")[0]);
        }

        // Publisher
        elements = doc.select(".writer");
        List<String> listPublisher = elements.eachText();

        // Category
        elements = doc.select(".borrow_d > ul > li:nth-child(1)");
        List<String> listCategory = elements.eachText();
        for (int i = 0; i < listCategory.size(); i++) {
            listCategory.set(i, listCategory.get(i).split("자 료 유 형 : ")[1]);
        }

        br.close();
        con.disconnect();


        //////////////////////////////////////// Detail ////////////////////////////////////////
        URLbuilder = new StringBuilder(RS_BOOK_SEARCH_DETAIL_LIST_URL);
        URLbuilder.append("cid=").append(id);

        url = new URL(URLbuilder.toString());
        con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.connect();

        responseCode = con.getResponseCode();

        if(responseCode == 200) { // 정상 호출
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));

        } else {  // 에러 발생
            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }

        searchResult = new StringBuilder();
        while ((inputLine = br.readLine()) != null) { searchResult.append(inputLine + "\n"); }

        doc = Jsoup.parse(searchResult.toString());

        elements = doc.select(".borrow > h2");
        List<String> listLocation =  elements.eachText();
        for (int i = 0; i < listLocation.size(); i++) {
            listLocation.set(i, listLocation.get(i).split("/")[1]);
        }

        elements = doc.select(".borrow > h3");
        final List<String> contentEachText = elements.eachText();
        List<String> listNumber =  new ArrayList<>();
        List<String> listStatus =  new ArrayList<>();

        for (int i = 0; i < listLocation.size(); i++) {
            listNumber.add(contentEachText.get(i).split(" / ")[0]);
            listStatus.add(contentEachText.get(i).split(" / ")[1].split(" /")[0]);
        }

        br.close();
        con.disconnect();

        item.setmImgURL(listImgURL.get(0));
        item.setmCategory(listCategory.get(0));
        item.setmAuthor(listAuthor.get(0));
        item.setmTitle(listTitle.get(0));
        item.setmLocation(listLocation.get(0));
        item.setmLocationNum(listNumber.get(0));
        item.setmStatus(listStatus.get(0));
        item.setmPublisher(listPublisher.get(0));

        return item;
    }

    public static RealSearchBookItem[] searchBookRealServer(final String searchObject, final int category, final int page, final int pageSize) throws IOException {
        final String searchStr = URLEncoder.encode(searchObject, "UTF-8");

        StringBuilder URLbuilder = new StringBuilder(RS_BOOK_SEARCH_URL);
        URLbuilder.append("sid=").append(category).append("&");
        URLbuilder.append("q=").append(searchStr).append("&");
        URLbuilder.append("page=").append(page).append("&");
        URLbuilder.append("pageSize=").append(pageSize);

        URL url = new URL(URLbuilder.toString());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
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

        Document doc = Jsoup.parse(searchResult.toString());

        // elements
        Elements rootElement = doc.select(".f_left");
        Elements elements;
        final int dataSize = rootElement.size();
        RealSearchBookItem[] realSearchBookRealItems = new RealSearchBookItem[dataSize];

        String dataId, dataCategory, dataTitle,
                dataAuthor, dataPublisher, dataPublishDate,
                dataLocation, dataLocatinNum, dataStatus;

        for (int i = 0; i < dataSize; i++) {
            // id
            elements = rootElement.get(i).select(".search_brief_title");
            dataId = elements.attr("href");
            dataId = dataId.split("cid=")[1];

            // category
            elements = rootElement.get(i).select(".padding_btn_searchClass");
            dataCategory = elements.text();

            // title, author
            elements = rootElement.get(i).select(".search_brief_title > span");

            if ( elements.text().split(" / ").length == 1 ) {
                dataTitle = elements.text().split(" / ")[0];
                dataAuthor = "";
            } else {
                dataTitle = elements.text().split(" / ")[0];
                dataAuthor = elements.text().split(" / ")[1];
            }

            // publisher, publish date
            elements = rootElement.get(i).select(".f_left > p");
            if( elements.text().split("출판사 : ").length == 1 ) { dataPublisher = ""; }
            else { dataPublisher = elements.text().split("출판사 : ")[1].split(" / ")[0]; }

            if( elements.text().split("출판년도 : ").length == 1 ) { dataPublishDate = ""; }
            else { dataPublishDate = elements.text().split("출판년도 : ")[1].split(" ")[0]; }

            // location, location number
            elements = rootElement.get(i).select(".black");
            if( elements.text().split("\\[").length == 1 ) {
                dataLocation = "";
            } else {
                dataLocation = elements.text().split("\\[")[1].split("]")[0];
            }

            if( elements.text().split("]").length == 1 ) {
                dataLocatinNum = "";
            } else {
                dataLocatinNum = elements.text().split("]")[1].split(" /")[0];
            }

            // status
            elements = rootElement.get(i).select(".status");
            dataStatus = elements.text();

            realSearchBookRealItems[i] = new RealSearchBookItem(
                    dataId, dataStatus, dataCategory, dataTitle,
                    dataAuthor, dataPublisher, dataPublishDate, dataLocation, dataLocatinNum
            );
        }

        return realSearchBookRealItems;
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
