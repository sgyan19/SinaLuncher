package com.sina.sinaluncher.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.sina.sinaluncher.core.SALInfo;
import com.sina.sinaluncher.utils.StorageUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sinash94857 on 2016/1/20.
 */
public class SALDao {
    private final static String dbFileName = "SinaLuncher.db";
    private final static String appTabelName = "app";
//    private final static String entryTabelName = "entry";
    protected  SQLiteDatabase db = null;

    public static boolean checkCacheExists(Context context){
        String dirPath = StorageUtils.getDbDir(context);
        if(dirPath != null){
            return new File(dirPath,dbFileName).exists();
        }
        return context.getDatabasePath(dbFileName).exists();
    }
    public SALDao(Context context){
        String dirPath = StorageUtils.getDbDir(context);
        File dbFile ;
        if(dirPath != null){
            dbFile = new File(dirPath,dbFileName);
        }else{
            dbFile = context.getDatabasePath(dbFileName);
        }
        db = SQLiteDatabase.openOrCreateDatabase(dbFile,null);
        createTable(db, true);
    }

    public static void createTable(SQLiteDatabase db, boolean ifNotExists){
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint
                + "'app' ("
                + "'appId' INTEGER PRIMARY KEY,"
                + "'packageName' TEXT,"
                + "'appName' TEXT,"
                + "'inList' TEXT,"
                + "'iconUrl' TEXT,"
                + "'entryStatus' INTEGER,"
                + "'downloadUrl' TEXT,"
                + ");");
//        db.execSQL("CREATE TABLE " + constraint
//                + "'entry' ("
//                + "'packageName' TEXT PRIMARY KEY,"
//                + "'entryStatus' INTEGER,"
//                + ");");
    }

    public List<SALInfo> read(){
        List<SALInfo> result = new ArrayList<SALInfo>();
        Cursor cursor ;
        cursor = db.rawQuery("SELECT * FROM " + appTabelName, null);
        while(cursor.moveToNext()){
            SALInfo item = new SALInfo();
            item.appId = cursor.getInt(cursor.getColumnIndex("appId"));
            item.packageName = cursor.getString(cursor.getColumnIndex("packageName"));
            item.appName = cursor.getString(cursor.getColumnIndex("appName"));
            item.iconUrl = cursor.getString(cursor.getColumnIndex("iconUrl"));
            item.inList = cursor.getString(cursor.getColumnIndex("inList"));
            item.downloadUrl = cursor.getString(cursor.getColumnIndex("downloadUrl"));
            item.entryStatus = cursor.getInt(cursor.getColumnIndex("entryStatus"));
            result.add(item);
        }
        return result;
    }

    public void clean(){
        db.execSQL("truncate table " + appTabelName);
    }

    public void write(List<SALInfo> data){
        db.beginTransaction();
        ContentValues cv = new ContentValues();
        for(SALInfo item : data){
            cv.clear();
            cv.put("appId", item.appId);
            cv.put("packageName",item.packageName);
            cv.put("appName",item.appName);
            cv.put("iconUrl",item.iconUrl);
            cv.put("inList",item.inList);
            cv.put("downlaodUrl",item.downloadUrl);
            cv.put("entryStatus",item.entryStatus);
            db.insert(appTabelName,null,cv);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

//    public int read(String packageName){
//        List<SALInfo> result = new ArrayList<SALInfo>();
//        Cursor cursor ;
//        cursor = db.rawQuery("SELECT '" + packageName + "' FROM " + entryTabelName, null);
//        int entry = 0;
//        if(cursor.moveToNext()){
//            entry = cursor.getInt(cursor.getColumnIndex("entryStatus"));
//        }
//        return entry;
//    }
//
//    public void write(String packageName,int entryStatus){
//        ContentValues cv = new ContentValues();
//        cv.put("packageName",packageName);
//        cv.put("entryStatus",entryStatus);
//        db.insert(entryTabelName,null,cv);
//    }
}
