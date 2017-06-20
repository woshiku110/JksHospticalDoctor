package com.woshiku.albumlibrary.base;

import android.app.Application;
import android.util.Log;

import com.woshiku.albumlibrary.imageloader.UniversalAndroidImageLoader;

/**
 * Created by Administrator on 2016/8/11.
 */
public class AlbumApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        /*UniversalAndroidImageLoader.init(getApplicationContext());*/
        Log.e("lookat","init album");
    }
}
