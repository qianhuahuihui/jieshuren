package ren.jieshu.jieshuren.entity;

import java.io.Serializable;

/**
 * Created by laomaotao on 2017/9/9.
 */

public class WechatBean implements Serializable {
    private String appid;
    private String partnerid;
    private String packagewx;
    private String noncestr;
    private String timestamp;
    private String sign;
    private String prepayid;
    private String msg;
    private String error;
    private Integer status;
    private WechatBean success;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPackagewx() {
        return packagewx;
    }

    public void setPackagewx(String packagewx) {
        this.packagewx = packagewx;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public WechatBean getSuccess() {
        return success;
    }

    public void setSuccess(WechatBean success) {
        this.success = success;
    }
}
