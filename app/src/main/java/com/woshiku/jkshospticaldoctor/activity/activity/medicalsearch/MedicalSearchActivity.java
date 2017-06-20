package com.woshiku.jkshospticaldoctor.activity.activity.medicalsearch;

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
import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.BaseActivity;
import com.woshiku.jkshospticaldoctor.activity.activity.medicalsearch.presenter.MedicalPresenter;
import com.woshiku.jkshospticaldoctor.activity.activity.medicalsearch.presenter.MedicalPresenterImple;
import com.woshiku.jkshospticaldoctor.activity.activity.medicalsearch.view.MedicalSearchView;
import com.woshiku.jkshospticaldoctor.activity.adapter.MedicalSearchAdapter;
import com.woshiku.jkshospticaldoctor.activity.domain.MedicalSearchData;
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
 * Created by admin on 2017-04-28.
 */

public class MedicalSearchActivity extends BaseActivity implements MedicalSearchView, MedicalSearchAdapter.TextChangeListener, MedicalSearchAdapter.OnItemClickListener {
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
    List<MedicalSearchData> showList;
    MedicalSearchAdapter preorderAdapter;
    String searchText = "";
    @Override
    protected void initViews() {
        setContentView(R.layout.activity_medical_search);
        ButterKnife.inject(this);
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
        titleView.setText("药品搜索");
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
            showList.add(0,new MedicalSearchData(-1));//初始化用于等待
            showList.add(1,new MedicalSearchData(PageState.PageWaiting));
            initDatas(showList);
        }
    }

    private void initDatas(List<MedicalSearchData> mList){
        preorderAdapter = new MedicalSearchAdapter(this,mList);
        preorderAdapter.setTextChangeListener(this);
        preorderAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(preorderAdapter);
        presenter.loadData(searchText);//用于第一次加载数据
    }
    @Override
    public void loadOk(Object object) {
        LogUtil.print("obj:"+object.toString());
        setPageData((List<MedicalSearchData>)object,PageState.PageOk);
    }

    @Override
    public void loadFail() {
        setPageData(null,PageState.PageFail);
    }

    @Override
    public void loadNoData() {
        setPageData(null,PageState.PageNoData);
    }
    public void setPageData(List<MedicalSearchData> mList, int pageState){
        MedicalSearchData medicalSearchData = showList.get(0);
        showList = new ArrayList<>();
        showList.add(medicalSearchData);
        if(pageState == PageState.PageWaiting){
            showList.add(new MedicalSearchData(pageState));
            preorderAdapter.updateDatas(showList);
        }else if(pageState == PageState.PageFail){
            showList.add(new MedicalSearchData(pageState));
            preorderAdapter.updateDatas(showList);
            closeFresh(false);
        }else if(pageState == PageState.PageNoData){
            showList.add(new MedicalSearchData(pageState));
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
    public void textChange(String text) {
        searchText = text;
        presenter.loadData(text);
    }

    @Override
    public void onItemClick(final MedicalSearchData medicalSearchData) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String[] datas = {medicalSearchData.getNo(),medicalSearchData.getMedicalName(),medicalSearchData.getStandard(),
                    medicalSearchData.getUnit(),medicalSearchData.getAmount(),medicalSearchData.getEleOne(),medicalSearchData.getEleTwo(),
                medicalSearchData.getOrder()};
                Gson gson = new Gson();
                String res = gson.toJson(datas);
                LogUtil.print("gson:"+res);
                Intent intent = new Intent();
                Bundle bd = new Bundle();
                bd.putString("res",res);
                intent.putExtras(bd);
                setResult(Global.medicalSearchReurn,intent);
                scrollToFinishActivity();
            }
        });
    }
}
