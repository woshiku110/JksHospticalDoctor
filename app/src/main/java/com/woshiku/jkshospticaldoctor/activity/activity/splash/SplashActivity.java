package com.woshiku.jkshospticaldoctor.activity.activity.splash;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.LinearLayout;
import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.BaseActivity;
import com.woshiku.jkshospticaldoctor.activity.activity.login.LoginActivity;
import com.woshiku.jkshospticaldoctor.activity.activity.splash.presenter.SplashPresenter;
import com.woshiku.jkshospticaldoctor.activity.activity.splash.presenter.SplashPresenterImple;
import com.woshiku.jkshospticaldoctor.activity.activity.splash.view.SplashView;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.jkshospticaldoctor.activity.utils.RdUtil;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2017/4/6.
 */
public class SplashActivity extends BaseActivity implements SplashView {
    @InjectView(R.id.line_splash)
    LinearLayout lineSplash;
    SplashPresenter splashPresenter;
    @Override
    protected void initViews() {
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;//定义全屏参数
        Window window = SplashActivity.this.getWindow();//获得当前窗体对象
        window.setFlags(flag, flag);//设置当前窗体为全屏显示
        setContentView(R.layout.activity_splash);
        ButterKnife.inject(this);
        splashPresenter = new SplashPresenterImple(this);
        splashPresenter.initPage();
    }

    @Override
    protected void swipeBackCallback() {

    }

    @Override
    public void setAnimViewShow() {
        LogUtil.print("set anim");
        AnimationSet set = new AnimationSet(false);// 动画集合
        AlphaAnimation alpha = new AlphaAnimation(1, 1);// 渐变动画
        alpha.setDuration(1500);// 动画时间
        alpha.setFillAfter(true);// 保持动画状态
        set.addAnimation(alpha);
        // 设置动画监听
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                splashPresenter.animStart();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {// 动画执行结束
                splashPresenter.animEnd();
            }
        });
        lineSplash.startAnimation(set);

    }

    @Override
    public void setAnimStart() {

    }
    /**
     * 动画运行结束
     * */
    @Override
    public void setAnimEnd() {
        dealApp();
    }
    /**
     * @desc用于处理APP命令
     * */
    private void dealApp(){
        String loginMsg = RdUtil.readData("logindata");
        LogUtil.print("处理闪屏页");
        if(!TextUtils.isEmpty(loginMsg)){//有登录数据,最好重新拿一下token
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SplashActivity.this.finish();
                }
            });
        }else{//没有登录数据，进入登录页面
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                }
            });
        }
    }
}
