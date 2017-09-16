package com.qcwp.carmanager.broadcast;

import com.baidu.mapapi.model.LatLng;

import java.util.List;
import java.util.Map;

/**
 * Created by qyh on 2017/6/23.
 */

public class LocationEvent {


    private LatLng centerLatLng;
    private List<LatLng> mapPoints;

    public LatLng getCenterLatLng() {
        return centerLatLng;
    }

    public void setCenterLatLng(LatLng centerLatLng) {
        this.centerLatLng = centerLatLng;
    }

    public List<LatLng> getMapPoints() {
        return mapPoints;
    }

    public void setMapPoints(List<LatLng> mapPoints) {
        this.mapPoints = mapPoints;
    }
}
