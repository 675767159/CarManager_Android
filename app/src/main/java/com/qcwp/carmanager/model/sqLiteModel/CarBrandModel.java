package com.qcwp.carmanager.model.sqLiteModel;

import com.qcwp.carmanager.enumeration.TableEnum;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by qyh on 2017/6/18.
 */
@Entity(nameInDb = "t_brand")
public class CarBrandModel {

    @Property(nameInDb = "Id")
    @Id(autoincrement = true)
    @NotNull
    private Long id;
    @Property(nameInDb = "BrandName")
    private String brandName;
    @Property(nameInDb = "BrandNamePinYin")
    private String brandNamePinYin;





    @Generated(hash = 1062401135)
    public CarBrandModel(@NotNull Long id, String brandName,
            String brandNamePinYin) {
        this.id = id;
        this.brandName = brandName;
        this.brandNamePinYin = brandNamePinYin;
    }
    @Generated(hash = 659223025)
    public CarBrandModel() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getBrandName() {
        return this.brandName;
    }
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
    public String getBrandNamePinYin() {
        return this.brandNamePinYin;
    }
    public void setBrandNamePinYin(String brandNamePinYin) {
        this.brandNamePinYin = brandNamePinYin;
    }
}
