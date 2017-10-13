package ren.jieshu.jieshuren.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by laomaotao on 2017/9/22.
 */

public class WearPayBean implements Serializable {
    private Integer orderId;
    private BigDecimal orderCompensatePrice;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getOrderCompensatePrice() {
        return orderCompensatePrice;
    }

    public void setOrderCompensatePrice(BigDecimal orderCompensatePrice) {
        this.orderCompensatePrice = orderCompensatePrice;
    }
}
