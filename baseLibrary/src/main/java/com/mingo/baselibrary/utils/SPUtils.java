package com.mingo.baselibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2015/9/6.
 * <p/>
 */
public class SPUtils {
    private final static String SP_NAME = "liveSix";
    private static SharedPreferences sp;

    private static SharedPreferences getSp(Context context) {
        if (sp == null) {
            sp = context.getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        return sp;
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        SharedPreferences sp = getSp(context);
        return sp.getBoolean(key, defValue);
    }

    public static void setBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = getSp(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void setInt(Context context, String key, int value) {
        SharedPreferences sp = getSp(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getInt(Context context, String key, int defValue) {
        SharedPreferences sp = getSp(context);
        return sp.getInt(key, defValue);
    }

    public static void setString(Context context, String key, String value) {
        SharedPreferences sp = getSp(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(Context context, String key, String defauValue) {
        SharedPreferences sp = getSp(context);
        return sp.getString(key, defauValue);
    }

    public static void setLong(Context context, String key, long value) {
        SharedPreferences sp = getSp(context);
        sp.edit().putLong(key, value).apply();
    }

    public static long getLong(Context context, String key, long defaultValue) {
        return getSp(context).getLong(key, defaultValue);
    }


}
