package com.qcwp.carmanager.model.sqLiteModel;

import com.qcwp.carmanager.enumeration.UploadStatusEnum;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.converter.PropertyConverter;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by qyh on 2017/6/24.
 */
@Entity(nameInDb = "upload_travel")
public class TravelDataModel {

    @Id(autoincrement = true)@Property(nameInDb = "uid")
    private Long  uid;
    @Property(nameInDb = "userName")private String  userName;
    @Property(nameInDb = "vinCode")private String vinCode;
    @Convert(converter =CarInfoModel.UploadStatusConverter.class, columnType = Integer.class)
    @Property(nameInDb = "uploadFlag")private UploadStatusEnum uploadFlag;
    @Property(nameInDb = "travelData")private String travelData;
    @Property(nameInDb = "onlyFlag")long  onlyFlag;
    @Generated(hash = 1949874773)
    public TravelDataModel(Long uid, String userName, String vinCode,
            UploadStatusEnum uploadFlag, String travelData, long onlyFlag) {
        this.uid = uid;
        this.userName = userName;
        this.vinCode = vinCode;
        this.uploadFlag = uploadFlag;
        this.travelData = travelData;
        this.onlyFlag = onlyFlag;
    }
    @Generated(hash = 918843800)
    public TravelDataModel() {
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
    public UploadStatusEnum getUploadFlag() {
        return this.uploadFlag;
    }
    public void setUploadFlag(UploadStatusEnum uploadFlag) {
        this.uploadFlag = uploadFlag;
    }
    public String getTravelData() {
        return this.travelData;
    }
    public void setTravelData(String travelData) {
        this.travelData = travelData;
    }
    public long getOnlyFlag() {
        return this.onlyFlag;
    }
    public void setOnlyFlag(long onlyFlag) {
        this.onlyFlag = onlyFlag;
    }







}
