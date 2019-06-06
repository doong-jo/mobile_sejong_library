package com.fourB.library;

import com.fourB.library.Report.User;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ReportManager {
    static Map<String, String> mRoomDic = new HashMap<String, String>();


    private static void init() {
        if( !mRoomDic.isEmpty() ) { return; }
        mRoomDic.put("A", "A");
        mRoomDic.put("B", "B");
        mRoomDic.put("C", "C");
        mRoomDic.put("D", "D");
        mRoomDic.put("3층", "3");

        mRoomDic.put("A열람실", "A");
        mRoomDic.put("B열람실", "B");
        mRoomDic.put("C열람실", "C");
        mRoomDic.put("D열람실", "D");
        mRoomDic.put("3층열람실", "3");

        mRoomDic.put("A 열람실", "A");
        mRoomDic.put("B 열람실", "B");
        mRoomDic.put("C 열람실", "C");
        mRoomDic.put("D 열람실", "D");
        mRoomDic.put("3층 열람실", "3");

        mRoomDic.put("열람실 A", "A");
        mRoomDic.put("열람실 B", "B");
        mRoomDic.put("열람실 C", "C");
        mRoomDic.put("열람실 D", "D");
        mRoomDic.put("3층 열람실", "3");
    }

    public static void report(String seatRoom, String seatNumber, String type, String content, String date) {
        init();
        seatRoom = mRoomDic.get(seatRoom);

        final String[] findIdQuery = {"seat_room=" + seatRoom, "seat=" + seatNumber };
        try {
            String idFindRes = HttpManager.internalServerAPI("user", findIdQuery, "", "GET");

            Gson gson = new Gson();
            idFindRes = idFindRes.substring(1, idFindRes.length() -1);
            User user = gson.fromJson(idFindRes, User.class);
            if ( user == null ) { return; }
            final String token = user.getToken();

            final String[] fcmQuery = {"token=" + token, "type=" + type, "content=" + content, "date=" + date };

            HttpManager.internalServerAPI("fcm", fcmQuery, "", "GET");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
