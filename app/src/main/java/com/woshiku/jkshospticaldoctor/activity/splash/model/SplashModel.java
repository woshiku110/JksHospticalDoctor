package com.woshiku.jkshospticaldoctor.activity.splash.model;

/**
 * Created by Administrator on 2017/4/7.
 */
public interface SplashModel {
    void initPage(SplashModelListener splashModelListener);
    void animStart(SplashModelListener splashModelListener);
    void animEnd(SplashModelListener splashModelListener);
    interface SplashModelListener{
        void onAnimViewShow();
        void onAnimStart();
        void onAnimEnd();
    }
}
