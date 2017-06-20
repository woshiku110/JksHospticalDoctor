package com.woshiku.jkshospticaldoctor.activity.activity.submitill.presenter;

import android.app.Activity;
import android.content.Intent;
import com.woshiku.jkshospticaldoctor.activity.activity.submitill.model.SubmitIllModel;
import com.woshiku.jkshospticaldoctor.activity.activity.submitill.model.SubmitModelImple;
import com.woshiku.jkshospticaldoctor.activity.activity.submitill.view.SubmitIllView;
import com.woshiku.jkshospticaldoctor.activity.domain.SubmitIllData;
import java.util.List;

/**
 * Created by admin on 2017-05-11.
 */

public class SubmitPresenterImple implements SubmitPresenter,SubmitIllModel.SubmitIllListener{
    SubmitIllView submitIllView;
    SubmitIllModel submitIllModel;

    public SubmitPresenterImple(SubmitIllView submitIllView) {
        this.submitIllView = submitIllView;
        submitIllModel = new SubmitModelImple(this);
    }

    @Override
    public void initPage() {
        onInitPage();
    }

    @Override
    public void takePhoto() {
        submitIllModel.takePhoto();
    }

    @Override
    public void dealActivityReturn(int requestCode, int resultCode, Intent data) {
        submitIllModel.dealActivityReturn(requestCode,resultCode,data);
    }

    @Override
    public void compressPics(List<SubmitIllData> oneList, List<SubmitIllData> twoList) {
        submitIllModel.compressPics(oneList,twoList);
    }

    @Override
    public void uploadMultiPic(List<SubmitIllData> dataList) {
        submitIllModel.uploadMultiPics(dataList);
    }

    @Override
    public void submitOrder(String orderId, String bls, String cfs) {
        submitIllModel.submitOrder(orderId,bls,cfs);
    }

    /*以下是视图接口*/
    @Override
    public void onInitPage() {
        if(submitIllView != null){
           submitIllView.setInitPage();
        }
    }

    @Override
    public void onPhotoSuccess(boolean isOk, String picPath) {
        if(submitIllView != null){
            submitIllView.getPhotoResult(isOk,picPath);
        }
    }

    @Override
    public void compressedFinish(List<SubmitIllData> dataList) {
        if(submitIllView != null){
            submitIllView.picsPressedOk(dataList);
        }
    }

    @Override
    public Activity onGetActivity() {
        if(submitIllView != null){
            return submitIllView.setOnActivity();
        }
        return null;
    }

    @Override
    public void uploadPicResult(boolean isSucc, List<SubmitIllData> dataList) {
        if(submitIllView != null){
            submitIllView.picsUploadResult(isSucc,dataList);
        }
    }

    @Override
    public void submitOrderResult(boolean isOk, Object object) {
        if(submitIllView != null){
            submitIllView.submitOrderResult(isOk,object);
        }
    }
}
