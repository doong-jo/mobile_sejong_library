package com.fourB.library.SearchBook;

public class RealSearchBookItem {
    private String mId;
    private String mTitle;

    private String mCategory;
    private String mAuthor;
    private String mPublisher;
    private String mPubdate;
    private String mLocation;
    private String mLocationNum;
    private String mImgURL;

    private String mStatus;

    public RealSearchBookItem() {

    }

    public RealSearchBookItem(String mId, String mStatus, String mCategory, String mTitle, String mAuthor, String mPublisher, String mPubdate, String mLocation, String mLocationNum) {
        this.mId = mId;
        this.mStatus = mStatus;
        this.mTitle = mTitle;
        this.mCategory = mCategory;
        this.mAuthor = mAuthor;
        this.mPublisher = mPublisher;
        this.mPubdate = mPubdate;
        this.mLocation = mLocation;
        this.mLocationNum = mLocationNum;
    }

    public String getmStatus() {
        return mStatus;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public String getmCategory() {
        return mCategory;
    }

    public void setmCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public String getmImgURL() {
        return mImgURL;
    }

    public void setmImgURL(String mImgURL) {
        this.mImgURL = mImgURL;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getmPublisher() {
        return mPublisher;
    }

    public void setmPublisher(String mPublisher) {
        this.mPublisher = mPublisher;
    }

    public String getmPubdate() {
        return mPubdate;
    }

    public void setmPubdate(String mPubdate) {
        this.mPubdate = mPubdate;
    }

    public String getmLocation() {
        return mLocation;
    }

    public void setmLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public String getmLocationNum() {
        return mLocationNum;
    }

    public void setmLocationNum(String mLocationNum) {
        this.mLocationNum = mLocationNum;
    }
}
