package com.qcwp.carmanager.model.sqLiteModel;



import android.content.Context;

import com.blankj.utilcode.util.TimeUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.qcwp.carmanager.APP;
import com.qcwp.carmanager.enumeration.TableEnum;
import com.qcwp.carmanager.enumeration.UploadStatusEnum;
import com.qcwp.carmanager.greendao.gen.CarInfoModelDao;
import com.qcwp.carmanager.ui.BaseActivity;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.converter.PropertyConverter;

@Entity(nameInDb = "CarInfo",indexes = {
        @Index(value = "vinCode", unique = true)
})
public class CarInfoModel {

  @NotNull//属性不能为空
  @Unique
  @Property(nameInDb = "vinCode")
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
    @Convert(converter = CarTypeConverter.class, columnType = String.class)
    @Property(nameInDb = "carType")
  private CarTypeModel carType;

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

    @Property(nameInDb = "buyDate")
    private String buyDate;

    @Convert(converter = UploadStatusConverter.class, columnType = Integer.class)
    @Property(nameInDb = "needUpload")
    UploadStatusEnum  needUpload;

    @Property(nameInDb = "Id")
    @Id(autoincrement = true)
    private Long id;


    @Property(nameInDb = "timestamp")
    private long timestamp;

    public String getCommonBrandName() {
        return commonBrandName;
    }

    public void setCommonBrandName(String commonBrandName) {
        this.commonBrandName = commonBrandName;
    }

    @Transient
    private String commonBrandName;

    @Generated(hash = 1201470894)
    public CarInfoModel(@NotNull String vinCode, String carNumber, String spValue, double totalMileage,
            int isTestSteer, String ownerName, String productiveYear, String maintenanceInterval, String carColor,
            String fuelOilType, long mid, long brandId, long carTypeId, long carSeriesId, CarTypeModel carType,
            String carSeries, String brand, String manufacturer, String officialConsume, String actualConsume,
            long memberId, String buyDate, UploadStatusEnum needUpload, Long id, long timestamp) {
        this.vinCode = vinCode;
        this.carNumber = carNumber;
        this.spValue = spValue;
        this.totalMileage = totalMileage;
        this.isTestSteer = isTestSteer;
        this.ownerName = ownerName;
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
        this.buyDate = buyDate;
        this.needUpload = needUpload;
        this.id = id;
        this.timestamp = timestamp;
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

    public CarTypeModel getCarType() {
        return this.carType;
    }

    public void setCarType(CarTypeModel carType) {
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

    public String getIsoBuyDate() {
        return this.buyDate;
    }

    public void setIsoBuyDate(String isoBuyDate) {
        this.buyDate = isoBuyDate;
    }

    public UploadStatusEnum getNeedUpload() {
        return this.needUpload;
    }

    public void setNeedUpload(UploadStatusEnum needUpload) {
        this.needUpload = needUpload;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
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
                ", productiveYear='" + productiveYear + '\'' +
                ", maintenanceInterval='" + maintenanceInterval + '\'' +
                ", carColor='" + carColor + '\'' +
                ", fuelOilType='" + fuelOilType + '\'' +
                ", mid=" + mid +
                ", brandId=" + brandId +
                ", carTypeId=" + carTypeId +
                ", carSeriesId=" + carSeriesId +
                ", carType=" + carType +
                ", carSeries='" + carSeries + '\'' +
                ", brand='" + brand + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", officialConsume='" + officialConsume + '\'' +
                ", actualConsume='" + actualConsume + '\'' +
                ", memberId=" + memberId +
                ", isoBuyDate='" + buyDate + '\'' +
                ", needUpload=" + needUpload +
                ", id=" + id +
                ", timestamp=" + timestamp +
                '}';
    }

    public String getBuyDate() {
        return this.buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }


    public static class UploadStatusConverter implements PropertyConverter<UploadStatusEnum, Integer> {
        @Override
        public UploadStatusEnum convertToEntityProperty(Integer databaseValue) {
            if (databaseValue == null) {
                return null;
            }
            for (UploadStatusEnum role : UploadStatusEnum.values()) {
                if (role.getValue() == databaseValue) {
                    return role;
                }
            }
            return UploadStatusEnum.NotUpload;
        }

        @Override
        public Integer convertToDatabaseValue(UploadStatusEnum entityProperty) {
            return entityProperty == null ? UploadStatusEnum.NotUpload.getValue() : entityProperty.getValue();
        }
    }

    public static class CarTypeConverter implements PropertyConverter<CarTypeModel, String> {

        @Override
        public CarTypeModel convertToEntityProperty(String databaseValue) {
            CarTypeModel carTypeModel=new CarTypeModel();
            carTypeModel.setCarTypeName(databaseValue);
            return carTypeModel;
        }

        @Override
        public String convertToDatabaseValue(CarTypeModel entityProperty) {
            return entityProperty==null?null:entityProperty.getCarTypeName();
        }
    }


    public static CarInfoModel getLatestCarInfo(){

        CarInfoModel carInfoModel=APP.getInstance().getDaoInstant().queryBuilder(CarInfoModel.class).orderDesc(CarInfoModelDao.Properties.Timestamp).limit(1).unique();
        return carInfoModel;
    }

    public static CarInfoModel getCarInfoByVinCode(String vinCode){

        CarInfoModel carInfoModel=APP.getInstance().getDaoInstant().queryBuilder(CarInfoModel.class).where(CarInfoModelDao.Properties.VinCode.eq(vinCode)).build().unique();

        return carInfoModel;
    }



}
