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
    private int cbId;
    @Property(nameInDb = "SeriesName")
    private String seriesName;

    public CommonBrandModel getCommonBrandModel() {
        return commonBrand;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCbId() {
        return this.cbId;
    }

    public void setCbId(int cbId) {
        this.cbId = cbId;
    }

    public String getSeriesName() {
        return this.seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    @Transient
    private CommonBrandModel commonBrand;

    @Generated(hash = 1259441614)
    public CarSeriesModel(Long id, int cbId, String seriesName) {
        this.id = id;
        this.cbId = cbId;
        this.seriesName = seriesName;
    }

    @Generated(hash = 499041401)
    public CarSeriesModel() {
    }


}
