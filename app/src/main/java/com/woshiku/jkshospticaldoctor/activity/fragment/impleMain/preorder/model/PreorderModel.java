package com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.preorder.model;

/**
 * Created by admin on 2017-04-19.
 */

public interface PreorderModel {
    void loadUrlData();
    interface PreorderModelListener{//视图层接口
        void onInitPage();
        void onLoadingPage();
        void onLoadFail();
        void onLoadOk();
    }
}
