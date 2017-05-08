package com.woshiku.wheelwidgetslib.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.woshiku.wheelwidgetslib.R;
import com.woshiku.wheelwidgetslib.utils.IntervalUtil;
import com.woshiku.wheelwidgetslib.widget.OnWheelChangedListener;
import com.woshiku.wheelwidgetslib.widget.WheelView;
import com.woshiku.wheelwidgetslib.widget.adapters.TestAdapter;
import java.util.List;

/**
 * Created by admin on 2017-04-27.
 */

public class IntervalDialog extends PopupWindow{
    View view,parent;
    int hourIndex = 0,minuteIndex =0;
    List<String> hourList,minuteList;
    WheelView hourWheel,minuteWheel;
    TextView chooseTimeView;
    Context context;
    private ChooseIntervalListener chooseTimeListener;
    public interface ChooseIntervalListener{
        void chooseInterval(int hour, int minute);
    }

    public IntervalDialog setChooseTimeListener(ChooseIntervalListener chooseTimeListener) {
        this.chooseTimeListener = chooseTimeListener;
        return this;
    }

    public IntervalDialog(Context context, View parent) {
        super(context);
        this.context = context;
        this.parent = parent;
        view = View.inflate(context, R.layout.interval_dialog_layout,null);
        view.startAnimation(AnimationUtils.loadAnimation(context,
                R.anim.fade_ins));
        chooseTimeView = (TextView)view.findViewById(R.id.wheel_dialog_title);
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
                Log.e("lookat","userClick");
                if (chooseTimeListener != null) {
                    chooseTimeListener.chooseInterval(hourIndex,minuteIndex);
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
        initDatas();
        initViews();
    }

    private void initDatas(){
        hourList = IntervalUtil.getHours();
        minuteList = IntervalUtil.getMinutes();
    }

    private void initViews(){
        hourWheel = configWheelView(new TestAdapter(context,R.layout.item_hour,R.id.item_hour,hourList),(LinearLayout)view.findViewById(R.id.line_interval_hour));
        minuteWheel = configWheelView(new TestAdapter(context,R.layout.item_hour,R.id.item_hour,minuteList),(LinearLayout)view.findViewById(R.id.line_interval_minute));
        addLine((RelativeLayout) view.findViewById(R.id.line_interval_relate_hour));
        addLine((RelativeLayout) view.findViewById(R.id.line_interval_relate_minute));
        addChangeListener();
        hourWheel.setCurrentIndex(0);
        minuteWheel.setCurrentIndex(0);
    }

    public IntervalDialog setTime(int hourIndex,int fenIndex){
        hourWheel.setCurrentIndex(hourIndex);
        minuteWheel.setCurrentIndex(fenIndex);
        return this;
    }
    private WheelView configWheelView(TestAdapter testAdapter, LinearLayout lineLayout){
        LinearLayout.LayoutParams defaultParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        WheelView wheelView = new WheelView(context);
        wheelView.setCyclic(true);
        wheelView.setVisibleItems(5);
        wheelView.setViewAdapter(testAdapter);
        wheelView.setLayoutParams(defaultParam);
        lineLayout.addView(wheelView);
        return wheelView;
    }
    private void addChangeListener(){
        hourWheel.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                hourIndex = newValue;
            }
        });
        minuteWheel.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                minuteIndex = newValue;
            }
        });
    }

    private void addLine(RelativeLayout relativeLayout){
        int itemHeight = (int)context.getResources().getDimension(R.dimen.item_mywheel_height);
        int line_height = 1;
        for(int i=0;i<4;i++){
            if(i==2||i==3){
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,line_height);
                params.topMargin = itemHeight*i;
                View view = new View(context);
                view.setLayoutParams(params);
                view.setBackgroundColor(ContextCompat.getColor(context,R.color.line_bg_one));
                relativeLayout.addView(view);
            }
        }
    }

    public void showInterval(){
        showAtLocation(parent, Gravity.LEFT | Gravity.TOP, 0, 0);
    }
    public void showInterval(String title){
        chooseTimeView.setText(title);
        showAtLocation(parent, Gravity.LEFT | Gravity.TOP, 0, 0);
    }
}
