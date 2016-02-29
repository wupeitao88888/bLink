package com.shiliuke.global;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import android.content.Intent;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.shiliuke.BabyLink.BaseSendActivity;
import com.shiliuke.bean.LocationBD;
import com.shiliuke.model.ApplicationLocationListener;
import com.shiliuke.utils.L;
import com.squareup.otto.Bus;
import com.umeng.update.UpdateConfig;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * @author wpt
 */
public class MApplication extends Application {
    private static List<Activity> activityList = new LinkedList<Activity>();
    //    public static  String VERSION;
    public static Context context;
    private static MApplication app;
    private Bus mBus;
    private LocationClient mLocationClient;
    public BDLocationListener myListener = new MyLocationListener();
    private ApplicationLocationListener applicationLocationListener;
    public static LocationBD locationBD = new LocationBD();
    private boolean lbs;
    private double latitude;
    private double longitude;
    private String city ="";
    private String district = "";
    private String street ="";
    private static Map<String,Boolean> hsaNewMap = new HashMap<>();

    public static void setSend_Result(String key) {
       hsaNewMap.put(key,true);
    }

    public static boolean hasNew(String key) {
        boolean a = hsaNewMap.get(key);
        hsaNewMap.put(key,false);
        return a;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        hsaNewMap.put(BaseSendActivity.SENDCHANGESUCCESS,false);
        hsaNewMap.put(BaseSendActivity.SENDISSUESUCCESS,false);
        hsaNewMap.put(BaseSendActivity.SENDSHOWSUCCESS,false);
        hsaNewMap.put(BaseSendActivity.SENDTOPICSUCCESS,false);
        app = this;
        // �쳣����
//        if(!L.isDebug){
////            CrashHandler crashHandler = CrashHandler.getInstance();
//            crashHandler.init(getApplicationContext());
//        }
//		VERSION=getResources().getString(R.string.vistion);
//        打开日志输出，发布应用时请去掉
        UpdateConfig.setDebug(true);
        startLBS();
        context = getApplicationContext();
    }

    public static synchronized MApplication getInstance() {
        return app;
    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public static void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        System.gc();
        System.exit(0);
    }

    public static MApplication getApp() {
        return app;
    }

    public Bus getBus() {
        if (mBus == null) {
            mBus = new Bus();
        }
        return mBus;
    }

    class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            L.e("error code : " + bdLocation.getLocType());

            if ("161".equals(bdLocation.getLocType() + "")) {
                //定位成功
                L.e("纬度 : " + bdLocation.getLatitude());
                L.e("经度 : " + bdLocation.getLongitude());
                L.e("街道：" + bdLocation.getStreet());
                lbs = true;
                latitude = bdLocation.getLatitude();
                longitude = bdLocation.getLongitude();
                city = bdLocation.getCity();
                district = bdLocation.getDistrict();
                street = bdLocation.getStreet();
                locationBD = new LocationBD(lbs,latitude,longitude,city,district,street);
                if (applicationLocationListener != null) {
                    applicationLocationListener.onReceiveLocation(bdLocation);
                }
            } else if ("66".equals(bdLocation.getLocType() + "")) {
                lbs = true;
                L.e("纬度 : " + bdLocation.getLatitude());
                L.e("经度 : " + bdLocation.getLongitude());
                L.e("街道：" + bdLocation.getStreet());
                latitude = bdLocation.getLatitude();
                longitude = bdLocation.getLongitude();
                street = bdLocation.getStreet();
                if (applicationLocationListener != null) {
                    applicationLocationListener.onReceiveLocation(bdLocation);
                }
            } else if("61".equals(bdLocation.getLocType() + "")){
                lbs = true;
                L.e("纬度 : " + bdLocation.getLatitude());
                L.e("经度 : " + bdLocation.getLongitude());
                L.e("街道：" + bdLocation.getStreet());
                latitude = bdLocation.getLatitude();
                longitude = bdLocation.getLongitude();
                street = bdLocation.getStreet();
                if (applicationLocationListener != null) {
                    applicationLocationListener.onReceiveLocation(bdLocation);
                }
            }else {
                //定位失败
                lbs = false;
                if (applicationLocationListener != null) {
                    applicationLocationListener.fild();
                }
            }
            mLocationClient.stop();
        }
    }

    public void setOnReceiveLocation(ApplicationLocationListener var1) {
        this.applicationLocationListener = var1;
    }


    public void startLBS() {
        mLocationClient = new LocationClient(getApplicationContext());     //声明Location
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        mLocationClient.start();
        initLocation();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }
}
