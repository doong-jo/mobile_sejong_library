package com.fourB.library.SearchBook;

public class SearchBookItem {
    private String BookTitle;
    private String BookAuthor;
    private String BookPublisher;
    private Boolean BookLend;
    private String BookPublishYear;
    private String BookCallNum; //청구기호
    private int BookImage;

    public SearchBookItem(String bookTitle, String bookAuthor, String bookPublisher, Boolean bookLend, String bookPublishYear, String bookCallNum, int bookImage) {
        this.BookTitle = bookTitle;
        this.BookAuthor = bookAuthor;
        this.BookPublisher = bookPublisher;
        this.BookLend = bookLend;
        this.BookPublishYear = bookPublishYear;
        this.BookCallNum = bookCallNum;
        this.BookImage = bookImage;
    }

    public String getBookTitle() {
        return BookTitle;
    }

    public void setBookTitle(String bookTitle) {
        BookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return BookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        BookAuthor = bookAuthor;
    }

    public String getBookPublisher() {
        return BookPublisher;
    }

    public void setBookPublisher(String bookPublisher) {
        BookPublisher = bookPublisher;
    }

    public Boolean getBookLend() {
        return BookLend;
    }

    public void setBookLend(Boolean bookLend) {
        BookLend = bookLend;
    }

    public String getBookPublishYear() {
        return BookPublishYear;
    }

    public void setBookPublishYear(String bookPublishYear) {
        BookPublishYear = bookPublishYear;
    }

    public String getBookCallNum() {
        return BookCallNum;
    }

    public void setBookCallNum(String bookCallNum) {
        BookCallNum = bookCallNum;
    }

    public int getBookImage() {
        return BookImage;
    }

    public void setBookImage(int bookImage) {
        BookImage = bookImage;
    }
}
