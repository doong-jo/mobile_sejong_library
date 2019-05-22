package com.fourB.library.SearchBook;

public class SearchBookItem {
    private String BookTitle;       //제목
    private String BookAuthor;      //저자
    private String BookPublisher;   //출판사
    private Boolean BookLend;       //대출가능 여부
    private String BookPublishYear; //발행년도
    private String BookCallNum;     //청구기호
    private int BookImage;          //책이미지
    private String BookType;        //자료유형
    private String BookPublishDetail;   //발행사항
    private String BookShapeDetail;      //형태사항





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

    public String getBookType() {
        return BookType;
    }

    public void setBookType(String bookType) {
        BookType = bookType;
    }

    public String getBookPublishDetail() {
        return BookPublishDetail;
    }

    public void setBookPublishDetail(String bookPublishDetail) {
        BookPublishDetail = bookPublishDetail;
    }

    public String getBookShapeDetail() {
        return BookShapeDetail;
    }

    public void setBookShapeDetail(String bookShapeDetail) {
        BookShapeDetail = bookShapeDetail;
    }
}
