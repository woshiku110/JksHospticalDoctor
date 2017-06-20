package com.woshiku.jkshospticaldoctor.activity.activity.holddialogdetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.LinearLayout;

import com.woshiku.dialoglib.ScaleImagePop;
import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.activity.checkticket.CheckTicketActivity;
import com.woshiku.jkshospticaldoctor.activity.activity.dealedticketdetail.DealedConfirmDetailActivity;
import com.woshiku.jkshospticaldoctor.activity.activity.meetdialog.MeetDialogsisActivity;
import com.woshiku.jkshospticaldoctor.activity.activity.submitill.SubmitIllnessActivity;
import com.woshiku.jkshospticaldoctor.activity.activity.web.WebActivity;
import com.woshiku.jkshospticaldoctor.activity.domain.PermissionData;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.jkshospticaldoctor.activity.utils.ThreadManage;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.url.base.CommonUrl;
import butterknife.InjectView;
import butterknife.OnClick;
import common.Global;
import inter.ResultListener;
import param.JumpOrderParam;
import parse.JumpOrderParse;

/**
 * Created by admin on 2017-04-25.
 * @desc 候诊详情
 */

public class HoldDialogDetailActivity extends WebActivity{
    @InjectView(R.id.hold_dialog_detail_line)
    LinearLayout preorderLine;
    String orderId;
    CommonUrl commonUrl;
    @Override
    protected void loadChildrenMethod() {
        commonUrl = new CommonUrl();
        Bundle bd = getIntent().getExtras();
        orderId = bd.getString("orderId");
        webView.addJavascriptInterface(new JsInteration(), "control");
        preorderLine.setVisibility(View.VISIBLE);
        concert_bt.setVisibility(View.VISIBLE);
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
        public void showPic(final String pic) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LogUtil.print("pic", pic);
                    new ScaleImagePop(HoldDialogDetailActivity.this, preorderLine, pic).show();
                }
            });
        }
    }
    @Override
    protected void swipeBackCallback() {

    }

    @OnClick({R.id.web_title_return,R.id.hold_dialog_detail_check,R.id.hold_dialog_detail_meet,R.id.hold_dialog_detail_submit,R.id.web_title_right})
    void userClick(View view){
        switch (view.getId()){
            case R.id.hold_dialog_detail_check:
                Intent intent = new Intent(this, CheckTicketActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("intent","holddialogdetail");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.hold_dialog_detail_meet:
                Intent intent1 = new Intent(this, MeetDialogsisActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("title","地址管理");
                bundle1.putString("loadUrl","JKSDoctor/consultation/consulation.html");
                bundle1.putString("intent","loadasset");
                intent1.putExtras(bundle1);
                startActivity(intent1);
                break;
            case R.id.hold_dialog_detail_submit:
                setPermissionData(new PermissionData(Global.WritePermission,Global.WriteReturnCode,Global.WriteReason));
                if(isPermission()){
                    setPermissionData(new PermissionData(Global.CameraPermission,Global.CameraReturnCode,Global.CameraReason));
                    if(isPermission()){
                        enterSubmitIllActivity();
                    }else{
                        allowPermission();
                    }
                }else{
                    allowPermission();
                }
                break;
            case R.id.web_title_right:
                jumpCommand(orderId);//执行跳过命令
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
     * @Title: ${enclosing_method}
     * @Description: ${todo}(传入订单id，执行跳过方法)
     * @param: ${orderId}
     * @return: ${void}
     * @throws
     */
    private void jumpCommand(String orderId){
        ThreadManage.getInstance().carry(new JumpOrder(orderId));
    }

    class JumpOrder implements Runnable{
        String orderId;

        public JumpOrder(String orderId) {
            this.orderId = orderId;
        }

        @Override
        public void run() {
            openDialog();
            Result result = commonUrl.loadUrlAsc(JumpOrderParam.jumpOrder(orderId));
            JumpOrderParse.jumpOrder(result, new ResultListener() {
                @Override
                public void onSuccess(Object obj) {
                    closeDialog();
                    dealJumpOrderResult(true);
                }

                @Override
                public void onFail(Object obj) {
                    closeDialog();
                    dealJumpOrderResult(false);
                }
            });
        }
    }

    private void dealJumpOrderResult(boolean isSuccess){
        if(isSuccess){//提交成功
            Intent intent = new Intent(Global.mainAction);
            Bundle bundle = new Bundle();
            bundle.putString("intent","jumpcommand");
            intent.putExtras(bundle);
            sendBroadcast(intent);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    scrollToFinishActivity();
                }
            });
        }else{//提交失败
            toastShort("提交失败");
        }
    }

    @Override
    protected void userPassPermission(PermissionData permissionData) {
        super.userPassPermission(permissionData);
        if(permissionData.getName().equals(Global.WritePermission)){
            setPermissionData(new PermissionData(Global.CameraPermission,Global.CameraReturnCode,Global.CameraReason));
            if(isPermission()){
                enterSubmitIllActivity();
            }else{
                allowPermission();
            }
        }else if(permissionData.getName().equals(Global.CameraPermission)){
            enterSubmitIllActivity();
        }
    }

    private void enterSubmitIllActivity(){
        Intent intent = new Intent(this, SubmitIllnessActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("orderId",orderId);
        intent.putExtras(bundle);
        startActivity(intent);
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
