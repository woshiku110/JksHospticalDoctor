package com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.personalCenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.fragment.BaseFragment;
import com.woshiku.jkshospticaldoctor.activity.utils.RdUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import common.Global;

/**
 * Created by admin on 2017-04-17.
 * @desc 个人中心fragment
 */

@SuppressLint("ValidFragment")
public class PersonalCenterFragment extends BaseFragment{
    View mView;
    @InjectView(R.id.item_personal_one_icon)
    ImageView icon;

    @SuppressLint("ValidFragment")
    public PersonalCenterFragment(FragmentActivity mActivity) {
        super(mActivity);
    }

    @Override
    public View initViews() {
        mView = View.inflate(getContext(), R.layout.fragment_main_personalcenter,null);
        ButterKnife.inject(this,mView);
        displayImageView(icon,Global.imagePath+Global.loginReturnData.logo);
        return mView;
    }

    @Override
    protected void dealBroadcastRece(Intent intent) {

    }

    @OnClick({R.id.item_personal_exit})
    void userClick(View view){
        RdUtil.saveData("logindata","");
        RdUtil.saveData("isLogin","");
        mActivity.finish();
    }
}
