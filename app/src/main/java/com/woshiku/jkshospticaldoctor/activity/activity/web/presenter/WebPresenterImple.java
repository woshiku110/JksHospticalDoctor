package com.woshiku.jkshospticaldoctor.activity.activity.web.presenter;

import android.app.Activity;

import com.woshiku.jkshospticaldoctor.activity.activity.web.model.WebInteractor;
import com.woshiku.jkshospticaldoctor.activity.activity.web.model.WebInteractorImple;
import com.woshiku.jkshospticaldoctor.activity.activity.web.view.WebView;


/**
 * Created by Administrator on 2017/2/22.
 */
public class WebPresenterImple implements WebPresenter,WebInteractor.OnWebListener{
    WebView webView;
    WebInteractor webInteractor;
    public WebPresenterImple(WebView webView) {
        this.webView = webView;
        webInteractor = new WebInteractorImple();
    }

    @Override
    public void onTitle(String title) {
        if(webView != null){
            webView.setWebTitle(title);
        }
    }

    @Override
    public android.webkit.WebView onGetWebView() {
        if(webView != null){
            return webView.getWebView();
        }
        return null;
    }

    @Override
    public Activity onGetWebActivity() {
        if(webView != null){
            return webView.getWebActivity();
        }
        return null;
    }

    @Override
    public void onProgressShow() {
        if(webView != null){
            webView.setProgressShow();
        }
    }

    @Override
    public void onProgressHide() {
        if(webView != null){
            webView.setProgressHide();
        }
    }

    @Override
    public void loadUrlPageProgress(int progress) {
        if(webView != null){
            webView.setloadUrlPageProgress(progress);
        }
    }

    @Override
    public void loadPageFinish() {
        if(webView != null){
            webView.setLoadPageFinish();
        }
    }

    @Override
    public void onLoadUrl(String url) {
        if(webView!=null){
            webView.setWebLoadUrl(url);
        }
    }

    @Override
    public void onInitView() {
        if(webView!=null){
            webView.setInitView();
        }
    }

    @Override
    public void onLoadChild() {
        if(webView != null){
            webView.loadChild();
        }
    }

    @Override
    public void loadUrl(String url) {
        webInteractor.loadUrl(url,this);
    }



    @Override
    public void getActivityDatas() {
        webInteractor.getActivityDatas(this);
    }

    @Override
    public void initWeb(String title, String url) {
        webInteractor.initWeb(title, url, this);
    }
}
