package com.fourB.library.MyPage;

public class LendBookItem {
    private String mTitle;
    private String mStartDate;
    private String mEndDate;

    public LendBookItem(String mTitle, String mStartDate, String mEndDate) {
        this.mTitle = mTitle;
        this.mStartDate = mStartDate;
        this.mEndDate = mEndDate;
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

    public String getmEndDate() {
        return mEndDate;
    }

    public void setmEndDate(String mEndDate) {
        this.mEndDate = mEndDate;
    }
}
