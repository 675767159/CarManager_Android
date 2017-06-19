package com.qcwp.carmanager.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by qyh on 2017/6/16.
 */

//@Entity
////如果该实体属于多个表单，可以使用该参数;
//schema = "myschema",
//// 该实体属于激活状态，激活状态的实体有更新，删除，刷新方法;
//        active = true,
//// 给这个表指定一个名字，默认情况下是名字是类名
//        nameInDb = "AWESOME_USERS",
//// 可以给多个属性定义索引和其他属性.
//        indexes = { @Index(value = "name DESC", unique = true) },
////是否使用GreenDao创建该表.
//        createInDb = false,
//// 是否所有的属性构造器都应该被生成，无参构造器总是被要求
//        generateConstructors = true,
//// 如果该类中没有set get方法是否自动生成
//        generateGettersSetters = true
public class Shop{

    //表示为购物车列表
    public static final int TYPE_CART = 0x01;
    //表示为收藏列表
    public static final int TYPE_LOVE = 0x02;

    //不能用int
    @Id(autoincrement = true)
    private Long id;
    //商品名称
    @Unique//：该属性值必须在数据库中是唯一值
    private String name;
    //商品价格
    @Property(nameInDb = "price")//可以自定义字段名，注意外键不能使用该属性
    private String price;
    //已售数量
    @NotNull//属性不能为空
    private int sell_num;
    //图标url
    @Transient//：使用该注释的属性不会被存入数据库的字段中
    private String image_url;
    //商家地址
    private String address;
    //商品列表类型
    private int type;
//    @Generated(hash = 811434019)
//    public Shop(Long id, String name, String price, int sell_num, String address,
//            int type) {
//        this.id = id;
//        this.name = name;
//        this.price = price;
//        this.sell_num = sell_num;
//        this.address = address;
//        this.type = type;
//    }
//    @Generated(hash = 633476670)
//    public Shop() {
//    }
//    public Long getId() {
//        return this.id;
//    }
//    public void setId(Long id) {
//        this.id = id;
//    }
//    public String getName() {
//        return this.name;
//    }
//    public void setName(String name) {
//        this.name = name;
//    }
//    public String getPrice() {
//        return this.price;
//    }
//    public void setPrice(String price) {
//        this.price = price;
//    }
//    public int getSell_num() {
//        return this.sell_num;
//    }
//    public void setSell_num(int sell_num) {
//        this.sell_num = sell_num;
//    }
//    public String getImage_url() {
//        return this.image_url;
//    }
//    public void setImage_url(String image_url) {
//        this.image_url = image_url;
//    }
//    public String getAddress() {
//        return this.address;
//    }
//    public void setAddress(String address) {
//        this.address = address;
//    }
//    public int getType() {
//        return this.type;
//    }
//    public void setType(int type) {
//        this.type = type;
//    }
}