package com.woshiku.jkshospticaldoctor.activity.activity.recetime;

import android.app.Activity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.BaseActivity;
import com.woshiku.jkshospticaldoctor.activity.activity.recetime.presenter.ReceTimePresenter;
import com.woshiku.jkshospticaldoctor.activity.activity.recetime.presenter.ReceTimePresenterImple;
import com.woshiku.jkshospticaldoctor.activity.activity.recetime.view.ReceTimeView;
import com.woshiku.jkshospticaldoctor.activity.utils.DateChangeUtil;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.jkshospticaldoctor.activity.utils.RdUtil;
import com.woshiku.mydatepicker.cons.DPMode;
import com.woshiku.mydatepicker.views.DatePicker;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import parse.QueryDateParse;

/**
 * Created by admin on 2017-04-28.
 * 接诊时间活动
 */

public class ReceTimeActivity extends BaseActivity implements ReceTimeView{
    @InjectView(R.id.web_title_right)
    LinearLayout rightView;
    @InjectView(R.id.web_title_title)
    TextView titleView;
    @InjectView(R.id.rece_time_datepicker_line)
    LinearLayout receLine;
    DatePicker datePicker;
    ReceTimePresenter presenter;
    String selectedDate;
    @Override
    protected void initViews() {
        setContentView(R.layout.activity_recetime_manage);
        ButterKnife.inject(this);
        presenter = new ReceTimePresenterImple(this);
        presenter.initPage();
    }
    /**
     * 初始化标题栏
     */
    private void initTitleBar(){
        titleView.setText("接诊时间管理");
        rightView.setVisibility(View.INVISIBLE);
    }

    /**
     * 用于初始化界面
     */
    @Override
    public void onInitPage() {
        setScrollDirection(SwipeBackLayout.EDGE_LEFT);//设置手势方向
        setGesture(true);//设置可以滑动
        initTitleBar();
        initDatePicker();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void updateDateResult(boolean isOk, Object object) {
        if(isOk){
            RdUtil.saveData("date",selectedDate);
        }
    }


    private void initDatePicker(){
        String datesStr = RdUtil.readData("date");
        List<String> showDateList = new ArrayList<>();
        if(!TextUtils.isEmpty(datesStr)){
            showDateList = DateChangeUtil.getShowDate(QueryDateParse.parseDate(datesStr));
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        datePicker = new DatePicker(this,null,showDateList);
        Calendar calendar = Calendar.getInstance();
        datePicker.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1);
        datePicker.setFestivalDisplay(false);
        datePicker.setHolidayDisplay(false);
        datePicker.setMode(DPMode.MULTIPLE);
        datePicker.setOnDateSelectedListener(new DatePicker.OnDateSelectedListener() {
            @Override
            public void onDateSelected(List<String> date, boolean isAdd, String dd) {
                selectedDate = new Gson().toJson(date);
                LogUtil.print("query",selectedDate);
                LogUtil.print("query",date.toString());
                LogUtil.print("query","isAdd:"+isAdd+"\t"+"date:"+dd);
                LogUtil.print("query",DateChangeUtil.getUploadDate(dd));
                presenter.updateDate(DateChangeUtil.getUploadDate(dd));
            }
        });
        datePicker.setLayoutParams(params);
        receLine.addView(datePicker);
    }
    @OnClick({R.id.web_title_return})
    void userClick(View view){
        switch (view.getId()){
            case R.id.web_title_return:
                scrollToFinishActivity();
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
