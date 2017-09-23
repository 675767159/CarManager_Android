package com.qcwp.carmanager.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.constant.TimeConstants;
import com.blankj.utilcode.util.TimeUtils;
import com.qcwp.carmanager.R;
import com.qcwp.carmanager.broadcast.MessageEvent;
import com.qcwp.carmanager.control.NavBarView;
import com.qcwp.carmanager.control.TitleContentView;
import com.qcwp.carmanager.enumeration.KeyEnum;
import com.qcwp.carmanager.enumeration.ProfessionalTestEnum;
import com.qcwp.carmanager.enumeration.UploadStatusEnum;
import com.qcwp.carmanager.implement.TextToSpeechClass;
import com.qcwp.carmanager.model.UserData;
import com.qcwp.carmanager.model.sqLiteModel.CarInfoModel;
import com.qcwp.carmanager.model.sqLiteModel.TestSummaryModel;
import com.qcwp.carmanager.obd.OBDClient;
import com.qcwp.carmanager.utils.CommonUtils;
import com.qcwp.carmanager.utils.Print;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class ProfessionalTestDetailActivity extends BaseActivity {


    @BindView(R.id.chartView)
    LineChartView chartView;
    @BindView(R.id.testState)
    TextView testState;
    @BindView(R.id.accelerateTime)
    TextView accelerateTime;
    @BindView(R.id.testItemValue)
    TextView testItemValue;
    @BindView(R.id.testDataBackground)
    LinearLayout testDataBackground;
    @BindView(R.id.carSeries)
    TitleContentView carSeries;
    @BindView(R.id.carType)
    TitleContentView carType;
    @BindView(R.id.productYear)
    TitleContentView productYear;
    @BindView(R.id.carTotalMileage)
    TitleContentView carTotalMileage;
    @BindView(R.id.testItemName)
    TextView testItemName;

    @BindView(R.id.NavbarView)
    NavBarView navBarView;

    @BindView(R.id.testTimeName)
    TextView testTimeName;



    private OBDClient obdClient;
    private Boolean isReady=false,isEnd=false;
    private Boolean isBrake=false;
    private long testStartTime;
    private long driveTime;
    private List<PointValue>pointValueList;
    private LineChartData data;
    private  Line line;
    private  float totalVehicleSpeed;
    private int count;
    private float lastVehicleSpeed;
    private  ProfessionalTestEnum professionalTestType;
    private  CarInfoModel carInfoModel;

    private float maxSpeed,testTime,testDist;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_professional_test_detail;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {

        obdClient=OBDClient.getDefaultClien();

        Intent intent=getIntent();
        professionalTestType=(ProfessionalTestEnum)intent.getSerializableExtra(KeyEnum.professionalTestType);
        String axisYName="";
        switch (professionalTestType){
            case HectometerAccelerate:
                navBarView.setTitle(this.getString(R.string.hectometerAccelerateName));
                axisYName="距离(m)";
                testItemName.setText("最高时速");
                break;
            case KilometersAccelerate:
                navBarView.setTitle(this.getString(R.string.kilometersAccelerateName));
                axisYName="速度(km/h)";
                testItemName.setText("行车距离");
                break;
            case KilometersBrake:
                navBarView.setTitle(this.getString(R.string.kilometersBrakeName));
                axisYName="速度(km/h)";
                testItemName.setText("刹车距离");
                testState.setText("请将速度提至100km/h以上");
                testTimeName.setText("刹车距离");
                break;
        }

        this.initChartView(axisYName);

        carInfoModel=CarInfoModel.getCarInfoByVinCode(UserData.getInstance().getVinCode());
        if (carInfoModel!=null){
            carType.setContentTextViewText(carInfoModel.getCarType().getCarTypeName());
            carSeries.setContentTextViewText(carInfoModel.getCarSeries());
            productYear.setContentTextViewText(carInfoModel.getProductiveYear());
            carTotalMileage.setContentTextViewText(String.format(Locale.getDefault(),"%.1f",carInfoModel.getTotalMileage()));
        }



    }

    @Override
    protected void onReceiveMessageEvent(MessageEvent messageEvent) {
        float vehicleSpeed=(float)obdClient.getVehicleSpeed();
        switch (messageEvent.getType()){
            case Driving:
                switch (professionalTestType){
                    case HectometerAccelerate:
                    case KilometersAccelerate:
                        if (vehicleSpeed==0){
                            obdClient.startTest(); //开始测试状态
                        }
                        break;
                    case KilometersBrake:
                        if (vehicleSpeed>100){
                            obdClient.startTest(); //开始测试状态
                        }
                        testState.setText(String.format(Locale.getDefault(),"预测试阶段速度：%.1f km/h",vehicleSpeed));
                        break;
                }
                break;
            case CarTest:

                switch (professionalTestType){

                    case HectometerAccelerate:
                    case KilometersAccelerate:

                        if (vehicleSpeed==0&&!isReady) {//测试初始化
                            this.readyTest();
                        }else if (vehicleSpeed>0){
                            if (isReady){
                                this.testStart();//测试开始
                            }
                            this.updateChartViewData(vehicleSpeed);
                        }

                        break;
                    case KilometersBrake:

                        if (!isReady&&!isBrake) {//测试初始化
                            this.readyTest();
                            Print.d(TAG,"readyTest");
                        }else{
                            if (vehicleSpeed<lastVehicleSpeed-2&&!isBrake){
                                this.testStart();//测试开始

                                Print.d(TAG,"testStart");
                            }
                            if (isBrake) {
                                this.updateChartViewData(vehicleSpeed);
                            }else {
                                maxSpeed=vehicleSpeed;
                                testState.setText(String.format(Locale.getDefault(),"预测试速度：%.1f km/h",vehicleSpeed));
                            }
                        }

                        break;
                }
                lastVehicleSpeed=vehicleSpeed;
                break;
        }
    }

    /*** 设置X 轴的显示
     */
    private  List<AxisValue>  getAxisX() {
        List<AxisValue> axisValueList=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            axisValueList.add(new AxisValue(i*1000).setLabel(String.valueOf(i)));
        }
        return axisValueList;
    }


    /*** 设置Y 轴的显示
     */
    private  List<AxisValue>  getAxisY() {
        List<AxisValue> axisValueList=new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            axisValueList.add(new AxisValue(i*20).setLabel(String.valueOf(i*20)));
        }
        return axisValueList;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.startTest:

                break;
        }
    }

    private void readyTest(){
        Print.d(TAG,"readyTest");
        isReady=true;
        testState.setText(this.getString(R.string.professionalTest_testReady));
        pointValueList=new ArrayList<>();
        totalVehicleSpeed=0;
        count=0;
        lastVehicleSpeed=0;

    }

    private void testStart(){
        if (professionalTestType!=ProfessionalTestEnum.KilometersBrake) {
            testState.setText(this.getString(R.string.professionalTest_testStart));
        }
        testStartTime= TimeUtils.getNowMills();
        isReady=false;
        isBrake=true;
    }
    private void testEnd(String message){
        obdClient.stopTest();
        testState.setText(message);
        isBrake=false;
        isEnd=true;
        String createDate=TimeUtils.getNowString();
        TestSummaryModel testSummaryModel=new TestSummaryModel();
        testSummaryModel.setVinCode(carInfoModel.getVinCode());
        testSummaryModel.setCarNumber(carInfoModel.getCarNumber());
        testSummaryModel.setCreateDate(createDate);
        testSummaryModel.setMaxSpeed(maxSpeed);
        testSummaryModel.setTestDist(testDist);
        testSummaryModel.setTestTime(testTime);
        testSummaryModel.setTestType(professionalTestType);
        testSummaryModel.setUploadFlag(UploadStatusEnum.NotUpload);
        testSummaryModel.setUserName(UserData.getInstance().getUserName());
        mDaoSession.insert(testSummaryModel);
        CommonUtils.saveCurrentImage(this,createDate);

    }

    private void updateChartViewData(float vehicleSpeed){





        long testEndTime=TimeUtils.getNowMills();
        driveTime=TimeUtils.getTimeSpan(testStartTime,testEndTime, TimeConstants.MSEC);
        accelerateTime.setText(String.format(Locale.getDefault(),"%.1f s",driveTime*1f/1000));

        //0.001s 的路程=  (speed值*1000m/3600s)*0.001s=speed值/3600 (m)
        totalVehicleSpeed+=vehicleSpeed;
        count++;
        float  currentMileage=(totalVehicleSpeed/count)/3600*driveTime;
        PointValue value=null;
        switch (professionalTestType){
            case HectometerAccelerate:
                testItemValue.setText(String.format(Locale.getDefault(),"%.1f km/h",vehicleSpeed));
                value = new PointValue(driveTime, currentMileage);
                if (currentMileage>100&&!isEnd){
                    maxSpeed=vehicleSpeed;
                    testDist=currentMileage;
                    testTime=driveTime*1f/1000;
                    this.testEnd(this.getString(R.string.professionalTest_testEnd));
                    return;
                }

                if (vehicleSpeed<lastVehicleSpeed-2){
                    this.testEnd(this.getString(R.string.professionalTest_testFailure));
                    return;

                }

                break;
            case KilometersAccelerate:
                testItemValue.setText(String.format(Locale.getDefault(),"%.1f m",currentMileage));
                value = new PointValue(driveTime, vehicleSpeed);
                if (vehicleSpeed>100&&!isEnd){
                    maxSpeed=vehicleSpeed;
                    testDist=currentMileage;
                    testTime=driveTime*1f/1000;
                    this.testEnd(this.getString(R.string.professionalTest_testEnd));
                    return;
                }

                if (vehicleSpeed<lastVehicleSpeed-2){
                    this.testEnd(this.getString(R.string.professionalTest_testFailure));
                    return;

                }

                break;
            case KilometersBrake:
                testItemValue.setText(String.format(Locale.getDefault(),"%.1f m",currentMileage));
                value = new PointValue(driveTime, vehicleSpeed);
                if (vehicleSpeed==0&&!isEnd){
                    testDist=currentMileage;
                    testTime=driveTime*1f/1000;
                    this.testEnd(this.getString(R.string.professionalTest_testEnd));
                    return;
                }

                if (vehicleSpeed>lastVehicleSpeed+2){
                    this.testEnd(this.getString(R.string.professionalTest_testFailure));
                    return;

                }

                break;
        }





        //实时添加新的点

        pointValueList.add(value);
        float x = value.getX();

//根据新的点的集合画出新的线

        line.setValues(pointValueList);

        List<Line> linesList = new ArrayList<>();
        linesList.add(line);


        data.setLines(linesList);
        chartView.setLineChartData(data);

        float right=10*1000;
        if (x>right){
            right=((int)((x-right)/1000)+1)*1000+right;
        }
        Viewport port = new Viewport(0,120,right,0);
        chartView.setMaximumViewport(port);//最大窗口
        chartView.setCurrentViewport(port);//当前窗口



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        obdClient.stopTest();
    }


  private void initChartView(String axisYName){

      chartView.setInteractive(false);//设置图表是可以交互的（拖拽，缩放等效果的前提）
      chartView.setZoomType(ZoomType.HORIZONTAL_AND_VERTICAL);//设置缩放方向

      data = new LineChartData();


      Axis axisX = new Axis();//x轴
      axisX.setName("时间(s)");
      axisX.setHasLines(true);
      axisX.setValues(getAxisX());
      axisX.setHasLines(true);
      data.setAxisXBottom(axisX);

      Axis axisY = new Axis();//y轴
      axisY.setName(axisYName);
      axisY.setValues(getAxisY());
      axisY.setHasLines(true);
      axisY.setHasLines(true);
      data.setAxisYLeft(axisY);


      line = new Line().setColor(Color.RED);  //折线的颜色（橙色）
      line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
      line.setCubic(false);//曲线是否平滑，即是曲线还是折线
      line.setFilled(false);//是否填充曲线的面积
      line.setHasLabels(false);//曲线的数据坐标是否加上备注
      //      line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
      line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
      line.setHasPoints(false);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）






      chartView.setLineChartData(data);//给图表设置数据


      Viewport v = new Viewport(0,120,10*1000,0);

      chartView.setMaximumViewport(v);

      chartView.setCurrentViewport(chartView.getMaximumViewport());

  }


}
