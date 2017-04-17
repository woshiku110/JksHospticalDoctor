package com.woshiku.jkshospticaldoctor.activity.fragment.impleMain;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.fragment.BaseFragment;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017-04-17.
 * @desc 预约列表fragment
 */

@SuppressLint("ValidFragment")
public class PreorderFragment extends BaseFragment{
    View mView;
    @SuppressLint("ValidFragment")
    public PreorderFragment(FragmentActivity mActivity) {
        super(mActivity);
    }

    @Override
    public View initViews() {
        mView = View.inflate(getContext(), R.layout.fragment_main_preorder,null);
        ButterKnife.inject(this,mView);
        return mView;
    }
    @OnClick({R.id.preorder_txt})
    void userClick(){
        Log.e("lookat","preorder txt");
    }
}
