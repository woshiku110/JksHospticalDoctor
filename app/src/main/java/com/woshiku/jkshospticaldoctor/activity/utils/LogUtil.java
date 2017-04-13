package com.woshiku.jkshospticaldoctor.activity.utils;

import android.util.Log;

/**
 * Created by admin on 2017-04-13.
 */

public class LogUtil {
    static boolean isPrint = true;//是不是要打印
    public static void print(String msg){
        if(isPrint){
            Log.e("lookat",msg);
        }
    }

    public static void print(String tag,String msg){
        if(isPrint){
            Log.e(tag,msg);
        }
    }

    public static void print(String tag,String msg,Exception e){
        if(isPrint){
            Log.e(tag,msg,e);
        }
    }
}
