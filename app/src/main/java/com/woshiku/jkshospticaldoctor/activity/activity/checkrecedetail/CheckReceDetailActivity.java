package com.woshiku.jkshospticaldoctor.activity.activity.checkrecedetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.LinearLayout;
import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.activity.medicalsearch.MedicalSearchActivity;
import com.woshiku.jkshospticaldoctor.activity.activity.web.WebActivity;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.urllibrary.url.base.CommonUrl;
import com.woshiku.wheelwidgetslib.view.MedicalDialog;
import butterknife.InjectView;
import butterknife.OnClick;
import common.Global;

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
        public String getOrderId(){//拿到订单ID
            LogUtil.print("orderId:"+orderId);
            return orderId;
        }

        @JavascriptInterface
        public void openAmountDialog(){//打开药品数量对话框
            new MedicalDialog(CheckReceDetailActivity.this,checkLine).setMedicalAmountListener(new MedicalDialog.MedicalAmountListener() {
                @Override
                public void medicalAmount(final String amount) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            webView.loadUrl("javascript:"+"androidReviseAmount("+"'"+amount+"'"+")");
                        }
                    });
                }
            }).showMedicalAmount();
        }

        @JavascriptInterface
        public void checkOk(boolean isOk,final String desc){//审核无误
            if(isOk){
                dealCheckOk(isOk,desc);
            }else{
                dealCheckOk(isOk,desc);
            }
        }
    }

    private void dealCheckOk(boolean isOk,final String desc){
        if(isOk){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    toastShort(desc);
                    scrollToFinishActivity();//关闭活动
                }
            });
        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    toastShort(desc);
                }
            });
        }
    }
    /**
     * @desc 通知更新列表
     * */
    private void notifyUpdate(){
        Intent intent = new Intent(Global.checkReceAction);
        Bundle bundle = new Bundle();
        bundle.putString("orderId",orderId);
        intent.putExtras(bundle);
        sendBroadcast(intent);
    }

    @OnClick({R.id.web_title_return,R.id.web_bottom_check_newdrug,R.id.web_bottom_check_ok})
    void userClick(View view){
        switch (view.getId()){
            case R.id.web_bottom_check_newdrug:
                Intent intent = new Intent(CheckReceDetailActivity.this, MedicalSearchActivity.class);
                startActivityForResult(intent, Global.medicalSearchEnter);
                break;
            case R.id.web_bottom_check_ok:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadUrl("javascript:"+"ok("+"'"+Global._token+"'"+")");
                        //notifyUpdate();
                        //scrollToFinishActivity();
                    }
                });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Global.medicalSearchEnter && resultCode == Global.medicalSearchReurn){
            Bundle bundle = data.getExtras();
            final String result = bundle.getString("res");
            LogUtil.print("result:"+result);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    webView.loadUrl("javascript:"+"addDrug("+"'"+result+"'"+")");
                }
            });
        }
    }
}
