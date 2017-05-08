package com.woshiku.jkshospticaldoctor.activity.activity.confirmorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.BaseActivity;
import com.woshiku.jkshospticaldoctor.activity.activity.addressmanage.AddressManageActivity;
import com.woshiku.wheelwidgetslib.view.IntervalDialog;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.imid.swipebacklayout.lib.SwipeBackLayout;

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
    @InjectView(R.id.web_title_right)
    protected LinearLayout concert_bt;
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
        setScrollDirection(SwipeBackLayout.EDGE_LEFT);//设置手势方向
        setGesture(true);//设置可以滑动
        titleView.setText("确认接单");
        concert_bt.setVisibility(View.INVISIBLE);
    }

    /*activity活动默认配置*/
    @OnClick({R.id.web_title_return,R.id.check_order_address_cardview,R.id.web_title_right,R.id.check_order_rece_time_cardview,R.id.check_order_rece_ticket_cardview})
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
                        receTimeView.setText(hour+":"+minute);
                    }
                }).showInterval("选择就诊时间");
                break;
            case R.id.check_order_rece_ticket_cardview:

                break;
            case R.id.web_title_right:

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
    protected void swipeBackCallback() {

    }
}
