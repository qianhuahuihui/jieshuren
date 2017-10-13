package ren.jieshu.jieshuren.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by laomaotao on 2017/7/17.
 */

public class BooksClassificationInquiryBean implements Serializable {
    private List<TypesBean> typesList;
    private Integer status;
    private Integer error;

    public List<TypesBean> getTypesList() {
        return typesList;
    }

    public void setTypesList(List<TypesBean> typesList) {
        this.typesList = typesList;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }
}
