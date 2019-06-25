package com.fourB.library.Util;


import com.fourB.library.SearchBook.SearchBookItem;
import com.fourB.library.SearchBook.RealSearchBookItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

        Document doc = Jsoup.parse(searchResult.toString());
        Elements elements;

        // ID
        elements = doc.select(".search_brief_title");
        List<String> listId = elements.eachAttr("href");
        for (int i = 0; i < listId.size(); i++) {
            listId.set(i, listId.get(i).split("cid=")[1]);
        }

        final int dataSize = listId.size();
        RealSearchBookItem[] realSearchBookRealItems = new RealSearchBookItem[dataSize];

        // Category
        elements = doc.select(".padding_btn_searchClass");
        List<String> listCategory = elements.eachText();

        elements = doc.select(".search_brief_title > span");
        List<String> contentEachTexts = elements.eachText();
        // title
        List<String> listTitle = new ArrayList<>();
        // author
        List<String> listAuthor = new ArrayList<>();
        for (int i = 0; i < contentEachTexts.size(); i++) {
            listTitle.add(contentEachTexts.get(i).split(" / ")[0]);
            listAuthor.add(contentEachTexts.get(i).split(" / ")[1]);
        }


        elements = doc.select(".f_left > p");
        contentEachTexts = elements.eachText();
        // publisher
        List<String> listPublisher = new ArrayList<>();
        for (int i = 0; i < contentEachTexts.size(); i++) {
            if( contentEachTexts.get(i).split("출판사 : ").length == 1 ) { continue; }
            listPublisher.add(contentEachTexts.get(i).split("출판사 : ")[1].split(" / ")[0]);
        }

        // publisher date
        List<String> listPublishDate = new ArrayList<>();
        for (int i = 0; i < contentEachTexts.size(); i++) {
            if( contentEachTexts.get(i).split("출판년도 : ").length == 1 ) { continue; }
            listPublishDate.add(contentEachTexts.get(i).split("출판년도 : ")[1]);
        }

        elements = doc.select(".black");
        contentEachTexts = elements.eachText();
        // location
        List<String> listLocation = new ArrayList<>();
        for (int i = 0; i < contentEachTexts.size(); i++) {
            listLocation.add(contentEachTexts.get(i).split("\\[")[1].split("]")[0]);
        }

        // location number
        List<String> listLocationNum = new ArrayList<>();
        for (int i = 0; i < contentEachTexts.size(); i++) {
            listLocationNum.add(contentEachTexts.get(i).split("]")[1].split(" /")[0]);
        }

        // status
        elements = doc.select(".status");
        List<String> listStatus = elements.eachText();

        br.close();
        con.disconnect();

        addNullItemIntoListFromLength(listId, dataSize);
        addNullItemIntoListFromLength(listStatus, dataSize);
        addNullItemIntoListFromLength(listCategory, dataSize);
        addNullItemIntoListFromLength(listTitle, dataSize);
        addNullItemIntoListFromLength(listAuthor, dataSize);
        addNullItemIntoListFromLength(listPublisher, dataSize);
        addNullItemIntoListFromLength(listPublishDate, dataSize);
        addNullItemIntoListFromLength(listLocation, dataSize);
        addNullItemIntoListFromLength(listLocationNum, dataSize);

        for (int i = 0; i < dataSize; i++) {
            realSearchBookRealItems[i] = new RealSearchBookItem(
                    listId.get(i),
                    listStatus.get(i),
                    listCategory.get(i),
                    listTitle.get(i),
                    listAuthor.get(i),
                    listPublisher.get(i),
                    listPublishDate.get(i),
                    listLocation.get(i),
                    listLocationNum.get(i)
            );
        }

        return realSearchBookRealItems;
    }

    private static void addNullItemIntoListFromLength(List<String> list, int size) {
        if( list.size() != size ) {
            list.add("");
        }
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
