package ren.jieshu.jieshuren.entity;


import java.io.Serializable;
import java.util.List;

/**
 * Author: shinianPan on 2017/10/19.
 * email : snow_psn@163.com
 */

public class ExpressResult implements Serializable {
    private ExpressResult expressResult;
    private String name;
    private String e_number;
    private Integer status;
    private Integer state;
    private String error;
    private List<Data> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getE_number() {
        return e_number;
    }

    public void setE_number(String e_number) {
        this.e_number = e_number;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}


