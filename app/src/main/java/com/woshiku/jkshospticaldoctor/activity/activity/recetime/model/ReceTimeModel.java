package com.woshiku.jkshospticaldoctor.activity.activity.recetime.model;

import android.app.Activity;

/**
 * Created by admin on 2017-05-15.
 */

public interface ReceTimeModel {
    void initPage(ReceTimeModelListener receTimeModelListener);
    void userChooseDate(String date,ReceTimeModelListener receTimeModelListener);
    interface ReceTimeModelListener{
        Activity onGetActivity();
        void onInitPage();
        void updateState(boolean isOk,Object object);
    }
}
