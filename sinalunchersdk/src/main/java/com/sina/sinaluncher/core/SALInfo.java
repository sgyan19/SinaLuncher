package com.sina.sinaluncher.core;

import android.content.Intent;
import android.graphics.drawable.Drawable;

/**
 * Created by sinash94857 on 2016/1/7.
 */
public class SALInfo {
    public String packageName = "";
    public boolean isInstall = false;
    public int appIcon = 0;
    public Drawable appIconDrawable = null;
    public String iconUrl;
    public String unInstallIconUrl;
    public String appName = "";
    public int appId = 0;
    public Intent intent = null;
    public boolean self = false;
    public String downloadUrl = "";
}
