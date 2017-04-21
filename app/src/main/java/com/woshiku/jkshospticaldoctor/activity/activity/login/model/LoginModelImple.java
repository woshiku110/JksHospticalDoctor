package com.woshiku.jkshospticaldoctor.activity.activity.login.model;

import android.text.TextUtils;
import com.woshiku.jkshospticaldoctor.activity.domain.LoginData;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.jkshospticaldoctor.activity.utils.ThreadManage;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.url.base.CommonUrl;
import param.LoginParam;

/**
 * Created by admin on 2017-04-21.
 */

public class LoginModelImple implements LoginModel{
    CommonUrl commonUrl;

    public LoginModelImple() {
        commonUrl = new CommonUrl();
    }

    @Override
    public void login(final LoginModelListener loginModelListener) {
        loginModelListener.onShowWait();
        LoginData loginData = loginModelListener.onGetUserInfo();
        LogUtil.print(loginData.toString());
        final String name = loginData.getUserTicket();
        final String pass = loginData.getUserPass();
        if(TextUtils.isEmpty(name)){
            loginModelListener.onToast("账号输入不能为空");
            return;
        }
        if(TextUtils.isEmpty(pass)){
            loginModelListener.onToast("密码输入不能为空");
            return;
        }
        ThreadManage.getInstance().carry(new Runnable() {
            @Override
            public void run() {
                //打开对话框
                loginModelListener.onShowWait();
                Result result = commonUrl.loadUrlAsc(LoginParam.login(name,pass));
                LogUtil.print(result.toString());
                //关闭对话框
                loginModelListener.onHideWait();
            }
        });
    }
}
