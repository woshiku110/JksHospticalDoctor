package com.woshiku.urllibrary.util;

import android.util.Log;

/**
 * Created by Administrator on 2016/11/2.
 */
public class BugLog{
    boolean isDebug = true;
    public static void print(String str){
        Log.e("lookat",str);
    }
    public static void print(String tag,String str){
        Log.e(tag,str);
    }
    public static void print(String tag,String str,Exception e){
        Log.e(tag,str,e);
    }
}
