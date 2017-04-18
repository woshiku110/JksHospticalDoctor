package com.woshiku.jkshospticaldoctor.activity.main;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.woshiku.bottomtabbarlib.BottomTabBar;
import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.BaseActivity;
import com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.FragmentFactory;
import com.woshiku.jkshospticaldoctor.activity.splash.SplashActivity;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.jkshospticaldoctor.activity.view.NoSmoothViewPager;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends BaseActivity {
    @InjectView(R.id.main_bottom_bar)
    BottomTabBar bottomTabBar;
    @InjectView(R.id.main_bottom_viewpager)
    NoSmoothViewPager viewPager;
    String []titles = {"预约列表","候诊列表","个人中心"};
    @Override
    protected void initViews() {
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        LogUtil.print("main activity");
        startActivity(new Intent(this, SplashActivity.class));
        initBottomBar();
        initFragment();
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
        viewPager.setAdapter(new MainPage(getSupportFragmentManager()));

    }

    class MainPage extends FragmentStatePagerAdapter{

        public MainPage(FragmentManager fm) {
            super(fm);
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
}
