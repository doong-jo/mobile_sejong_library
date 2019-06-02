package com.fourB.library;

import android.util.Log;

import com.fourB.library.HttpManager;
import com.fourB.library.Report.User;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ReportManager {
    static Map<String, String> mRoomDic = new HashMap<String, String>();


    private static void init() {
        if( !mRoomDic.isEmpty() ) { return; }
        mRoomDic.put("열람실 A", "A");
        mRoomDic.put("열람실 B", "B");
        mRoomDic.put("열람실 C", "C");
        mRoomDic.put("열람실 D-A", "DA");
        mRoomDic.put("열람실 D-B", "DB");
        mRoomDic.put("3층 열람실 A", "3A");
        mRoomDic.put("3층 열람실 B", "3B");
    }

    public static void report(String seatRoom, String seatNumber, String type, String content, String date) {
        init();
        seatRoom = mRoomDic.get(seatRoom);

        final String[] findIdQuery = {"seat_room=" + seatRoom, "seat=" + seatNumber };
        try {
            String idFindRes = HttpManager.httpRun("user", findIdQuery, "", "GET");

            Gson gson = new Gson();
            idFindRes = idFindRes.substring(1, idFindRes.length() -1);
            User user = gson.fromJson(idFindRes, User.class);
            final String token = user.getToken();

            final String[] fcmQuery = {"token=" + token, "type=" + type, "content=" + content, "date=" + date };

            HttpManager.httpRun("fcm", fcmQuery, "", "GET");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
