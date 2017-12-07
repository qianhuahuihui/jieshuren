package ren.jieshu.jieshuren.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Author: shinianPan on 2017/10/30.
 * email : snow_psn@163.com
 */

public class Douban implements Serializable {
    private Douban data;
    private int count;
    private int start;
    private int total;
    private List<DoubanBook> books;

    private String msg;
    private String error;
    private int status;

    public Douban getData() {
        return data;
    }

    public void setData(Douban data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DoubanBook> getBooks() {
        return books;
    }

    public void setBooks(List<DoubanBook> books) {
        this.books = books;
    }
}
