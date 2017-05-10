package com.woshiku.jkshospticaldoctor.activity.activity.checkticket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.andview.refreshview.XRefreshView;
import com.google.gson.Gson;
import com.woshiku.dialoglib.AZView;
import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.BaseActivity;
import com.woshiku.jkshospticaldoctor.activity.activity.checkticket.presenter.CheckTicketPresenter;
import com.woshiku.jkshospticaldoctor.activity.activity.checkticket.presenter.CheckTicketPresenterImple;
import com.woshiku.jkshospticaldoctor.activity.activity.checkticket.view.CheckTicketView;
import com.woshiku.jkshospticaldoctor.activity.adapter.CheckTicketAdapter;
import com.woshiku.jkshospticaldoctor.activity.domain.CheckTicketData;
import com.woshiku.jkshospticaldoctor.activity.inter.PageState;
import com.woshiku.jkshospticaldoctor.activity.utils.AppManager;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import common.Global;
import me.imid.swipebacklayout.lib.SwipeBackLayout;

/**
 * Created by admin on 2017-05-05.
 */

public class CheckTicketActivity extends BaseActivity implements CheckTicketView, CheckTicketAdapter.OnItemClickListener, AZView.AzClickListener {
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
    @InjectView(R.id.check_ticket_azview)
    AZView azView;
    LinearLayoutManager layoutManager;
    CheckTicketPresenter presenter;
    List<CheckTicketData> showList;
    CheckTicketAdapter checkTicketAdapter;
    @InjectView(R.id.web_title_concert_txt)
    TextView concertTextView;
    String searchText = "1";
    boolean isSubmit = false;
    boolean isHoldDialog = false;//是不是从候诊数据
    String orderId;//订单Id
    @Override
    protected void initViews() {
        setContentView(R.layout.activity_checkticket);
        ButterKnife.inject(this);
        presenter = new CheckTicketPresenterImple(this);
        presenter.initPage();
    }

    @Override
    protected void swipeBackCallback() {

    }

    @Override
    public void setInitPage() {
        setScrollDirection(SwipeBackLayout.EDGE_LEFT);//设置手势方向
        setGesture(true);//设置可以滑动
        titleView.setText("开检查单");
        concertTextView.setText("确定");
        concert_bt.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        xRefreshView.setPinnedTime(200);//设置刷新完成以后，headerview固定的时间
        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                presenter.loadData(searchText);
            }
        });
        presenter.firstLoadingPage();//第一次加载数据
        try{
            isSubmit = getIntent().getExtras().getBoolean("isChecked");
            orderId = getIntent().getExtras().getString("orderId");
        }catch (Exception e){

        }
        try{
            if(getIntent().getExtras().getString("intent").equals("holddialogdetail")){
                isHoldDialog = true;
            }
        }catch (Exception e){

        }
    }

    @Override
    public void loadingPage(boolean isFirst) {
        if(isFirst){
            showList = new ArrayList<>();
            showList.add(0,new CheckTicketData(PageState.PageWaiting));
            initDatas(showList);
        }
    }

    private void initDatas(List<CheckTicketData> mList){
        checkTicketAdapter = new CheckTicketAdapter(this,mList);
        checkTicketAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(checkTicketAdapter);
        azView.setAzClickListener(this);
        presenter.loadData(searchText);//用于第一次加载数据
    }

    @Override
    public void loadOk(Object object) {
        setPageData((List<CheckTicketData>)object,PageState.PageOk);
    }

    @Override
    public void loadFail() {
        setPageData(null,PageState.PageFail);
    }

    @Override
    public void loadNoData() {
        setPageData(null,PageState.PageNoData);
    }

    @Override
    public void dialogOpen() {
        openDialog();
    }

    @Override
    public void dialogClose() {
        closeDialog();
    }
    /**
     * @desc 用户提交检查单返回的结果
     * */
    @Override
    public void submitPageState(boolean isOk) {
        if(isOk){
            if(isHoldDialog){//是从候诊进入的
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        scrollToFinishActivity();
                    }
                });
            }else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            AppManager.getAppManager()
                                    .finishActivity("com.woshiku.jkshospticaldoctor.activity.activity.dealedticketdetail.DealedConfirmDetailActivity");//关闭上一个activity
                            Thread.sleep(500);
                            Intent intent = new Intent(Global.mainAction);
                            Bundle bundle = new Bundle();
                            bundle.putString("intent","checkUndeal");//用于区分其它的意图
                            bundle.putString("orderId",orderId);
                            intent.putExtras(bundle);
                            sendBroadcast(intent);
                            scrollToFinishActivity();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }else{
            toastShort("提交失败");
        }
    }

    public void setPageData(List<CheckTicketData> mList, int pageState){
        showList = new ArrayList<>();
        if(pageState == PageState.PageWaiting){
            showList.add(new CheckTicketData(pageState));
            checkTicketAdapter.updateDatas(showList);
        }else if(pageState == PageState.PageFail){
            showList.add(new CheckTicketData(pageState));
            checkTicketAdapter.updateDatas(showList);
            closeFresh(false);
        }else if(pageState == PageState.PageNoData){
            showList.add(new CheckTicketData(pageState));
            checkTicketAdapter.updateDatas(showList);
            closeFresh(false);
        }else if(pageState == PageState.PageOk){
            showList.addAll(mList);
            LogUtil.print("page ok ...........");
            checkTicketAdapter.updateDatas(showList);
            closeFresh(true);
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

    /*activity活动默认配置*/
    @OnClick({R.id.web_title_return,R.id.web_title_right})
    void userClick(View view){
        switch (view.getId()){
            case R.id.web_title_return:
                scrollToFinishActivity();
                break;
            case R.id.web_title_right://确定点击
                if(!isHoldDialog){
                    if(isSubmit){
                        if(getSelectedIdsorNames(true).length>0){//没有选中数据
                            String ids = new Gson().toJson(getSelectedIdsorNames(true));
                            String names = new Gson().toJson(getSelectedIdsorNames(false));
                            presenter.submitData(ids,names);
                        }else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    scrollToFinishActivity();
                                }
                            });
                        }
                    }else{
                        Bundle bd = new Bundle();
                        bd.putStringArray("ids",getSelectedIdsorNames(true));
                        bd.putStringArray("names",getSelectedIdsorNames(false));
                        Intent intent = new Intent();
                        intent.putExtras(bd);
                        setResult(Global.checkTicketReturn,intent);
                        scrollToFinishActivity();
                    }
                }else{//从候诊界面进入
                    if(getSelectedIdsorNames(true).length>0){//没有选中数据
                        String ids = new Gson().toJson(getSelectedIdsorNames(true));
                        String names = new Gson().toJson(getSelectedIdsorNames(false));
                        presenter.submitData(ids,names);
                    }else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                scrollToFinishActivity();
                            }
                        });
                    }
                }
                break;
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
    public void onItemClick(CheckTicketData checkTicketData, final int pos) {
        if(checkTicketData.getPageState() == PageState.PageOk){
            if(!checkTicketData.isMark()){//表示是没有被选择的数据
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(showList != null){
                            try{
                                showList.get(pos).setChoosed(!showList.get(pos).isChoosed());
                                checkTicketAdapter.updateData(showList,pos);
                            }catch (Exception e){
                                e.toString();
                            }
                        }
                    }
                });
            }
        }
    }

    /**
     * @desc 排序View的点击事件
     * */
    @Override
    public void azClick(String cc) {
        int index = findElementIndex(cc);
        if(index != -1){
            moveToPosition(layoutManager,recyclerView,index);
        }
    }

    /**
     * RecyclerView 移动到当前位置，
     *
     * @param manager   设置RecyclerView对应的manager
     * @param mRecyclerView  当前的RecyclerView
     * @param n  要跳转的位置
     */
    public static void moveToPosition(LinearLayoutManager manager, RecyclerView mRecyclerView, int n) {
        int firstItem = manager.findFirstVisibleItemPosition();
        int lastItem = manager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            mRecyclerView.scrollToPosition(n);
        } else if (n <= lastItem) {
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.scrollBy(0, top);
        } else {
            mRecyclerView.scrollToPosition(n);
        }

    }

    /**
     * @desc 找到元素的索引
     * */
    private int findElementIndex(String cc){
        int index = -1;
        for(int i=0;i<showList.size();i++){
            if(showList.get(i).getIndexLetter().equals(cc)){
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * 获取所有选择的检查项目
     * */
    private String[] getSelectedIdsorNames(boolean isIds){
        List<String> idsList = new ArrayList<>();
        List<String> namesList = new ArrayList<>();
        if(showList != null){
            for(int i=0;i<showList.size();i++){
                if(showList.get(i).isChoosed()){
                    idsList.add(showList.get(i).getId());
                    namesList.add(showList.get(i).getName());
                }
            }
        }
        if(isIds){
            return idsList.toArray(new String[idsList.size()]);
        }else{
            return namesList.toArray(new String[idsList.size()]);
        }
    }
}
