package com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.personalCenter.presenter;

/**
 * Created by admin on 2017-04-28.
 */

public interface PersonalCenterPresenter {
    void initPage();
    /**
     * @desc 用于打开接诊时间活动
     * */
    void openReceTime();
    /**
     *@desc 打开诊断时间
     * */
    void openDialgsisTime();
    /**
     * 打开接诊历史
     * */
    void openReceHistory();
    void openDialogsisInterval();
    /**
     * @desc 核实处方
     * */
    void checkRecipe();

    /**
     *@desc 退出登录
     * */
    void exitLogin();
}
