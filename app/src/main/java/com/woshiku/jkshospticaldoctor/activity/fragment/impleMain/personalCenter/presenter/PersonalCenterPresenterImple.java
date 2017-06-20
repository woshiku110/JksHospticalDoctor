package com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.personalCenter.presenter;

import android.app.Activity;
import android.widget.TextView;
import com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.personalCenter.model.PersonalCenterModel;
import com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.personalCenter.model.PersonalCenterModelImple;
import com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.personalCenter.view.PersonalCenterView;

/**
 * Created by admin on 2017-04-28.
 */

public class PersonalCenterPresenterImple implements PersonalCenterPresenter,PersonalCenterModel.PersonalCenterModelListener{
    PersonalCenterView personalCenterView;
    PersonalCenterModel personalCenterModel;

    public PersonalCenterPresenterImple(PersonalCenterView personalCenterView) {
        this.personalCenterView = personalCenterView;
        this.personalCenterModel = new PersonalCenterModelImple();
    }

    @Override
    public void openReceTime() {
        personalCenterModel.openReceTime(this);
    }

    @Override
    public void openDialgsisTime() {
        personalCenterModel.openDialgsisTime(this);
    }

    @Override
    public void openReceHistory() {
        personalCenterModel.openReceHistory(this);
    }

    @Override
    public void openDialogsisInterval() {
        personalCenterModel.openDialogsisInterval(this);
    }

    @Override
    public void checkRecipe() {
        personalCenterModel.checkRecipe(this);
    }

    @Override
    public void exitLogin() {
        personalCenterModel.exitLogin(this);
    }
    /*以下是view视图部分*/
    @Override
    public void initPage() {
        if(personalCenterView != null){
            personalCenterView.setInitPage();
        }
    }

    @Override
    public void openDialog() {
        if(personalCenterView != null){
            personalCenterView.setOpenDialog();
        }
    }

    @Override
    public void closeDialog() {
        if(personalCenterView != null){
            personalCenterView.setCloseDialog();
        }
    }

    @Override
    public Activity getActivity() {
        if(personalCenterView != null){
            return personalCenterView.onGetActivity();
        }
        return null;
    }

    @Override
    public TextView getIntervalTimeView() {
        if(personalCenterView != null){
            return personalCenterView.getIntervalTimeView();
        }
        return null;
    }
}
