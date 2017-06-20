package com.woshiku.jkshospticaldoctor.activity.activity.web.model;

import android.app.Activity;
import android.webkit.WebView;

/**
 * Created by Administrator on 2017/2/22.
 */
public interface WebInteractor {
    void getActivityDatas(OnWebListener onWebListener);
    void initWeb(String title, String url, OnWebListener onWebListener);
    void loadUrl(String url, OnWebListener onWebListener);
    interface OnWebListener{
        void onTitle(String title);
        WebView onGetWebView();
        Activity onGetWebActivity();
        void onProgressShow();
        void onProgressHide();
        void loadUrlPageProgress(int progress);
        void loadPageFinish();
        void onLoadUrl(String url);
        void onInitView();
        void onLoadChild();
    }
}
