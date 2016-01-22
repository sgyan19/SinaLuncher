package com.sina.sinaluncherdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.sina.sinaluncher.SALEntryView;
import com.sina.sinaluncher.core.SALInfo;
import com.sina.sinaluncher.utils.GsonUtils;
import com.sina.sinaluncher.core.SALModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.sina.sinaluncherdemo.R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
