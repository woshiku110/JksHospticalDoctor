package com.woshiku.jkshospticaldoctor.activity.activity.checkticket.presenter;

import com.woshiku.jkshospticaldoctor.activity.activity.checkticket.model.CheckTicketModel;
import com.woshiku.jkshospticaldoctor.activity.activity.checkticket.model.CheckTicketModelImple;
import com.woshiku.jkshospticaldoctor.activity.activity.checkticket.view.CheckTicketView;

/**
 * Created by admin on 2017-05-05.
 */

public class CheckTicketPresenterImple implements CheckTicketPresenter,CheckTicketModel.CheckTicketModelListener{
    CheckTicketView checkTicketView;
    CheckTicketModel checkTicketModel;
    public CheckTicketPresenterImple(CheckTicketView checkTicketView) {
        this.checkTicketView = checkTicketView;
        checkTicketModel = new CheckTicketModelImple();
    }

    @Override
    public void initPage() {
        onInitPage();
    }

    @Override
    public void firstLoadingPage() {
        onLoadingPage(true);
    }

    @Override
    public void loadData(String yyid) {
        checkTicketModel.loadData(yyid,this);
    }
    /*以下是视图接口*/
    @Override
    public void onInitPage() {
        if(checkTicketView != null){
            checkTicketView.setInitPage();
        }
    }

    @Override
    public void onLoadingPage(boolean isFirst) {
        if(checkTicketView != null){
            checkTicketView.loadingPage(isFirst);
        }
    }

    @Override
    public void onLoadFail() {
        if(checkTicketView != null){
            checkTicketView.loadFail();
        }
    }

    @Override
    public void onLoadNoData() {
        if(checkTicketView != null){
            checkTicketView.loadNoData();
        }
    }

    @Override
    public void onLoadOk(Object object) {
        if(checkTicketView != null){
            checkTicketView.loadOk(object);
        }
    }
}
