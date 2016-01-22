package com.sina.sinaluncher.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.widget.Toast;

import com.sina.sinaluncher.R;
import com.sina.sinaluncher.core.SALInfo;
import com.sina.sinaluncher.core.SALModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sinash94857 on 2016/1/8.
 */
public class Utils {

    public static List<SALInfo> getTestData(){
        List<SALInfo> list = new ArrayList<SALInfo>();

        int index = 0;
        SALInfo info;

        info = new SALInfo();
        info.appName = "新浪视频";
        info.packageName = "com.sina.sinavideo";
        info.appId = index++;
        info.appIcon = R.drawable.sinavideo_icon;
        list.add(info);

        info = new SALInfo();
        info.appName = "新浪体育";
        info.packageName = "cn.com.sina.sports";
        info.appId = index++;
        info.appIcon = R.drawable.sinasports_icon;
        list.add(info);

        info = new SALInfo();
        info.appName = "新浪微博";
        info.packageName = "com.sina.weibo";
        info.appId = index++;
        info.appIcon = R.drawable.sinaweibo_icon;
        list.add(info);

        info = new SALInfo();
        info.appName = "新浪视频";
        info.packageName = "com.sina.sinavideo";
        info.appId = index++;
        info.appIcon = R.drawable.sinavideo_icon;
        list.add(info);

        info = new SALInfo();
        info.appName = "新浪体育";
        info.packageName = "cn.com.sina.sports";
        info.appId = index++;
        info.appIcon = R.drawable.sinasports_icon;
        list.add(info);

        info = new SALInfo();
        info.appName = "新浪微博";
        info.packageName = "com.sina.weibo";
        info.appId = index++;
        info.appIcon = R.drawable.sinaweibo_icon;
        list.add(info);

        info = new SALInfo();
        info.appName = "新浪视频";
        info.packageName = "com.sina.sinavideo";
        info.appId = index++;
        info.appIcon = R.drawable.sinavideo_icon;
        list.add(info);

        info = new SALInfo();
        info.appName = "新浪体育";
        info.packageName = "cn.com.sina.sports";
        info.appId = index++;
        info.appIcon = R.drawable.sinasports_icon;
        list.add(info);

        info = new SALInfo();
        info.appName = "新浪微博";
        info.packageName = "com.sina.weibo";
        info.appId = index++;
        info.appIcon = R.drawable.sinaweibo_icon;
        list.add(info);

        info = new SALInfo();
        info.appName = "新浪视频";
        info.packageName = "com.sina.sinavideo";
        info.appId = index++;
        info.appIcon = R.drawable.sinavideo_icon;
        list.add(info);

        info = new SALInfo();
        info.appName = "新浪体育";
        info.packageName = "cn.com.sina.sports";
        info.appId = index++;
        info.appIcon = R.drawable.sinasports_icon;
        list.add(info);

        info = new SALInfo();
        info.appName = "新浪快速入口";
        info.packageName = "com.sina.sinaluncherdemo";
        info.appId = index++;
        info.appIcon = R.drawable.sinasports_icon;
        list.add(info);
        return list;
    }

    public static void removeSelf(Context context,List<SALInfo> data){
        Iterator<SALInfo> iterator = data.iterator();
        while(iterator.hasNext()){
            if(context.getPackageName().equals(iterator.next().packageName)){
                iterator.remove();
                break;
            }
        }
    }


    public static List<SALInfo> converModel(List<SALModel> models){
        List<SALInfo> result = new ArrayList<SALInfo>();
        int index = 0;
        for(SALModel model : models){
            SALInfo info = new SALInfo();
            info.appId = index++;
            info.packageName = model.packageName;
            info.iconUrl = model.appIcon;
            info.appName = model.appName;
            info.entryStatus = model.entryStatus;
            info.downloadUrl = model.download;
            info.inList = model.inList;
            result.add(info);
        }
        return result;
    }

    // 通过包名检测系统中是否安装某个应用程序
    public static boolean improveAppsInfo(Context context, String packageName) {
        if (packageName == null || "".equals(packageName)) {
            return false;
        }
        try {
            context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }

    // 通过包名检测系统中是否安装某个应用程序
    public static SALInfo improveAppsInfo(Context context, List<SALInfo> sALInfos) {
        PackageManager manager = context.getPackageManager();
        String thisAppPackageName = context.getPackageName();
        SALInfo self = null;
        List<ApplicationInfo> infos = manager.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        for(SALInfo item : sALInfos){
            for(ApplicationInfo info : infos){
                if(info.packageName.equals(item.packageName)){
                    item.isInstall = true;
                    item.intent = manager.getLaunchIntentForPackage(item.packageName);
                    break;
                }
            }
            if(thisAppPackageName.equals(item.packageName)){
                item.self = true;
                self = item;
            }
        }
        return self;
    }

    // 通过包名检测系统中是否安装某个应用程序
    public static boolean[] improveAppsInfo(Context context, String[] packageNames) {
        boolean[] result = new boolean[packageNames.length];
        List<ApplicationInfo> infos =context.getPackageManager().getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        for(ApplicationInfo info : infos){
            for(int i = 0; i < packageNames.length ; i++){
                if(info.packageName.equals(packageNames[i])){
                    result[i] = true;
                    break;
                }
            }
        }
        return result;
    }

    public static void jumpApp(Activity activity,SALInfo info){
        if(info.intent != null) {
            activity.startActivity(info.intent);
        }else{
            Toast.makeText(activity, "跳转异常", Toast.LENGTH_SHORT).show();
        }
    }


    private static Boolean isInstallMarket;

    public static void jumpMarket(Activity activity,SALInfo info){
        try {
            //Uri uri = Uri.parse("https://market.android.com/details?id="+ info.packageName);
            Intent in = new Intent(Intent.ACTION_VIEW);
            in.setData(Uri.parse("market://details?id=" + info.packageName));
            if(isInstallMarket == null) {
                List<ResolveInfo> list = activity.getPackageManager().queryIntentActivities(in, PackageManager.MATCH_DEFAULT_ONLY);
                isInstallMarket = list.size() > 0;
            }
            if(isInstallMarket) {
                activity.startActivity(in);
            }else{
                in.setData(Uri.parse(info.downloadUrl));
                activity.startActivity(in);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检测是否有网络
     *
     * @param context
     * @return
     */
    public static boolean checkNetWork(Context context) {
        int dd;
        if ((dd = checkNetWorkStatue(context)) != 0) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param context
     * @return 0:无网络 1:2G 3G 4G 2:wifi
     */
    public static int checkNetWorkStatue(Context context) {

        // 获取手机的连接服务管理器，这里是连接管理器类
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo.State wifiState=null;
        try {
            wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                    .getState();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        NetworkInfo.State mobileState=null;
        try {
            mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                    .getState();
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        if (wifiState != null && mobileState != null
                && NetworkInfo.State.CONNECTED != wifiState
                && NetworkInfo.State.CONNECTED == mobileState) {
            return 1;
        } else if (wifiState != null && mobileState != null
                && NetworkInfo.State.CONNECTED == wifiState
                && NetworkInfo.State.CONNECTED != mobileState) {
            return 2;
        } else if (wifiState != null && mobileState != null
                && NetworkInfo.State.CONNECTED != wifiState
                && NetworkInfo.State.CONNECTED != mobileState) {
            return 0;
        }
        return 0;
    }
}
