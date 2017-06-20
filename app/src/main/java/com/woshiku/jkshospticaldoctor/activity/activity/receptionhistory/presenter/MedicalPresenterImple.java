package com.woshiku.jkshospticaldoctor.activity.activity.receptionhistory.presenter;

import com.woshiku.jkshospticaldoctor.activity.activity.receptionhistory.model.MedicalSearchModel;
import com.woshiku.jkshospticaldoctor.activity.activity.receptionhistory.model.MedicalSearchModelImple;
import com.woshiku.jkshospticaldoctor.activity.activity.receptionhistory.view.MedicalSearchView;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;

/**
 * Created by admin on 2017-04-28.
 */

public class MedicalPresenterImple implements MedicalPresenter,MedicalSearchModel.PreorderModelListener{
    MedicalSearchView preorderView;
    MedicalSearchModel medicalSearchModel;

    public MedicalPresenterImple(MedicalSearchView preorderView) {
        this.preorderView = preorderView;
        this.medicalSearchModel = new MedicalSearchModelImple();
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
    public void loadData(String msg) {
        medicalSearchModel.loadData(msg,this);
    }
    /*以下是要实现的视图类*/
    @Override
    public void onInitPage() {
        LogUtil.print("reception onPage");
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
    public void onLoadFail() {
        if(preorderView != null){
            preorderView.loadFail();
        }
    }

    @Override
    public void onLoadNoData() {
        if(preorderView != null){
            preorderView.loadNoData();
        }
    }

    @Override
    public void onLoadOk(Object object) {
        if(preorderView != null){
            preorderView.loadOk(object);
        }
    }
}
