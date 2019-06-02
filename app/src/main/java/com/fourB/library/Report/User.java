package com.fourB.library.Report;

public class User {
    private int id;
    private String pw;
    private boolean seat_reserved;
    private int seat;
    private String seat_room;
    private String token;

    public int getUserId() {
        return id;
    }

    public String getPw() {
        return pw;
    }

    public boolean isSeat_reserved() {
        return seat_reserved;
    }

    public int getSeat() {
        return seat;
    }

    public String getSeat_room() {
        return seat_room;
    }

    public String getToken() {
        return token;
    }
}
