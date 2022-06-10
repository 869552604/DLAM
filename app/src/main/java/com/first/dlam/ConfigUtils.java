package com.first.dlam;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ConfigUtils {
    static final String SP_RX = "DLAM_SP"; //SP的key
    static final String SP_CODE="CODE_NAME";//保存的code

    public static String string3 = "3";
    public static String string4 = "4";
    public static String string6 = "6";

    public static int getUid(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            @SuppressLint("WrongConstant") ApplicationInfo ai = pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            return ai.uid;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;

    }

    public static String getTime() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatDay = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault());
        return formatDay.format(date);

    }
}
