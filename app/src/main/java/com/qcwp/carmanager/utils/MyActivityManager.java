package com.qcwp.carmanager.utils;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by qyh on 2016/11/30.
 */

public class MyActivityManager {
    private static MyActivityManager sInstance = new MyActivityManager();
    private WeakReference<Activity> sCurrentActivityWeakRef;
    private List<Activity> activityList = new LinkedList<Activity>();

    private MyActivityManager() {

    }

    public synchronized static MyActivityManager getInstance() {
        return sInstance;
    }

    public Activity getCurrentActivity() {
        Activity currentActivity = null;
        if (sCurrentActivityWeakRef != null) {
            currentActivity = sCurrentActivityWeakRef.get();
        }
        return currentActivity;
    }

    public void setCurrentActivity(Activity activity) {
        sCurrentActivityWeakRef = new WeakReference<>(activity);
    }

    // add Activity
    public void addActivity(Activity activity) {
        if (!activityList.contains(activity))
            activityList.add(activity);
    }

    // remove Activity
    public void removeActivity(Activity activity) {
        if (activityList.contains(activity))
            activityList.remove(activity);
    }

    //关闭每一个list内的activity
    public void exitToHome() {
        try {
            for (Activity activity:activityList) {
                if (activity != null) {

                    String className = activity.getClass().getSimpleName();
                    if (!className.equals("HomeActivity"))
                        activity.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            System.exit(0);
        }
    }
    public void finishActivityList() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }
}
