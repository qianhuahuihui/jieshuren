package ren.jieshu.jieshuren.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Author: shinianPan on 2017/10/24.
 * email : snow_psn@163.com
 */

public class BookFriends implements Serializable {
    private BookFriends data;
    private String bookImg;
    private String bookName;
    private String bookAverage;
    private String bookAuthor;
    private int bookUse;
    private int readCount;
    private BigDecimal bookPrice;

    private String error;
    private Integer status;

    //图书借阅列表
    private List<BookList> bookList;

    public BookFriends getData() {
        return data;
    }

    public void setData(BookFriends data) {
        this.data = data;
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

    public String getBookImg() {
        return bookImg;
    }

    public void setBookImg(String bookImg) {
        this.bookImg = bookImg;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAverage() {
        return bookAverage;
    }

    public void setBookAverage(String bookAverage) {
        this.bookAverage = bookAverage;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public int getBookUse() {
        return bookUse;
    }

    public void setBookUse(int bookUse) {
        this.bookUse = bookUse;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public BigDecimal getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(BigDecimal bookPrice) {
        this.bookPrice = bookPrice;
    }

    public List<BookList> getBookList() {
        return bookList;
    }

    public void setBookList(List<BookList> bookList) {
        this.bookList = bookList;
    }
}
