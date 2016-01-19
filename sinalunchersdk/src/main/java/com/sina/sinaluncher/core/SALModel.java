package com.sina.sinaluncher.core;

import java.util.List;

/**
 * Created by sinash94857 on 2016/1/19.
 */
public class SALModel {
    public List<Struct> appList;
    public int entryStatus;
    public static class Struct {
        public String appName;
        public String packageName;
        public String appIcon;
        public String unInstallIcon;
        public String download;
    }
}
