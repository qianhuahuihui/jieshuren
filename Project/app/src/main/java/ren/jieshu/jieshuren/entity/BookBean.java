package ren.jieshu.jieshuren.entity;

import java.io.Serializable;

/**
 * Created by laomaotao on 2017/7/17.
 */

public class BookBean implements Serializable {
    private Integer bookid;
    private String bookName;
    private String bookImage;
    private String bookAuthor;
    private String bookPress;
    private String bookPuttime;
    private String bookBind;
    private String isbn;
    private String average;
    private String numraters;
    private String bookIntroduction;
    private Integer bookPageCount;
    private Double bookPrice;
    private Double bookPricing;
    private Integer publishNum;
    private Integer printingNum;
    private Integer freeDb;
    private Integer readCount;
    private Integer use;
    private Integer hasRead;
    private Integer wantTORead;
    private BookBean book;

    private String error;
    private Integer status;

    public String getBookIntroduction() {
        return bookIntroduction;
    }

    public void setBookIntroduction(String bookIntroduction) {
        this.bookIntroduction = bookIntroduction;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BookBean getBook() {
        return book;
    }

    public void setBook(BookBean book) {
        this.book = book;
    }

    public Integer getBookid() {
        return bookid;
    }

    public void setBookid(Integer bookid) {
        this.bookid = bookid;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookPress() {
        return bookPress;
    }

    public void setBookPress(String bookPress) {
        this.bookPress = bookPress;
    }

    public String getBookPuttime() {
        return bookPuttime;
    }

    public void setBookPuttime(String bookPuttime) {
        this.bookPuttime = bookPuttime;
    }

    public String getBookBind() {
        return bookBind;
    }

    public void setBookBind(String bookBind) {
        this.bookBind = bookBind;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public String getNumraters() {
        return numraters;
    }

    public void setNumraters(String numraters) {
        this.numraters = numraters;
    }

    public Integer getBookPageCount() {
        return bookPageCount;
    }

    public void setBookPageCount(Integer bookPageCount) {
        this.bookPageCount = bookPageCount;
    }

    public Double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(Double bookPrice) {
        this.bookPrice = bookPrice;
    }

    public Double getBookPricing() {
        return bookPricing;
    }

    public void setBookPricing(Double bookPricing) {
        this.bookPricing = bookPricing;
    }

    public Integer getPublishNum() {
        return publishNum;
    }

    public void setPublishNum(Integer publishNum) {
        this.publishNum = publishNum;
    }

    public Integer getPrintingNum() {
        return printingNum;
    }

    public void setPrintingNum(Integer printingNum) {
        this.printingNum = printingNum;
    }

    public Integer getFreeDb() {
        return freeDb;
    }

    public void setFreeDb(Integer freeDb) {
        this.freeDb = freeDb;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Integer getUse() {
        return use;
    }

    public void setUse(Integer use) {
        this.use = use;
    }

    public Integer getHasRead() {
        return hasRead;
    }

    public void setHasRead(Integer hasRead) {
        this.hasRead = hasRead;
    }

    public Integer getWantTORead() {
        return wantTORead;
    }

    public void setWantTORead(Integer wantTORead) {
        this.wantTORead = wantTORead;
    }
}
