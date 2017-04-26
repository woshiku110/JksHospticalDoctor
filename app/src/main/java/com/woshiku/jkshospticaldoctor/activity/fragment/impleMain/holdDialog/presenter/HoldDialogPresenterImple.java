package com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.holdDialog.presenter;

import com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.holdDialog.model.HoldDialogModel;
import com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.holdDialog.model.HoldDialogModelImple;
import com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.holdDialog.view.HoldDialogView;

/**
 * Created by admin on 2017-04-26.
 */

public class HoldDialogPresenterImple implements HoldDialogPresenter,HoldDialogModel.PreorderModelListener{
    HoldDialogModel holdDialogModel;
    HoldDialogView preorderView;

    public HoldDialogPresenterImple(HoldDialogView preorderView) {
        this.preorderView = preorderView;
        holdDialogModel = new HoldDialogModelImple();
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
    public void loadData() {
        holdDialogModel.loadData(this);
    }
    /**
     * @desc 以下是视图类接口
     * */
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
