package com.woshiku.jkshospticaldoctor.activity.activity.submitill;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.woshiku.albumlibrary.activity.AlbumActivity;
import com.woshiku.dialoglib.ScaleImagePop;
import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.BaseActivity;
import com.woshiku.jkshospticaldoctor.activity.activity.submitill.presenter.SubmitPresenter;
import com.woshiku.jkshospticaldoctor.activity.activity.submitill.presenter.SubmitPresenterImple;
import com.woshiku.jkshospticaldoctor.activity.activity.submitill.view.SubmitIllView;
import com.woshiku.jkshospticaldoctor.activity.adapter.SubmitIllAdapter;
import com.woshiku.jkshospticaldoctor.activity.domain.SubmitIllData;
import com.woshiku.jkshospticaldoctor.activity.utils.AppManager;
import com.woshiku.jkshospticaldoctor.activity.utils.GridSpacingItemDecoration;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.jkshospticaldoctor.activity.view.PhotoView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import common.Global;
import me.imid.swipebacklayout.lib.SwipeBackLayout;

/**
 * Created by admin on 2017-05-11.
 * 提交病历
 */

public class SubmitIllnessActivity extends BaseActivity implements SubmitIllView, SubmitIllAdapter.OnItemClickListener, SubmitIllAdapter.OnItemDeleClickListener {
    //标题栏
    @InjectView(R.id.web_title_return)
    protected LinearLayout returnView;
    @InjectView(R.id.web_title_title)
    protected TextView titleView;
    @InjectView(R.id.web_title_right)
    protected LinearLayout concert_bt;
    @InjectView(R.id.multi_xrecycleview)
    RecyclerView recyclerView;
    @InjectView(R.id.multi_xrecycleview_one)
    RecyclerView recyclerViewOne;
    SubmitIllAdapter submitIllAdapter;
    SubmitIllAdapter submitIllAdapterOne;
    SubmitPresenter submitPresenter;
    List<SubmitIllData> illPhotoList;
    List<SubmitIllData> illPhotoListOne;

    boolean isTopAdapter = true;
    String orderId;
    @Override
    protected void initViews() {
        setContentView(R.layout.activity_submit_illness);
        ButterKnife.inject(this);
        submitPresenter = new SubmitPresenterImple(this);
        submitPresenter.initPage();
    }

    private void initDatas(){
        orderId = getIntent().getExtras().getString("orderId");
        illPhotoList = new ArrayList<>();
        illPhotoList.add(new SubmitIllData(true));
        illPhotoListOne = new ArrayList<>();
        illPhotoListOne.add(new SubmitIllData(true));
    }
    private void initRecycleAdapter(){
        submitIllAdapter = new SubmitIllAdapter(this,illPhotoList,"zero");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, getResources().getDimensionPixelSize(R.dimen.padding_middle), true));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(submitIllAdapter);
        submitIllAdapter.setOnItemClickListener(this);
        submitIllAdapter.setOnItemDeleClickListener(this);
        GridLayoutManager gridLayoutManagerOne = new GridLayoutManager(this,3);
        submitIllAdapterOne = new SubmitIllAdapter(this,illPhotoListOne,"one");
        recyclerViewOne.setLayoutManager(gridLayoutManagerOne);
        recyclerViewOne.addItemDecoration(new GridSpacingItemDecoration(3, getResources().getDimensionPixelSize(R.dimen.padding_middle), true));
        recyclerViewOne.setHasFixedSize(true);
        recyclerViewOne.setAdapter(submitIllAdapterOne);
        submitIllAdapterOne.setOnItemClickListener(this);
        submitIllAdapterOne.setOnItemDeleClickListener(this);
    }
    @Override
    protected void swipeBackCallback() {

    }

    /*以下上视图接口*/
    @Override
    public void setInitPage() {
        titleView.setText("提交病历");
        concert_bt.setVisibility(View.INVISIBLE);
        setScrollDirection(SwipeBackLayout.EDGE_LEFT);//设置手势方向
        setGesture(true);//设置可以滑动
        initDatas();
        initRecycleAdapter();
    }
    /**
     * @param isOk 拍照是否成功
     * @param  path 拍照成功后返回的路径
     */
    @Override
    public void getPhotoResult(boolean isOk, String path) {
        if(isOk){
            if(isTopAdapter){
                illPhotoList.add(illPhotoList.size()-1,new SubmitIllData(false,path));
                submitIllAdapter.addSingleData(illPhotoList,illPhotoList.size()-1);//插入一条新数据
            }else{
                illPhotoListOne.add(illPhotoListOne.size()-1,new SubmitIllData(false,path,true));
                submitIllAdapterOne.addSingleData(illPhotoListOne,illPhotoListOne.size()-1);//插入一条新数据
            }
        }
    }
    /**
     * @param submitIllDatas 图片压缩完成结果回调
     */
    @Override
    public void picsPressedOk(List<SubmitIllData> submitIllDatas) {
        submitPresenter.uploadMultiPic(submitIllDatas);
    }
    /**
     * @param  isOk 图片是否全部上传完成
     * @param dataList 上传数据完成后的上传数据
     */
    @Override
    public void picsUploadResult(boolean isOk, List<SubmitIllData> dataList) {
        if(isOk){
            LogUtil.print("upload",dataList.toString());
            toastShort("上传完成");
            String[] blList = getConditionList(dataList,true);
            String[] cfList = getConditionList(dataList,false);;
            submitPresenter.submitOrder(orderId,new Gson().toJson(blList),new Gson().toJson(cfList));
        }else{
            toastShort("图片上传失败,请重新上传!!!");
            closeDialog();
        }
    }
    /**
     * @param  isbl true表示病历 false表示处方
     * @return List<SubmitIllData>
     */
    private String[] getConditionList(List<SubmitIllData> mList,boolean isbl){
        List<String>  dataList = new ArrayList<>() ;
        for(SubmitIllData submitIllData:mList){
            if(isbl){
                if(!submitIllData.isPhotoType()){//false表示病历
                    dataList.add(submitIllData.getUploadReturnAddress());
                }
            }else{
                if(submitIllData.isPhotoType()){//true表示处方
                    dataList.add(submitIllData.getUploadReturnAddress());
                }
            }
        }
        return dataList.toArray(new String[dataList.size()]);
    }

    /**
     * @param  isOk 提交是否成功
     * @param object 提交的结果
     */
    @Override
    public void submitOrderResult(boolean isOk, Object object) {
        if(isOk){
            toastShort("提交成功");
            closeDialog();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AppManager.getAppManager().finishActivity("com.woshiku.jkshospticaldoctor.activity.activity.holddialogdetail.HoldDialogDetailActivity");
                    Intent tt = new Intent();
                    Bundle bd = new Bundle();
                    bd.putString("intent","deledata");
                    bd.putString("orderId",orderId);
                    tt.putExtras(bd);
                    sendBroadcast(tt);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    scrollToFinishActivity();
                }
            });
        }else{
            toastShort("提交失败");
        }
    }


    @Override
    public Activity setOnActivity() {
        return this;
    }

    /**
     * @param submitIllData 用户提交的数据
     */
    @Override
    public void onItemClick(SubmitIllData submitIllData,String name) {
        if(name.equals("zero")){
            isTopAdapter = true;
        }else{
            isTopAdapter = false;
        }
        if(submitIllData.isAddPhoto()){
            new PhotoView(this,concert_bt).addAlbumChooseListener(new PhotoView.AlbumChooseListener() {
                @Override
                public void albumChoose() {//相册选择
                    Intent intent = new Intent(SubmitIllnessActivity.this, AlbumActivity.class);
                    startActivityForResult(intent, Global.ACTION_ALBUMN_REQUEST);
                }
            }).addAlbumPhotoListener(new PhotoView.AlbumPhotoListener() {
                @Override
                public void albumPhoto() {//拍照片
                    submitPresenter.takePhoto();
                }
            }).showDialog();
        }else{
            new ScaleImagePop(this,concert_bt,submitIllData.getPath()).show();
        }
    }

    @Override
    public void onItemDeleClick(SubmitIllData submitIllData,String name) {
        if(name.equals("zero")){
            isTopAdapter = true;
        }else{
            isTopAdapter = false;
        }
        if(isTopAdapter){
            int index = findDeleIndex(illPhotoList,submitIllData);
            if(index != -1){
                illPhotoList.remove(index);
                submitIllAdapter.deleSingleData(illPhotoList,index);
            }
        }else{
            int index = findDeleIndex(illPhotoListOne,submitIllData);
            if(index != -1){
                illPhotoListOne.remove(index);
                submitIllAdapterOne.deleSingleData(illPhotoListOne,index);
            }
        }
    }



    /**
     * @param mList 相册列表
     * @return List<SubmitIllData> 返回的显示图片的地址
     */
    private List<SubmitIllData> getAlbumList(List<String> mList){
        List<SubmitIllData> dataList = new ArrayList<>();
        for(String str:mList){
            if(isTopAdapter){
                dataList.add(new SubmitIllData(false,str));
            }else{
                dataList.add(new SubmitIllData(false,str,true));//表示处方照片类型
            }
        }
        return dataList;
    }



    /**
     * @param  dataList 要用到的数据
     * @param submitIllData 要删除的数据
     */
    public int findDeleIndex(List<SubmitIllData> dataList,SubmitIllData submitIllData){
        int index = -1;
        for(int i=0;i<dataList.size();i++){
            if(submitIllData.getPath().equals(dataList.get(i).getPath())){
                index = i;
                break;
            }
        }
        return index;
    }

    /*activity活动默认配置*/
    @OnClick({R.id.web_title_return,R.id.submit_ill_bottom})
    void userClick(View view){
        switch (view.getId()){
            case R.id.web_title_return:
                scrollToFinishActivity();
                break;
            case R.id.submit_ill_bottom:
                if(illPhotoList.size()+illPhotoList.size()>2){
                    openDialog();
                    submitPresenter.compressPics(illPhotoList,illPhotoListOne);//压缩图片
                }else{
                    toastShort("请选择图片后再提交!!!");
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        submitPresenter.dealActivityReturn(requestCode,resultCode,data);
        if(resultCode == RESULT_OK){
            if(requestCode == Global.ACTION_ALBUMN_REQUEST){
                if(isTopAdapter){
                    List<SubmitIllData> dataList = getAlbumList(data.getExtras().getStringArrayList(Global.IMAGE_SELECTED_ADDR));
                    if(dataList.size()>0){
                        int firstIndex = illPhotoList.size()-1;//插入数据位置
                        illPhotoList.addAll(firstIndex,dataList);
                        submitIllAdapter.addMultiDatas(illPhotoList,firstIndex);
                    }
                }else{
                    List<SubmitIllData> dataList = getAlbumList(data.getExtras().getStringArrayList(Global.IMAGE_SELECTED_ADDR));
                    if(dataList.size()>0){
                        int firstIndex = illPhotoListOne.size()-1;//插入数据位置
                        illPhotoListOne.addAll(firstIndex,dataList);
                        submitIllAdapterOne.addMultiDatas(illPhotoListOne,firstIndex);
                    }
                }
            }
        }
    }
}
