package com.woshiku.jkshospticaldoctor.activity.activity.web;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.BaseActivity;
import com.woshiku.jkshospticaldoctor.activity.activity.web.presenter.WebPresenter;
import com.woshiku.jkshospticaldoctor.activity.activity.web.presenter.WebPresenterImple;
import com.woshiku.jkshospticaldoctor.activity.activity.web.view.WebView;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.progresslibrary.RoundCornerProgressBar;
import butterknife.ButterKnife;
import butterknife.InjectView;
import me.imid.swipebacklayout.lib.SwipeBackLayout;

/**
 * Created by Administrator on 2017/2/22.
 *用于显示网络页面
 * 这次使用mvp设计模式做的，看看效果怎么样
 */
public abstract class WebActivity extends BaseActivity implements WebView {
    @InjectView(R.id.web_title_return)
    protected LinearLayout returnView;
    @InjectView(R.id.web_title_title)
    protected TextView titleView;
    @InjectView(R.id.web_title_right)
    protected LinearLayout concert_bt;
    @InjectView(R.id.web_panel_pb)
    protected RoundCornerProgressBar roundPb;
    @InjectView(R.id.web_panel_webview)
    protected android.webkit.WebView webView;
    @InjectView(R.id.web_title_concert_txt)
    protected TextView concertText;
    protected WebPresenter webPresenter;
    protected Activity activity;
    protected abstract void loadChildrenMethod();//用于孩子的初始化

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_web);
        ButterKnife.inject(this);
        activity = this;
        webPresenter = new WebPresenterImple(this);
        webPresenter.getActivityDatas();
        LogUtil.print("load webview");
    }

    @Override
    public void setInitView() {
        setScrollDirection(SwipeBackLayout.EDGE_LEFT);//设置手势方向
        setGesture(true);//设置可以滑动
        concert_bt.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setWebTitle(String title) {
        titleView.setText(title);
        titleChanged(title);
    }

    @Override
    public void setloadUrlPageProgress(int pb) {
        roundPb.setProgress(pb);
    }

    @Override
    public void setLoadPageFinish() {

    }

    @Override
    public void setWebLoadUrl(String url) {
        webView.loadUrl(url);
    }

    @Override
    public void setProgressShow() {
        roundPb.setVisibility(View.VISIBLE);
    }

    @Override
    public void setProgressHide() {
        roundPb.setVisibility(View.GONE);
    }

    @Override
    public Activity getWebActivity() {
        return activity;
    }

    @Override
    public android.webkit.WebView getWebView() {
        return webView;
    }

    @Override
    public void loadChild() {
        loadChildrenMethod();
    }

    public void titleChanged(String title){

    }
}
