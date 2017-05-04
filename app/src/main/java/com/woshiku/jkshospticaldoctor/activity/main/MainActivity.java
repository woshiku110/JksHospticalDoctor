package com.woshiku.jkshospticaldoctor.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.TextUtils;
import android.view.KeyEvent;
import com.google.gson.Gson;
import com.woshiku.bottomtabbarlib.BottomTabBar;
import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.BaseActivity;
import com.woshiku.jkshospticaldoctor.activity.activity.splash.SplashActivity;
import com.woshiku.jkshospticaldoctor.activity.domain.LoginReturnData;
import com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.FragmentFactory;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.jkshospticaldoctor.activity.utils.RdUtil;
import com.woshiku.jkshospticaldoctor.activity.view.NoSmoothViewPager;
import butterknife.ButterKnife;
import butterknife.InjectView;
import common.Global;

public class MainActivity extends BaseActivity {
    @InjectView(R.id.main_bottom_bar)
    BottomTabBar bottomTabBar;
    @InjectView(R.id.main_bottom_viewpager)
    NoSmoothViewPager viewPager;
    String []titles = {"预约列表","候诊列表","个人中心"};
    private int maxPage = 3;
    @Override
    protected void initViews() {
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        LogUtil.print("main activity");
        try{
            Bundle bundle = getIntent().getExtras();
            boolean isLogin = bundle.getBoolean("isLogin");
            if(isLogin){//从登录页面进来的
                initBottomBar();
                initFragment();
            }else{//不是从登录页面进来的
                startActivity(new Intent(this, SplashActivity.class));
                String msg = RdUtil.readData("logindata");
                if(!TextUtils.isEmpty(msg)){
                    String loginMsg = RdUtil.readData("loginReturn");
                    if(!TextUtils.isEmpty(loginMsg)){
                        Gson gson = new Gson();
                        Global.loginReturnData = gson.fromJson(loginMsg, LoginReturnData.class);
                        Global._token = Global.loginReturnData.token;
                    }
                    initBottomBar();
                    initFragment();
                }else{
                    finish();
                }
            }
        }catch (Exception e){
            //不是从登录页面进来的
            LogUtil.print("闪屏页面进入");
            startActivity(new Intent(this, SplashActivity.class));
            String msg = RdUtil.readData("logindata");
            if(!TextUtils.isEmpty(msg)){
                String loginMsg = RdUtil.readData("loginReturn");
                if(!TextUtils.isEmpty(loginMsg)){
                    Gson gson = new Gson();
                    Global.loginReturnData = gson.fromJson(loginMsg, LoginReturnData.class);
                    Global._token = Global.loginReturnData.token;
                }
                initBottomBar();
                initFragment();
            }else{
                finish();
            }
        }
    }
    private void initBottomBar(){
        bottomTabBar.setTabbarNews(1,true,5);
        bottomTabBar.setTabbarClickListener(new BottomTabBar.TabbarClickListener(){
            @Override
            public void tabbarClick(int index) {
                LogUtil.print("click index:"+index);
            }
        });
        bottomTabBar.setNewsClearRemindListener(new BottomTabBar.NewsClearRemindListener() {
            @Override
            public void newsClearRemind(int index, boolean isRemindText, int amount) {
                LogUtil.print("newsClear index:"+index+"\t"+isRemindText+"\t"+amount);
            }
        });
    }
    private void initFragment(){
        viewPager.setAdapter(new MainPage(MainActivity.this.getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(maxPage);
    }

    class MainPage extends FragmentStatePagerAdapter{

        public MainPage(FragmentManager fm) {
            super(fm);
            LogUtil.print("实例化mainpage");
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentFactory.createFragment(MainActivity.this,position);
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }
    @Override
    protected void swipeBackCallback() {

    }


    public void closeApp(){
        FragmentFactory.clearFragments();
        RdUtil.saveData("logindata","");
        RdUtil.saveData("isLogin","");
        finish();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) )
        {
           FragmentFactory.clearFragments();
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
