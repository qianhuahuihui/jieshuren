package ren.jieshu.jieshuren.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Author: shinianPan on 2017/10/21.
 * email : snow_psn@163.com
 */

public class IntegralRecord implements Serializable {
    private int id;
    private int member_id;
    private BigDecimal integral;
    private String j_explain;
    private int j_behavior;
    private Date create_time;
    private BigDecimal surplus_integral;
    private BigDecimal fronze_integral;
    private Integer status;
    private String error;
    private List<IntegralRecord> list;

    public BigDecimal getFronze_integral() {
        return fronze_integral;
    }

    public void setFronze_integral(BigDecimal fronze_integral) {
        this.fronze_integral = fronze_integral;
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

    public List<IntegralRecord> getList() {
        return list;
    }

    public void setList(List<IntegralRecord> list) {
        this.list = list;
    }

    public BigDecimal getSurplus_integral() {
        return surplus_integral;
    }
    public void setSurplus_integral(BigDecimal surplus_integral) {
        this.surplus_integral = surplus_integral;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getMember_id() {
        return member_id;
    }
    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }
    public BigDecimal getIntegral() {
        return integral;
    }
    public void setIntegral(BigDecimal integral) {
        this.integral = integral;
    }
    public String getJ_explain() {
        return j_explain;
    }
    public void setJ_explain(String j_explain) {
        this.j_explain = j_explain;
    }
    public int getJ_behavior() {
        return j_behavior;
    }
    public void setJ_behavior(int j_behavior) {
        this.j_behavior = j_behavior;
    }
    public Date getCreate_time() {
        return create_time;
    }
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
