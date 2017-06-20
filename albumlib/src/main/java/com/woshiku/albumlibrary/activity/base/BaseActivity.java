package com.woshiku.albumlibrary.activity.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import java.util.LinkedList;

/**
 * Created by Administrator on 2016/8/2.
 */
public abstract class BaseActivity extends AppCompatActivity {
    //图片的结果
    public static String actionAlbum = "com.woshiku.albumlibrary.activity.albumResult";
    //用于管理多个活动
    private final static LinkedList<BaseActivity> mActivities = new LinkedList<BaseActivity>();
    @Override
    public void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        synchronized (mActivities) {
            mActivities.add(this);
        }
        init();
        initView();
    }
    //调用该方法可以结束所有Activity
    public void killAllActivities(){
        LinkedList<BaseActivity> copies;
        synchronized (mActivities) {
            copies = mActivities;
            for(BaseActivity copy:copies){
                copy.finish();
            }
        }
        //杀掉所有服务
        android.os.Process.killProcess(android.os.Process.myPid());
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (mActivities) {
            mActivities.remove(this);
        }
    }
    protected void init(){

    }
    //初始化view
    protected abstract void initView();
    public void sendPicResult(Bundle bd){
        Intent intent = new Intent();
        intent.setAction(actionAlbum);
        bd.putString("type","albumResult");
        intent.putExtras(bd);
        sendBroadcast(intent);
    }
}
