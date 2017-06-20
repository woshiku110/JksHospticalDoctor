package com.woshiku.jkshospticaldoctor.activity.activity.dealedticketdetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.LinearLayout;
import com.woshiku.dialoglib.BackOrderDialog;
import com.woshiku.dialoglib.ScaleImagePop;
import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.activity.checkticket.CheckTicketActivity;
import com.woshiku.jkshospticaldoctor.activity.activity.reception.AppointReceActivity;
import com.woshiku.jkshospticaldoctor.activity.activity.web.WebActivity;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.jkshospticaldoctor.activity.utils.ThreadManage;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.url.base.CommonUrl;
import butterknife.InjectView;
import butterknife.OnClick;
import common.Global;
import inter.ResultListener;
import param.BackOrderParam;
import parse.BackOrderParse;

/**
 * Created by admin on 2017-04-25.
 * @desc 确认接单的详情界面
 */

public class DealedConfirmDetailActivity extends WebActivity{
    @InjectView(R.id.web_confirm_detail)
    LinearLayout preorderLine;
    @InjectView(R.id.web_confirm_detail_checkTicket)
    LinearLayout checkTicketLine;
    String orderId;
    CommonUrl commonUrl;
    @Override
    protected void loadChildrenMethod() {
        commonUrl = new CommonUrl();
        Bundle bd = getIntent().getExtras();
        orderId = bd.getString("orderId");
        int amount = bd.getInt("amount");
        LogUtil.print("amount"+amount);
        if(amount >= 1){
            checkTicketLine.setEnabled(false);
        }else{
            checkTicketLine.setEnabled(true);
        }
        webView.addJavascriptInterface(new JsInteration(), "control");
        preorderLine.setVisibility(View.VISIBLE);
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
        public String getOrderId(){
            LogUtil.print("orderId:"+orderId);
            return orderId;
        }
        @JavascriptInterface
        public void showPic(final String pic){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LogUtil.print("pic",pic);
                    new ScaleImagePop(DealedConfirmDetailActivity.this,preorderLine,pic).show();
                }
            });
        }
    }
    @Override
    protected void swipeBackCallback() {

    }

    @OnClick({R.id.web_title_return,R.id.web_confirm_detail_backOrder,R.id.web_confirm_detail_checkTicket})
    void userClick(View view){
        switch (view.getId()){
            case R.id.web_confirm_detail_checkTicket:
                Intent intent = new Intent(this, CheckTicketActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("isChecked",true);
                bundle.putString("orderId",orderId);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.web_confirm_detail_backOrder:
                userBackOrder();
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
    /**
     * @desc 用户退单
     * */
    private void userBackOrder(){
        new BackOrderDialog(DealedConfirmDetailActivity.this,preorderLine)
                .setUserChooseListener(new BackOrderDialog.UserChooseListener() {
                    @Override
                    public void userChoose(final String msg) {
                        openDialog();
                        LogUtil.print("orderId"+orderId);
                        ThreadManage.getInstance().carry(new Runnable() {
                            @Override
                            public void run() {
                                Result result = commonUrl.loadUrlAsc(BackOrderParam.backOrder(orderId,msg));
                                BackOrderParse.backOrder(result, new ResultListener() {
                                    @Override
                                    public void onSuccess(Object obj){
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent intent = new Intent(common.Global.mainAction);
                                                Bundle bd = new Bundle();
                                                bd.putString("intent","preorderUndeal");
                                                bd.putString("orderId",orderId);
                                                intent.putExtras(bd);
                                                DealedConfirmDetailActivity.this.sendBroadcast(intent);
                                                toastShort("退单成功");
                                                closeDialog();
                                                scrollToFinishActivity();
                                            }
                                        });
                                    }

                                    @Override
                                    public void onFail(final Object obj) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                toastShort("退单失败"+obj);
                                                closeDialog();
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) )
        {
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
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
