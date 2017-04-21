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
        //默认加载初如化页面
        loadUndealData();
    }

    @Override
    public void loadUndealData() {
        preorderModel.loadUndealData(this);
    }

    /*以下是view视图层要实现的接口*/
    @Override
    public void onInitPage() {
        if(preorderView != null){
            preorderView.setInitPage();
        }
    }

    @Override
    public void onLoadingPage() {
        if(preorderView != null){
            preorderView.loadingPage();
        }
    }

    @Override
    public void onLoadFail() {
        if(preorderView != null){
            preorderView.loadFail();
        }
    }

    @Override
    public void onLoadOk() {
        if(preorderView != null){
            preorderView.loadOk();
        }
    }
}
