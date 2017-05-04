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
import android.widget.TextView;

import com.woshiku.wheelwidgetslib.R;
import com.woshiku.wheelwidgetslib.widget.OnWheelChangedListener;
import com.woshiku.wheelwidgetslib.widget.WheelView;
import com.woshiku.wheelwidgetslib.widget.adapters.TestAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/8.
 */
public class SexDialog extends PopupWindow {
    View view,parent;
    Context context;
    WheelView sexWheel;
    List<String> sexList;
    TextView title;
    private SexListener sexListener;
    boolean isNan = true;
    public interface SexListener{
        void userChooseSex(boolean isNan);
    }
    public SexDialog setSexListener(SexListener sexListener) {
        this.sexListener = sexListener;
        return this;
    }

    public SexDialog(Context context, View parent) {
        this.context = context;
        this.parent = parent;
        view = View.inflate(context, R.layout.sex_dialog_layout,null);
        view.startAnimation(AnimationUtils.loadAnimation(context,
                R.anim.fade_ins));
        title = (TextView)view.findViewById(R.id.wheel_dialog_title);
        title.setText("性别选择");
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
                if(sexListener != null){
                    sexListener.userChooseSex(isNan);
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
        sexList = new ArrayList<>();
        sexList.add("男");
        sexList.add("女");
    }
    private void initViews(){
        sexWheel = configWheelView(new TestAdapter(context,R.layout.item_sex,R.id.item_sex,sexList),(LinearLayout)view.findViewById(R.id.line_sex));
        sexWheel.setCurrentIndex(0);
        addLine((RelativeLayout) view.findViewById(R.id.relate_line));
        addChangeListener();
    }
    private WheelView configWheelView(TestAdapter testAdapter,LinearLayout lineLayout){
        LinearLayout.LayoutParams defaultParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        WheelView wheelView = new WheelView(context);
        wheelView.setCyclic(false);
        wheelView.setVisibleItems(3);
        wheelView.setViewAdapter(testAdapter);
        wheelView.setLayoutParams(defaultParam);
        lineLayout.addView(wheelView);
        return wheelView;
    }
    private void addLine(RelativeLayout relativeLayout){
        int itemHeight = (int)context.getResources().getDimension(R.dimen.item_mywheel_height);
        int line_height = 1;
        for(int i=0;i<4;i++){
            if(i==1||i==2){
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,line_height);
                params.topMargin = itemHeight*i;
                View view = new View(context);
                view.setLayoutParams(params);
                view.setBackgroundColor(context.getResources().getColor(R.color.line_bg_one));
                relativeLayout.addView(view);
            }
        }
    }
    private void addChangeListener(){
        sexWheel.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if(newValue == 0){
                    isNan = true;
                }else{
                    isNan = false;
                }
            }
        });
    }

    public void showSex(){
        showAtLocation(parent, Gravity.LEFT | Gravity.TOP, 0, 0);
    }
}
