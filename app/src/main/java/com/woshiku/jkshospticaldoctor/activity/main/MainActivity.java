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
import com.woshiku.jkshospticaldoctor.activity.utils.AppManager;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.jkshospticaldoctor.activity.utils.RdUtil;
import com.woshiku.jkshospticaldoctor.activity.utils.ThreadManage;
import com.woshiku.jkshospticaldoctor.activity.view.NoSmoothViewPager;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.url.base.CommonUrl;

import butterknife.ButterKnife;
import butterknife.InjectView;
import common.Global;
import inter.ResultListener;
import param.DefaultAddressParam;
import param.QueryDateParam;
import parse.DefaultAddressParse;
import parse.QueryDateParse;

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
        LogUtil.print("主活动");
        ThreadManage.getInstance().carry(new InitPage());
    }

    class InitPage implements Runnable{

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Bundle bundle = getIntent().getExtras();
                        boolean isLogin = bundle.getBoolean("isLogin");
                        if(isLogin){//从登录页面进来的
                            String loginMsg = RdUtil.readData("loginReturn");
                            if(!TextUtils.isEmpty(loginMsg)){
                                Gson gson = new Gson();
                                Global.loginReturnData = gson.fromJson(loginMsg, LoginReturnData.class);
                                Global._token = Global.loginReturnData.token;
                            }
                            initBottomBar();
                            initFragment();
                            initQueryDate();
                            initGetDefaultAddr();
                        }else{//不是从登录页面进来的
                            LogUtil.print("不是从登录页面进来的");
                            startActivity(new Intent(MainActivity.this, SplashActivity.class));//打开闪屏页
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
                                initQueryDate();
                                initGetDefaultAddr();
                            }else{
                                FragmentFactory.clearFragments();
                                MainActivity.this.finish();
                            }
                        }
                    }catch (Exception e){
                        //不是从登录页面进来的
                        LogUtil.print("闪屏页面进入");
                        startActivity(new Intent(MainActivity.this, SplashActivity.class));
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
                            initQueryDate();
                            initGetDefaultAddr();
                        }else{
                            LogUtil.print("没有账号,结束主活动");
                            FragmentFactory.clearFragments();
                            MainActivity.this.finish();
                        }
                    }
                }
            });
        }
    }
    private void initBottomBar(){
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
    /**
     *获取查询日期
     */
    private void initQueryDate(){
        ThreadManage.getInstance().carry(new Runnable() {
            @Override
            public void run() {
                Result result = new CommonUrl().loadUrlAsc(QueryDateParam.queryDate());
                QueryDateParse.queryDate(result);
            }
        });
    }
    /**
     * 获取默认地址
     * */
    private void initGetDefaultAddr(){
        ThreadManage.getInstance().carry(new Runnable() {
            @Override
            public void run() {
                Result result = new CommonUrl().loadUrlAsc(DefaultAddressParam.defaultAddr());
                DefaultAddressParse.defaultAddr(result);
            }
        });
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
            AppManager.getAppManager().finishAllActivity();
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
