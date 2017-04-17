package com.woshiku.jkshospticaldoctor.activity.main;

import android.content.Intent;
import com.woshiku.bottomtabbarlib.BottomTabBar;
import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.BaseActivity;
import com.woshiku.jkshospticaldoctor.activity.splash.SplashActivity;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends BaseActivity {
    @InjectView(R.id.main_bottom_bar)
    BottomTabBar bottomTabBar;
    @Override
    protected void initViews() {
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        LogUtil.print("main activity");
        startActivity(new Intent(this, SplashActivity.class));
        initBottomBar();
    }
    private void initBottomBar(){
        /*String[] titles = {"测试一","测试二","测试三","测试四"};
        int []tabIcons = {com.woshiku.bottomtabbarlib.R.drawable.tabbar_bespeak_drawble, com.woshiku.bottomtabbarlib.R.drawable.tabbar_head_drawble, com.woshiku.bottomtabbarlib.R.drawable.tabbar_right_drawble,com.woshiku.bottomtabbarlib.R.drawable.tabbar_head_drawble};
        int []tabSelectedColors = {com.woshiku.bottomtabbarlib.R.color.selector_txt_selected, com.woshiku.bottomtabbarlib.R.color.selector_txt_selected, com.woshiku.bottomtabbarlib.R.color.selector_txt_selected,com.woshiku.bottomtabbarlib.R.color.selector_txt_selected};
        int []tabUnselectedColors= {com.woshiku.bottomtabbarlib.R.color.selector_txt_unselect, com.woshiku.bottomtabbarlib.R.color.selector_txt_unselect, com.woshiku.bottomtabbarlib.R.color.selector_txt_unselect,com.woshiku.bottomtabbarlib.R.color.selector_txt_unselect};
        bottomTabBar.setTitles(titles);
        bottomTabBar.setTabIcons(tabIcons);
        bottomTabBar.setTabUnselectedColors(tabUnselectedColors);
        bottomTabBar.setTabSelectedColors(tabSelectedColors);
        bottomTabBar.commit();//用于生成界面*/
        bottomTabBar.setTabbarNews(1,true,2);
        bottomTabBar.setTabbarNews(2,true,5);
        bottomTabBar.setTabbarNews(2,false,-1);
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
    @Override
    protected void swipeBackCallback() {

    }
}
