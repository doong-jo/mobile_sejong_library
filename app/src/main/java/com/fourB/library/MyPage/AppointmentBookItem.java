package com.fourB.library.MyPage;

public class AppointmentBookItem {
    private String mTitle;
    private String mStartDate;
    private String mRank;

    public AppointmentBookItem(String mTitle, String mStartDate, String mRank) {
        this.mTitle = mTitle;
        this.mStartDate = mStartDate;
        this.mRank = mRank;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmStartDate() {
        return mStartDate;
    }

    public void setmStartDate(String mStartDate) {
        this.mStartDate = mStartDate;
    }

    public String getmRank() {
        return mRank;
    }

    public void setmRank(String mRank) {
        this.mRank = mRank;
    }
}
