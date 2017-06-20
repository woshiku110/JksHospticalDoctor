package com.woshiku.jkshospticaldoctor.activity.activity.checkticket.presenter;

/**
 * Created by admin on 2017-05-05.
 */

public interface CheckTicketPresenter {
    void initPage();
    void firstLoadingPage();
    void loadData(String yyid);
    void submitData(String ids,String names);
}
