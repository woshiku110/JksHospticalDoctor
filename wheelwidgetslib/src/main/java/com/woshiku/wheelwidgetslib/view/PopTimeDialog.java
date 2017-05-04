package com.woshiku.wheelwidgetslib.view;

import android.widget.PopupWindow;
/**
 * Created by Administrator on 2016/12/21.
 */
public class PopTimeDialog extends PopupWindow{
    /*View view;
    WheelView yearView;
    WheelView monthView;
    WheelView dayView;
    WheelView hourView;
    WheelView fenView;
    LinearLayout dayLine;
    List<String> yearList;
    List<String> monthList;
    List<String> dayList;
    List<String> fenList;
    List<String> hourList;
    private int defaultYear,defaultMonth,defaultDay,defaultHour,defaultFen,maxDay;
    private int yearIndex,monthIndex,dayIndex,hourIndex,fenIndex;
    Context context;
    TextView week;
    private ChooseTimeListener chooseTimeListener;
    View parent;
    boolean isDefault = true;
    public interface ChooseTimeListener{
        void chooseTime(String[] date);
    }
    public void setChooseTimeListener(ChooseTimeListener chooseTimeListener) {
        this.chooseTimeListener = chooseTimeListener;
    }
    public PopTimeDialog(final Context context,View parent) {
        this.context = context;
        this.parent = parent;
        view = View.inflate(context,R.layout.time_dialog_layout,null);
        view.startAnimation(AnimationUtils.loadAnimation(context,
                R.anim.fade_ins));
        RelativeLayout ll_popup = (RelativeLayout) view.findViewById(R.id.road_line_ll);
        ll_popup.startAnimation(AnimationUtils.loadAnimation(context,
                R.anim.push_bottom_in_2));
        LinearLayout bt_ok =(LinearLayout) view.findViewById(R.id.bt_sure);
        LinearLayout bt_cancel = (LinearLayout)view.findViewById(R.id.bt_cancel);
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chooseTimeListener != null) {
                    chooseTimeListener.chooseTime(CommonUtil.getAllTime(yearIndex, monthIndex, dayIndex, hourIndex, fenIndex, isDefault));
                }
                dismiss();
            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        initDate();
        initViews(view, context);
        update();
        showAtLocation(parent, Gravity.LEFT | Gravity.TOP, 0, 0);
    }

    private void initDate(){
        defaultYear = CommonUtil.getTime()[0];
        defaultMonth = CommonUtil.getTime()[1];
        defaultDay = CommonUtil.getTime()[2];
        defaultHour = CommonUtil.getTime()[3];
        defaultFen = CommonUtil.getTime()[4];
        hourIndex = defaultHour;
        fenIndex = defaultFen;
        yearList = CommonUtil.getYear();
        monthList = CommonUtil.getMonth();
        dayList = CommonUtil.getDay(defaultYear, defaultMonth);
        hourList = CommonUtil.getHour();
        fenList = CommonUtil.getFen();
        yearIndex = CommonUtil.getYearIndex(defaultYear);
        monthIndex = CommonUtil.getMonthIndex(defaultMonth);
        dayIndex = CommonUtil.getDayIndex(defaultYear,defaultMonth,defaultDay)-1;
    }
    private void initViews(View view,Context context){
        RelativeLayout wheelLine = (RelativeLayout)view.findViewById(R.id.time_dialog_relate);
        addLines(wheelLine);
        LinearLayout yearLine = (LinearLayout)view.findViewById(R.id.wheel_year_line);
        LinearLayout monthLine = (LinearLayout)view.findViewById(R.id.wheel_month_line);
        LinearLayout hourLine = (LinearLayout)view.findViewById(R.id.wheel_hour_line);
        LinearLayout fenLine = (LinearLayout)view.findViewById(R.id.wheel_minute_line);
        dayLine = (LinearLayout)view.findViewById(R.id.wheel_day_line);
        //年
        yearView = new WheelView(context);
        yearView.setCyclic(true);
        yearView.setViewAdapter(new TestAdapter(context, R.layout.item_wheelview_year, R.id.wheel_textview_year, yearList));
        LinearLayout.LayoutParams yearParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        yearView.setLayoutParams(yearParam);
        yearLine.addView(yearView);
        //月
        monthView = new WheelView(context);
        monthView.setCyclic(true);
        monthView.setViewAdapter(new TestAdapter(context, R.layout.item_wheelview_month, R.id.wheel_textview_month, monthList));
        LinearLayout.LayoutParams monthParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        monthView.setLayoutParams(monthParam);
        monthLine.addView(monthView);
        //日
        addDayView(dayList);
        //星期几
        week = (TextView)view.findViewById(R.id.wheel_week_text);
        //时
        hourView = new WheelView(context);
        hourView.setCyclic(true);
        hourView.setViewAdapter(new TestAdapter(context, R.layout.item_wheelview_time, R.id.wheel_textview_time, hourList));
        LinearLayout.LayoutParams hourParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        hourView.setLayoutParams(hourParam);
        hourLine.addView(hourView);
        //分
        fenView = new WheelView(context);
        fenView.setCyclic(true);
        fenView.setViewAdapter(new TestAdapter(context, R.layout.item_wheelview_time, R.id.wheel_textview_time, fenList));
        LinearLayout.LayoutParams fenParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        fenView.setLayoutParams(fenParam);
        fenLine.addView(fenView);
        setDefaultDate(yearView, monthView,hourView,fenView);
        addWheelListener(yearView,monthView);
    }
    private void setDefaultDate(WheelView yearView,WheelView monthView,WheelView hourView,WheelView fenView){
        yearView.setInitIndex(CommonUtil.getYearIndex(defaultYear));
        monthView.setInitIndex(CommonUtil.getMonthIndex(defaultMonth));
        hourView.setCurrentIndex(hourIndex);
        fenView.setCurrentIndex(fenIndex);
        maxDay = CommonUtil.getMaxDay(defaultYear, defaultMonth);
        dayIndex = CommonUtil.getDayThrIndex(defaultDay);
        String weekStr = CommonUtil.getDefaultWeek(defaultYear, defaultMonth,defaultDay);
        week.setText(weekStr);
    }
    private void addWheelListener(WheelView yearView,WheelView monthView){
        yearView.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                Log.e("lookkat","change nian"+newValue);
                yearIndex = newValue;
                changeDate();
                changeWeek();
            }
        });
        
        monthView.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                Log.e("lookkat","change yue"+newValue);
                monthIndex = newValue;
                changeDate();
                changeWeek();
            }
        });
        hourView.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                hourIndex = newValue;
            }
        });
        fenView.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                fenIndex = newValue;
            }
        });
    }
    private void changeDate(){
        int nowMaxday = CommonUtil.getMaxDay(CommonUtil.getYearThrIndex(yearIndex),CommonUtil.getMonthThrIndex(monthIndex));
        if(nowMaxday == maxDay){
            return;
        }else{
            if(CommonUtil.getDayThrIndex(dayIndex)<=nowMaxday){

            }else{
                dayIndex = 0;
            }
            dayList = CommonUtil.getDay(CommonUtil.getYearThrIndex(yearIndex),CommonUtil.getMonthThrIndex(monthIndex));
            changeDayView(dayList,dayIndex);
            maxDay = nowMaxday;
        }
    }
    private void addDayView(List<String> dayList){
        dayLine.removeAllViews();
        dayView = new WheelView(context);
        dayView.setCyclic(true);
        dayView.setViewAdapter(new TestAdapter(context, R.layout.item_wheelview_day, R.id.wheel_textview_day, dayList));
        LinearLayout.LayoutParams dayParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dayView.setLayoutParams(dayParam);
        dayLine.addView(dayView);
        dayView.setCurrentIndex(CommonUtil.getDayIndex(defaultYear, defaultMonth, defaultDay));
        dayView.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                dayIndex = newValue;
                isDefault = false;
                Log.e("lookat","change"+dayIndex);
                changeWeek();
            }
        });
    }
    private void changeDayView(List<String> dayList,int dayIndex){
        dayView.setViewAdapter(new TestAdapter(context, R.layout.item_wheelview_day, R.id.wheel_textview_day, dayList));
        dayView.setCurrentIndexNoSmooth(dayIndex);
    }
    private void changeWeek(){
        Log.e("lookkat","change week"+"yearIndex"+yearIndex);
        Log.e("lookkat","change week"+"monthIndex"+monthIndex);
        Log.e("lookkat","change week"+"dayIndex"+dayIndex);
        String weekStr = CommonUtil.getWeek(yearIndex, monthIndex, dayIndex);
        week.setText(weekStr);
    }
    private void addLines(RelativeLayout relativeLayout){
        int itemHeight = (int)context.getResources().getDimension(R.dimen.item_height);
        Log.e("lookkat","size"+itemHeight);
        int line_height = 1;
        for(int i=0;i<6;i++){
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,line_height);
            params.topMargin = itemHeight*i;
            View view = new View(context);
            view.setLayoutParams(params);
            view.setBackgroundColor(context.getResources().getColor(R.color.top));
            relativeLayout.addView(view);
        }
    }*/
}
