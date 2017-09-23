package com.qcwp.carmanager.model.sqLiteModel;

import com.qcwp.carmanager.enumeration.ProfessionalTestEnum;
import com.qcwp.carmanager.enumeration.UploadStatusEnum;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.converter.PropertyConverter;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by qyh on 2017/6/18.
 */
@Entity(nameInDb = "test_Data")
public class TestSummaryModel {

    @Property(nameInDb = "uid")
    @Id(autoincrement = true)
    private Long id;


    @Property(nameInDb = "userName")
    private String userName;

    @Property(nameInDb = "vinCode")
    private String vinCode;

    @Convert(converter = ProfessionalTestTypeConverter.class, columnType = Integer.class)
    @Property(nameInDb = "TestType")
    private ProfessionalTestEnum TestType;


    @Property(nameInDb = "maxSpeed")
    private float maxSpeed;


    @Property(nameInDb = "testDist")
    private float testDist;

    @Property(nameInDb = "testTime")
    private float testTime;


    @Property(nameInDb = "createDate")
    private String createDate;

    @Property(nameInDb = "pointx")
    private double pointx;


    @Property(nameInDb = "pointy")
    private double pointy;

    @Convert(converter = CarInfoModel.UploadStatusConverter.class, columnType = Integer.class)
    @Property(nameInDb = "uploadFlag")
    private UploadStatusEnum uploadFlag;


    @Property(nameInDb = "carNumber")
    private String carNumber;



    @Generated(hash = 2035265061)
    public TestSummaryModel(Long id, String userName, String vinCode, ProfessionalTestEnum TestType, float maxSpeed,
            float testDist, float testTime, String createDate, double pointx, double pointy, UploadStatusEnum uploadFlag,
            String carNumber) {
        this.id = id;
        this.userName = userName;
        this.vinCode = vinCode;
        this.TestType = TestType;
        this.maxSpeed = maxSpeed;
        this.testDist = testDist;
        this.testTime = testTime;
        this.createDate = createDate;
        this.pointx = pointx;
        this.pointy = pointy;
        this.uploadFlag = uploadFlag;
        this.carNumber = carNumber;
    }



    @Generated(hash = 1923096130)
    public TestSummaryModel() {
    }



    public Long getId() {
        return this.id;
    }



    public void setId(Long id) {
        this.id = id;
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



    public ProfessionalTestEnum getTestType() {
        return this.TestType;
    }



    public void setTestType(ProfessionalTestEnum TestType) {
        this.TestType = TestType;
    }



    public float getMaxSpeed() {
        return this.maxSpeed;
    }



    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }



    public float getTestDist() {
        return this.testDist;
    }



    public void setTestDist(float testDist) {
        this.testDist = testDist;
    }



    public float getTestTime() {
        return this.testTime;
    }



    public void setTestTime(float testTime) {
        this.testTime = testTime;
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



    public   static class ProfessionalTestTypeConverter implements PropertyConverter<ProfessionalTestEnum, Integer> {
        @Override
        public ProfessionalTestEnum convertToEntityProperty(Integer databaseValue) {
            if (databaseValue == null) {
                return null;
            }
            for (ProfessionalTestEnum type : ProfessionalTestEnum.values()) {
                if (type.getValue() == databaseValue) {
                    return type;
                }
            }
            return ProfessionalTestEnum.Unknown;
        }

        @Override
        public Integer convertToDatabaseValue(ProfessionalTestEnum entityProperty) {
            return entityProperty == null ? ProfessionalTestEnum.Unknown.getValue() : entityProperty.getValue();
        }
    }


}
