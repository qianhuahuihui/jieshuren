package ren.jieshu.jieshuren.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by laomaotao on 2017/8/28.
 */

public class WearPaymentBean implements Serializable {
    private List<WearPaymentBean> list;
    private List<BooksBean> books;
    private Integer number;
    private Double orderCompensatePrice = 0.0;
    private Integer return_order_id;
    private Integer orderId ;
    private Integer count;
    private Integer j_id;    //订单id
    private Integer j_member_id;
    private String j_order_number;
    private String return_book_time;
    private Integer j_order_status;
    private Integer j_book_order_type;
    private BigDecimal j_available_amount;
    private BigDecimal price;
    private BigDecimal compensate_pirce;
    private BigDecimal j_fixed_amount;
    private BigDecimal j_book_countprice;
    private String j_comment;
    private Integer j_address_id;
    private Date j_create_time;
    private Integer j_order_type;
    private Integer first_count;
    private BigDecimal first_price;
    private BigDecimal second_price;
    private Integer update_type;
    private Integer update_id;
    private Date update_time;
    private Integer express_id;
    private String express_number;
    private Integer dispatching_type;
    private Date send_time;
    private Date success_time;
    private Integer is_del;
    private String name;
    private String mobile;
    private String address;
    private String province_code;
    private String city_code;
    private String area_code;
    private Integer is_pay;

    private Integer pay_type;
    private BigDecimal practical_service_charge;
    private BigDecimal percentage_service_charge;
    private BigDecimal j_wx_amount;
    private BigDecimal order_count_price;
    private BigDecimal practical_order_count_price;
    private BigDecimal j_book_need_price;

    private String express_name;
    private String express_code;
    private String error;
    private Integer status;
    private String msg;


    public Double getOrderCompensatePrice() {
        return orderCompensatePrice;
    }

    public void setOrderCompensatePrice(Double orderCompensatePrice) {
        this.orderCompensatePrice = orderCompensatePrice;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public List<BooksBean> getBooks() {
        return books;
    }

    public void setBooks(List<BooksBean> books) {
        this.books = books;
    }

    public Integer getReturn_order_id() {
        return return_order_id;
    }

    public void setReturn_order_id(Integer return_order_id) {
        this.return_order_id = return_order_id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getReturn_book_time() {
        return return_book_time;
    }

    public void setReturn_book_time(String return_book_time) {
        this.return_book_time = return_book_time;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getCompensate_pirce() {
        return compensate_pirce;
    }

    public void setCompensate_pirce(BigDecimal compensate_pirce) {
        this.compensate_pirce = compensate_pirce;
    }

    public List<WearPaymentBean> getList() {
        return list;
    }

    public void setList(List<WearPaymentBean> list) {
        this.list = list;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getJ_id() {
        return j_id;
    }

    public void setJ_id(Integer j_id) {
        this.j_id = j_id;
    }

    public Integer getJ_member_id() {
        return j_member_id;
    }

    public void setJ_member_id(Integer j_member_id) {
        this.j_member_id = j_member_id;
    }

    public String getJ_order_number() {
        return j_order_number;
    }

    public void setJ_order_number(String j_order_number) {
        this.j_order_number = j_order_number;
    }

    public Integer getJ_order_status() {
        return j_order_status;
    }

    public void setJ_order_status(Integer j_order_status) {
        this.j_order_status = j_order_status;
    }

    public Integer getJ_book_order_type() {
        return j_book_order_type;
    }

    public void setJ_book_order_type(Integer j_book_order_type) {
        this.j_book_order_type = j_book_order_type;
    }

    public BigDecimal getJ_available_amount() {
        return j_available_amount;
    }

    public void setJ_available_amount(BigDecimal j_available_amount) {
        this.j_available_amount = j_available_amount;
    }

    public BigDecimal getJ_fixed_amount() {
        return j_fixed_amount;
    }

    public void setJ_fixed_amount(BigDecimal j_fixed_amount) {
        this.j_fixed_amount = j_fixed_amount;
    }

    public BigDecimal getJ_book_countprice() {
        return j_book_countprice;
    }

    public void setJ_book_countprice(BigDecimal j_book_countprice) {
        this.j_book_countprice = j_book_countprice;
    }

    public String getJ_comment() {
        return j_comment;
    }

    public void setJ_comment(String j_comment) {
        this.j_comment = j_comment;
    }

    public Integer getJ_address_id() {
        return j_address_id;
    }

    public void setJ_address_id(Integer j_address_id) {
        this.j_address_id = j_address_id;
    }

    public Date getJ_create_time() {
        return j_create_time;
    }

    public void setJ_create_time(Date j_create_time) {
        this.j_create_time = j_create_time;
    }

    public Integer getJ_order_type() {
        return j_order_type;
    }

    public void setJ_order_type(Integer j_order_type) {
        this.j_order_type = j_order_type;
    }

    public Integer getFirst_count() {
        return first_count;
    }

    public void setFirst_count(Integer first_count) {
        this.first_count = first_count;
    }

    public BigDecimal getFirst_price() {
        return first_price;
    }

    public void setFirst_price(BigDecimal first_price) {
        this.first_price = first_price;
    }

    public BigDecimal getSecond_price() {
        return second_price;
    }

    public void setSecond_price(BigDecimal second_price) {
        this.second_price = second_price;
    }

    public Integer getUpdate_type() {
        return update_type;
    }

    public void setUpdate_type(Integer update_type) {
        this.update_type = update_type;
    }

    public Integer getUpdate_id() {
        return update_id;
    }

    public void setUpdate_id(Integer update_id) {
        this.update_id = update_id;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public Integer getExpress_id() {
        return express_id;
    }

    public void setExpress_id(Integer express_id) {
        this.express_id = express_id;
    }

    public String getExpress_number() {
        return express_number;
    }

    public void setExpress_number(String express_number) {
        this.express_number = express_number;
    }

    public Integer getDispatching_type() {
        return dispatching_type;
    }

    public void setDispatching_type(Integer dispatching_type) {
        this.dispatching_type = dispatching_type;
    }

    public Date getSend_time() {
        return send_time;
    }

    public void setSend_time(Date send_time) {
        this.send_time = send_time;
    }

    public Date getSuccess_time() {
        return success_time;
    }

    public void setSuccess_time(Date success_time) {
        this.success_time = success_time;
    }

    public Integer getIs_del() {
        return is_del;
    }

    public void setIs_del(Integer is_del) {
        this.is_del = is_del;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince_code() {
        return province_code;
    }

    public void setProvince_code(String province_code) {
        this.province_code = province_code;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public Integer getIs_pay() {
        return is_pay;
    }

    public void setIs_pay(Integer is_pay) {
        this.is_pay = is_pay;
    }

    public Integer getPay_type() {
        return pay_type;
    }

    public void setPay_type(Integer pay_type) {
        this.pay_type = pay_type;
    }

    public BigDecimal getPractical_service_charge() {
        return practical_service_charge;
    }

    public void setPractical_service_charge(BigDecimal practical_service_charge) {
        this.practical_service_charge = practical_service_charge;
    }

    public BigDecimal getPercentage_service_charge() {
        return percentage_service_charge;
    }

    public void setPercentage_service_charge(BigDecimal percentage_service_charge) {
        this.percentage_service_charge = percentage_service_charge;
    }

    public BigDecimal getJ_wx_amount() {
        return j_wx_amount;
    }

    public void setJ_wx_amount(BigDecimal j_wx_amount) {
        this.j_wx_amount = j_wx_amount;
    }

    public BigDecimal getOrder_count_price() {
        return order_count_price;
    }

    public void setOrder_count_price(BigDecimal order_count_price) {
        this.order_count_price = order_count_price;
    }

    public BigDecimal getPractical_order_count_price() {
        return practical_order_count_price;
    }

    public void setPractical_order_count_price(BigDecimal practical_order_count_price) {
        this.practical_order_count_price = practical_order_count_price;
    }

    public BigDecimal getJ_book_need_price() {
        return j_book_need_price;
    }

    public void setJ_book_need_price(BigDecimal j_book_need_price) {
        this.j_book_need_price = j_book_need_price;
    }

    public String getExpress_name() {
        return express_name;
    }

    public void setExpress_name(String express_name) {
        this.express_name = express_name;
    }

    public String getExpress_code() {
        return express_code;
    }

    public void setExpress_code(String express_code) {
        this.express_code = express_code;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
