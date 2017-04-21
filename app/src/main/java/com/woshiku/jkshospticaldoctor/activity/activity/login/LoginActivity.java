package com.woshiku.jkshospticaldoctor.activity.activity.login;

import android.view.View;
import android.widget.EditText;
import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.BaseActivity;
import com.woshiku.jkshospticaldoctor.activity.activity.login.presenter.LoginPresenter;
import com.woshiku.jkshospticaldoctor.activity.activity.login.presenter.LoginPresenterImple;
import com.woshiku.jkshospticaldoctor.activity.activity.login.view.LoginView;
import com.woshiku.jkshospticaldoctor.activity.domain.LoginData;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by admin on 2017-04-20.
 */

public class LoginActivity extends BaseActivity implements LoginView{
    @InjectView(R.id.login_input_user)
    EditText userNameInput;
    @InjectView(R.id.login_input_pass)
    EditText userPassInput;
    LoginPresenter loginPresenter;
    @Override
    protected void initViews() {
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        loginPresenter = new LoginPresenterImple(this);
    }

    @Override
    protected void swipeBackCallback() {

    }

    @OnClick({R.id.login_bt})
    void userClick(View view){
        loginPresenter.login();
    }
    /**
     * @desc 初始化页面
     * */
    @Override
    public void setInitPage() {

    }

    @Override
    public void toast(String msg) {
        toastShort(msg);
    }

    @Override
    public void showWait() {
        openDialog();
    }

    @Override
    public void hideWait() {
        closeDialog();
    }

    @Override
    public LoginData getUserInfo() {
        String userName = userNameInput.getText().toString();
        String userPass = userPassInput.getText().toString();
        return new LoginData(userName,userPass);
    }
}
