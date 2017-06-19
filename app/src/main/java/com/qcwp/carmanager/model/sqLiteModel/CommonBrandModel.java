package com.qcwp.carmanager.model.sqLiteModel;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.qcwp.carmanager.greendao.gen.DaoSession;
import com.qcwp.carmanager.greendao.gen.CarBrandModelDao;
import org.greenrobot.greendao.annotation.NotNull;
import com.qcwp.carmanager.greendao.gen.CommonBrandModelDao;

/**
 * Created by qyh on 2017/6/19.
 */
@Entity(nameInDb = "t_common_brand")
public class CommonBrandModel {

    @Property(nameInDb = "Id")
    private long id;

    @Property(nameInDb = "Bid")
    private long bid;

    @Property(nameInDb = "Mid")
    private long mid;

    @Property(nameInDb = "CommonBrandName")
    private String commonBrandName;



    @ToOne(joinProperty = "bid")
    private CarBrandModel carBrand;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1184197884)
    private transient CommonBrandModelDao myDao;



    @Generated(hash = 40471274)
    public CommonBrandModel(long id, long bid, long mid, String commonBrandName) {
        this.id = id;
        this.bid = bid;
        this.mid = mid;
        this.commonBrandName = commonBrandName;
    }



    @Generated(hash = 1086720834)
    public CommonBrandModel() {
    }



    public long getId() {
        return this.id;
    }



    public void setId(long id) {
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



    @Generated(hash = 424800249)
    private transient Long carBrand__resolvedKey;



    /** To-one relationship, resolved on first access. */
    @Generated(hash = 196653839)
    public CarBrandModel getCarBrand() {
        long __key = this.bid;
        if (carBrand__resolvedKey == null || !carBrand__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CarBrandModelDao targetDao = daoSession.getCarBrandModelDao();
            CarBrandModel carBrandNew = targetDao.load(__key);
            synchronized (this) {
                carBrand = carBrandNew;
                carBrand__resolvedKey = __key;
            }
        }
        return carBrand;
    }



    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1824570746)
    public void setCarBrand(@NotNull CarBrandModel carBrand) {
        if (carBrand == null) {
            throw new DaoException(
                    "To-one property 'bid' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.carBrand = carBrand;
            bid = carBrand.getId();
            carBrand__resolvedKey = bid;
        }
    }



    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }



    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }



    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }



    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1733909678)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCommonBrandModelDao() : null;
    }

}
