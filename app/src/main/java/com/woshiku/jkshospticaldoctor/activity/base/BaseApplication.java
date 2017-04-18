package com.woshiku.jkshospticaldoctor.activity.base;

import android.app.Application;

import org.xutils.x;

/**
 * Created by admin on 2017-04-18.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
