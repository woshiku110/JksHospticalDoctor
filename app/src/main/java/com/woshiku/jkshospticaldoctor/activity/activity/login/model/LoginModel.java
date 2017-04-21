package com.woshiku.jkshospticaldoctor.activity.activity.login.model;

import com.woshiku.jkshospticaldoctor.activity.domain.LoginData;

/**
 * Created by admin on 2017-04-21.
 */

public interface LoginModel {
    void login(LoginModelListener loginModelListener);
    interface LoginModelListener{
        LoginData onGetUserInfo();
        void onToast(String msg);
        void onShowWait();
        void onHideWait();
    }
}
