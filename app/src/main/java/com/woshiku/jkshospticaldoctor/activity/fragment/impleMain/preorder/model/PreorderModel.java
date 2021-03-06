package com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.preorder.model;

/**
 * Created by admin on 2017-04-19.
 */

public interface PreorderModel {
    void loadUndealData(PreorderModelListener preorderModelListener);
    void loadDealedData(PreorderModelListener preorderModelListener);
    interface PreorderModelListener{//视图层接口
        void onInitPage();
        void onLoadingPage(boolean isFirst);
        void onLoadFail(boolean isUndeal);
        void onLoadNoData(boolean isUndeal);
        void onLoadOk(boolean isUndeal,Object object);
    }
}
