package com.woshiku.jkshospticaldoctor.activity.activity.receptionhistory.model;

/**
 * Created by admin on 2017-04-28.
 */

public interface MedicalSearchModel {
    void loadData(String msg, PreorderModelListener preorderModelListener);

    interface PreorderModelListener{//视图层接口
        void onInitPage();
        void onLoadingPage(boolean isFirst);
        void onLoadFail();
        void onLoadNoData();
        void onLoadOk(Object object);
    }
}
