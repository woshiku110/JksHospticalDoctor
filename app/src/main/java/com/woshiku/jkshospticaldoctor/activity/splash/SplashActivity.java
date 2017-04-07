package com.woshiku.jkshospticaldoctor.activity.splash;

import android.content.Intent;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.LinearLayout;
import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.BaseActivity;
import com.woshiku.jkshospticaldoctor.activity.main.MainActivity;
import com.woshiku.jkshospticaldoctor.activity.splash.presenter.SplashPresenter;
import com.woshiku.jkshospticaldoctor.activity.splash.presenter.SplashPresenterImple;
import com.woshiku.jkshospticaldoctor.activity.splash.view.SplashView;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2017/4/6.
 */
public class SplashActivity extends BaseActivity implements SplashView{
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
        AnimationSet set = new AnimationSet(false);// 动画集合
        AlphaAnimation alpha = new AlphaAnimation(1, 1);// 渐变动画
        alpha.setDuration(2000);// 动画时间
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
        final Intent intent = new Intent(this, MainActivity.class);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                scrollToFinishActivity();
            }
        });
    }
}
