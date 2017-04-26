package com.woshiku.jkshospticaldoctor.activity.activity.login.model;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.woshiku.jkshospticaldoctor.activity.domain.LoginData;
import com.woshiku.jkshospticaldoctor.activity.domain.LoginReturnData;
import com.woshiku.jkshospticaldoctor.activity.main.MainActivity;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.jkshospticaldoctor.activity.utils.RdUtil;
import com.woshiku.jkshospticaldoctor.activity.utils.ThreadManage;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.url.base.CommonUrl;
import common.Global;
import inter.ResultListener;
import param.LoginParam;
import parse.LoginParse;

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
        final LoginData loginData = loginModelListener.onGetUserInfo();
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
                LoginParse.login(result, new ResultListener() {
                    @Override
                    public void onSuccess(Object obj) {
                        Gson gson = new Gson();
                        RdUtil.saveData("logindata",gson.toJson(loginData));//登录成功后保存数据
                        LoginReturnData loginReturnData = (LoginReturnData)obj;
                        Global._token = loginReturnData.token;
                        RdUtil.saveData("loginReturn",gson.toJson(loginReturnData));//保存登录返回数据
                        //登录成功后进入主活动
                        Activity activity = loginModelListener.onGetActivity();
                        Intent intent = new Intent(activity,MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("isLogin",true);
                        intent.putExtras(bundle);
                        activity.startActivity(intent);//Jump到主活动
                        activity.finish();
                    }

                    @Override
                    public void onFail(Object obj) {
                        String msg = (String)obj;
                        loginModelListener.onToast(msg);
                    }
                });
                LogUtil.print(result.toString());
                //关闭对话框
                loginModelListener.onHideWait();
            }
        });
    }
}
