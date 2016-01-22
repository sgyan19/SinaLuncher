package com.sina.sinaluncher.utils;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;

/**
 * Created by sinash94857 on 2016/1/21.
 */
public class StorageUtils {

    private static String SdcardDir;
    private static final String HomeDirName = "sina";
    private static final String ModelDirName = "sinaLuncher";
    private static final String DbDirName = "database";
    private static final String CacheDirName = "cache";
    private static final String PreferencesDirName = "sharePreferences";

    private static String sdcard_DatabaseDirPath;
    private static String sdcard_CacheDirPath;
    private static String sdcard_PreferencesDirPath;

    public static String getCacheDir(Context context){
        if(initSdcardDirs()){
            return sdcard_CacheDirPath;
        }
        return context.getCacheDir().getAbsolutePath();
    }
    public static String getDbDir(Context context){
        initSdcardDirs();
        return sdcard_DatabaseDirPath;
    }

    public static String getSharePreferencesDir(Context context){
        initSdcardDirs();
        return sdcard_PreferencesDirPath;
    }

    private static boolean initSdcardDirs(){
        if(!TextUtils.isEmpty(SdcardDir)) {
            File f = new File(SdcardDir);
            if (f.exists() && f.isDirectory()) {
                return true;
            }
        }
        File file1 = new File("/storage/sdcard0");
        File file2 = new File("/mnt/sdcard");
        if(file1.exists() && file1.isDirectory()){
            SdcardDir = file1.getAbsolutePath();
        }else if(file2.exists() && file2.isDirectory()){
            SdcardDir = file2.getAbsolutePath();
        }else{
            return false;
        }
        File homeFile = new File(SdcardDir,HomeDirName);
        File modelDir = new File(homeFile,ModelDirName);
        File dbDir = new File(modelDir,DbDirName);
        if(!dbDir.exists())
            dbDir.mkdirs();
        sdcard_DatabaseDirPath = dbDir.getAbsolutePath();
        File cacheDir = new File(modelDir,CacheDirName);
        if(!cacheDir.exists())
            cacheDir.mkdir();
        sdcard_CacheDirPath = cacheDir.getAbsolutePath();
        File preferencesDir = new File(modelDir,PreferencesDirName);
        if(!preferencesDir.exists())
            preferencesDir.mkdir();
        sdcard_PreferencesDirPath = preferencesDir.getAbsolutePath();
        return true;
    }
}
