package com.woshiku.jkshospticaldoctor.activity.activity.splash.model;

import android.text.TextUtils;

import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.jkshospticaldoctor.activity.utils.ThreadManage;
import com.woshiku.urllibrary.domain.CommonUrlData;
import com.woshiku.urllibrary.url.base.CommonUrl;

import common.Global;
import param.QueryDateParam;

/**
 * Created by Administrator on 2017/4/7.
 */
public class SplashModelImple implements SplashModel{
    CommonUrl commonUrl;
    public SplashModelImple() {
        commonUrl = new CommonUrl();
    }

    /**
     * @param splashModelListener 用于调用view的视图
     * @desc 用于初始化闪屏界面
     * */
    @Override
    public void initPage(SplashModelListener splashModelListener) {
        splashModelListener.onAnimViewShow();
    }


    @Override
    public void animStart(SplashModelListener splashModelListener) {
        splashModelListener.onAnimStart();
    }

    @Override
    public void animEnd(SplashModelListener splashModelListener) {
        LogUtil.print("anim end");
        splashModelListener.onAnimEnd();
    }
}
