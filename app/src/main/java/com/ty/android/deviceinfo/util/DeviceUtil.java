package com.ty.android.deviceinfo.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.ClipboardManager;
import android.util.DisplayMetrics;
import android.view.View;

import com.ty.android.deviceinfo.MyApplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DecimalFormat;

public class DeviceUtil {

    static DecimalFormat df = new DecimalFormat("0.00");

    static TelephonyManager tm;

    /**
     * 获取屏幕信息
     * @param activity
     * @return
     */
    public static String getScreenInfo(Activity activity){

        String screenInfo;

        DisplayMetrics metrics =new DisplayMetrics();
        /**
         * getRealMetrics - 屏幕的原始尺寸，即包含状态栏。
         * version >= 4.2.2
         */
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);

        int screenHeight = metrics.heightPixels;

        int screenWidth = metrics.widthPixels;

        String  resolution = screenHeight + "×" + screenWidth;

        float density = metrics.density;        // 屏幕密度（0.75 / 1.0 / 1.5）

        int densityDpi = metrics.densityDpi;    // 屏幕DPI（120 / 160 / 240）

        screenInfo = "分辨率:" + resolution  + " 屏幕密度:" + density + " DPI:" + densityDpi;

        return screenInfo;
    }

    /**
     * 获取IMEI和IMSI
     */
    @SuppressLint("MissingPermission")
    public static String[] getImeiAndImsi(Context context) {
        String[] imei_imsi = new String[2];

        tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        imei_imsi[0] = tm.getDeviceId();

        if(tm.getDeviceId() != null) {
            imei_imsi[0] = tm.getDeviceId();
        }else {
            imei_imsi[0] = "未获取到IMEI";
        }

        if(tm.getSubscriberId() != null) {
            imei_imsi[1] = tm.getSubscriberId();
        }else {
            imei_imsi[1] = "未插SIM卡";
        }

        LogUtil.log(imei_imsi[0] + "\n" + imei_imsi[1]);

        return imei_imsi;

    }

    /**
     * 获取手机号
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getPhoneNumber(Context context) {

        String phoneNumber = "";

        tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        phoneNumber = tm.getLine1Number();

        if ("".equals(phoneNumber) || phoneNumber == null) {
            phoneNumber = "未获取到电话号码";
        }

        LogUtil.log("电话号码" + phoneNumber);

        return phoneNumber;

    }

    /**
     * 获取移动网络运营商的名称
     * @param context
     * @return
     */
    public static String getNetworkOperatorName(Context context) {

        String networkOperatorName;

        tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        networkOperatorName = tm.getNetworkOperatorName();

        LogUtil.log("网络运营商:" + networkOperatorName);

        if ("".equals(networkOperatorName) || "null".equals(networkOperatorName)) {
            networkOperatorName = "未获取到运营商名称";
        }

        LogUtil.log("网络运营商:" + networkOperatorName);

        return networkOperatorName;
    }

    /**
     * 获取手机品牌
     *
     * @return
     */
    public static String getPhoneBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getPhoneModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getOsVersion() {
        return "Android " + android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
     }

    /***
     * 使用WIFI时，获取本机IP地址
     * @param mContext
     * @return
     */
    public static String[] getWiFiInfo(Context mContext) {

        String[] WifiInfo = new String[2];

        //获取wifi服务
        WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);

        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            WifiInfo[0] = "Wifi未开启"; // Wifi名称
            WifiInfo[1] = "Wifi未开启"; // Wifi IP地址
        } else {

            WifiInfo[0] = wifiInfo.getSSID().substring(1, (wifiInfo.getSSID().length() - 1));
            int ipAddress = wifiInfo.getIpAddress();

            WifiInfo[1] = formatIpAddress(ipAddress);

            if (WifiInfo[1].equals("0.0.0.0")) {
                WifiInfo[0] = "Wifi未连接";
                WifiInfo[1] = "Wifi未连接";
            }
        }

        return WifiInfo;
    }

    /**
     * 格式化IP地址
     * @param ipAdress
     * @return
     */
    private static String formatIpAddress(int ipAdress) {

        return (ipAdress & 0xFF ) + "." +
                ((ipAdress >> 8 ) & 0xFF) + "." +
                ((ipAdress >> 16 ) & 0xFF) + "." +
                ( ipAdress >> 24 & 0xFF) ;
    }

    /**
     * 获取rom版本号
     * @return
     */
    public static String getRomVersion() {
        return android.os.Build.DISPLAY;
    }

    /**
     * 获取总内存
     * @return
     */
    public static String getTotalMemory(){//GB

        String path = "/proc/meminfo";
        String firstLine = null;
        String totalMemory = null ;

        try{
            FileReader fileReader = new FileReader(path);
            BufferedReader br = new BufferedReader(fileReader,8192);
            firstLine = br.readLine().split("\\s+")[1];
            br.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(firstLine != null){
            totalMemory = df.format(Float.valueOf(firstLine) / (1024 * 1024));
        }

        return totalMemory + "GB";

    }

    /**
     * 获取剩余内存
     * @param context
     * @return
     */
    public static String getFreeMemory(Context context) {

        String freeMemory;

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();

        activityManager.getMemoryInfo(info);

        freeMemory = df.format(Float.valueOf(info.availMem) / (1024 * 1024 * 1024));

        return freeMemory + "GB";

    }

    /**
     * 复制到剪贴板
     * @param content
     */
    public static void copyToClipboard(View view, String content, int height) {
        ClipboardManager cm = (ClipboardManager) MyApplication.getAppContext().getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(content);
//        ToastUtil.show("“" + content + "”" + " 已复制");
//        SnackBarUtils.showSnackBar(view, "“" + content + "”" + " 已复制");
        SnackBarUtils.showTopSnackBar(view, "“" + content + "”" + " 已复制", height, 1);
    }

    /**
     * 获取权限对应的中文名称
     * @param permission
     * @return
     */
    public static String getPermissionContent(String permission) {

        String permissionContent = "";

        switch (permission) {
            case Manifest.permission.READ_PHONE_STATE:
                permissionContent = "电话";
                break;

            default:
                break;
        }

        return permissionContent;
    }

    /**
     * 获取TSnackbar高度
     * @return
     */
    public static int getTSnackbarHeight(Context context) {
        return (getStatusBarHeight(context) + getActionBarSize(context));
    }

    /**
     * 获取状态栏高度
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /**
     * 获取ActionBar的高度
     * @param context
     * @return
     */
    protected static int getActionBarSize(Context context) {
        int[] attrs = { android.R.attr.actionBarSize };
        TypedArray values = context.getTheme().obtainStyledAttributes(attrs);
        try {
            return values.getDimensionPixelSize(0, 0);//第一个参数数组索引，第二个参数 默认值
        } finally {
            values.recycle();
        }
    }
}
