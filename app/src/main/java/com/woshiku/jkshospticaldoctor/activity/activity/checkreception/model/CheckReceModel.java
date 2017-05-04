package com.woshiku.jkshospticaldoctor.activity.activity.checkreception.model;

/**
 * Created by admin on 2017-05-04.
 */

public interface CheckReceModel {
    void loadData(CheckReceModelListener checkReceModelListener);
    interface CheckReceModelListener{//视图层接口
        void onInitPage();
        void onLoadingPage(boolean isFirst);
        void onLoadFail(boolean isUndeal);
        void onLoadNoData(boolean isUndeal);
        void onLoadOk(boolean isUndeal,Object object);
    }
}
