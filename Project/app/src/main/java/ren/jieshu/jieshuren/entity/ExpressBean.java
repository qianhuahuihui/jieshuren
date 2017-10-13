package ren.jieshu.jieshuren.entity;

import java.io.Serializable;

/**
 * Created by laomaotao on 2017/8/27.
 */

public class ExpressBean implements Serializable {
    private ExpressBean express;
    private String name;
    private String pinyin;
    private String com_code;
    private Integer status;
    private Integer id;
    private String error;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ExpressBean getExpress() {
        return express;
    }

    public void setExpress(ExpressBean express) {
        this.express = express;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getCom_code() {
        return com_code;
    }

    public void setCom_code(String com_code) {
        this.com_code = com_code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
