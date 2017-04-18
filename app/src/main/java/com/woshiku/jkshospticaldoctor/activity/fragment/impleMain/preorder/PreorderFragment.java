package com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.preorder;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.andview.refreshview.XRefreshView;
import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.fragment.BaseFragment;
import com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.preorder.view.PreorderView;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by admin on 2017-04-17.
 * @desc 预约列表fragment
 */

@SuppressLint("ValidFragment")
public class PreorderFragment extends BaseFragment implements PreorderView{
    View mView;
    @InjectView(R.id.preorder_xrecycle)
    XRefreshView xRefreshView;
    @InjectView(R.id.preorder_recycle)
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

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

    @Override
    public void setInitPage() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    }
}
