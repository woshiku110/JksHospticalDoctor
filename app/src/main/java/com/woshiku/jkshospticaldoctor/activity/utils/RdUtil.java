package com.woshiku.jkshospticaldoctor.activity.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import com.woshiku.jkshospticaldoctor.activity.base.BaseApplication;

/**
 * Created by Administrator on 2016/4/26.
 */
public class RdUtil {
    //保存WIFI信息
    private static String id = "clientinfo";
    public static String theLastName ="thelast";
    private static Context context = BaseApplication.getApplication();
    private static String ip = "configIp";
    private static void writeShare(Context context,int mode,String name,String content){
        //Context.MODE_PRIVATE：为默认操作模式,代表该文件是私有数据,
        //只能被应用本身访问,在该模式下,写入的内容会覆盖原文件的内容
        //Context.MODE_APPEND：模式会检查文件是否存在,存在就往文件追加内容,否则就创建新文件.
        SharedPreferences mySharedPreferences ;
        switch(mode){
            case 0:mySharedPreferences= context.getSharedPreferences(id, Activity.MODE_PRIVATE);break;
            case 1:mySharedPreferences= context.getSharedPreferences(id, Activity.MODE_APPEND);break;
            default:mySharedPreferences= context.getSharedPreferences(id, Activity.MODE_PRIVATE);break;
        }
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString(name, content);
        editor.commit();
    }
    private static String readShare(Context context,int mode,String name){
        String content;
        SharedPreferences mySharedPreferences ;
        switch(mode){
            case 0:mySharedPreferences= context.getSharedPreferences(id, Activity.MODE_PRIVATE);break;
            case 1:mySharedPreferences= context.getSharedPreferences(id, Activity.MODE_APPEND);break;
            default:mySharedPreferences= context.getSharedPreferences(id, Activity.MODE_PRIVATE);break;
        }
        content=mySharedPreferences.getString(name, "");
        return content;
    }
    public static void saveData(String name,String content){
        writeShare(context,0,name,content);
        saveLast(name);
    }
    public static String readData(String name){
        return readShare(context,0,name);
    }
    public static String readLast(){
        return readShare(context,0,theLastName);
    }
    private static void saveLast(String content){
        writeShare(context,0,theLastName,content);
    }
    public static void saveIp(String content){
        writeShare(context,0,ip,content);
    }
    public static String readIp(){
        return readShare(context,0,ip);
    }
}
