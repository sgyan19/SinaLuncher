package com.sina.sinaluncher;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.sina.sinaluncher.core.SALInfo;
import com.sina.sinaluncher.core.SALModel;
import com.sina.sinaluncher.db.SALDao;
import com.sina.sinaluncher.network.ServerCenter;
import com.sina.sinaluncher.network.ServerConfig;
import com.sina.sinaluncher.network.ServerSimpleRequest;
import com.sina.sinaluncher.utils.Utils;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by sinash94857 on 2016/1/20.
 */
public class Global {

    public static final int INIT_STATUS_UNREADY = 0;
    public static final int INIT_STATUS_LOCAL = 1;
    public static final int INIT_STATUS_NETWORK = 2;

    private  List<SALInfo> appData; // 内核数据
    private  SALInfo self;      // 入口状态
    private  Runnable notify;
    private  Handler handler;
    public  int InitStatus  = INIT_STATUS_UNREADY;
    private  WeakReference<Activity> activityWeakReference;
    private static Global instance = new Global();



    public static Global getInstance(){
        return instance;
    }

    public  void init(final Activity activity,Runnable callback,Handler handler){
        notify = callback;
        this.handler = handler;
        activityWeakReference = new WeakReference<Activity>(activity);
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                if(SALDao.checkCacheExists(activity)){
                    SALDao dao = new SALDao(activity);
                    appData = dao.read();
                    self = Utils.improveAppsInfo(activity, appData);
                    initIconDrawable(activity,appData);
                    InitStatus = INIT_STATUS_LOCAL;
                    Global.this.handler.post(notify);
                }
                if(Utils.checkNetWork(activity)){
                    IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
                    activity.registerReceiver(new NetWorkStatusReceiver(activity),intentFilter);
                }else{
                    initNetworkData();
                }
                return null;
            }
        }.execute();
    }

    public int getEntryStatus(){
        return self.entryStatus;
    }

    public List<SALInfo> getAppList(){
        return appData;
    }

    private void initIconDrawable(Context context,List<SALInfo> data){
        try {
            for (SALInfo item : data) {
                item.appIconDrawable = ServerSimpleRequest.getDrawable(context, item.downloadUrl);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private  void initNetworkData(){
        try {
            List<SALModel> model = ServerCenter.getServerData();
            appData = Utils.converModel(model);
            InitStatus = INIT_STATUS_NETWORK;
            Activity activity = activityWeakReference.get();
            if(activity != null) {
                initIconDrawable(activity,appData);
                if(notify != null && handler != null){
                    handler.post(notify);
                }
                SALDao dao = new SALDao(activity);
                dao.clean();
                dao.write(appData);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private class NetWorkStatusReceiver extends BroadcastReceiver{
        public Activity activity;
        public NetWorkStatusReceiver(Activity activity){
            this.activity = activity;
        }
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//            NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo activeInfo = manager.getActiveNetworkInfo();
            if(activeInfo.isConnected()){
                initNetworkData();
                activity.unregisterReceiver(this);
            }
        }
    }
}
