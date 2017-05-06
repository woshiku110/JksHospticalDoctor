package com.woshiku.jkshospticaldoctor.activity.activity.checkreception;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.andview.refreshview.XRefreshView;
import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.BaseActivity;
import com.woshiku.jkshospticaldoctor.activity.activity.checkrecedetail.CheckReceDetailActivity;
import com.woshiku.jkshospticaldoctor.activity.activity.checkreception.presenter.CheckRecePresenter;
import com.woshiku.jkshospticaldoctor.activity.activity.checkreception.presenter.CheckRecePresenterImple;
import com.woshiku.jkshospticaldoctor.activity.activity.checkreception.view.CheckReceView;
import com.woshiku.jkshospticaldoctor.activity.activity.lookrecedetail.LookReceDetailActivity;
import com.woshiku.jkshospticaldoctor.activity.adapter.CheckReceptionAdapter;
import com.woshiku.jkshospticaldoctor.activity.domain.CheckReceData;
import com.woshiku.jkshospticaldoctor.activity.inter.PageState;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import common.Global;
import me.imid.swipebacklayout.lib.SwipeBackLayout;

/**
 * Created by admin on 2017-05-04.
 *@desc 核实处方活动
 */

public class CheckReceptionActivity extends BaseActivity implements CheckReceView, CheckReceptionAdapter.OnItemClickListener, CheckReceptionAdapter.OnChooseListener {
    @InjectView(R.id.multi_xrecycleview)
    XRefreshView xRefreshView;
    @InjectView(R.id.multi_recycleview)
    RecyclerView recyclerView;
    //标题栏
    @InjectView(R.id.web_title_return)
    protected LinearLayout returnView;
    @InjectView(R.id.web_title_title)
    protected TextView titleView;
    @InjectView(R.id.web_title_right)
    protected LinearLayout concert_bt;
    CheckRecePresenter presenter;
    LinearLayoutManager layoutManager;
    List<CheckReceData> showList;//用于显示数据
    List<CheckReceData> undealList,dealedList;
    CheckReceptionAdapter checkReceptionAdapter;
    boolean isUndealCheck = true;
    NotifyReceiver notifyReceiver;
    @Override
    protected void initViews() {
        setContentView(R.layout.activity_check_rece);//核实处方
        ButterKnife.inject(this);
        presenter = new CheckRecePresenterImple(this);
        presenter.initPage();
    }

    @Override
    public void setInitPage() {
        initReceiver();
        setScrollDirection(SwipeBackLayout.EDGE_LEFT);//设置手势方向
        setGesture(true);//设置可以滑动
        titleView.setText("核实处方");
        concert_bt.setVisibility(View.INVISIBLE);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        xRefreshView.setPinnedTime(200);//设置刷新完成以后，headerview固定的时间
        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                presenter.loadData();
            }
        });
        presenter.firstLoadingPage();//第一次加载数据
    }

    @Override
    public void loadingPage(boolean isFirst) {
        if(isFirst){//第一次加载页面
            showList = new ArrayList<>();
            showList.add(0,new CheckReceData(true));//表示选择的cell
            showList.add(1,new CheckReceData(false, PageState.PageWaiting));//初始化用于等待表示下常的cell
            initData(showList);
        }
    }
    private void initData(List<CheckReceData> mList){
        checkReceptionAdapter = new CheckReceptionAdapter(this,mList);
        checkReceptionAdapter.setOnItemClickListener(this);
        checkReceptionAdapter.setOnChooseListener(this);
        recyclerView.setAdapter(checkReceptionAdapter);
        presenter.loadData();//用于加载数据
    }

    @Override
    public void loadOk(boolean isUndeal, Object object) {
        LogUtil.print("isUndeal"+isUndeal);
        if(isUndeal){//待处理加载成功
            undealList = (List<CheckReceData>)object;
            setPageData(isUndeal,undealList,PageState.PageOk);
        }else{//已处理加载成功
            dealedList = (List<CheckReceData>)object;
            setPageData(isUndeal,dealedList,PageState.PageOk);
        }
    }

    @Override
    public void loadFail(boolean isUndeal) {
        setPageData(isUndeal,null,PageState.PageFail);
    }

    @Override
    public void loadNoData(boolean isUndeal) {
        if(isUndeal){
            undealList = new ArrayList<>();
        }else{
            dealedList = new ArrayList<>();
        }
        setPageData(isUndeal,null,PageState.PageNoData);
    }

    public void setPageData(boolean isUndeal, List<CheckReceData> mList, int pageState){
        CheckReceData preorderData = showList.get(0);
        showList = new ArrayList<>();
        showList.add(preorderData);
        if(pageState == PageState.PageWaiting){
            if(isUndeal == isUndealCheck){//等处理数据
                showList.add(new CheckReceData(false,pageState));
                checkReceptionAdapter.updateDatas(showList);
            }
        }else if(pageState == PageState.PageFail){
            if(isUndeal == isUndealCheck){//等处理数据
                showList.add(new CheckReceData(false,pageState));
                checkReceptionAdapter.updateDatas(showList);
                closeFresh(false);
            }
        }else if(pageState == PageState.PageNoData){
            if(isUndeal == isUndealCheck){//等处理数据
                showList.add(new CheckReceData(false,pageState));
                checkReceptionAdapter.updateDatas(showList);
                closeFresh(true);
            }
        }else if(pageState == PageState.PageOk){
            if(isUndeal == isUndealCheck){
                LogUtil.print("load undeal page");
                showList.addAll(mList);
                checkReceptionAdapter.updateDatas(showList);
                closeFresh(true);
            }
        }
    }
    public void closeFresh(final boolean isFresh){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                xRefreshView.stopRefresh(isFresh);
            }
        });
    }

    @Override
    public void onItemClick(CheckReceData preorderData, int pos) {
        if(isUndealCheck){
            Intent intent = new Intent(this, CheckReceDetailActivity.class);
            Bundle bd = new Bundle();
            bd.putString("title","查看处方");
            bd.putString("loadUrl","JKSDoctor/verifyPrescription/verifyPrescription.html");
            bd.putString("intent","loadasset");
            bd.putString("orderId",preorderData.getId());
            intent.putExtras(bd);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, LookReceDetailActivity.class);
            Bundle bd = new Bundle();
            bd.putString("title","查看处方");
            bd.putString("loadUrl","JKSDoctor/seePrescription/seePrescription.html");
            bd.putString("intent","loadasset");
            bd.putString("orderId",preorderData.getId());
            intent.putExtras(bd);
            startActivity(intent);
        }
    }

    @Override
    public void onChoose(boolean isUndeal) {
        isUndealCheck = isUndeal;
        CheckReceData preorderData = showList.get(0);
        preorderData.setCheckState(isUndeal);
        if(isUndeal){
            if(undealList != null){
                if(undealList.size() == 0){
                    loadNoData(isUndeal);
                }else{
                    loadOk(isUndeal,undealList);
                }
            }else{
                loadFail(isUndeal);
            }
            LogUtil.print("undeal");
        }else{
            if(dealedList != null){
                if(dealedList.size() == 0){
                    loadNoData(isUndeal);
                }else{
                    loadOk(isUndeal,dealedList);
                }
            }else{
                loadFail(isUndeal);
            }
            LogUtil.print("dealed");
        }
        checkReceptionAdapter.updateDatas(showList);
    }

    /*activity活动默认配置*/
    @OnClick({R.id.web_title_return})
    void userClick(View view){
        switch (view.getId()){
            case R.id.web_title_return:
                scrollToFinishActivity();
                break;
        }
    }

    class NotifyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Global.checkReceAction)){
                String orderId = intent.getExtras().getString("orderId");
                int index = findEle(orderId);//打到要删除的数据并移除
                if(index != -1){
                    CheckReceData checkReceData = showList.get(0);
                    showList = new ArrayList<>();
                    showList.add(checkReceData);
                    if(undealList.size() <= 0){//没有数据时
                        showList.add(1,new CheckReceData(false, PageState.PageNoData));//没有数据的cell
                    }else{
                        showList.addAll(undealList);
                    }
                    checkReceptionAdapter.deleData(showList,index+1);
                }
            }
        }
    }

    private int findEle(String orderId){
        int index = -1;
        for(int i=0;i<undealList.size();i++){
            if(undealList.get(i).getId().equals(orderId)){
                index = i;
            }
        }
        if(index != -1){
            dealedList.add(undealList.get(index));
            undealList.remove(index);
        }
        return index;
    }
    private void initReceiver(){
        if(notifyReceiver == null){
            notifyReceiver = new NotifyReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(Global.checkReceAction);
            registerReceiver(notifyReceiver,filter);
        }
    }

    private void removeReceiver(){
        if(notifyReceiver != null){
            unregisterReceiver(notifyReceiver);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) )
        {
            scrollToFinishActivity();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void swipeBackCallback() {
        removeReceiver();
    }
}
