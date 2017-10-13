package ren.jieshu.jieshuren.entity;

import java.io.Serializable;

/**
 * Created by laomaotao on 2017/6/30.
 */

public class ButtonsBean implements Serializable {
    private String title;
    private String icon;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
