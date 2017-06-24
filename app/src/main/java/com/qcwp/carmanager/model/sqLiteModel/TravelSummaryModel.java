package com.qcwp.carmanager.model.sqLiteModel;

import com.qcwp.carmanager.enumeration.UploadStatusEnum;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * Created by qyh on 2017/6/23.
 */
@Entity(nameInDb = "upload_summry")
public class TravelSummaryModel {

    @Property(nameInDb = "uid")
    @Id(autoincrement = true)
    private Long uid;


    @Property(nameInDb = "userName")
    private String userName;

    @Property(nameInDb = "vinCode")
    private String vinCode;
    @Property(nameInDb = "summryData")
    private String summryData;

    @Convert(converter =CarInfoModel.UploadStatusConverter.class, columnType = Integer.class)
    @Property(nameInDb = "uploadFlag")
    private UploadStatusEnum uploadFlag;

    @Property(nameInDb = "StartDate")
    private String StartDate;
    @Property(nameInDb = "onlyFlag")
    private long onlyFlag;
    @Property(nameInDb = "accelerCount")
    private int accelerCount;
    @Property(nameInDb = "decelerCount")
    private int decelerCount;
    @Property(nameInDb = "mileage")
    private double mileage;
    @Property(nameInDb = "driveTime")
    private int driveTime;
    @Property(nameInDb = "averageSpeed")
    private double averageSpeed;
    @Property(nameInDb = "averageOilConsume")
    private double averageOilConsume;
    @Property(nameInDb = "carNumber")
    private String carNumber;
    @Property(nameInDb = "EndDate")
    private String EndDate;
    @Property(nameInDb = "carCheckUpScore")
    private int carCheckUpScore;

    @Generated(hash = 1708116571)
    public TravelSummaryModel(Long uid, String userName, String vinCode, String summryData,
            UploadStatusEnum uploadFlag, String StartDate, long onlyFlag, int accelerCount, int decelerCount,
            double mileage, int driveTime, double averageSpeed, double averageOilConsume, String carNumber,
            String EndDate, int carCheckUpScore) {
        this.uid = uid;
        this.userName = userName;
        this.vinCode = vinCode;
        this.summryData = summryData;
        this.uploadFlag = uploadFlag;
        this.StartDate = StartDate;
        this.onlyFlag = onlyFlag;
        this.accelerCount = accelerCount;
        this.decelerCount = decelerCount;
        this.mileage = mileage;
        this.driveTime = driveTime;
        this.averageSpeed = averageSpeed;
        this.averageOilConsume = averageOilConsume;
        this.carNumber = carNumber;
        this.EndDate = EndDate;
        this.carCheckUpScore = carCheckUpScore;
    }

    @Generated(hash = 1886512717)
    public TravelSummaryModel() {
    }

    public Long getUid() {
        return this.uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
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

    public String getSummryData() {
        return this.summryData;
    }

    public void setSummryData(String summryData) {
        this.summryData = summryData;
    }

    public UploadStatusEnum getUploadFlag() {
        return this.uploadFlag;
    }

    public void setUploadFlag(UploadStatusEnum uploadFlag) {
        this.uploadFlag = uploadFlag;
    }

    public String getStartDate() {
        return this.StartDate;
    }

    public void setStartDate(String StartDate) {
        this.StartDate = StartDate;
    }

    public long getOnlyFlag() {
        return this.onlyFlag;
    }

    public void setOnlyFlag(long onlyFlag) {
        this.onlyFlag = onlyFlag;
    }

    public int getAccelerCount() {
        return this.accelerCount;
    }

    public void setAccelerCount(int accelerCount) {
        this.accelerCount = accelerCount;
    }

    public int getDecelerCount() {
        return this.decelerCount;
    }

    public void setDecelerCount(int decelerCount) {
        this.decelerCount = decelerCount;
    }

    public double getMileage() {
        return this.mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public int getDriveTime() {
        return this.driveTime;
    }

    public void setDriveTime(int driveTime) {
        this.driveTime = driveTime;
    }

    public double getAverageSpeed() {
        return this.averageSpeed;
    }

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public double getAverageOilConsume() {
        return this.averageOilConsume;
    }

    public void setAverageOilConsume(double averageOilConsume) {
        this.averageOilConsume = averageOilConsume;
    }

    public String getCarNumber() {
        return this.carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getEndDate() {
        return this.EndDate;
    }

    public void setEndDate(String EndDate) {
        this.EndDate = EndDate;
    }

    public int getCarCheckUpScore() {
        return this.carCheckUpScore;
    }

    public void setCarCheckUpScore(int carCheckUpScore) {
        this.carCheckUpScore = carCheckUpScore;
    }




}
