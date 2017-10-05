package com.qcwp.carmanager.model.sqLiteModel;

import com.qcwp.carmanager.enumeration.UploadStatusEnum;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by qyh on 2017/6/18.
 */
@Entity(nameInDb = "CarCheck")
public class CarCheckModel {

    @Property(nameInDb = "id")
    @Id(autoincrement = true)
    private Long id;


    @Property(nameInDb = "score")
    private int score;

    @Property(nameInDb = "unusualCodeCount")
    private int unusualCodeCount;

    @Property(nameInDb = "faultCodeCount")
    private int faultCodeCount;


    @Property(nameInDb = "createDate")
    private String createDate;


    @Property(nameInDb = "vinCode")
    private String vinCode;

    @Property(nameInDb = "powertrainDTCS")
    private String powertrainDTCS;

    @Property(nameInDb = "chassisDTCS")
    private String chassisDTCS;

    @Property(nameInDb = "carBodyDTCS")
    private String carBodyDTCS;


    @Property(nameInDb = "networkDTCS")
    private String networkDTCS;

    @Property(nameInDb = "driveDatas")
    private String driveDatas;


    @Property(nameInDb = "engineConditions")
    private String engineConditions;

    @Property(nameInDb = "carSeries")
    private String carSeries;

    @Convert(converter = CarInfoModel.UploadStatusConverter.class, columnType = Integer.class)
    @Property(nameInDb = "uploadFlag")
    private UploadStatusEnum uploadFlag;

    @Generated(hash = 36144552)
    public CarCheckModel(Long id, int score, int unusualCodeCount, int faultCodeCount,
            String createDate, String vinCode, String powertrainDTCS, String chassisDTCS,
            String carBodyDTCS, String networkDTCS, String driveDatas,
            String engineConditions, String carSeries, UploadStatusEnum uploadFlag) {
        this.id = id;
        this.score = score;
        this.unusualCodeCount = unusualCodeCount;
        this.faultCodeCount = faultCodeCount;
        this.createDate = createDate;
        this.vinCode = vinCode;
        this.powertrainDTCS = powertrainDTCS;
        this.chassisDTCS = chassisDTCS;
        this.carBodyDTCS = carBodyDTCS;
        this.networkDTCS = networkDTCS;
        this.driveDatas = driveDatas;
        this.engineConditions = engineConditions;
        this.carSeries = carSeries;
        this.uploadFlag = uploadFlag;
    }

    @Generated(hash = 1099894684)
    public CarCheckModel() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getUnusualCodeCount() {
        return this.unusualCodeCount;
    }

    public void setUnusualCodeCount(int unusualCodeCount) {
        this.unusualCodeCount = unusualCodeCount;
    }

    public int getFaultCodeCount() {
        return this.faultCodeCount;
    }

    public void setFaultCodeCount(int faultCodeCount) {
        this.faultCodeCount = faultCodeCount;
    }

    public String getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getVinCode() {
        return this.vinCode;
    }

    public void setVinCode(String vinCode) {
        this.vinCode = vinCode;
    }

    public String getPowertrainDTCS() {
        return this.powertrainDTCS;
    }

    public void setPowertrainDTCS(String powertrainDTCS) {
        this.powertrainDTCS = powertrainDTCS;
    }

    public String getChassisDTCS() {
        return this.chassisDTCS;
    }

    public void setChassisDTCS(String chassisDTCS) {
        this.chassisDTCS = chassisDTCS;
    }

    public String getCarBodyDTCS() {
        return this.carBodyDTCS;
    }

    public void setCarBodyDTCS(String carBodyDTCS) {
        this.carBodyDTCS = carBodyDTCS;
    }

    public String getNetworkDTCS() {
        return this.networkDTCS;
    }

    public void setNetworkDTCS(String networkDTCS) {
        this.networkDTCS = networkDTCS;
    }

    public String getDriveDatas() {
        return this.driveDatas;
    }

    public void setDriveDatas(String driveDatas) {
        this.driveDatas = driveDatas;
    }

    public String getEngineConditions() {
        return this.engineConditions;
    }

    public void setEngineConditions(String engineConditions) {
        this.engineConditions = engineConditions;
    }

    public String getCarSeries() {
        return this.carSeries;
    }

    public void setCarSeries(String carSeries) {
        this.carSeries = carSeries;
    }

    public UploadStatusEnum getUploadFlag() {
        return this.uploadFlag;
    }

    public void setUploadFlag(UploadStatusEnum uploadFlag) {
        this.uploadFlag = uploadFlag;
    }





}
