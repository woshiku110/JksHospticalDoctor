package com.woshiku.jkshospticaldoctor.activity.activity.confirmorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.BaseActivity;
import com.woshiku.jkshospticaldoctor.activity.activity.addressmanage.AddressManageActivity;
import com.woshiku.jkshospticaldoctor.activity.activity.checkticket.CheckTicketActivity;
import com.woshiku.jkshospticaldoctor.activity.utils.AppManager;
import com.woshiku.jkshospticaldoctor.activity.utils.ThreadManage;
import com.woshiku.jkshospticaldoctor.activity.view.CheckTicketViews;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.url.base.CommonUrl;
import com.woshiku.wheelwidgetslib.view.IntervalDialog;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import common.Global;
import inter.ResultListener;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import param.DoctorCreateOrderParam;
import param.DoctorReceiveParam;
import parse.DoctorCreateOrderParse;
import parse.DoctorReceiveParse;

/**
 * Created by admin on 2017-05-08.
 * @desc 确认接单
 */

public class ConfirmOrderActivity extends BaseActivity{

    //标题栏
    @InjectView(R.id.web_title_return)
    protected LinearLayout returnView;
    @InjectView(R.id.web_title_title)
    protected TextView titleView;
    @InjectView(R.id.check_rece_order_time)
    TextView receTimeView;
    @InjectView(R.id.check_order_ticket_desc)
    TextView checkDescView;
    @InjectView(R.id.web_title_right)
    protected LinearLayout concert_bt;
    @InjectView(R.id.check_order_line)
    LinearLayout checkLineLayout;//检查项选择展示的布局
    private String[] selectedCheckedIds;//返回选择被选择的检查单ID
    private String[] selectedCheckedNames;//返回选择被选择的检查单项目名称
    private String orderId;//订单Id
    CommonUrl commonUrl;
    @Override
    protected void initViews() {
        setContentView(R.layout.activity_confirm_order);
        ButterKnife.inject(this);
        initPage();
    }
    /**
     * @desc 初始化页面
     * */
    private void initPage(){
        commonUrl = new CommonUrl();
        setScrollDirection(SwipeBackLayout.EDGE_LEFT);//设置手势方向
        setGesture(true);//设置可以滑动
        titleView.setText("确认接单");
        concert_bt.setVisibility(View.INVISIBLE);
        orderId = getIntent().getExtras().getString("orderId");
    }

    /*activity活动默认配置*/
    @OnClick({R.id.web_title_return,R.id.check_order_address_cardview,R.id.web_title_right,R.id.check_order_rece_time_cardview,R.id.check_order_rece_ticket_cardview,R.id.check_order_bt_sure})
    void userClick(View view){
        switch (view.getId()){
            case R.id.web_title_return:
                scrollToFinishActivity();
                break;
            case R.id.check_order_address_cardview:
                Intent intent = new Intent(this, AddressManageActivity.class);
                Bundle bd = new Bundle();
                bd.putString("title","地址管理");
                bd.putString("loadUrl","JKSDoctor/AddressManagement/AddressManagement.html");
                bd.putString("intent","loadasset");
                intent.putExtras(bd);
                startActivity(intent);
                break;
            case R.id.check_order_rece_time_cardview:
                new IntervalDialog(this,concert_bt).setChooseTimeListener(new IntervalDialog.ChooseIntervalListener() {
                    @Override
                    public void chooseInterval(int hour, int minute) {
                        String h,m;
                        h = hour<10?"0"+hour:hour+"";
                        m = minute<10?"0"+minute:minute+"";
                        receTimeView.setText(h+":"+m);
                    }
                }).showInterval("选择就诊时间");
                break;
            case R.id.check_order_rece_ticket_cardview:
                Intent intentOne = new Intent(this, CheckTicketActivity.class);//进入检查单活动
                startActivityForResult(intentOne, Global.checkTicketEnter);
                break;
            case R.id.web_title_right:

                break;
            case R.id.check_order_bt_sure://用户选择接单
                doctorAcceptOrder();//医生接单
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) )
        {
            scrollToFinishActivity();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        dealCheckTicketReturnData(requestCode,resultCode,data);
    }

    @Override
    protected void swipeBackCallback() {

    }
    /**
     * @desc 处理检查单返回的数据
     * */
    private void dealCheckTicketReturnData(int requestCode, int resultCode, Intent data){
        if(requestCode == Global.checkTicketEnter&&resultCode == Global.checkTicketReturn){
            selectedCheckedIds = data.getStringArrayExtra("ids");//检查活动返回的ids
            selectedCheckedNames = data.getStringArrayExtra("names");//检查活动返回的names
            if(selectedCheckedNames.length>0){
                new CheckTicketViews(this,checkLineLayout).generViews(selectedCheckedNames);
                checkDescView.setText("已选择"+selectedCheckedNames.length+"项检查项");
            }
        }
    }
    private void doctorAcceptOrder(){
        ThreadManage.getInstance().carry(new Runnable() {
            @Override
            public void run() {
                openDialog();//打开对话框
                if(selectedCheckedIds!=null){
                    if(selectedCheckedIds.length>0){//有开检查单
                        Result result = commonUrl.loadUrlAsc(DoctorCreateOrderParam.doctorCreateOrder(new Gson().toJson(selectedCheckedIds),new Gson().toJson(selectedCheckedNames)));
                        DoctorCreateOrderParse.createOrder(result, new ResultListener() {
                            @Override
                            public void onSuccess(Object obj) {
                                submitOrder("2017-8-10 04:40","地址一","地址二",selectedCheckedIds.length,orderId);
                            }

                            @Override
                            public void onFail(Object obj) {
                                closeDialog();
                                toastShort("提交失败,请重新提交！");
                            }
                        });
                    }else{
                        submitOrder("2017-8-10 04:40","地址一","地址二",0,orderId);
                    }
                }else{
                    submitOrder("2017-8-10 04:40","地址一","地址二",0,orderId);
                }
            }
        });
    }
    private void submitOrder(String time, String treatmentAddr, String waitAddr, final int amount,final String orderId){
        Result result = commonUrl.loadUrlAsc(DoctorReceiveParam.doctorReceive(time,treatmentAddr,waitAddr,orderId));
        DoctorReceiveParse.doctorReceive(result, new ResultListener() {
            @Override
            public void onSuccess(Object obj) {
                closeDialog();
                Intent intent = new Intent(Global.mainAction);
                Bundle bundle = new Bundle();
                bundle.putString("intent","preorderCheck");//用于确认订单
                bundle.putInt("checkAmount",amount);
                bundle.putString("orderId",orderId);
                intent.putExtras(bundle);
                sendBroadcast(intent);
                toastShort("提交成功!!!");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AppManager.getAppManager().finishActivity("com.woshiku.jkshospticaldoctor.activity.activity.reception.AppointReceActivity");
                        try {
                            Thread.sleep(500);//睡眼0.5s
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        scrollToFinishActivity();
                    }
                });
            }

            @Override
            public void onFail(Object obj) {
                closeDialog();
                toastShort("提交失败,请重新提交!!!");
            }
        });
    }
}
