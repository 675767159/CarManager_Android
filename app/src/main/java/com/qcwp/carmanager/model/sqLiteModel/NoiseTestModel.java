package com.qcwp.carmanager.model.sqLiteModel;

import com.qcwp.carmanager.enumeration.ProfessionalTestEnum;
import com.qcwp.carmanager.enumeration.UploadStatusEnum;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * Created by qyh on 2017/6/18.
 */
@Entity(nameInDb = "NoiseTest")
public class NoiseTestModel {


    @Property(nameInDb = "userName")
    private String userName;

    @Property(nameInDb = "vinCode")
    private String vinCode;



    @Property(nameInDb = "createDate")
    private String createDate;

    @Property(nameInDb = "pointx")
    private double pointx;


    @Property(nameInDb = "pointy")
    private double pointy;

    @Property(nameInDb = "minNoise")
    private double minNoise;

    @Property(nameInDb = "maxNoise")
    private double maxNoise;


    @Property(nameInDb = "avgNoise")
    private double avgNoise;

    @Property(nameInDb = "maxSpeed")
    private double maxSpeed;


    @Property(nameInDb = "avgSpeed")
    private double avgSpeed;

    @Convert(converter = CarInfoModel.UploadStatusConverter.class, columnType = Integer.class)
    @Property(nameInDb = "uploadFlag")
    private UploadStatusEnum uploadFlag;

    @Property(nameInDb = "carNumber")
    private String carNumber;

    @Generated(hash = 1192885718)
    public NoiseTestModel(String userName, String vinCode, String createDate, double pointx,
            double pointy, double minNoise, double maxNoise, double avgNoise, double maxSpeed,
            double avgSpeed, UploadStatusEnum uploadFlag, String carNumber) {
        this.userName = userName;
        this.vinCode = vinCode;
        this.createDate = createDate;
        this.pointx = pointx;
        this.pointy = pointy;
        this.minNoise = minNoise;
        this.maxNoise = maxNoise;
        this.avgNoise = avgNoise;
        this.maxSpeed = maxSpeed;
        this.avgSpeed = avgSpeed;
        this.uploadFlag = uploadFlag;
        this.carNumber = carNumber;
    }

    @Generated(hash = 1671649984)
    public NoiseTestModel() {
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public double getMinNoise() {
        return this.minNoise;
    }

    public void setMinNoise(double minNoise) {
        this.minNoise = minNoise;
    }

    public double getMaxNoise() {
        return this.maxNoise;
    }

    public void setMaxNoise(double maxNoise) {
        this.maxNoise = maxNoise;
    }

    public double getAvgNoise() {
        return this.avgNoise;
    }

    public void setAvgNoise(double avgNoise) {
        this.avgNoise = avgNoise;
    }

    public double getMaxSpeed() {
        return this.maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public double getAvgSpeed() {
        return this.avgSpeed;
    }

    public void setAvgSpeed(double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public UploadStatusEnum getUploadFlag() {
        return this.uploadFlag;
    }

    public void setUploadFlag(UploadStatusEnum uploadFlag) {
        this.uploadFlag = uploadFlag;
    }

    public String getCarNumber() {
        return this.carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }


}
