package com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.holdDialog;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.fragment.BaseFragment;

/**
 * Created by admin on 2017-04-17.
 * @desc 候诊列表
 */

@SuppressLint("ValidFragment")
public class HoldDialogFragment extends BaseFragment{
    View mView;

    @SuppressLint("ValidFragment")
    public HoldDialogFragment(FragmentActivity mActivity) {
        super(mActivity);
    }

    @Override
    public View initViews() {
        mView = View.inflate(getContext(), R.layout.fragment_main_holddialog,null);
        return mView;
    }
}
