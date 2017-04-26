package com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.preorder.view;

/**
 * Created by admin on 2017-04-18.
 */

public interface PreorderView {
    void setInitPage();//初始化页面
    void loadingPage(boolean isFirst);//等待中
    void loadOk(boolean isUndeal,Object object);//加载完成
    void loadFail(boolean isUndeal);//加载失败
    void loadNoData(boolean isUndeal);//加载无数据
}
