package com.woshiku.mydatepicker.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.woshiku.mydatepicker.R;
import com.woshiku.mydatepicker.bizs.decors.DPDecor;
import com.woshiku.mydatepicker.bizs.languages.DPLManager;
import com.woshiku.mydatepicker.bizs.themes.DPTManager;
import com.woshiku.mydatepicker.cons.DPMode;
import com.woshiku.mydatepicker.utils.MeasureUtil;
import java.util.List;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * DatePicker
 *
 * @author AigeStudio 2015-06-29
 */
public class DatePicker extends LinearLayout {
    private DPTManager mTManager;// 主题管理器
    private DPLManager mLManager;// 语言管理器
    private MonthView monthView;// 月视图
    private TextView tvYear, tvMonth;// 年份 月份显示,leftBt,rightBt
    private OnDateSelectedListener onDateSelectedListener;// 日期多选后监听
    private ImageView leftBt,rightBt;
    private UserChooseDateListener userChooseDateListener;

    public interface UserChooseDateListener{
        void userChooseDate(boolean isAdd,String date);
    }

    public void setUserChooseDateListener(UserChooseDateListener userChooseDateListener) {
        this.userChooseDateListener = userChooseDateListener;
    }

    /**
     * 日期单选监听器
     */
    public interface OnDatePickedListener {
        void onDatePicked(String date);
    }

    /**
     * 日期多选监听器
     */
    public interface OnDateSelectedListener {
        void onDateSelected(List<String> date,boolean isAdd,String dd);
    }

    public DatePicker(Context context) {
        this(context, null);
    }
    public DatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    /**
     *设置多个日期以被选择*/
    public void setOnMutiDatesSelectedListener(OnDateSelectedListener onDateSelectedListener){
        this.onDateSelectedListener = onDateSelectedListener;
    }
    public void getMutiDates(){
        if(onDateSelectedListener != null){
            onDateSelectedListener.onDateSelected(monthView.getDateSelected(),monthView.isAdd,monthView.selectedDate);
        }
    }
    public DatePicker(Context context, AttributeSet attrs,List<String> multiDates) {
        super(context, attrs);
        mTManager = DPTManager.getInstance();
        mLManager = DPLManager.getInstance();
        // 设置排列方向为竖向
        setOrientation(VERTICAL);
        LayoutParams llParams =
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        // 标题栏根布局
        RelativeLayout rlTitle = new RelativeLayout(context);
        rlTitle.setBackgroundColor(mTManager.colorTitleBG());
        int rlTitlePadding = MeasureUtil.dp2px(context, 10);
        rlTitle.setPadding(rlTitlePadding, rlTitlePadding, rlTitlePadding, rlTitlePadding);

        // 周视图根布局
        LinearLayout llWeek = new LinearLayout(context);
        llWeek.setBackgroundColor(mTManager.colorTitleBG());
        llWeek.setOrientation(HORIZONTAL);
        int llWeekPadding = MeasureUtil.dp2px(context, 5);
        llWeek.setPadding(0, llWeekPadding, 0, llWeekPadding);
        LayoutParams lpWeek = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpWeek.weight = 1;
        // 标题栏子元素布局参数
        RelativeLayout.LayoutParams lpUpage = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpUpage.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        lpUpage.addRule(RelativeLayout.CENTER_VERTICAL);
        RelativeLayout.LayoutParams rpDownage = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        rpDownage.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rpDownage.addRule(RelativeLayout.CENTER_VERTICAL);
        RelativeLayout.LayoutParams ymParam =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        ymParam.addRule(RelativeLayout.CENTER_IN_PARENT);
        // --------------------------------------------------------------------------------标题栏
        //左边按钮
        leftBt = new ImageView(context);
        leftBt.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.left_arrow_selector));
        /*leftBt = new TextView(context);
        leftBt.setText("左边");*/
        leftBt.setId(R.id.my_left_bt);
        leftBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                monthView.subMonth();
            }
        });
        //右边按钮
        rightBt = new ImageView(context);
        rightBt.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.right_arrow_selector));
        /*rightBt = new TextView(context);
        rightBt.setText("右边");*/
        rightBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                monthView.addMonth();
            }
        });
        // 年份显示
        tvYear = new TextView(context);
        tvYear.setText("2017");
        tvYear.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        tvYear.setTextColor(mTManager.colorTitle());
        // 月份显示
        tvMonth = new TextView(context);
        tvMonth.setText("6月");
        tvMonth.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        tvMonth.setTextColor(mTManager.colorTitle());
        LinearLayout ymLine = new LinearLayout(context);
        ymLine.setOrientation(HORIZONTAL);
        ymLine.addView(tvYear);
        ymLine.addView(tvMonth);
        rlTitle.addView(leftBt,lpUpage);
        rlTitle.addView(ymLine, ymParam);
        rlTitle.addView(rightBt,rpDownage);
        addView(rlTitle, llParams);

        // --------------------------------------------------------------------------------周视图
        for (int i = 0; i < mLManager.titleWeek().length; i++) {
            TextView tvWeek = new TextView(context);
            tvWeek.setText(mLManager.titleWeek()[i]);
            tvWeek.setGravity(Gravity.CENTER);
            tvWeek.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            tvWeek.setTextColor(mTManager.colorTitle());
            llWeek.addView(tvWeek, lpWeek);
        }
        addView(llWeek, llParams);

        // ------------------------------------------------------------------------------------月视图
        monthView = new MonthView(context,multiDates);
        monthView.setDateChooseListener(new MonthView.DateChooseListener() {
            @Override
            public void dateChoose(List<String> dateList) {
                getMutiDates();
            }
        });
        monthView.setOnDateChangeListener(new MonthView.OnDateChangeListener() {
            @Override
            public void onMonthChange(int month) {
                //Log.e("lookat", "month:" + month + "月");
                //tvMonth.setText(mLManager.titleMonth()[month - 1]);
                tvMonth.setText( month + "月");
            }

            @Override
            public void onYearChange(int year) {
                String tmp = String.valueOf(year);
                if (tmp.startsWith("-")) {
                    tmp = tmp.replace("-", mLManager.titleBC());
                }
                //tvYear.setText(tmp);
                tvYear.setText(tmp+"年");
            }
        });
        addView(monthView, llParams);
    }

    /**
     * 设置初始化年月日期
     *
     * @param year  ...
     * @param month ...
     */
    public void setDate(int year, int month) {
        if (month < 1) {
            month = 1;
        }
        if (month > 12) {
            month = 12;
        }
        monthView.setDate(year, month);
    }

    public void setDPDecor(DPDecor decor) {
        monthView.setDPDecor(decor);
    }

    /**
     * 设置日期选择模式
     *
     * @param mode ...
     */
    public void setMode(DPMode mode) {
        if (mode != DPMode.MULTIPLE) {
            //tvEnsure.setVisibility(GONE);
        }
        monthView.setDPMode(mode);
    }

    public void setFestivalDisplay(boolean isFestivalDisplay) {
        monthView.setFestivalDisplay(isFestivalDisplay);
    }

    public void setTodayDisplay(boolean isTodayDisplay) {
        monthView.setTodayDisplay(isTodayDisplay);
    }

    public void setHolidayDisplay(boolean isHolidayDisplay) {
        monthView.setHolidayDisplay(isHolidayDisplay);
    }

    public void setDeferredDisplay(boolean isDeferredDisplay) {
        monthView.setDeferredDisplay(isDeferredDisplay);
    }

    /**
     * 设置单选监听器
     *
     * @param onDatePickedListener ...
     */
    public void setOnDatePickedListener(OnDatePickedListener onDatePickedListener) {
        if (monthView.getDPMode() != DPMode.SINGLE) {
            throw new RuntimeException(
                    "Current DPMode does not SINGLE! Please call setMode set DPMode to SINGLE!");
        }
        monthView.setOnDatePickedListener(onDatePickedListener);
    }

    /**
     * 设置多选监听器
     *
     * @param onDateSelectedListener ...
     */
    public void setOnDateSelectedListener(OnDateSelectedListener onDateSelectedListener) {
        if (monthView.getDPMode() != DPMode.MULTIPLE) {
            throw new RuntimeException(
                    "Current DPMode does not MULTIPLE! Please call setMode set DPMode to MULTIPLE!");
        }
        this.onDateSelectedListener = onDateSelectedListener;
    }
}
