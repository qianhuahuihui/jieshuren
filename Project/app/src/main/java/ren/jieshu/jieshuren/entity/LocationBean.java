package ren.jieshu.jieshuren.entity;

import java.io.Serializable;

/**
 * Created by laomaotao on 2017/7/20.
 */

public class LocationBean implements Serializable {
    private String Latitude;
    private String Longitude;

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }
}
