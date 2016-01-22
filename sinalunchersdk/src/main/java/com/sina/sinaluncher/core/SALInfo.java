package com.sina.sinaluncher.core;

import android.content.Intent;
import android.graphics.drawable.Drawable;

/**
 * Created by sinash94857 on 2016/1/7.
 */
public class SALInfo {
    public static final int ENTRY_STATUS_HIDE = 1;
    public static final int ENTRY_STATUS_SHOW = 2;
    public static final int ENTRY_STATUS_USE = 3;

    public int appId = 0;
    public String packageName = "";
    public String appName = "";
    public int entryStatus = ENTRY_STATUS_HIDE;
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
