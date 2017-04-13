package com.woshiku.jkshospticaldoctor.activity.main;

import android.content.Intent;

import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.BaseActivity;
import com.woshiku.jkshospticaldoctor.activity.splash.SplashActivity;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        LogUtil.print("main activity");
        startActivity(new Intent(this, SplashActivity.class));
    }

    @Override
    protected void swipeBackCallback() {

    }
}
