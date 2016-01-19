package com.sina.sinaluncherdemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sina.sinaluncher.SALEntryView;
import com.sina.sinaluncher.core.GsonUtils;
import com.sina.sinaluncher.core.SALInfo;
import com.sina.sinaluncher.core.SALModel;
import com.sina.sinaluncher.network.ServerSimpleRequest;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.sina.sinaluncherdemo.R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String json = "{\"appList\":[{\"appName\": \"新浪视频\",\"packageName\": \"com.sina.sinavideo\",\"appIcon\": \"http://XXX\",\"unInstallIcon\": \"http://XXX\"},{\"appName\": \"新浪体育\",\"packageName\": \"cn.com.sina.sports\",\"appIcon\": \"http://XXX\",\"unInstallIcon\": \"http://XXX\"},{\"appName\": \"新浪微博\",\"packageName\": \"com.sina.weibo\",\"appIcon\": \"http://XXX\",\"unInstallIcon\": \"http://XXX\"}]}";
                SALModel model = GsonUtils.fromJson(json,SALModel.class);


//                new AsyncTask<Void,Void,String>(){
//                    @Override
//                    protected String doInBackground(Void... params) {
//                        HashMap<String,String> header = new HashMap<String, String>();
//                        header.put("cookie","thisiscookie");
//                        header.put("referer","http://sina.com");
//                        try {
//                            final String str = ServerSimpleRequest.get("http://www.baidu.com",header);
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    ((TextView)findViewById(R.id.hello_world)).setText(str);
//                                }
//                            });
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//                        return null;
//                    }
//                }.execute();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.sina.sinaluncherdemo.R.menu.main_menu, menu);
        MenuItem item = menu.findItem(com.sina.sinaluncherdemo.R.id.SAL_item);
        item.setActionView(new SALEntryView(this));
        return true;
    }
}
