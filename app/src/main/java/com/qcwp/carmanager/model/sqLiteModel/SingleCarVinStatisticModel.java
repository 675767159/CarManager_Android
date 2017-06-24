package com.qcwp.carmanager.model.sqLiteModel;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by qyh on 2017/6/19.
 */
@Entity(nameInDb = "SingleCarVinStatistic")
public class SingleCarVinStatisticModel {

    @Property(nameInDb = "vinCode")
    private String vinCode;
    @Property(nameInDb = "distCount")
    private double distCount;//使用软件 的行驶里程
    @Property(nameInDb = "maxDist")
    private double maxDist;//使用软件 的单次最长行驶里程
    @Property(nameInDb = "timeCount")
    private int timeCount;//使用软件 的行驶时长
    @Property(nameInDb = "maxTime")
    private int maxTime;//使用软件 的单次最长行驶时长
    @Property(nameInDb = "fuelCount")
    private double fuelCount;//使用软件 的行驶用油（单位:升）
    @Property(nameInDb = "avgFuel")
    private double avgFuel;//平均油耗
    @Property(nameInDb = "accelerCount")
    private int accelerCount;//急加速次数
    @Property(nameInDb = "decelerCount")
    private int decelerCount;//急刹车 次数
    @Property(nameInDb = "overspeedCount")
    private int overspeedCount;//超速预警          次数
    @Property(nameInDb = "wzcxCount")
    private int wzcxCount;//违章查询           次数
    @Property(nameInDb = "carCheckCount")
    private int carCheckCount;//体检次数
    @Property(nameInDb = "carCheckAvg")
    private double carCheckAvg;//体检平均分
    @Property(nameInDb = "faultCodeCount")
    private int faultCodeCount;//共检测发现的故障码
    @Property(nameInDb = "clearCodeCount")
    private int clearCodeCount;//清除的故障码个数
    @Property(nameInDb = "lastCarCheck")
    private int lastCarCheck;//最后体检分数
    @Property(nameInDb = "bmtestCount")
    private int bmtestCount;//百米测试的   次数
    @Property(nameInDb = "bmSeriesBestScore")
    private double bmSeriesBestScore;//百米加速最好成绩 车系
    @Property(nameInDb = "bmtestBestScore")
    private double bmtestBestScore;//本车百米加速最好成绩
    @Property(nameInDb = "bmtestBestBeat")
    private double bmtestBestBeat;//百米加速最好成绩排名 ％
    @Property(nameInDb = "bltestCount")
    private int bltestCount;//百公里测试的 次数
    @Property(nameInDb = "blSeriesBestScore")
    private double blSeriesBestScore;//百公里加速最好成绩 车系
    @Property(nameInDb = "bltestBestScore")
    private double bltestBestScore;//本车百公里加速最好成绩
    @Property(nameInDb = "bltestBestBeat")
    private double bltestBestBeat;//百公里加速最好成绩排名％
    @Property(nameInDb = "zdtestCount")
    private int zdtestCount;//制动测试的   次数
    @Property(nameInDb = "zdSeriesBestScore")
    private double zdSeriesBestScore;//制动最好成绩 车系
    @Property(nameInDb = "zdtestBestScore")
    private double zdtestBestScore;//本车刹车制动最好成绩
    @Property(nameInDb = "zdtestBestBeat")
    private double zdtestBestBeat;//刹车制动最好成绩排名％
    @Property(nameInDb = "carStataccelerCount")
    private int carStataccelerCount;//平均速度（额外增加）
    @Property(nameInDb = "avgVehicleSpeed")
    private double avgVehicleSpeed;//平均速度（额外增加）
    @Id(autoincrement = true)
    @Property(nameInDb = "onlyFlag")
    private Long onlyFlag;

    @Generated(hash = 1056699906)
    public SingleCarVinStatisticModel(String vinCode, double distCount,
            double maxDist, int timeCount, int maxTime, double fuelCount,
            double avgFuel, int accelerCount, int decelerCount, int overspeedCount,
            int wzcxCount, int carCheckCount, double carCheckAvg,
            int faultCodeCount, int clearCodeCount, int lastCarCheck,
            int bmtestCount, double bmSeriesBestScore, double bmtestBestScore,
            double bmtestBestBeat, int bltestCount, double blSeriesBestScore,
            double bltestBestScore, double bltestBestBeat, int zdtestCount,
            double zdSeriesBestScore, double zdtestBestScore, double zdtestBestBeat,
            int carStataccelerCount, double avgVehicleSpeed, Long onlyFlag) {
        this.vinCode = vinCode;
        this.distCount = distCount;
        this.maxDist = maxDist;
        this.timeCount = timeCount;
        this.maxTime = maxTime;
        this.fuelCount = fuelCount;
        this.avgFuel = avgFuel;
        this.accelerCount = accelerCount;
        this.decelerCount = decelerCount;
        this.overspeedCount = overspeedCount;
        this.wzcxCount = wzcxCount;
        this.carCheckCount = carCheckCount;
        this.carCheckAvg = carCheckAvg;
        this.faultCodeCount = faultCodeCount;
        this.clearCodeCount = clearCodeCount;
        this.lastCarCheck = lastCarCheck;
        this.bmtestCount = bmtestCount;
        this.bmSeriesBestScore = bmSeriesBestScore;
        this.bmtestBestScore = bmtestBestScore;
        this.bmtestBestBeat = bmtestBestBeat;
        this.bltestCount = bltestCount;
        this.blSeriesBestScore = blSeriesBestScore;
        this.bltestBestScore = bltestBestScore;
        this.bltestBestBeat = bltestBestBeat;
        this.zdtestCount = zdtestCount;
        this.zdSeriesBestScore = zdSeriesBestScore;
        this.zdtestBestScore = zdtestBestScore;
        this.zdtestBestBeat = zdtestBestBeat;
        this.carStataccelerCount = carStataccelerCount;
        this.avgVehicleSpeed = avgVehicleSpeed;
        this.onlyFlag = onlyFlag;
    }
    @Generated(hash = 40543826)
    public SingleCarVinStatisticModel() {
    }
    public String getVinCode() {
        return this.vinCode;
    }
    public void setVinCode(String vinCode) {
        this.vinCode = vinCode;
    }
    public double getDistCount() {
        return this.distCount;
    }
    public void setDistCount(double distCount) {
        this.distCount = distCount;
    }
    public double getMaxDist() {
        return this.maxDist;
    }
    public void setMaxDist(double maxDist) {
        this.maxDist = maxDist;
    }
    public int getTimeCount() {
        return this.timeCount;
    }
    public void setTimeCount(int timeCount) {
        this.timeCount = timeCount;
    }
    public int getMaxTime() {
        return this.maxTime;
    }
    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }
    public double getFuelCount() {
        return this.fuelCount;
    }
    public void setFuelCount(double fuelCount) {
        this.fuelCount = fuelCount;
    }
    public double getAvgFuel() {
        return this.avgFuel;
    }
    public void setAvgFuel(double avgFuel) {
        this.avgFuel = avgFuel;
    }
    public int getAccelerCount() {
        return this.accelerCount;
    }
    public void setAccelerCount(int accelerCount) {
        this.accelerCount = accelerCount;
    }
    public int getDecelerCount() {
        return this.decelerCount;
    }
    public void setDecelerCount(int decelerCount) {
        this.decelerCount = decelerCount;
    }
    public int getOverspeedCount() {
        return this.overspeedCount;
    }
    public void setOverspeedCount(int overspeedCount) {
        this.overspeedCount = overspeedCount;
    }
    public int getWzcxCount() {
        return this.wzcxCount;
    }
    public void setWzcxCount(int wzcxCount) {
        this.wzcxCount = wzcxCount;
    }
    public int getCarCheckCount() {
        return this.carCheckCount;
    }
    public void setCarCheckCount(int carCheckCount) {
        this.carCheckCount = carCheckCount;
    }
    public double getCarCheckAvg() {
        return this.carCheckAvg;
    }
    public void setCarCheckAvg(double carCheckAvg) {
        this.carCheckAvg = carCheckAvg;
    }
    public int getFaultCodeCount() {
        return this.faultCodeCount;
    }
    public void setFaultCodeCount(int faultCodeCount) {
        this.faultCodeCount = faultCodeCount;
    }
    public int getClearCodeCount() {
        return this.clearCodeCount;
    }
    public void setClearCodeCount(int clearCodeCount) {
        this.clearCodeCount = clearCodeCount;
    }
    public int getLastCarCheck() {
        return this.lastCarCheck;
    }
    public void setLastCarCheck(int lastCarCheck) {
        this.lastCarCheck = lastCarCheck;
    }
    public int getBmtestCount() {
        return this.bmtestCount;
    }
    public void setBmtestCount(int bmtestCount) {
        this.bmtestCount = bmtestCount;
    }
    public double getBmSeriesBestScore() {
        return this.bmSeriesBestScore;
    }
    public void setBmSeriesBestScore(double bmSeriesBestScore) {
        this.bmSeriesBestScore = bmSeriesBestScore;
    }
    public double getBmtestBestScore() {
        return this.bmtestBestScore;
    }
    public void setBmtestBestScore(double bmtestBestScore) {
        this.bmtestBestScore = bmtestBestScore;
    }
    public double getBmtestBestBeat() {
        return this.bmtestBestBeat;
    }
    public void setBmtestBestBeat(double bmtestBestBeat) {
        this.bmtestBestBeat = bmtestBestBeat;
    }
    public int getBltestCount() {
        return this.bltestCount;
    }
    public void setBltestCount(int bltestCount) {
        this.bltestCount = bltestCount;
    }
    public double getBlSeriesBestScore() {
        return this.blSeriesBestScore;
    }
    public void setBlSeriesBestScore(double blSeriesBestScore) {
        this.blSeriesBestScore = blSeriesBestScore;
    }
    public double getBltestBestScore() {
        return this.bltestBestScore;
    }
    public void setBltestBestScore(double bltestBestScore) {
        this.bltestBestScore = bltestBestScore;
    }
    public double getBltestBestBeat() {
        return this.bltestBestBeat;
    }
    public void setBltestBestBeat(double bltestBestBeat) {
        this.bltestBestBeat = bltestBestBeat;
    }
    public int getZdtestCount() {
        return this.zdtestCount;
    }
    public void setZdtestCount(int zdtestCount) {
        this.zdtestCount = zdtestCount;
    }
    public double getZdSeriesBestScore() {
        return this.zdSeriesBestScore;
    }
    public void setZdSeriesBestScore(double zdSeriesBestScore) {
        this.zdSeriesBestScore = zdSeriesBestScore;
    }
    public double getZdtestBestScore() {
        return this.zdtestBestScore;
    }
    public void setZdtestBestScore(double zdtestBestScore) {
        this.zdtestBestScore = zdtestBestScore;
    }
    public double getZdtestBestBeat() {
        return this.zdtestBestBeat;
    }
    public void setZdtestBestBeat(double zdtestBestBeat) {
        this.zdtestBestBeat = zdtestBestBeat;
    }
    public int getCarStataccelerCount() {
        return this.carStataccelerCount;
    }
    public void setCarStataccelerCount(int carStataccelerCount) {
        this.carStataccelerCount = carStataccelerCount;
    }
    public double getAvgVehicleSpeed() {
        return this.avgVehicleSpeed;
    }
    public void setAvgVehicleSpeed(double avgVehicleSpeed) {
        this.avgVehicleSpeed = avgVehicleSpeed;
    }
    public Long getOnlyFlag() {
        return this.onlyFlag;
    }
    public void setOnlyFlag(Long onlyFlag) {
        this.onlyFlag = onlyFlag;
    }


}
