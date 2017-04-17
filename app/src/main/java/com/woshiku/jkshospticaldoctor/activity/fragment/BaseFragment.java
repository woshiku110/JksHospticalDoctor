package com.woshiku.jkshospticaldoctor.activity.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/4/16.
 */
@SuppressLint("ValidFragment")
public abstract class BaseFragment extends Fragment{
    public FragmentActivity mActivity;

    @SuppressLint("ValidFragment")
    public BaseFragment(FragmentActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();//初始化数据
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initViews();
    }
    public abstract View initViews();

    public void initDatas(){

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
