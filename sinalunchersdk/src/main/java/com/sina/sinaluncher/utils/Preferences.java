package com.sina.sinaluncher.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sinash94857 on 2016/1/20.
 */
public class Preferences {
    private static final String FILE_NAME = "SinaLuncher";
    private static final String KAY_ITEM_HEIGHT = "ItemHeight";

    public static int getCacheItemHeight(Context context){

        SharedPreferences preferences = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);

        return preferences.getInt(KAY_ITEM_HEIGHT,-1);
   }

    public static void saveItemHeight(Context context, int height){
        SharedPreferences preferences = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        preferences.edit().putInt(KAY_ITEM_HEIGHT,height).commit();
    }
}
