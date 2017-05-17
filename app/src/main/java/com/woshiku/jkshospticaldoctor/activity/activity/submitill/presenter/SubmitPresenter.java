package com.woshiku.jkshospticaldoctor.activity.activity.submitill.presenter;

import android.content.Intent;

import com.woshiku.jkshospticaldoctor.activity.domain.SubmitIllData;

import java.util.List;

/**
 * Created by admin on 2017-05-11.
 */

public interface SubmitPresenter {
    void initPage();
    void takePhoto();
    void dealActivityReturn(int requestCode, int resultCode, Intent data);
    void compressPics(List<SubmitIllData> oneList, List<SubmitIllData> twoList);//压缩图片
    void uploadMultiPic(List<SubmitIllData> dataList);
    void submitOrder(String orderId,String bls,String cfs);
}
