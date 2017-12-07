package ren.jieshu.jieshuren.entity;

import java.io.Serializable;

/**
 * Author: shinianPan on 2017/10/26.
 * email : snow_psn@163.com
 */

public class ComfirmResult implements Serializable {
    private String msg;
    private String error;
    private int status;

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
}
