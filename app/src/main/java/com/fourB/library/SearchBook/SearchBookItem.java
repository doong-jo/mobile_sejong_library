package com.fourB.library.SearchBook;

public class SearchBookItem {
    private String mTitle;
    private String mImageURL;
    private String mAuthor;
    private String mPublisher;
    private String mPubdate;
    private int mIsbn;

    public SearchBookItem() {

    }

    public SearchBookItem(String mTitle, String mImageURL, String mAuthor, String mPublisher, String mPubdate, int mIsbn) {
        this.mTitle = mTitle;
        this.mImageURL = mImageURL;
        this.mAuthor = mAuthor;
        this.mPublisher = mPublisher;
        this.mPubdate = mPubdate;
        this.mIsbn = mIsbn;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getImageURL() {
        return mImageURL;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getPublisher() {
        return mPublisher;
    }

    public String getPubdate() {
        return mPubdate;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public void setImageURL(String imageURL) {
        this.mImageURL = imageURL;
    }

    public void setAuthor(String author) {
        this.mAuthor = author;
    }

    public void setPublisher(String publisher) {
        this.mPublisher = publisher;
    }

    public void setPubdate(String pubdate) {
        this.mPubdate = pubdate;
    }

    public int getIsbn() {
        return mIsbn;
    }

    public void setIsbn(int mIsbn) {
        this.mIsbn = mIsbn;
    }


}
