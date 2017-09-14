package com.qcwp.carmanager.model.sqLiteModel;

import com.qcwp.carmanager.enumeration.UploadStatusEnum;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by qyh on 2017/9/13.
 *
 * @email:675767159@qq.com
 */
@Entity(nameInDb = "map_Location")
public class LocationModel {

    @Property(nameInDb = "userName")
    private String  userName;
    @Property(nameInDb = "vinCode")
    private String vinCode;

    @Property(nameInDb = "latitude")
    private double  latitude;
    @Property(nameInDb = "longitude")
    private  double longitude;

    @Property(nameInDb = "createTime")
    private  String createTime;

    @Property(nameInDb = "currentTime")
    private  String currentTime;


    @Convert(converter = CarInfoModel.UploadStatusConverter.class, columnType = Integer.class)
    @Property(nameInDb = "uploadFlag")
    UploadStatusEnum uploadFlag;


    @Property(nameInDb = "id")
    @Id(autoincrement = true)
    private Long id;


    @Generated(hash = 573531486)
    public LocationModel(String userName, String vinCode, double latitude, double longitude,
            String createTime, String currentTime, UploadStatusEnum uploadFlag, Long id) {
        this.userName = userName;
        this.vinCode = vinCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createTime = createTime;
        this.currentTime = currentTime;
        this.uploadFlag = uploadFlag;
        this.id = id;
    }


    @Generated(hash = 536868411)
    public LocationModel() {
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


    public double getLatitude() {
        return this.latitude;
    }


    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


    public double getLongitude() {
        return this.longitude;
    }


    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    public String getCreateTime() {
        return this.createTime;
    }


    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


    public String getCurrentTime() {
        return this.currentTime;
    }


    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }


    public UploadStatusEnum getUploadFlag() {
        return this.uploadFlag;
    }


    public void setUploadFlag(UploadStatusEnum uploadFlag) {
        this.uploadFlag = uploadFlag;
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }
}
