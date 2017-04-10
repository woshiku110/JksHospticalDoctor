package com.woshiku.bottomtabbarlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2017/4/7.
 */
public class BottomTabBar extends RelativeLayout{
    ViewPager viewPager;
    LinearLayout bottomBar;
    int tabBarHeight,tabBarBg;

    public BottomTabBar(Context context) {
        super(context);
    }

    public BottomTabBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTabBar(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initChildren();
    }

    /**
     * @desc 用于获取配置底部栏目的一些设置
     * */
    private void initTabBar(Context context,AttributeSet attrs){
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.BottomTabBar);
        tabBarHeight = (int)a.getDimension(R.styleable.BottomTabBar_tabBarHeight,(int)context.getResources().getDimension(R.dimen.bottom_tabbar_height));
        tabBarBg = a.getColor(R.styleable.BottomTabBar_tabBarBg, ContextCompat.getColor(context,R.color.tabbar_bg));
        bottomBar = new LinearLayout(context);
        bottomBar.setBackgroundColor(tabBarBg);
        bottomBar.setId(R.id.bottom_bar_line);
    }
    /**
     * @desc 用于布局viewpage和底部栏
     * */
    private void initChildren(){
        if(getChildAt(0) instanceof ViewPager){
            viewPager = (ViewPager)getChildAt(0);
        }else{
            try {
                throw new Exception("need viewpager as children");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        RelativeLayout.LayoutParams tabbarLp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,tabBarHeight);//给tabbar的布局位于布局的左下角
        tabbarLp.addRule(ALIGN_PARENT_LEFT);
        tabbarLp.addRule(ALIGN_PARENT_BOTTOM);
        addView(bottomBar, tabbarLp);//添加底边栏到布局
        RelativeLayout.LayoutParams viewPageLp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);//获取viewpage的布局并设置viewpage在底边栏上面
        viewPageLp.addRule(ABOVE, R.id.bottom_bar_line);
        viewPager.setLayoutParams(viewPageLp);//给viewpage设置布局参数
    }
}
