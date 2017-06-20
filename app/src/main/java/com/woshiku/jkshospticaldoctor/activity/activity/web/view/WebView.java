package com.woshiku.jkshospticaldoctor.activity.activity.web.view;

import android.app.Activity;

/**
 * Created by Administrator on 2017/2/22.
 */
public interface WebView {
    void setInitView();
    void setWebTitle(String title);
    void setloadUrlPageProgress(int pb);
    void setLoadPageFinish();
    void setWebLoadUrl(String url);
    void setProgressShow();
    void setProgressHide();
    void loadChild();
    Activity getWebActivity();
    android.webkit.WebView getWebView();
}
