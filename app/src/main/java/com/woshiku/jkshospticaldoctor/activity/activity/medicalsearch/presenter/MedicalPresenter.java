package com.woshiku.jkshospticaldoctor.activity.activity.medicalsearch.presenter;

/**
 * Created by admin on 2017-04-28.
 */

public interface MedicalPresenter {
    void initPage();
    void firstLoadingPage();
    void loadData(String msg);//加载待处理数据
}
