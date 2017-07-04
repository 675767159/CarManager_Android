package com.qcwp.carmanager.model.sqLiteModel;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.qcwp.carmanager.greendao.gen.DaoSession;
import com.qcwp.carmanager.greendao.gen.CarBrandModelDao;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Transient;

import com.qcwp.carmanager.greendao.gen.CommonBrandModelDao;

/**
 * Created by qyh on 2017/6/19.
 */
@Entity(nameInDb = "t_common_brand")
public class CommonBrandModel {

    @Property(nameInDb = "Id")
   @Id(autoincrement = true) private Long id;

    @Property(nameInDb = "Bid")
    private long bid;

    @Property(nameInDb = "Mid")
    private long mid;

    @Property(nameInDb = "CommonBrandName")
    private String commonBrandName;


    public CarBrandModel getCarBrand() {
        return brand;
    }

    @ToOne(joinProperty = "bid")
    @Transient
    private CarBrandModel brand;

    @Generated(hash = 474320732)
    public CommonBrandModel(Long id, long bid, long mid, String commonBrandName) {
        this.id = id;
        this.bid = bid;
        this.mid = mid;
        this.commonBrandName = commonBrandName;
    }



    @Generated(hash = 1086720834)
    public CommonBrandModel() {
    }



    public Long getId() {
        return this.id;
    }



    public void setId(Long id) {
        this.id = id;
    }



    public long getBid() {
        return this.bid;
    }



    public void setBid(long bid) {
        this.bid = bid;
    }



    public long getMid() {
        return this.mid;
    }



    public void setMid(long mid) {
        this.mid = mid;
    }



    public String getCommonBrandName() {
        return this.commonBrandName;
    }



    public void setCommonBrandName(String commonBrandName) {
        this.commonBrandName = commonBrandName;
    }



}
