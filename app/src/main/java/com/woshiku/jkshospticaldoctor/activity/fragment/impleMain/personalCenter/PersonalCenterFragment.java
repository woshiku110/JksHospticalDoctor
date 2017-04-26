package com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.personalCenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.fragment.BaseFragment;

/**
 * Created by admin on 2017-04-17.
 * @desc 个人中心fragment
 */

@SuppressLint("ValidFragment")
public class PersonalCenterFragment extends BaseFragment{
    View mView;



    @SuppressLint("ValidFragment")
    public PersonalCenterFragment(FragmentActivity mActivity) {
        super(mActivity);
    }

    @Override
    public View initViews() {
        mView = View.inflate(getContext(), R.layout.fragment_main_personalcenter,null);
        return mView;
    }

    @Override
    protected void dealBroadcastRece(Intent intent) {

    }
}
