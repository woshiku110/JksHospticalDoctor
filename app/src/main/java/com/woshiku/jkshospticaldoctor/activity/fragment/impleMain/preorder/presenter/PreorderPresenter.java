package com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.preorder.presenter;

/**
 * Created by admin on 2017-04-20.
 */

public interface PreorderPresenter {
    void initPage();
    void firstLoadingPage();
    void loadUndealData();//加载待处理数据
    void loadDealedData();//加载已经处理数据
}
