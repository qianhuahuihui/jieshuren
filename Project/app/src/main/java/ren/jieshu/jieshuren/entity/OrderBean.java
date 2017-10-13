package ren.jieshu.jieshuren.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by laomaotao on 2017/7/20.
 */

public class OrderBean implements Serializable {
    private List<BooksBean> list;
    private Integer status;
    private Integer is_send;
    private Integer orderid;
    private BigDecimal count_price;
    private BigDecimal needPrice;
    private BigDecimal total_price;
    private BigDecimal service_charge;
    private BlanceBean blance;
    private String msg;
    private String error;
    private J_AddressBean address;
    private OrderBean result;
    private int j_id;
    private String j_private_isbn;
    private int j_book_id;
    private BigDecimal j_book_price;
    private int j_order_id;
    /*
     * 图书状态:
     * -10取消
     * -1没查询到库存
     * 0未处理状态 3借阅中 1提交还书 2已确认还书
     * 4已经转借他人 5确认采购(不允许取消) 6替换
     */
    private int j_book_status;
    private int store_type;
    private int volume_num;
    private int if_compensate;

    //总支付的磨损费用
    private BigDecimal total_compensate_price;
    //会员支付的费用
    private BigDecimal compensate_price;

    private Date j_create_time;



    //支付磨损费的比例
    private int ratio;

    public int getJ_id() {
        return j_id;
    }

    public void setJ_id(int j_id) {
        this.j_id = j_id;
    }

    public String getJ_private_isbn() {
        return j_private_isbn;
    }

    public void setJ_private_isbn(String j_private_isbn) {
        this.j_private_isbn = j_private_isbn;
    }

    public int getJ_book_id() {
        return j_book_id;
    }

    public void setJ_book_id(int j_book_id) {
        this.j_book_id = j_book_id;
    }

    public BigDecimal getJ_book_price() {
        return j_book_price;
    }

    public void setJ_book_price(BigDecimal j_book_price) {
        this.j_book_price = j_book_price;
    }

    public int getJ_order_id() {
        return j_order_id;
    }

    public void setJ_order_id(int j_order_id) {
        this.j_order_id = j_order_id;
    }

    public int getJ_book_status() {
        return j_book_status;
    }

    public void setJ_book_status(int j_book_status) {
        this.j_book_status = j_book_status;
    }

    public int getStore_type() {
        return store_type;
    }

    public void setStore_type(int store_type) {
        this.store_type = store_type;
    }

    public int getVolume_num() {
        return volume_num;
    }

    public void setVolume_num(int volume_num) {
        this.volume_num = volume_num;
    }

    public int getIf_compensate() {
        return if_compensate;
    }

    public void setIf_compensate(int if_compensate) {
        this.if_compensate = if_compensate;
    }

    public BigDecimal getTotal_compensate_price() {
        return total_compensate_price;
    }

    public void setTotal_compensate_price(BigDecimal total_compensate_price) {
        this.total_compensate_price = total_compensate_price;
    }

    public BigDecimal getCompensate_price() {
        return compensate_price;
    }

    public void setCompensate_price(BigDecimal compensate_price) {
        this.compensate_price = compensate_price;
    }

    public Date getJ_create_time() {
        return j_create_time;
    }

    public void setJ_create_time(Date j_create_time) {
        this.j_create_time = j_create_time;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public BigDecimal getCount_price() {
        return count_price;
    }

    public void setCount_price(BigDecimal count_price) {
        this.count_price = count_price;
    }

    public BigDecimal getNeedPrice() {
        return needPrice;
    }

    public void setNeedPrice(BigDecimal needPrice) {
        this.needPrice = needPrice;
    }

    public BigDecimal getTotal_price() {
        return total_price;
    }

    public void setTotal_price(BigDecimal total_price) {
        this.total_price = total_price;
    }

    public BigDecimal getService_charge() {
        return service_charge;
    }

    public void setService_charge(BigDecimal service_charge) {
        this.service_charge = service_charge;
    }

    public BlanceBean getBlance() {
        return blance;
    }

    public void setBlance(BlanceBean blance) {
        this.blance = blance;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<BooksBean> getList() {
        return list;
    }

    public void setList(List<BooksBean> list) {
        this.list = list;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIs_send() {
        return is_send;
    }

    public void setIs_send(Integer is_send) {
        this.is_send = is_send;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public J_AddressBean getAddress() {
        return address;
    }

    public void setAddress(J_AddressBean address) {
        this.address = address;
    }

    public OrderBean getResult() {
        return result;
    }

    public void setResult(OrderBean result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "OrderBean{" +
                "list=" + list +
                '}';
    }
}
