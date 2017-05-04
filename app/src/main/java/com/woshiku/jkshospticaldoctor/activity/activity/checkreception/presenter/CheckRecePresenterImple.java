package com.woshiku.jkshospticaldoctor.activity.activity.checkreception.presenter;

import com.woshiku.jkshospticaldoctor.activity.activity.checkreception.model.CheckReceModel;
import com.woshiku.jkshospticaldoctor.activity.activity.checkreception.model.CheckReceModelImple;
import com.woshiku.jkshospticaldoctor.activity.activity.checkreception.view.CheckReceView;

/**
 * Created by admin on 2017-05-04.
 */

public class CheckRecePresenterImple implements CheckRecePresenter,CheckReceModel.CheckReceModelListener {
    CheckReceModel checkReceModel;
    CheckReceView preorderView;

    public CheckRecePresenterImple(CheckReceView checkReceView) {
        this.preorderView = checkReceView;
        checkReceModel = new CheckReceModelImple();
    }

    @Override
    public void loadData() {
        checkReceModel.loadData(this);
    }

    @Override
    public void initPage() {
        onInitPage();
    }

    @Override
    public void firstLoadingPage() {
        onLoadingPage(true);
    }

    /*以下要实现视图类*/
    @Override
    public void onInitPage() {
        if(preorderView != null){
            preorderView.setInitPage();
        }
    }

    @Override
    public void onLoadingPage(boolean isFirst) {
        if(preorderView != null){
            preorderView.loadingPage(isFirst);
        }
    }

    @Override
    public void onLoadFail(boolean isUndeal) {
        if(preorderView != null){
            preorderView.loadFail(isUndeal);
        }
    }

    @Override
    public void onLoadNoData(boolean isUndeal) {
        if(preorderView != null){
            preorderView.loadNoData(isUndeal);
        }
    }

    @Override
    public void onLoadOk(boolean isUndeal, Object object) {
        if(preorderView != null){
            preorderView.loadOk(isUndeal,object);
        }
    }
}
