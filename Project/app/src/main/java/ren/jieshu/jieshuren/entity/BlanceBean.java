package ren.jieshu.jieshuren.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by laomaotao on 2017/9/2.
 */

public class BlanceBean implements Serializable {
    private int j_id;
    private BigDecimal j_available_blance;
    private BigDecimal j_count_blance;
    private BigDecimal j_frozen_blance;
    private BigDecimal j_fixed_blance;
    private int j_member_id;
    private BigDecimal integral;
    private int ranking;
    private String j_name;
    private String j_headimgurl;

    public int getJ_id() {
        return j_id;
    }

    public void setJ_id(int j_id) {
        this.j_id = j_id;
    }

    public BigDecimal getJ_available_blance() {
        return j_available_blance;
    }

    public void setJ_available_blance(BigDecimal j_available_blance) {
        this.j_available_blance = j_available_blance;
    }

    public BigDecimal getJ_count_blance() {
        return j_count_blance;
    }

    public void setJ_count_blance(BigDecimal j_count_blance) {
        this.j_count_blance = j_count_blance;
    }

    public BigDecimal getJ_frozen_blance() {
        return j_frozen_blance;
    }

    public void setJ_frozen_blance(BigDecimal j_frozen_blance) {
        this.j_frozen_blance = j_frozen_blance;
    }

    public BigDecimal getJ_fixed_blance() {
        return j_fixed_blance;
    }

    public void setJ_fixed_blance(BigDecimal j_fixed_blance) {
        this.j_fixed_blance = j_fixed_blance;
    }

    public int getJ_member_id() {
        return j_member_id;
    }

    public void setJ_member_id(int j_member_id) {
        this.j_member_id = j_member_id;
    }

    public BigDecimal getIntegral() {
        return integral;
    }

    public void setIntegral(BigDecimal integral) {
        this.integral = integral;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getJ_name() {
        return j_name;
    }

    public void setJ_name(String j_name) {
        this.j_name = j_name;
    }

    public String getJ_headimgurl() {
        return j_headimgurl;
    }

    public void setJ_headimgurl(String j_headimgurl) {
        this.j_headimgurl = j_headimgurl;
    }
}
