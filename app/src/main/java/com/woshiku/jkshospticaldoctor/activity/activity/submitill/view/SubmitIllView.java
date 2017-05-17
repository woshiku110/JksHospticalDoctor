package com.woshiku.jkshospticaldoctor.activity.activity.submitill.view;

import android.app.Activity;

import com.woshiku.jkshospticaldoctor.activity.domain.SubmitIllData;

import java.util.List;

/**
 * Created by admin on 2017-05-11.
 */

public interface SubmitIllView {
    void setInitPage();
    void getPhotoResult(boolean isOk,String path);
    void picsPressedOk(List<SubmitIllData> submitIllDatas);
    void picsUploadResult(boolean isOk,List<SubmitIllData> dataList);
    void submitOrderResult(boolean isOk, Object object);
    Activity setOnActivity();
}
