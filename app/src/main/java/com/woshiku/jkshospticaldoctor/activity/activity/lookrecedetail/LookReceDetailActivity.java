package com.woshiku.jkshospticaldoctor.activity.activity.lookrecedetail;

import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.LinearLayout;

import com.woshiku.dialoglib.ScaleImagePop;
import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.activity.holddialogdetail.HoldDialogDetailActivity;
import com.woshiku.jkshospticaldoctor.activity.activity.web.WebActivity;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.urllibrary.url.base.CommonUrl;
import butterknife.InjectView;
import butterknife.OnClick;
import common.Global;

/**
 * Created by admin on 2017-05-04.
 */

public class LookReceDetailActivity extends WebActivity{
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
    }

    @Override
    protected void swipeBackCallback() {

    }

    /**
     * 需要js实现的方法
     * */
    public class JsInteration {
        @JavascriptInterface
        public String getToken(){
            return Global._token;
        }
        @JavascriptInterface
        public String getOrderId(){//拿到订单ID
            LogUtil.print("orderId:"+orderId);
            return orderId;
        }
        @JavascriptInterface
        public void showPic(final String pic) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LogUtil.print("pic", pic);
                    new ScaleImagePop(LookReceDetailActivity.this, checkLine, pic).show();
                }
            });
        }
    }

    @OnClick({R.id.web_title_return})
    void userClick(View view){
        switch (view.getId()){
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
