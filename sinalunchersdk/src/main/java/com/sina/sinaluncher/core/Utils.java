package com.sina.sinaluncher.core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import com.sina.sinaluncher.R;

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


    public static List<SALInfo> converModel(SALModel model){
        List<SALInfo> result = new ArrayList<SALInfo>();
        int index = 0;
        for(SALModel.Struct item : model.appList){
            SALInfo i = new SALInfo();
            i.packageName = item.packageName;
            i.appName = item.appName;
            i.iconUrl = item.appIcon;
            i.unInstallIconUrl = item.unInstallIcon;
            i.downloadUrl = item.download;
            i.appId = index ++;
            result.add(i);
        }
        return result;
    }

    // 通过包名检测系统中是否安装某个应用程序
    public static boolean getAppsInfo(Context context, String packageName) {
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
    public static void getAppsInfo(Context context, List<SALInfo> sALInfos) {
        PackageManager manager = context.getPackageManager();
        String thisAppPackageName = context.getPackageName();
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
            }
        }
    }

    // 通过包名检测系统中是否安装某个应用程序
    public static boolean[] getAppsInfo(Context context, String[] packageNames) {
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

    public static void jumpMarket(Activity activity,SALInfo info){
        try {
            //Uri uri = Uri.parse("https://market.android.com/details?id="+ info.packageName);
            Intent in = new Intent(Intent.ACTION_VIEW);
            in.setData(Uri.parse("market://details?id=" + info.packageName));
            
            activity.startActivity(in);
        } catch (Exception e) {
        }
    }
}
