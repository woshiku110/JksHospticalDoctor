package com.woshiku.wheelwidgetslib;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.woshiku.wheelwidgetslib.widget.WheelView;
import com.woshiku.wheelwidgetslib.widget.adapters.TestAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by woshiku on 2016-12-20.
 */
public class TimeDialog extends BottomSheetDialog {
    Context context;
    LinearLayout dialog_line;
    List<String> mList;
    public TimeDialog(Context context) {
        super(context, 0);
        this.context = context;
        initView();
    }
    public TimeDialog(Context context, int theme) {
        super(context, theme);
        initView();
    }
    protected TimeDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }
    private void initView(){
        View dialogView= LayoutInflater.from(context).inflate(R.layout.time_dialog_layout, null);
        setContentView(dialogView);
        View parent = (View) dialogView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        dialogView.measure(0, 0);
        behavior.setPeekHeight(dialogView.getMeasuredHeight());
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) parent.getLayoutParams();
        params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        parent.setLayoutParams(params);
        dialog_line = (LinearLayout)dialogView.findViewById(R.id.dialog_line);
        mList = new ArrayList<>();

        WheelView wheelView = new WheelView(context);
        wheelView.setCyclic(true);
        initDatas();
        wheelView.setViewAdapter(new TestAdapter(context, mList));
        LinearLayout.LayoutParams lineparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        wheelView.setLayoutParams(lineparams);
        wheelView.setCurrentIndex(10);
        dialog_line.addView(wheelView);
    }
    private void initDatas(){
        mList = new ArrayList<>();
        mList.add("1月");
        mList.add("2月");
        mList.add("3月");
        mList.add("4月");
        mList.add("5月");
        mList.add("6月");
        mList.add("7月");
        mList.add("8月");
        mList.add("9月");
        mList.add("10月");
        mList.add("11月");
        mList.add("12月");
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int screenHeight = getScreenHeight(context);
        int statusBarHeight = getStatusBarHeight(context);
        int dialogHeight = screenHeight - statusBarHeight;
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dialogHeight == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);
    }

    private static int getScreenHeight(Context context) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        Activity activity = (Activity)context;
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.heightPixels;
    }

    private static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

}
