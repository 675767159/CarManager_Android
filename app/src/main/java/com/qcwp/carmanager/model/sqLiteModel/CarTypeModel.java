package com.qcwp.carmanager.model.sqLiteModel;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by qyh on 2017/6/19.
 */
@Entity(nameInDb = "t_car_type")
public class CarTypeModel {
    @Property(nameInDb = "Year")
    private int year;
    @Property(nameInDb = "Sid")
    private int sid;
    @Property(nameInDb = "Id")
    @Id(autoincrement = true) private Long id;
    @Property(nameInDb = "CarTypeName")
    private String carTypeName;
    @Property(nameInDb = "avgFuel")
    private String avgFuel;

    @Transient
    private CarSeriesModel carSeries;

    public CarSeriesModel getCarSeriesModel() {
        return carSeries;
    }

    @Generated(hash = 121937898)
    public CarTypeModel(int year, int sid, Long id, String carTypeName,
            String avgFuel) {
        this.year = year;
        this.sid = sid;
        this.id = id;
        this.carTypeName = carTypeName;
        this.avgFuel = avgFuel;
    }

    @Generated(hash = 1253858367)
    public CarTypeModel() {
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSid() {
        return this.sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarTypeName() {
        return this.carTypeName;
    }

    public void setCarTypeName(String carTypeName) {
        this.carTypeName = carTypeName;
    }

    public String getAvgFuel() {
        return this.avgFuel;
    }

    public void setAvgFuel(String avgFuel) {
        this.avgFuel = avgFuel;
    }

}
