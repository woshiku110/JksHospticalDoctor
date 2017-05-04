package com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.personalCenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.activity.recetime.ReceTimeActivity;
import com.woshiku.jkshospticaldoctor.activity.fragment.BaseFragment;
import com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.personalCenter.presenter.PersonalCenterPresenter;
import com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.personalCenter.presenter.PersonalCenterPresenterImple;
import com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.personalCenter.view.PersonalCenterView;
import com.woshiku.jkshospticaldoctor.activity.main.MainActivity;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.jkshospticaldoctor.activity.utils.RdUtil;
import com.woshiku.wheelwidgetslib.view.IntervalDialog;
import com.woshiku.wheelwidgetslib.view.SexDialog;
import com.woshiku.wheelwidgetslib.view.YmdDialog;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import common.Global;

/**
 * Created by admin on 2017-04-17.
 * @desc 个人中心fragment
 */

@SuppressLint("ValidFragment")
public class PersonalCenterFragment extends BaseFragment implements PersonalCenterView{
    View mView;
    @InjectView(R.id.item_personal_one_icon)
    ImageView icon;
    @InjectView(R.id.item_personal_interval_time_txt)
    TextView timeIntervalView;
    @InjectView(R.id.item_personal_interval_name)
    TextView nameView;
    PersonalCenterPresenter presenter;
    @SuppressLint("ValidFragment")
    public PersonalCenterFragment(FragmentActivity mActivity) {
        super(mActivity);
    }

    @Override
    public View initViews() {
        mView = View.inflate(getContext(), R.layout.fragment_main_personalcenter,null);
        ButterKnife.inject(this,mView);
        presenter = new PersonalCenterPresenterImple(this);
        presenter.initPage();
        return mView;
    }

    @Override
    protected void dealBroadcastRece(Intent intent) {

    }



    @OnClick({R.id.item_personal_exit,R.id.item_personal_interval,R.id.item_personal_interval_rece_time
                ,R.id.item_personal_check_receipe,R.id.item_personal_rece_history})
    void userClick(View view){
       switch (view.getId()){
           case R.id.item_personal_exit:
               LogUtil.print("user click");
               presenter.exitLogin();
               break;
           case R.id.item_personal_interval:
               presenter.openDialogsisInterval();
               break;
           case R.id.item_personal_interval_rece_time://接诊时间
                presenter.openReceTime();
               break;
           case R.id.item_personal_check_receipe:
               presenter.checkRecipe();
               break;
           case R.id.item_personal_rece_history:
               LogUtil.print("接诊历史");
               presenter.openReceHistory();
               break;
       }
    }




    @Override
    public void setInitPage() {
        displayImageView(icon,Global.imagePath+Global.loginReturnData.logo);
        nameView.setText(Global.loginReturnData.name);
    }

    @Override
    public void setOpenDialog() {
        MainActivity mainUi = (MainActivity)mActivity;
        mainUi.openDialog();
    }

    @Override
    public void setCloseDialog() {
        MainActivity mainUi = (MainActivity)mActivity;
        mainUi.closeDialog();
    }

    @Override
    public void setShortToast(String msg) {
        MainActivity mainUi = (MainActivity)mActivity;
        mainUi.toastShort(msg);
    }

    @Override
    public TextView getIntervalTimeView() {
        return timeIntervalView;
    }

    @Override
    public Activity onGetActivity() {
        return mActivity;
    }
}
