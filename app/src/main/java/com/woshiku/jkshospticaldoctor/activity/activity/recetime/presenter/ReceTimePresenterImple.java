package com.woshiku.jkshospticaldoctor.activity.activity.recetime.presenter;

import android.app.Activity;

import com.woshiku.jkshospticaldoctor.activity.activity.recetime.model.ReceTimeModel;
import com.woshiku.jkshospticaldoctor.activity.activity.recetime.model.ReceTimeModelImple;
import com.woshiku.jkshospticaldoctor.activity.activity.recetime.view.ReceTimeView;

/**
 * Created by admin on 2017-05-15.
 */

public class ReceTimePresenterImple implements ReceTimePresenter,ReceTimeModel.ReceTimeModelListener{
    ReceTimeView receTimeView;
    ReceTimeModel receTimeModel;

    public ReceTimePresenterImple(ReceTimeView receTimeView) {
        this.receTimeView = receTimeView;
        receTimeModel = new ReceTimeModelImple();
    }

    @Override
    public void initPage() {
        receTimeModel.initPage(this);
    }

    @Override
    public void updateDate(String date) {
        receTimeModel.userChooseDate(date,this);
    }

    @Override
    public Activity onGetActivity() {
        if(receTimeView != null){
            return receTimeView.getActivity();
        }
        return null;
    }

    @Override
    public void onInitPage() {
        if(receTimeView != null){
            receTimeView.onInitPage();
        }
    }

    @Override
    public void updateState(boolean isOk, Object object) {
        if(receTimeView != null){
            receTimeView.updateDateResult(isOk,object);
        }
    }
}
