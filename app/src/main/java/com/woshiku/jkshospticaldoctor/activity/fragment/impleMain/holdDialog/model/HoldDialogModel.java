package com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.holdDialog.model;

import android.app.Activity;

import com.woshiku.jkshospticaldoctor.activity.domain.HoldDialogData;

/**
 * Created by admin on 2017-04-19.
 */

public interface HoldDialogModel {
    void loadData(PreorderModelListener preorderModelListener);
    void doctorTreatMent(HoldDialogData holdDialogData, PreorderModelListener preorderModelListener);
    interface PreorderModelListener{//视图层接口
        void onInitPage();
        void onLoadingPage(boolean isFirst);
        void onLoadFail(boolean isUndeal);
        void onLoadNoData(boolean isUndeal);
        void onLoadOk(boolean isUndeal, Object object);
        void onDoctorTreat(boolean isSuccess,HoldDialogData holdDialogData);
        Activity onActivity();//拿到活动语境
    }
}
