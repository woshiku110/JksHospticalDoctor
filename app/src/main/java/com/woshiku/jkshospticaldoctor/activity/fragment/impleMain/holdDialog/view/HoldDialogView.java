package com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.holdDialog.view;

import android.app.Activity;

import com.woshiku.jkshospticaldoctor.activity.domain.HoldDialogData;

/**
 * Created by admin on 2017-04-25.
 */

public interface HoldDialogView {
    void setInitPage();//初始化页面
    void loadingPage(boolean isFirst);//等待中
    void loadOk(boolean isUndeal,Object object);//加载完成
    void loadFail(boolean isUndeal);//加载失败
    void loadNoData(boolean isUndeal);//加载无数据
    void doctorTreat(boolean isSucc,HoldDialogData holdDialogData);//医生叫号是否成功
    Activity onGetActivity();
}
