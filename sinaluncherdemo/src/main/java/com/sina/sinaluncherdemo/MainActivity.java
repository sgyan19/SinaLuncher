package com.sina.sinaluncherdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.sina.sinaluncherdemo.R.menu.main_menu, menu);
        MenuItem item = menu.findItem(com.sina.sinaluncherdemo.R.id.SAL_item);
        View v = new SALEntryView(this);
        v.setPadding(0,0,40,0);
        item.setActionView(v);
        return true;
    }
}
