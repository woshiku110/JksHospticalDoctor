package com.woshiku.jkshospticaldoctor.activity.activity.recetime;

import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.BaseActivity;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.mydatepicker.cons.DPMode;
import com.woshiku.mydatepicker.views.DatePicker;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.imid.swipebacklayout.lib.SwipeBackLayout;

/**
 * Created by admin on 2017-04-28.
 * 接诊时间活动
 */

public class ReceTimeActivity extends BaseActivity{
    @InjectView(R.id.web_title_right)
    LinearLayout rightView;
    @InjectView(R.id.web_title_title)
    TextView titleView;
    @InjectView(R.id.rece_time_datepicker_line)
    LinearLayout receLine;
    DatePicker datePicker;
    @Override
    protected void initViews() {
        setContentView(R.layout.activity_recetime_manage);
        ButterKnife.inject(this);
        initPage();
    }

    private void initPage(){
        setScrollDirection(SwipeBackLayout.EDGE_LEFT);//设置手势方向
        setGesture(true);//设置可以滑动
        initTitleBar();
        initDatePicker();
    }

    private void initTitleBar(){
        titleView.setText("接诊时间管理");
        rightView.setVisibility(View.INVISIBLE);
    }
    private void initDatePicker(){
        //为日历设置当前系统时间
        List<String> selectedDate = new ArrayList<>();
        selectedDate.add("2017-4-29");
        selectedDate.add("2017-4-30");
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        datePicker = new DatePicker(this,null,selectedDate);
        Calendar calendar = Calendar.getInstance();
        datePicker.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1);
        datePicker.setFestivalDisplay(false);
        datePicker.setHolidayDisplay(false);
        datePicker.setMode(DPMode.MULTIPLE);
        datePicker.setOnDateSelectedListener(new DatePicker.OnDateSelectedListener() {
            @Override
            public void onDateSelected(List<String> date) {
                LogUtil.print(date.toString());
            }
        });
        datePicker.setLayoutParams(params);
        receLine.addView(datePicker);
    }
    @Override
    protected void swipeBackCallback() {

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
}
