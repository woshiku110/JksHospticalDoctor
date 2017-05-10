package com.woshiku.jkshospticaldoctor.activity.base;

import android.app.Application;

import com.woshiku.jkshospticaldoctor.activity.utils.AppManager;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;

import org.xutils.x;

/**
 * Created by admin on 2017-04-18.
 */

public class BaseApplication extends Application {
    static Application application;
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        application = this;
    }
    public static Application getApplication(){
        return application;
    }
}
