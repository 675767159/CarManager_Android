package com.qcwp.carmanager.model.sqLiteModel;

import com.qcwp.carmanager.enumeration.DrivingCustomEnum;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.converter.PropertyConverter;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by qyh on 2017/6/24.
 */
@Entity(nameInDb = "DrivingCustom")
public class DrivingCustomModel {

    @Property(nameInDb = "startDate")private String  startDate;
    @Property(nameInDb = "vinCode")private String vinCode;
    @Property(nameInDb = "createDate")private String createDate;
    @Property(nameInDb = "pointx")private double pointx;
    @Property(nameInDb = "pointy")private double pointy;

    @Convert(converter = DrivingCustomTypeConverter.class, columnType = Integer.class)
    @Property(nameInDb = "type")DrivingCustomEnum  type;



    @Generated(hash = 565421307)
    public DrivingCustomModel(String startDate, String vinCode, String createDate, double pointx, double pointy,
            DrivingCustomEnum type) {
        this.startDate = startDate;
        this.vinCode = vinCode;
        this.createDate = createDate;
        this.pointx = pointx;
        this.pointy = pointy;
        this.type = type;
    }



    @Generated(hash = 1956051040)
    public DrivingCustomModel() {
    }



    public String getStartDate() {
        return this.startDate;
    }



    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }



    public String getVinCode() {
        return this.vinCode;
    }



    public void setVinCode(String vinCode) {
        this.vinCode = vinCode;
    }



    public String getCreateDate() {
        return this.createDate;
    }



    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }



    public double getPointx() {
        return this.pointx;
    }



    public void setPointx(double pointx) {
        this.pointx = pointx;
    }



    public double getPointy() {
        return this.pointy;
    }



    public void setPointy(double pointy) {
        this.pointy = pointy;
    }



    public DrivingCustomEnum getType() {
        return this.type;
    }



    public void setType(DrivingCustomEnum type) {
        this.type = type;
    }



    public   static class DrivingCustomTypeConverter implements PropertyConverter<DrivingCustomEnum, Integer> {
        @Override
        public DrivingCustomEnum convertToEntityProperty(Integer databaseValue) {
            if (databaseValue == null) {
                return null;
            }
            for (DrivingCustomEnum type : DrivingCustomEnum.values()) {
                if (type.getValue() == databaseValue) {
                    return type;
                }
            }
            return DrivingCustomEnum.Unknown;
        }

        @Override
        public Integer convertToDatabaseValue(DrivingCustomEnum entityProperty) {
            return entityProperty == null ? DrivingCustomEnum.Unknown.getValue() : entityProperty.getValue();
        }
    }





}
