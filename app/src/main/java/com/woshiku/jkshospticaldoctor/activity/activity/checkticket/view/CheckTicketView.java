package com.woshiku.jkshospticaldoctor.activity.activity.checkticket.view;

/**
 * Created by admin on 2017-05-05.
 */

public interface CheckTicketView {
    void setInitPage();
    void loadingPage(boolean isFirst);//等待中
    void loadOk(Object object);//加载完成
    void loadFail();//加载失败
    void loadNoData();//加载无数据
    void dialogOpen();//打开对话框
    void dialogClose();//关闭对话框
    void submitPageState(boolean isOk);
}
