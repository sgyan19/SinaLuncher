package com.sina.sinaluncher.core;

import android.content.Intent;
import android.graphics.drawable.Drawable;

/**
 * Created by sinash94857 on 2016/1/7.
 */
public class SALInfo {
    public int appId = 0;
    public String packageName = "";
    public String appName = "";
    public int entryStatus = 1;
    public String iconUrl;
    public String inList = "yse";
    public String downloadUrl = "";

    public boolean isInstall = false;
    public boolean self = false;
    public Intent intent = null;
    public Drawable appIconDrawable = null;
    public Drawable inInstallIconDrawable = null;

    // 废弃
    public int appIcon = 0;
}
