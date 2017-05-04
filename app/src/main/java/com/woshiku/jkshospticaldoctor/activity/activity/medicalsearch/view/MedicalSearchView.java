package com.woshiku.jkshospticaldoctor.activity.activity.medicalsearch.view;

/**
 * Created by admin on 2017-04-28.
 */

public interface MedicalSearchView {
    void setInitPage();
    void loadingPage(boolean isFirst);//等待中
    void loadOk(Object object);//加载完成
    void loadFail();//加载失败
    void loadNoData();//加载无数据
}
