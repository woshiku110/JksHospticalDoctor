package com.woshiku.jkshospticaldoctor.activity.activity.checkticket.model;

/**
 * Created by admin on 2017-05-05.
 */

public interface CheckTicketModel {
    void loadData(String yyid,CheckTicketModelListener checkTicketModelListener);
    interface CheckTicketModelListener{
        void onInitPage();
        void onLoadingPage(boolean isFirst);
        void onLoadFail();
        void onLoadNoData();
        void onLoadOk(Object object);
    }
}
