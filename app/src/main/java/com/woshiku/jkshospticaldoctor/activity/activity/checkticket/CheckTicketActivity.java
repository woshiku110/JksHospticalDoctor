package com.woshiku.jkshospticaldoctor.activity.activity.checkticket;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.andview.refreshview.XRefreshView;
import com.woshiku.dialoglib.AZView;
import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.BaseActivity;
import com.woshiku.jkshospticaldoctor.activity.activity.checkticket.presenter.CheckTicketPresenter;
import com.woshiku.jkshospticaldoctor.activity.activity.checkticket.presenter.CheckTicketPresenterImple;
import com.woshiku.jkshospticaldoctor.activity.activity.checkticket.view.CheckTicketView;
import com.woshiku.jkshospticaldoctor.activity.adapter.CheckTicketAdapter;
import com.woshiku.jkshospticaldoctor.activity.domain.CheckTicketData;
import com.woshiku.jkshospticaldoctor.activity.inter.PageState;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
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
    String searchText = "1";
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
        concert_bt.setVisibility(View.INVISIBLE);
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
    @OnClick({R.id.web_title_return})
    void userClick(View view){
        switch (view.getId()){
            case R.id.web_title_return:
                scrollToFinishActivity();
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
}
