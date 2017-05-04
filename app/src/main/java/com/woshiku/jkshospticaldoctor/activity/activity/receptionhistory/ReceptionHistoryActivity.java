package com.woshiku.jkshospticaldoctor.activity.activity.receptionhistory;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.andview.refreshview.XRefreshView;
import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.BaseActivity;
import com.woshiku.jkshospticaldoctor.activity.activity.receptionhistory.presenter.MedicalPresenter;
import com.woshiku.jkshospticaldoctor.activity.activity.receptionhistory.presenter.MedicalPresenterImple;
import com.woshiku.jkshospticaldoctor.activity.activity.receptionhistory.view.MedicalSearchView;
import com.woshiku.jkshospticaldoctor.activity.adapter.ReceHistoryAdapter;
import com.woshiku.jkshospticaldoctor.activity.domain.ReceHistoryData;
import com.woshiku.jkshospticaldoctor.activity.inter.PageState;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.imid.swipebacklayout.lib.SwipeBackLayout;

/**
 * Created by admin on 2017-04-29.
 */

public class ReceptionHistoryActivity  extends BaseActivity implements MedicalSearchView, ReceHistoryAdapter.TextChangeListener {
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
    LinearLayoutManager layoutManager;
    MedicalPresenter presenter;
    String searchText ="";
    List<ReceHistoryData> showList;
    ReceHistoryAdapter receHistoryAdapter;
    @Override
    protected void initViews() {
        setContentView(R.layout.activity_medical_search);
        ButterKnife.inject(this);
        LogUtil.print("rece history activity");
        presenter = new MedicalPresenterImple(this);
        presenter.initPage();
    }

    @Override
    protected void swipeBackCallback() {

    }

    @Override
    public void setInitPage() {
        setScrollDirection(SwipeBackLayout.EDGE_LEFT);//设置手势方向
        setGesture(true);//设置可以滑动
        titleView.setText("接诊历史");
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
        if(isFirst){//第一次加载数据
            showList = new ArrayList<>();
            showList.add(0,new ReceHistoryData(-1));//初始化用于等待
            showList.add(1,new ReceHistoryData(PageState.PageWaiting));
            initDatas(showList);
        }
    }
    private void initDatas(List<ReceHistoryData> mList){
        receHistoryAdapter = new ReceHistoryAdapter(this,mList);
        receHistoryAdapter.setTextChangeListener(this);
        recyclerView.setAdapter(receHistoryAdapter);
        presenter.loadData(searchText);//用于第一次加载数据
    }
    @Override
    public void loadOk(Object object) {
        setPageData((List<ReceHistoryData>)object,PageState.PageOk);
    }

    @Override
    public void loadFail() {
        setPageData(null,PageState.PageFail);
    }

    @Override
    public void loadNoData() {
        setPageData(null,PageState.PageNoData);
    }

    public void setPageData(List<ReceHistoryData> mList, int pageState){
        ReceHistoryData medicalSearchData = showList.get(0);
        showList = new ArrayList<>();
        showList.add(medicalSearchData);
        if(pageState == PageState.PageWaiting){
            showList.add(new ReceHistoryData(pageState));
            receHistoryAdapter.updateDatas(showList);
        }else if(pageState == PageState.PageFail){
            showList.add(new ReceHistoryData(pageState));
            receHistoryAdapter.updateDatas(showList);
            closeFresh(false);
        }else if(pageState == PageState.PageNoData){
            showList.add(new ReceHistoryData(pageState));
            receHistoryAdapter.updateDatas(showList);
            closeFresh(false);
        }else if(pageState == PageState.PageOk){
            showList.addAll(mList);
            LogUtil.print("page ok ...........");
            receHistoryAdapter.updateDatas(showList);
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

    @Override
    public void textChange(String text) {
        searchText = text;
        presenter.loadData(text);
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

}
