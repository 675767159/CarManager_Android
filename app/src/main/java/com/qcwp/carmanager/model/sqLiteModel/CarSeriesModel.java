package com.qcwp.carmanager.model.sqLiteModel;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by qyh on 2017/6/19.
 */
@Entity(nameInDb = "t_car_series")
public class CarSeriesModel {
    @Property(nameInDb = "Id")
    @Id(autoincrement = true) private Long id;
    @Property(nameInDb = "Cid")
    private int cid;
    @Property(nameInDb = "SeriesName")
    private String seriesName;

    public CommonBrandModel getCommonBrandModel() {
        return commonBrand;
    }

    @Transient
    private CommonBrandModel commonBrand;

    @Generated(hash = 1415501026)
    public CarSeriesModel(Long id, int cid, String seriesName) {
        this.id = id;
        this.cid = cid;
        this.seriesName = seriesName;
    }
    @Generated(hash = 499041401)
    public CarSeriesModel() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
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
