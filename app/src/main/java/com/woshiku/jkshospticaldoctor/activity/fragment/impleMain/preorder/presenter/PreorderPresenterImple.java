package com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.preorder.presenter;

import com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.preorder.model.PreorderModel;
import com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.preorder.model.PreorderModelImple;
import com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.preorder.view.PreorderView;

/**
 * Created by admin on 2017-04-20.
 */

public class PreorderPresenterImple implements PreorderPresenter,PreorderModel.PreorderModelListener{
    PreorderView preorderView;
    PreorderModel preorderModel;

    public PreorderPresenterImple(PreorderView preorderView) {
        this.preorderView = preorderView;
        preorderModel = new PreorderModelImple();
    }

    @Override
    public void initPage() {
        onInitPage();
    }

    @Override
    public void firstLoadingPage() {
        onLoadingPage(true);
    }

    @Override
    public void loadUndealData() {
        preorderModel.loadUndealData(this);
    }

    @Override
    public void loadDealedData() {
        preorderModel.loadDealedData(this);
    }

    /*以下是view视图层要实现的接口*/
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
