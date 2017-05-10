package com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.preorder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.andview.refreshview.XRefreshView;
import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.activity.dealedticketdetail.DealedConfirmDetailActivity;
import com.woshiku.jkshospticaldoctor.activity.activity.reception.AppointReceActivity;
import com.woshiku.jkshospticaldoctor.activity.adapter.fragment.PreorderAdapter;
import com.woshiku.jkshospticaldoctor.activity.domain.PreorderData;
import com.woshiku.jkshospticaldoctor.activity.fragment.BaseFragment;
import com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.preorder.presenter.PreorderPresenter;
import com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.preorder.presenter.PreorderPresenterImple;
import com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.preorder.view.PreorderView;
import com.woshiku.jkshospticaldoctor.activity.inter.PageState;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
/**
 * Created by admin on 2017-04-17.
 * @desc 预约列表fragment
 */

@SuppressLint("ValidFragment")
public class PreorderFragment extends BaseFragment implements PreorderView, PreorderAdapter.OnItemClickListener, PreorderAdapter.OnChooseListener {
    View mView;
    @InjectView(R.id.preorder_xrecycle)
    XRefreshView xRefreshView;
    @InjectView(R.id.preorder_recycle)
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    PreorderAdapter preorderAdapter;
    PreorderPresenter preorderPresent;
    boolean isUndealCheck = true;
    List<PreorderData> undealList;//等接单数据
    List<PreorderData> dealedList;//已接单数据
    List<PreorderData> showList;

    @SuppressLint("ValidFragment")
    public PreorderFragment(FragmentActivity mActivity) {
        super(mActivity);
    }

    @Override
    public View initViews() {
        mView = View.inflate(getContext(), R.layout.fragment_main_preorder,null);
        ButterKnife.inject(this,mView);
        preorderPresent = new PreorderPresenterImple(this);
        preorderPresent.initPage();
        return mView;
    }

    /*以下是preorderview视图层*/
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
                preorderPresent.loadUndealData();
                preorderPresent.loadDealedData();
            }
        });
        preorderPresent.firstLoadingPage();//第一次加载数据
    }

    @Override
    public void loadingPage(boolean isFirst) {
        if(isFirst){//第一次加载页面
            showList = new ArrayList<>();
            showList.add(0,new PreorderData(true));
            showList.add(1,new PreorderData(false, PageState.PageWaiting));//初始化用于等待
            initData(showList);
        }
    }

    @Override
    public void loadOk(boolean isUndeal, Object object) {//数据加载成功
        LogUtil.print("isUndeal"+isUndeal);
        if(isUndeal){//待处理加载成功
            undealList = (List<PreorderData>)object;
            setPageData(isUndeal,undealList,PageState.PageOk);
        }else{//已处理加载成功
            dealedList = (List<PreorderData>)object;
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
    private void initData(List<PreorderData> mList){
        preorderAdapter = new PreorderAdapter(mActivity,mList);
        preorderAdapter.setOnItemClickListener(PreorderFragment.this);
        preorderAdapter.setOnChooseListener(this);
        recyclerView.setAdapter(preorderAdapter);
        preorderPresent.loadUndealData();//用于加载待处理数据
        preorderPresent.loadDealedData();//用于处理已加载处理数据
    }
    public void setPageData(boolean isUndeal,List<PreorderData> mList,int pageState){
        PreorderData preorderData = showList.get(0);
        showList = new ArrayList<>();
        showList.add(preorderData);
        if(pageState == PageState.PageWaiting){
            if(isUndeal == isUndealCheck){//等处理数据
                showList.add(new PreorderData(false,pageState));
                preorderAdapter.updateDatas(showList);
            }
        }else if(pageState == PageState.PageFail){
            if(isUndeal == isUndealCheck){//等处理数据
                showList.add(new PreorderData(false,pageState));
                preorderAdapter.updateDatas(showList);
                closeFresh(false);
            }
        }else if(pageState == PageState.PageNoData){
            if(isUndeal == isUndealCheck){//等处理数据
                showList.add(new PreorderData(false,pageState));
                preorderAdapter.updateDatas(showList);
                closeFresh(true);
            }
        }else if(pageState == PageState.PageOk){
            LogUtil.print("is undeal eq:"+(isUndeal == isUndealCheck));
            if(isUndeal == isUndealCheck){
                LogUtil.print("load undeal page");
                showList.addAll(mList);
                preorderAdapter.updateDatas(showList);
                closeFresh(true);
            }
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
    /**
     * @desc 用于recycleview 子item点击时调用
     * */
    @Override
    public void onItemClick(PreorderData preorderData, int pos) {
        if(isUndealCheck){
            Intent intent = new Intent(mActivity, AppointReceActivity.class);
            Bundle bd = new Bundle();
            bd.putString("title","预约接诊");
            bd.putString("loadUrl","JKSDoctor/MentDiagnose/MentDiagnose.html");
            bd.putString("intent","loadasset");
            bd.putString("orderId",preorderData.getId());
            intent.putExtras(bd);
            startActivity(intent);
        }else{
            Intent intent = new Intent(mActivity, DealedConfirmDetailActivity.class);
            Bundle bd = new Bundle();
            bd.putString("title","预约接诊");
            bd.putString("loadUrl","JKSDoctor/MentDiagnose/MentDiagnose.html");
            bd.putString("intent","loadasset");
            bd.putString("orderId",preorderData.getId());
            int amount = 0;
            try{
                amount = Integer.parseInt(preorderData.getAmount());
            }catch (Exception e){

            }
            bd.putInt("amount",amount);
            intent.putExtras(bd);
            startActivity(intent);
        }
    }

    /**
     * @desc 用于选项点击时调用
     * **/
    @Override
    public void onChoose(boolean isUndeal) {
        isUndealCheck = isUndeal;
        PreorderData preorderData = showList.get(0);
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
                    LogUtil.print("dealed:"+dealedList.toString());
                }
            }else{
                loadFail(isUndeal);
            }
            LogUtil.print("dealed");
        }
        preorderAdapter.updateDatas(showList);
    }

    @Override
    protected void dealBroadcastRece(Intent intent) {
        Bundle bundle = intent.getExtras();
        String intentStr = bundle.getString("intent");
        if(intentStr.equals("preorderUndeal")){
            final String orderId = bundle.getString("orderId");
            LogUtil.print("删除orderId"+orderId);
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    deleData(isUndealCheck,orderId);
                }
            });
        }else if(intentStr.equals("preorderCheck")){//用户提交了订单并且开了检查单
            int amount = bundle.getInt("checkAmount");
            String orderId = bundle.getString("orderId");
            dealConfirmOrder(amount,orderId);
        }else if(intentStr.equals("checkUndeal")){
            final String orderId = bundle.getString("orderId");
            updateDealedScreen(orderId);
        }
    }

    private void deleData(boolean isUndeal,String orderId){
        LogUtil.print("isUndeal"+isUndeal+"\t"+"orderId:"+orderId);
        int index = -1;
        if(isUndeal){
            index = getDeleIndex(undealList,orderId);
        }else{
            index = getDeleIndex(dealedList,orderId);
        }
        if(index == -1){
            return;
        }
        LogUtil.print("isUndeal two"+isUndeal+"\t"+"orderId:"+orderId);
        if(isUndeal){
            undealList.remove(index);
            PreorderData preorderData = showList.get(0);
            showList = new ArrayList<>();
            showList.add(preorderData);
            if(undealList.size() >= 1){
                showList.addAll(undealList);
            }else{
                showList.add(new PreorderData(false,PageState.PageNoData));
            }
            preorderAdapter.deleData(showList,index+1);
        }else{
            dealedList.remove(index);
            PreorderData preorderData = showList.get(0);
            showList = new ArrayList<>();
            showList.add(preorderData);
            if(dealedList.size() >= 1){
                showList.addAll(dealedList);
            }else{
                showList.add(new PreorderData(true,PageState.PageNoData));
            }
            preorderAdapter.deleData(showList,index+1);
        }
    }
    /**
     * @desc 用于处理确认订单并且改变列表
     * */
    private void dealConfirmOrder(int amount,String orderId){
        int index = getDeleIndex(undealList,orderId);
        if(index == -1){
            return;
        }
        PreorderData preorderData = undealList.get(index);
        preorderData.setChoose(false);
        LogUtil.print("confirm order:"+preorderData.toString());
        preorderData.setAmount(amount+"");
        undealList.remove(index);
        dealedList.add(0,preorderData);
        LogUtil.print("dealed confirm:"+dealedList.toString());
        PreorderData firstData = showList.get(0);
        showList = new ArrayList<>();
        if(isUndealCheck){//待处理
            showList.add(firstData);
            if(undealList.size() >= 1){
                showList.addAll(undealList);
            }else{
                showList.add(new PreorderData(false,PageState.PageNoData));
            }
        }else{//已处理
            showList.add(firstData);
            if(dealedList.size() >= 1){
                showList.addAll(dealedList);
            }else{
                showList.add(new PreorderData(true,PageState.PageNoData));
            }
        }
        preorderAdapter.confirmUpdateData(isUndealCheck,showList,index+1);
    }
    /**
     * @desc 更新检查单已处理界面，为检查状态。
     * */
    private void updateDealedScreen(String orderId){
        int index = getDeleIndex(dealedList,orderId);
        if(index == -1){
            return;
        }
        dealedList.get(index).setAmount("2");
        PreorderData preorderData = showList.get(0);
        showList = new ArrayList<>();
        showList.add(preorderData);
        if(dealedList.size() >= 1){
            showList.addAll(dealedList);
        }else{
            showList.add(new PreorderData(true,PageState.PageNoData));
        }
        preorderAdapter.updateDealedScreen(showList,index+1);
    }
    //得到要删除用的索引
    private int getDeleIndex(List<PreorderData> newDatas,String orderId){
        int index = -1;
        for(int i=0;i<newDatas.size();i++){
            if(newDatas.get(i).getId().equals(orderId)){
                index = i;
                break;
            }
        }
        return index;
    }
}
