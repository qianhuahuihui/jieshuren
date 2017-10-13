package ren.jieshu.jieshuren.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by laomaotao on 2017/8/5.
 */

public class ReturnbookBean implements Serializable{
    private List<ReturnbookBean> list;
    private Integer id;
    private Double price;
    private String error;
    private String return_book_time;
    private String available_blance;
    private String totalPrice;
    private String orderSn;
    private Integer status;
    private Integer return_order_id;
    private Integer total;
    private Integer count;
    private List<String> image;
    private List<BooksBean> books;

    public Integer getReturn_order_id() {
        return return_order_id;
    }

    public void setReturn_order_id(Integer return_order_id) {
        this.return_order_id = return_order_id;
    }

    public List<BooksBean> getBooks() {
        return books;
    }

    public void setBooks(List<BooksBean> books) {
        this.books = books;
    }

    public String getAvailable_blance() {
        return available_blance;
    }

    public void setAvailable_blance(String available_blance) {
        this.available_blance = available_blance;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public List<ReturnbookBean> getList() {
        return list;
    }

    public void setList(List<ReturnbookBean> list) {
        this.list = list;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getReturn_book_time() {
        return return_book_time;
    }

    public void setReturn_book_time(String return_book_time) {
        this.return_book_time = return_book_time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }
}
