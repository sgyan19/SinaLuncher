package com.sina.sinaluncher.network;

import com.google.gson.reflect.TypeToken;
import com.sina.sinaluncher.core.SALInfo;
import com.sina.sinaluncher.core.SALModel;
import com.sina.sinaluncher.utils.GsonUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by sinash94857 on 2016/1/13.
 */
public class ServerCenter {
    public static final String url = "http://sinaapp.sina.cn/api/androidswitch.d.html";
    public static List<SALModel> getServerData() throws IOException{
        String json = ServerSimpleRequest.get(url);
        List<SALModel> models =GsonUtils.fromJson(json, new TypeToken<List<SALModel>>() {
            }.getType());
        return models;
    }
}
