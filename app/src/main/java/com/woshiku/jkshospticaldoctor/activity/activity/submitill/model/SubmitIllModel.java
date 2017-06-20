package com.woshiku.jkshospticaldoctor.activity.activity.submitill.model;

import android.app.Activity;
import android.content.Intent;

import com.woshiku.jkshospticaldoctor.activity.domain.SubmitIllData;

import java.util.List;

/**
 * Created by admin on 2017-05-11.
 */

public interface SubmitIllModel {
    void takePhoto();
    void dealActivityReturn(int requestCode, int resultCode, Intent data);
    void compressPics(List<SubmitIllData> oneList,List<SubmitIllData> twoList);
    void uploadMultiPics(List<SubmitIllData> picList);
    void submitOrder(String orderId,String bls,String cfs);
    interface SubmitIllListener{
        void onInitPage();
        void onPhotoSuccess(boolean isOk,String picPath);
        void compressedFinish(List<SubmitIllData> dataList);
        Activity onGetActivity();
        void uploadPicResult(boolean isSucc,List<SubmitIllData> dataList);
        void submitOrderResult(boolean isOk,Object object);
    }
}
