package com.woshiku.jkshospticaldoctor.activity.activity.login.presenter;

import android.app.Activity;
import com.woshiku.jkshospticaldoctor.activity.activity.login.model.LoginModel;
import com.woshiku.jkshospticaldoctor.activity.activity.login.model.LoginModelImple;
import com.woshiku.jkshospticaldoctor.activity.activity.login.view.LoginView;
import com.woshiku.jkshospticaldoctor.activity.domain.LoginData;

/**
 * Created by admin on 2017-04-21.
 */

public class LoginPresenterImple implements LoginPresenter,LoginModel.LoginModelListener{
    LoginView loginView;
    LoginModel loginModel;
    public LoginPresenterImple(LoginView loginView) {
        this.loginView = loginView;
        loginModel = new LoginModelImple();
    }

    @Override
    public void initPage() {

    }

    @Override
    public void login() {
        loginModel.login(this);
    }

    /*下面是视图类接口*/
    @Override
    public LoginData onGetUserInfo() {
        if(loginView != null){
            return loginView.getUserInfo();
        }
        return null;
    }

    @Override
    public void onToast(String msg) {
        if(loginView != null){
            loginView.toast(msg);
        }
    }

    @Override
    public void onShowWait() {
        if(loginView != null){
            loginView.showWait();
        }
    }

    @Override
    public void onHideWait() {
        if(loginView != null){
            loginView.hideWait();
        }
    }

    @Override
    public Activity onGetActivity() {
        if(loginView != null){
            return loginView.getActivity();
        }
        return null;
    }
}
