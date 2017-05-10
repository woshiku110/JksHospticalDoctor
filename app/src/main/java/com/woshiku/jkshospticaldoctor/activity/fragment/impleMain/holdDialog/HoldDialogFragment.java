package com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.holdDialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.andview.refreshview.XRefreshView;
import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.activity.holddialogdetail.HoldDialogDetailActivity;
import com.woshiku.jkshospticaldoctor.activity.adapter.fragment.HoldDialogAdapter;
import com.woshiku.jkshospticaldoctor.activity.domain.HoldDialogData;
import com.woshiku.jkshospticaldoctor.activity.fragment.BaseFragment;
import com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.holdDialog.presenter.HoldDialogPresenter;
import com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.holdDialog.presenter.HoldDialogPresenterImple;
import com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.holdDialog.view.HoldDialogView;
import com.woshiku.jkshospticaldoctor.activity.inter.PageState;
import com.woshiku.jkshospticaldoctor.activity.main.MainActivity;
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
    private int selectedIndex;
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

    }

    @Override
    public void loadingPage(boolean isFirst) {
        if(isFirst){//第一次加载页面
            showList = new ArrayList<>();
            showList.add(0,new HoldDialogData(PageState.PageWaiting));//初始化用于等待
            initData(tidyDatas(showList));
        }
    }

    private void initData(List<HoldDialogData> mList){
        preorderAdapter = new HoldDialogAdapter(mActivity,mList);
        preorderAdapter.setOnItemClickListener(HoldDialogFragment.this);
        recyclerView.setAdapter(preorderAdapter);
        preorderPresent.loadData();//用于加载处理数据
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

    /**
     * 叫号
     * @param isSucc 是不是叫号成功
     * @param holdDialogData（候诊数据列表）
     * @return void
     */
    @Override
    public void doctorTreat(boolean isSucc,HoldDialogData holdDialogData) {
        if(isSucc){//叫号成功
            preorderAdapter.updateDatas(tidyDatas(changeDataList(showList,selectedIndex)));
            enterHoldDialogDetail(holdDialogData);
        }else{
            MainActivity mainUi = (MainActivity)mActivity;
            mainUi.toastShort("叫号失败,请重新叫号!!!");
        }
    }

    @Override
    public Activity onGetActivity() {
        return mActivity;
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
            preorderAdapter.updateDatas(tidyDatas(showList));
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
        selectedIndex = pos;//选中指定索引
        dealUserCall(preorderData);
    }


    @Override
    protected void dealBroadcastRece(Intent intent) {
        if(intent.getExtras().getString("intent").equals("jumpcommand")){//收到发送跳过命令
            preorderAdapter.updateDatas(recoverDataList(showList));
        }
    }
    /**
     * @Title: ${dealUserCall}
     * @Description: ${todo}(用户点击Item的动作处理)
     * @param: ${holdDialogData}
     * @return: ${void}
     * @throws null
     */
    private void dealUserCall(HoldDialogData holdDialogData){
        if(holdDialogData.isBtEnable()){//按钮可编辑
            if(holdDialogData.isReturnDialog()){//按钮是诊断
                enterHoldDialogDetail(holdDialogData);//进入诊断活动
            }else{//按钮是叫号
                preorderPresent.doctorTreatMent(holdDialogData);//医生点击叫号
            }
        }
    }
    /**
     * (这里用一句话描述这个方法的作用)
     * @param preorderData 用于传入参数
     * @return void
     */
    private void enterHoldDialogDetail(HoldDialogData preorderData){
        Intent intent = new Intent(mActivity, HoldDialogDetailActivity.class);
        Bundle bd = new Bundle();
        bd.putString("title","预约接诊");
        bd.putString("loadUrl","JKSDoctor/MentDiagnose/MentDiagnose.html");
        bd.putString("intent","loadasset");
        bd.putString("orderId",preorderData.getId());
        intent.putExtras(bd);
        startActivity(intent);
    }

    /**
     * 整理数据,因为点击叫号时会变成正在候诊，对于其它Item的按钮应该变为不可点击状态
     * @param mList 表示要整理的数据
     * @return List
     * */
    private List tidyDatas(List<HoldDialogData> mList){
        int index = -1;
        for(int i=0;i<mList.size();i++){
            if(mList.get(i).getState().equals("6")){
                index = i;
                break;
            }
        }
        if(index != -1){//有返回状态索引
            for(int i=0;i<mList.size();i++){
                if(i == index){
                    mList.get(i).setBtEnable(true);
                    mList.get(i).setReturnDialog(true);//表示诊断
                }else{
                    mList.get(i).setBtEnable(false);
                }
            }
        }
        return mList;
    }

    /**
     * (用于恢复数据列表)
     * @param mList（旧数据也就是showList）
     * @return ${List<HoldDialogData>}(数据列表)
     */
    private List<HoldDialogData> recoverDataList(List<HoldDialogData> mList){
        for(int i=0;i<mList.size();i++){
            if(mList.get(i).getState().equals("6")){
                mList.get(i).setState("5");
            }
            mList.get(i).setBtEnable(true);
            mList.get(i).setReturnDialog(false);//表示诊断
        }
        return mList;
    }



    /**
     * 用于改变具体位置的索引
     * @param mList 要放入的参数
     * @return List<HoldDialogData>
     */
    private List<HoldDialogData> changeDataList(List<HoldDialogData> mList,int index){
        mList.get(index).setState("6");
        return mList;
    }
}
