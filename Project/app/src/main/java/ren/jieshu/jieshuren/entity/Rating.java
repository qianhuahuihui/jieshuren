package ren.jieshu.jieshuren.entity;

import java.io.Serializable;

/**
 * Author: shinianPan on 2017/10/31.
 * email : snow_psn@163.com
 */

public class Rating implements Serializable {
    private String max;
    private String numRaters;
    private String average;
    private String min;
    public String getMax() {
        return max;
    }
    public void setMax(String max) {
        this.max = max;
    }
    public String getNumRaters() {
        return numRaters;
    }
    public void setNumRaters(String numRaters) {
        this.numRaters = numRaters;
    }
    public String getAverage() {
        return average;
    }
    public void setAverage(String average) {
        this.average = average;
    }
    public String getMin() {
        return min;
    }
    public void setMin(String min) {
        this.min = min;
    }
}
