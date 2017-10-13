package ren.jieshu.jieshuren.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by laomaotao on 2017/8/16.
 */

public class ConsumeBean implements Serializable {
    private Integer total;
    private Integer status;
    private Integer behavior;
    private BigDecimal j_available_amount;
    private String explain;
    private String consume_time;
    private String error;
    private BigDecimal service_charge;
    private BigDecimal surplus_blance;
    private List<ConsumeBean> list;
    private BlanceBean blance;

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

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getBehavior() {
        return behavior;
    }

    public void setBehavior(Integer behavior) {
        this.behavior = behavior;
    }

    public BigDecimal getJ_available_amount() {
        return j_available_amount;
    }

    public void setJ_available_amount(BigDecimal j_available_amount) {
        this.j_available_amount = j_available_amount;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getConsume_time() {
        return consume_time;
    }

    public void setConsume_time(String consume_time) {
        this.consume_time = consume_time;
    }

    public BigDecimal getService_charge() {
        return service_charge;
    }

    public void setService_charge(BigDecimal service_charge) {
        this.service_charge = service_charge;
    }

    public BigDecimal getSurplus_blance() {
        return surplus_blance;
    }

    public void setSurplus_blance(BigDecimal surplus_blance) {
        this.surplus_blance = surplus_blance;
    }

    public List<ConsumeBean> getList() {
        return list;
    }

    public void setList(List<ConsumeBean> list) {
        this.list = list;
    }
}
