package com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.personalCenter.model;

import android.app.Activity;
import android.widget.TextView;

/**
 * Created by admin on 2017-04-28.
 */

public interface PersonalCenterModel {
    /**
     * @desc 用于打开接诊时间活动
     * */
    void openReceTime(PersonalCenterModelListener personalCenterModelListener);
    /**
     *@desc 打开诊断时间
     * */
    void openDialgsisTime(PersonalCenterModelListener personalCenterModelListener);
    void openDialogsisInterval(PersonalCenterModelListener personalCenterModelListener);
    /**
     * @desc 核实处方
     * */
    void checkRecipe(PersonalCenterModelListener personalCenterModelListener);
    /**
     * 打开接诊历史
     * */
    void openReceHistory(PersonalCenterModelListener personalCenterModelListener);
    /**
     *@desc 退出登录
     * */
    void exitLogin(PersonalCenterModelListener personalCenterModelListener);
    interface PersonalCenterModelListener{
        void initPage();
        void openDialog();
        void closeDialog();
        Activity getActivity();
        TextView getIntervalTimeView();
    }
}
