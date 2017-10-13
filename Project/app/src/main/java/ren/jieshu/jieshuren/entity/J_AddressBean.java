package ren.jieshu.jieshuren.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by laomaotao on 2017/9/2.
 */

public class J_AddressBean implements Serializable {
    private int j_id;
    private String j_gain_address;
    private String j_gain_mobile;
    private String j_merger_addr;
    private String j_gain_name;
    private String j_province;
    private String j_city;
    private String j_area;
    private String j_zipcode;
    private int j_member_id;
    private Date j_time;
    private int is_del;
    private int j_defaultaddr;
    private String province;
    private String city;
    private String area;

    public int getJ_id() {
        return j_id;
    }

    public void setJ_id(int j_id) {
        this.j_id = j_id;
    }

    public String getJ_gain_address() {
        return j_gain_address;
    }

    public void setJ_gain_address(String j_gain_address) {
        this.j_gain_address = j_gain_address;
    }

    public String getJ_gain_mobile() {
        return j_gain_mobile;
    }

    public void setJ_gain_mobile(String j_gain_mobile) {
        this.j_gain_mobile = j_gain_mobile;
    }

    public String getJ_merger_addr() {
        return j_merger_addr;
    }

    public void setJ_merger_addr(String j_merger_addr) {
        this.j_merger_addr = j_merger_addr;
    }

    public String getJ_gain_name() {
        return j_gain_name;
    }

    public void setJ_gain_name(String j_gain_name) {
        this.j_gain_name = j_gain_name;
    }

    public String getJ_province() {
        return j_province;
    }

    public void setJ_province(String j_province) {
        this.j_province = j_province;
    }

    public String getJ_city() {
        return j_city;
    }

    public void setJ_city(String j_city) {
        this.j_city = j_city;
    }

    public String getJ_area() {
        return j_area;
    }

    public void setJ_area(String j_area) {
        this.j_area = j_area;
    }

    public String getJ_zipcode() {
        return j_zipcode;
    }

    public void setJ_zipcode(String j_zipcode) {
        this.j_zipcode = j_zipcode;
    }

    public int getJ_member_id() {
        return j_member_id;
    }

    public void setJ_member_id(int j_member_id) {
        this.j_member_id = j_member_id;
    }

    public Date getJ_time() {
        return j_time;
    }

    public void setJ_time(Date j_time) {
        this.j_time = j_time;
    }

    public int getIs_del() {
        return is_del;
    }

    public void setIs_del(int is_del) {
        this.is_del = is_del;
    }

    public int getJ_defaultaddr() {
        return j_defaultaddr;
    }

    public void setJ_defaultaddr(int j_defaultaddr) {
        this.j_defaultaddr = j_defaultaddr;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
