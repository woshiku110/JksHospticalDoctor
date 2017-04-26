package com.woshiku.jkshospticaldoctor.activity.activity.login.view;

import android.app.Activity;

import com.woshiku.jkshospticaldoctor.activity.domain.LoginData;

/**
 * Created by admin on 2017-04-21.
 */

public interface LoginView {
    void setInitPage();
    void toast(String msg);
    void showWait();
    void hideWait();
    LoginData getUserInfo();
    Activity getActivity();
}
