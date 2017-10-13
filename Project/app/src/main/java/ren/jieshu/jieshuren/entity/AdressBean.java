package ren.jieshu.jieshuren.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by laomaotao on 2017/8/10.
 */

public class AdressBean implements Serializable {
    private List<AdressBean> list;
    private Integer addr_id;
    private Integer status;
    private Integer j_defaultaddr;
    private String address;
    private String mobile;
    private String merger_addr;
    private String name;
    private String province;
    private String city;
    private String area;
    private String error;

    public Integer getAddr_id() {
        return addr_id;
    }

    public void setAddr_id(Integer addr_id) {
        this.addr_id = addr_id;
    }

    public Integer getJ_defaultaddr() {
        return j_defaultaddr;
    }

    public void setJ_defaultaddr(Integer j_defaultaddr) {
        this.j_defaultaddr = j_defaultaddr;
    }

    public List<AdressBean> getList() {
        return list;
    }

    public void setList(List<AdressBean> list) {
        this.list = list;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMerger_addr() {
        return merger_addr;
    }

    public void setMerger_addr(String merger_addr) {
        this.merger_addr = merger_addr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
