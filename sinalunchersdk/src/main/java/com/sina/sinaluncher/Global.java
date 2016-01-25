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
import android.util.Log;

import com.sina.sinaluncher.core.SALInfo;
import com.sina.sinaluncher.core.SALModel;
import com.sina.sinaluncher.db.SALDao;
import com.sina.sinaluncher.network.ServerCenter;
import com.sina.sinaluncher.network.ServerConfig;
import com.sina.sinaluncher.network.ServerSimpleRequest;
import com.sina.sinaluncher.utils.Utils;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sinash94857 on 2016/1/20.
 */
public class Global {
    private static final String TAG = "sinaluncher.global";

    private static final boolean SortNoInstalled = true;

    public static final int INIT_STATUS_UNREADY = 0;
    public static final int INIT_STATUS_LOCAL = 1;
    public static final int INIT_STATUS_NETWORK = 2;

    private  List<SALInfo> appDataAll; // 内核数据
    private  SALInfo self;      // 入口状态
    private  Runnable notify;
    private  Handler handler;
    public  int InitStatus  = INIT_STATUS_UNREADY;
    private  WeakReference<Activity> activityWeakReference;
    private static Global instance = new Global();

    public static Global getInstance(){
        return instance;
    }

    /**
     * 先尝试读取本地配置，再查看联网状态，请求后台配置
     * @param activity
     * @param callback
     * @param handler
     */
    public  void init(final Activity activity,Runnable callback,Handler handler){
        notify = callback;
        this.handler = handler;
        activityWeakReference = new WeakReference<Activity>(activity);
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                if(SALDao.checkCacheExists(activity)){
                    Log.d(TAG,"local cache is exist! now read it.");
                    SALDao dao = new SALDao(activity);
                    appDataAll = dao.read();
                    self = Utils.improveAppsInfo(activity, appDataAll);
                    if(SortNoInstalled) appDataAll = Utils.sort(appDataAll);
                    initIconDrawable(activity,appDataAll);
                    InitStatus = INIT_STATUS_LOCAL;
                    Global.this.handler.post(notify);
                    dao.close();
                }
                if(Utils.checkNetWork(activity)){
                    Log.d(TAG,"network is on,start to connect server.");
                    initNetworkData();
                }else{
                    Log.d(TAG,"network is off,register listener.");
                    IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
                    activity.registerReceiver(new NetWorkStatusReceiver(activity),intentFilter);
                }
                return null;
            }
        }.execute();
    }

    public int getEntryStatus(){
        return self == null ? SALInfo.ENTRY_STATUS_HIDE : self.entryStatus;
    }

    public List<SALInfo> getAppList(){
        List<SALInfo> result = new ArrayList<SALInfo>();
        for(SALInfo item : appDataAll){
            if("yes".equals(item.inList)){
                result.add(item);
            }
        }
        return result;
    }

    private void initIconDrawable(Context context,List<SALInfo> data){
        try {
            for (SALInfo item : data) {
                item.appIconDrawable = ServerSimpleRequest.getDrawable(context, item.iconUrl);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private  void initNetworkData(){
        try {
            List<SALModel> model = ServerCenter.getServerData();
            appDataAll = Utils.converModel(model);
            Log.d(TAG,"Receive " + appDataAll.size() + " notes.");
            InitStatus = INIT_STATUS_NETWORK;
            Activity activity = activityWeakReference.get();
            if(activity != null) {
                self = Utils.improveAppsInfo(activity, appDataAll);
                if(SortNoInstalled) appDataAll = Utils.sort(appDataAll);
                initIconDrawable(activity,appDataAll);
                if(notify != null && handler != null){
                    handler.post(notify);
                }
                SALDao dao = new SALDao(activity);
                dao.clean();
                dao.write(appDataAll);
                dao.close();
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

            Log.d(TAG,"onReceive network status change.");
            NetworkInfo activeInfo = manager.getActiveNetworkInfo();
            if(activeInfo != null &&activeInfo.isAvailable()){
                Log.d(TAG,"onReceive network is now on");
                new AsyncTask<Void,Void,Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        initNetworkData();
                        return null;
                    }
                }.execute();
                activity.unregisterReceiver(this);
            }else{
                Log.d(TAG,"onReceive network is still off.");
            }
        }
    }
}
