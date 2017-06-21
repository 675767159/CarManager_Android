package com.qcwp.carmanager.model.sqLiteModel;



import com.qcwp.carmanager.enumeration.TableEnum;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "CarInfo")
public class CarInfoModel {

  @NotNull//属性不能为空
  @Property(nameInDb = "vinCode")
  @Unique
  private String vinCode;

  @Property(nameInDb = "carNumber")
  private String carNumber;

  @Property(nameInDb = "spValue")
  private String spValue;

  @Property(nameInDb = "totalMileage")
  private double totalMileage;

    @Property(nameInDb = "isTestSteer")
   private int isTestSteer;
    @Property(nameInDb = "ownerName")
  private String ownerName;
    @Property(nameInDb = "buyDate")
  private String buyDate;
    @Property(nameInDb = "productiveYear")
  private String productiveYear;
    @Property(nameInDb = "maintenanceInterval")
  private String maintenanceInterval;
    @Property(nameInDb = "carColor")
  private String carColor;
    @Property(nameInDb = "fuelOilType")
  private String fuelOilType;
    @Property(nameInDb = "mid")
  private long mid;

  @Property(nameInDb = "bid")
  private long brandId;
    @Property(nameInDb = "carTypeId")
  private long carTypeId;
    @Property(nameInDb = "carSeriesId")
  private long carSeriesId;
    @Property(nameInDb = "carType")
  private String carType;
    @Property(nameInDb = "carSeries")
  private String carSeries;
    @Property(nameInDb = "brand")
  private String brand;
    @Property(nameInDb = "manufacturer")
  private String manufacturer;

    @Property(nameInDb = "officialConsume")
  private String officialConsume;
    @Property(nameInDb = "actualConsume")
  private String actualConsume;
    @Property(nameInDb = "memberId")
  private long memberId;

  @Transient
  private String isoBuyDate;

    @Property(nameInDb = "needUpload")
  private int needUpload;

    @Generated(hash = 1975190541)
    public CarInfoModel(@NotNull String vinCode, String carNumber, String spValue,
            double totalMileage, int isTestSteer, String ownerName, String buyDate,
            String productiveYear, String maintenanceInterval, String carColor,
            String fuelOilType, long mid, long brandId, long carTypeId,
            long carSeriesId, String carType, String carSeries, String brand,
            String manufacturer, String officialConsume, String actualConsume,
            long memberId, int needUpload) {
        this.vinCode = vinCode;
        this.carNumber = carNumber;
        this.spValue = spValue;
        this.totalMileage = totalMileage;
        this.isTestSteer = isTestSteer;
        this.ownerName = ownerName;
        this.buyDate = buyDate;
        this.productiveYear = productiveYear;
        this.maintenanceInterval = maintenanceInterval;
        this.carColor = carColor;
        this.fuelOilType = fuelOilType;
        this.mid = mid;
        this.brandId = brandId;
        this.carTypeId = carTypeId;
        this.carSeriesId = carSeriesId;
        this.carType = carType;
        this.carSeries = carSeries;
        this.brand = brand;
        this.manufacturer = manufacturer;
        this.officialConsume = officialConsume;
        this.actualConsume = actualConsume;
        this.memberId = memberId;
        this.needUpload = needUpload;
    }

    @Generated(hash = 1135225804)
    public CarInfoModel() {
    }

    public String getVinCode() {
        return this.vinCode;
    }

    public void setVinCode(String vinCode) {
        this.vinCode = vinCode;
    }

    public String getCarNumber() {
        return this.carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getSpValue() {
        return this.spValue;
    }

    public void setSpValue(String spValue) {
        this.spValue = spValue;
    }

    public double getTotalMileage() {
        return this.totalMileage;
    }

    public void setTotalMileage(double totalMileage) {
        this.totalMileage = totalMileage;
    }

    public int getIsTestSteer() {
        return this.isTestSteer;
    }

    public void setIsTestSteer(int isTestSteer) {
        this.isTestSteer = isTestSteer;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getBuyDate() {
        return this.buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public String getProductiveYear() {
        return this.productiveYear;
    }

    public void setProductiveYear(String productiveYear) {
        this.productiveYear = productiveYear;
    }

    public String getMaintenanceInterval() {
        return this.maintenanceInterval;
    }

    public void setMaintenanceInterval(String maintenanceInterval) {
        this.maintenanceInterval = maintenanceInterval;
    }

    public String getCarColor() {
        return this.carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getFuelOilType() {
        return this.fuelOilType;
    }

    public void setFuelOilType(String fuelOilType) {
        this.fuelOilType = fuelOilType;
    }

    public long getMid() {
        return this.mid;
    }

    public void setMid(long mid) {
        this.mid = mid;
    }

    public long getBrandId() {
        return this.brandId;
    }

    public void setBrandId(long brandId) {
        this.brandId = brandId;
    }

    public long getCarTypeId() {
        return this.carTypeId;
    }

    public void setCarTypeId(long carTypeId) {
        this.carTypeId = carTypeId;
    }

    public long getCarSeriesId() {
        return this.carSeriesId;
    }

    public void setCarSeriesId(long carSeriesId) {
        this.carSeriesId = carSeriesId;
    }

    public String getCarType() {
        return this.carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarSeries() {
        return this.carSeries;
    }

    public void setCarSeries(String carSeries) {
        this.carSeries = carSeries;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getOfficialConsume() {
        return this.officialConsume;
    }

    public void setOfficialConsume(String officialConsume) {
        this.officialConsume = officialConsume;
    }

    public String getActualConsume() {
        return this.actualConsume;
    }

    public void setActualConsume(String actualConsume) {
        this.actualConsume = actualConsume;
    }

    public long getMemberId() {
        return this.memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public int getNeedUpload() {
        return this.needUpload;
    }

    public void setNeedUpload(int needUpload) {
        this.needUpload = needUpload;
    }


    public String getIsoBuyDate() {
        return isoBuyDate;
    }

    public void setIsoBuyDate(String isoBuyDate) {
        this.isoBuyDate = isoBuyDate;
    }

    @Override
    public String toString() {
        return "CarInfoModel{" +
                "vinCode='" + vinCode + '\'' +
                ", carNumber='" + carNumber + '\'' +
                ", spValue='" + spValue + '\'' +
                ", totalMileage=" + totalMileage +
                ", isTestSteer=" + isTestSteer +
                ", ownerName='" + ownerName + '\'' +
                ", buyDate='" + buyDate + '\'' +
                ", productiveYear='" + productiveYear + '\'' +
                ", maintenanceInterval='" + maintenanceInterval + '\'' +
                ", carColor='" + carColor + '\'' +
                ", fuelOilType='" + fuelOilType + '\'' +
                ", mid=" + mid +
                ", brandId=" + brandId +
                ", carTypeId=" + carTypeId +
                ", carSeriesId=" + carSeriesId +
                ", carType='" + carType + '\'' +
                ", carSeries='" + carSeries + '\'' +
                ", brand='" + brand + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", officialConsume='" + officialConsume + '\'' +
                ", actualConsume='" + actualConsume + '\'' +
                ", memberId=" + memberId +
                ", isoBuyDate='" + isoBuyDate + '\'' +
                ", needUpload=" + needUpload +
                '}';
    }
}
