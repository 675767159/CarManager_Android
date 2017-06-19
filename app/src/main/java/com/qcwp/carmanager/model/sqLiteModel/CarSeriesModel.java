package com.qcwp.carmanager.model.sqLiteModel;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by qyh on 2017/6/19.
 */
@Entity(nameInDb = "t_car_series")
public class CarSeriesModel {
    @Property(nameInDb = "Id")
    private int id;
    @Property(nameInDb = "Cid")
    private int cid;
    @Property(nameInDb = "SeriesName")
    private String seriesName;
    @Generated(hash = 2119948504)
    public CarSeriesModel(int id, int cid, String seriesName) {
        this.id = id;
        this.cid = cid;
        this.seriesName = seriesName;
    }
    @Generated(hash = 499041401)
    public CarSeriesModel() {
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getCid() {
        return this.cid;
    }
    public void setCid(int cid) {
        this.cid = cid;
    }
    public String getSeriesName() {
        return this.seriesName;
    }
    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }
}
