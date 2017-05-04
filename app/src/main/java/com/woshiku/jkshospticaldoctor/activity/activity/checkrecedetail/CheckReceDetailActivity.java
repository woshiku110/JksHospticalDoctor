package com.woshiku.jkshospticaldoctor.activity.activity.checkrecedetail;

import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.LinearLayout;

import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.activity.web.WebActivity;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.urllibrary.url.base.CommonUrl;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by admin on 2017-05-04.
 */

public class CheckReceDetailActivity extends WebActivity{
    @InjectView(R.id.web_bottom_check)
    LinearLayout checkLine;
    String orderId;
    CommonUrl commonUrl;
    @Override
    protected void loadChildrenMethod() {
        commonUrl = new CommonUrl();
        Bundle bd = getIntent().getExtras();
        orderId = bd.getString("orderId");
        LogUtil.print("orderId:"+orderId);
        webView.addJavascriptInterface(new JsInteration(), "control");
        checkLine.setVisibility(View.VISIBLE);
    }

    @Override
    protected void swipeBackCallback() {

    }

    /**
     * 需要js实现的方法
     * */
    public class JsInteration {
        @JavascriptInterface
        public String getOrderId(){
            LogUtil.print("orderId:"+orderId);
            return orderId;
        }
    }

    @OnClick({R.id.web_title_return,R.id.web_bottom_check_newdrug,R.id.web_bottom_check_ok})
    void userClick(View view){
        switch (view.getId()){
            case R.id.web_bottom_check_newdrug:

                break;
            case R.id.web_bottom_check_ok:

                break;
            case R.id.web_title_return:
                if(webView.canGoBack()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            webView.goBack();
                        }
                    });
                }else{
                    scrollToFinishActivity();
                }
                break;
        }
    }
}
