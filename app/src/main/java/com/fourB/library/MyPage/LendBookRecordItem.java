package com.fourB.library.MyPage;

public class LendBookRecordItem {
    private String mTitle;
    private String mStartDate;
    private String mEndDate;
    private String mPosition;

    public LendBookRecordItem(String mTitle, String mStartDate, String mEndDate, String mPosition) {
        this.mTitle = mTitle;
        this.mStartDate = mStartDate;
        this.mEndDate = mEndDate;
        this.mPosition = mPosition;
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

    public String getmPosition() {
        return mPosition;
    }

    public void setmPosition(String mPosition) {
        this.mPosition = mPosition;
    }
}
