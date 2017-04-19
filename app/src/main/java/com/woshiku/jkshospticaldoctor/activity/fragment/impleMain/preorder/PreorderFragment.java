package com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.preorder;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.andview.refreshview.XRefreshView;
import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.adapter.fragment.PreorderAdapter;
import com.woshiku.jkshospticaldoctor.activity.domain.UndealPreorderData;
import com.woshiku.jkshospticaldoctor.activity.fragment.BaseFragment;
import com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.preorder.view.PreorderView;
import java.util.ArrayList;
import java.util.List;
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
    List<UndealPreorderData> mList;
    PreorderAdapter preorderAdapter;
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
        recyclerView.setLayoutManager(layoutManager);
        xRefreshView.setPinnedTime(1000);//设置刷新完成以后，headerview固定的时间
        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        xRefreshView.stopRefresh(false);
                    }
                }, 1500);
            }
        });
    }

    @Override
    public void loadingPage() {

    }

    @Override
    public void loadOk() {

    }

    @Override
    public void loadFail() {

    }

    @Override
    public void loadNoData() {

    }

    private void initData(){
        mList = new ArrayList<>();
        mList.add(new UndealPreorderData("woshiku-1",true));
        for(int i=0;i<10;i++){
            mList.add(new UndealPreorderData("woshiku"+i,false));
        }
        preorderAdapter = new PreorderAdapter(mActivity,mList);
        recyclerView.setAdapter(preorderAdapter);
    }
}
