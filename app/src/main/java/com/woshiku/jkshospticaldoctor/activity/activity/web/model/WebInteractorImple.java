package com.woshiku.jkshospticaldoctor.activity.activity.web.model;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;

/**
 * Created by Administrator on 2017/2/22.
 */
public class WebInteractorImple implements WebInteractor{
    OnWebListener onWebListener;
    @Override
    public void getActivityDatas(final OnWebListener onWebListener) {
        if(onWebListener != null){
            this.onWebListener = onWebListener;
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    onWebListener.onInitView();
                    Activity activity = onWebListener.onGetWebActivity();
                    Bundle bd = activity.getIntent().getExtras();
                    String title = bd.getString("title");
                    String loadUrl = bd.getString("loadUrl");
                    String intent = bd.getString("intent");
                    LogUtil.print("url:" + loadUrl);
                    if(intent.equals("loadasset")){
                        LogUtil.print("one");
                        initWeb(title,"file:///android_asset/"+loadUrl, onWebListener);
                    }else{
                        LogUtil.print("two");
                        initWeb(title, loadUrl, onWebListener);
                    }
                    onWebListener.onLoadChild();
                }
            });
        }
    }

    @Override
    public void initWeb(String title, String url, OnWebListener onWebListener) {
       if(onWebListener != null){
           onWebListener.onTitle(title);
           configWeb(onWebListener);
           onWebListener.onLoadUrl(url);
       }
    }

    @Override
    public void loadUrl(String url, OnWebListener onWebListener) {
        if(onWebListener != null){
            onWebListener.onLoadUrl(url);
        }
    }

    private void configWeb(final OnWebListener onWebListener){
        if(onWebListener != null){
            //能够的调用JavaScript代码
            WebSettings webSettings = onWebListener.onGetWebView().getSettings();
            webSettings.setDisplayZoomControls(false);
            webSettings.setJavaScriptEnabled(true);
            webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
            webSettings.setSupportZoom(true);//设定支持缩放
            webSettings.setLoadWithOverviewMode(true);
            // 设置出现缩放工具
            webSettings.setBuiltInZoomControls(true);
            //自适应屏幕
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
            onWebListener.onGetWebView().setHorizontalScrollBarEnabled(false);
            onWebListener.onGetWebView().setWebViewClient(new WebViewClient() {

                @Override
                public void onPageFinished(android.webkit.WebView view, String url) {
                    super.onPageFinished(view, url);
                    //加载完成
                    onWebListener.onProgressHide();
                    onWebListener.loadPageFinish();
                    onWebListener.onTitle(view.getTitle());
                }

                @Override
                public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    //开始加载
                    onWebListener.onProgressShow();
                }

                @Override
                public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }

                @Override
                public void onReceivedError(android.webkit.WebView view, WebResourceRequest request, WebResourceError error) {
                    super.onReceivedError(view, request, error);
                }
            });
            onWebListener.onGetWebView().setWebChromeClient(new WebChromeClient() {
                @Override
                public void onReceivedTitle(android.webkit.WebView view, String title) {
                    super.onReceivedTitle(view, title);
                }

                @Override
                public void onProgressChanged(android.webkit.WebView view, int newProgress) {
                    super.onProgressChanged(view, newProgress);
                    onWebListener.loadUrlPageProgress(newProgress);
                }
            });
        }
    }
}
