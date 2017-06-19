package com.qcwp.carmanager.model.sqLiteModel;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

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
    private int id;
    @Property(nameInDb = "CarTypeName")
    private String carTypeName;
    @Property(nameInDb = "avgFuel")
    private String avgFuel;
    @Generated(hash = 1301763668)
    public CarTypeModel(int year, int sid, int id, String carTypeName,
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
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
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
