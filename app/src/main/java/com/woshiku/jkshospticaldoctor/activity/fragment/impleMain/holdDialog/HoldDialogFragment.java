package com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.holdDialog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.andview.refreshview.XRefreshView;
import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.adapter.fragment.HoldDialogAdapter;
import com.woshiku.jkshospticaldoctor.activity.domain.HoldDialogData;
import com.woshiku.jkshospticaldoctor.activity.fragment.BaseFragment;
import com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.holdDialog.presenter.HoldDialogPresenter;
import com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.holdDialog.presenter.HoldDialogPresenterImple;
import com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.holdDialog.view.HoldDialogView;
import com.woshiku.jkshospticaldoctor.activity.inter.PageState;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by admin on 2017-04-17.
 * @desc 候诊列表
 */

@SuppressLint("ValidFragment")
public class HoldDialogFragment extends BaseFragment implements HoldDialogView, HoldDialogAdapter.OnItemClickListener {
    View mView;
    @InjectView(R.id.preorder_xrecycle)
    XRefreshView xRefreshView;
    @InjectView(R.id.preorder_recycle)
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    HoldDialogPresenter preorderPresent;
    HoldDialogAdapter preorderAdapter;
    List<HoldDialogData> showList;



    @SuppressLint("ValidFragment")
    public HoldDialogFragment(FragmentActivity mActivity) {
        super(mActivity);
    }

    @Override
    public View initViews() {
        mView = View.inflate(getContext(), R.layout.fragment_main_preorder,null);//在这里引用预约列表布局，因为同是下拉列表。
        ButterKnife.inject(this,mView);
        preorderPresent = new HoldDialogPresenterImple(this);
        preorderPresent.initPage();
        return mView;
    }

    @Override
    public void setInitPage() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        xRefreshView.setPinnedTime(200);//设置刷新完成以后，headerview固定的时间
        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                preorderPresent.loadData();
            }
        });
        preorderPresent.firstLoadingPage();//第一次加载数据
        preorderPresent.loadData();//用于加载处理数据
    }

    @Override
    public void loadingPage(boolean isFirst) {
        if(isFirst){//第一次加载页面
            showList = new ArrayList<>();
            showList.add(0,new HoldDialogData(PageState.PageWaiting));//初始化用于等待
            initData(showList);
        }
    }

    private void initData(List<HoldDialogData> mList){
        preorderAdapter = new HoldDialogAdapter(mActivity,mList);
        preorderAdapter.setOnItemClickListener(HoldDialogFragment.this);
        recyclerView.setAdapter(preorderAdapter);
    }

    @Override
    public void loadOk(boolean isUndeal, Object object) {
        LogUtil.print(((List<HoldDialogData>)object).toString());
        setPageData((List<HoldDialogData>)object,PageState.PageOk);
    }

    @Override
    public void loadFail(boolean isUndeal) {
        setPageData(null,PageState.PageFail);
    }

    @Override
    public void loadNoData(boolean isUndeal) {
        setPageData(null,PageState.PageNoData);
    }
    public void setPageData(List<HoldDialogData> mList,int pageState){
        showList = new ArrayList<>();
        if(pageState == PageState.PageWaiting){
            showList.add(new HoldDialogData(pageState));
            preorderAdapter.updateDatas(showList);
        }else if(pageState == PageState.PageFail){
            showList.add(new HoldDialogData(pageState));
            preorderAdapter.updateDatas(showList);
            closeFresh(false);
        }else if(pageState == PageState.PageNoData){
            showList.add(new HoldDialogData(pageState));
            preorderAdapter.updateDatas(showList);
            closeFresh(false);
        }else if(pageState == PageState.PageOk){
            showList.addAll(mList);
            LogUtil.print("page ok ...........");
            preorderAdapter.updateDatas(showList);
            closeFresh(true);
        }
    }
    public void closeFresh(final boolean isFresh){
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                xRefreshView.stopRefresh(isFresh);
            }
        });
    }
    @Override
    public void onItemClick(HoldDialogData preorderData, int pos) {

    }

    @Override
    protected void dealBroadcastRece(Intent intent) {

    }
}
