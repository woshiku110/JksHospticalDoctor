package com.woshiku.jkshospticaldoctor.activity.activity.splash.presenter;

import com.woshiku.jkshospticaldoctor.activity.activity.splash.model.SplashModel;
import com.woshiku.jkshospticaldoctor.activity.activity.splash.model.SplashModelImple;
import com.woshiku.jkshospticaldoctor.activity.activity.splash.view.SplashView;

/**
 * Created by Administrator on 2017/4/7.
 */
public class SplashPresenterImple implements SplashPresenter,SplashModel.SplashModelListener {
    SplashView splashView;
    SplashModel splashModel;

    public SplashPresenterImple(SplashView splashView) {
        this.splashView = splashView;
        splashModel = new SplashModelImple();
    }
    /**
     * @desc 用于初始化页面
     * */
    @Override
    public void initPage() {
        splashModel.initPage(this);
    }

    /**
     * @desc 动画开始运行
     * */
    @Override
    public void animStart() {
        splashModel.animStart(this);
    }

    /**
     * @desc 动画结束运行
     * */
    @Override
    public void animEnd() {
        splashModel.animEnd(this);
    }

    /**
     * @desc 用于动画显示
     * */
    @Override
    public void onAnimViewShow() {
        if(splashView != null){
            splashView.setAnimViewShow();
        }

    }

    /**
     * @desc 动画开始
     * */
    @Override
    public void onAnimStart() {
        if(splashView != null){
            splashView.setAnimStart();
        }
    }
    /**
     * @desc 动画结束
     * */
    @Override
    public void onAnimEnd() {
        if(splashView != null){
            splashView.setAnimEnd();
        }
    }
}
