package com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.personalCenter.model;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.TextView;

import com.woshiku.jkshospticaldoctor.activity.activity.checkreception.CheckReceptionActivity;
import com.woshiku.jkshospticaldoctor.activity.activity.medicalsearch.MedicalSearchActivity;
import com.woshiku.jkshospticaldoctor.activity.activity.receptionhistory.ReceptionHistoryActivity;
import com.woshiku.jkshospticaldoctor.activity.activity.recetime.ReceTimeActivity;
import com.woshiku.jkshospticaldoctor.activity.main.MainActivity;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.jkshospticaldoctor.activity.utils.RdUtil;
import com.woshiku.wheelwidgetslib.view.IntervalDialog;

/**
 * Created by admin on 2017-04-28.
 */

public class PersonalCenterModelImple implements PersonalCenterModel{

    @Override
    public void openReceTime(PersonalCenterModelListener personalCenterModelListener) {
        Activity activity = personalCenterModelListener.getActivity();
        activity.startActivity(new Intent(activity, ReceTimeActivity.class));
    }

    @Override
    public void openDialgsisTime(PersonalCenterModelListener personalCenterModelListener) {

    }

    @Override
    public void openDialogsisInterval(PersonalCenterModelListener personalCenterModelListener) {
        Activity activity = personalCenterModelListener.getActivity();
        chooseTime(activity,personalCenterModelListener.getIntervalTimeView());
    }

    @Override
    public void checkRecipe(PersonalCenterModelListener personalCenterModelListener) {
        Activity activity = personalCenterModelListener.getActivity();
        /*activity.startActivity(new Intent(activity, MedicalSearchActivity.class));*/
        activity.startActivity(new Intent(activity, CheckReceptionActivity.class));
    }

    @Override
    public void openReceHistory(PersonalCenterModelListener personalCenterModelListener) {
        Activity activity = personalCenterModelListener.getActivity();
        activity.startActivity(new Intent(activity, ReceptionHistoryActivity.class));
    }

    @Override
    public void exitLogin(PersonalCenterModelListener personalCenterModelListener) {
        Activity activity = personalCenterModelListener.getActivity();
        jumpDialog(activity);
    }

    private void jumpDialog(final Activity mActivity){
        LogUtil.print("jump dialog");
        AlertDialog.Builder normalDialog = new AlertDialog.Builder(mActivity);
        normalDialog.setTitle("退出");
        normalDialog.setMessage("您确定要退出吗？");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        MainActivity mainUi = (MainActivity)mActivity;
                        mainUi.closeApp();
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }

    private void chooseTime(Activity mActivity,final TextView timeIntervalView){
        String hour = RdUtil.readData("hour");
        String minute = RdUtil.readData("minute");
        IntervalDialog intervalDialog = new IntervalDialog(mActivity,timeIntervalView).setChooseTimeListener(new IntervalDialog.ChooseIntervalListener() {
            @Override
            public void chooseInterval(int hour, int minute) {
                RdUtil.saveData("hour",hour+"");
                RdUtil.saveData("minute",minute+"");
                String hourTime = hour<10?"0"+hour:hour+"";
                String minuteTime = minute<10?"0"+minute:minute+"";
                timeIntervalView.setText(hourTime+":"+minuteTime);
            }
        });
        if(!TextUtils.isEmpty(hour)){
            intervalDialog.setTime(Integer.parseInt(hour),Integer.parseInt(minute));
            intervalDialog.showInterval();
        }else{
            intervalDialog.setTime(0,30);
            intervalDialog.showInterval();
        }
    }

}
