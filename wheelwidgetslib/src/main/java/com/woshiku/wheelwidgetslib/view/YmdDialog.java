package com.woshiku.wheelwidgetslib.view;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import com.woshiku.wheelwidgetslib.R;
import com.woshiku.wheelwidgetslib.utils.YmdUtil;
import com.woshiku.wheelwidgetslib.widget.OnWheelChangedListener;
import com.woshiku.wheelwidgetslib.widget.WheelView;
import com.woshiku.wheelwidgetslib.widget.adapters.TestAdapter;
import java.util.List;
/**
 * Created by Administrator on 2017/2/7.
 */
public class YmdDialog extends PopupWindow{
    View view,parent;
    WheelView yearView,monthView,dayView;
    int yearIndex,monthIndex,dayIndex;
    List<String> yearList,monthList,dayList;
    Context context;
    private ChooseYmdListener chooseYmdListener;
    public interface ChooseYmdListener{
        void chooseYmd(int year, int month, int day);
    }
    public YmdDialog setChooseYmdListener(ChooseYmdListener chooseYmdListener){
        this.chooseYmdListener = chooseYmdListener;
        return this;
    }

    public YmdDialog(Context context, View parent) {
        this.context = context;
        this.parent = parent;
        view = View.inflate(context,R.layout.ymd_dialog_layout,null);
        view.startAnimation(AnimationUtils.loadAnimation(context,
                R.anim.fade_ins));
        ((LinearLayout)view.findViewById(R.id.mywheel_dialog_top)).setOnClickListener(new View.OnClickListener() {
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
        update();
        showAtLocation(parent, Gravity.LEFT | Gravity.TOP, 0, 0);
        LinearLayout btOk = (LinearLayout)view.findViewById(R.id.bt_sure);
        LinearLayout btCancel = (LinearLayout)view.findViewById(R.id.bt_cancel);
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chooseYmdListener != null) {
                    chooseYmdListener.chooseYmd(yearIndex+YmdUtil.getStartTime(),monthIndex+1,dayIndex+1);
                }
                dismiss();
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        initDates();
        initViews(view,context);
        initWheelListener();
    }
    public void showYmd(){
        showAtLocation(parent, Gravity.LEFT | Gravity.TOP, 0, 0);
    }
    private void initDates(){
        yearList = YmdUtil.getYearList();
        monthList = YmdUtil.getMonthList();
        //拿到今天日期的的年月
        dayList = YmdUtil.getDayList(YmdUtil.getTime()[0],YmdUtil.getTime()[1]);
    }

    private void initViews(View view,Context context){
        yearView = configWheelView(new TestAdapter(context,R.layout.item_ymd_year,R.id.item_ymd_year,yearList),(LinearLayout)view.findViewById(R.id.line_ymd_year));
        monthView = configWheelView(new TestAdapter(context,R.layout.item_ymd_month,R.id.item_ymd_month,monthList),(LinearLayout)view.findViewById(R.id.line_ymd_month));
        dayView = configWheelView(new TestAdapter(context,R.layout.item_ymd_day,R.id.item_ymd_day,dayList),(LinearLayout)view.findViewById(R.id.line_ymd_day));
        //提前30年
        yearIndex = YmdUtil.getTime()[0] - YmdUtil.getStartTime()-30;
        monthIndex = YmdUtil.getTime()[1]-1;
        dayIndex = YmdUtil.getTime()[2]-1;
        yearView.setCurrentIndex(yearIndex);
        monthView.setCurrentIndex(monthIndex);
        dayView.setCurrentIndex(dayIndex);
        addLine((RelativeLayout)view.findViewById(R.id.relate_line));
    }
    private void addLine(RelativeLayout relativeLayout){
        int itemHeight = (int)context.getResources().getDimension(R.dimen.item_mywheel_height);
        int line_height = 1;
        for(int i=0;i<6;i++){
            if(i==2||i==3){
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,line_height);
                params.topMargin = itemHeight*i;
                View view = new View(context);
                view.setLayoutParams(params);
                view.setBackgroundColor(context.getResources().getColor(R.color.line_bg_one));
                relativeLayout.addView(view);
            }
        }
    }
    private WheelView configWheelView(TestAdapter testAdapter,LinearLayout lineLayout){
        LinearLayout.LayoutParams defaultParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        WheelView wheelView = new WheelView(context);
        wheelView.setCyclic(true);
        wheelView.setViewAdapter(testAdapter);
        wheelView.setLayoutParams(defaultParam);
        lineLayout.addView(wheelView);
        return wheelView;
    }
    private void initWheelListener(){
        yearView.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                yearIndex = newValue;
                changeDay();
            }
        });
        monthView.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                monthIndex = newValue;
                changeDay();
            }
        });
        dayView.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                dayIndex = newValue;
            }
        });
    }
    private void changeDay(){
        int year = yearIndex+YmdUtil.getStartTime();
        int month = monthIndex+1;
        List<String> nowList = YmdUtil.getDayList(year,month);
        dayView.setViewAdapter(new TestAdapter(context, R.layout.item_ymd_day, R.id.item_ymd_day, nowList));
        if(dayIndex<nowList.size()-1){
            dayView.setCurrentIndex(dayIndex);
        }else{
            dayView.setCurrentIndex(0);
        }
    }
}
