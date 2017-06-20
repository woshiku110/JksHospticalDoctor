package com.woshiku.jkshospticaldoctor.activity.activity.revisepersonalinfo.model;

import android.app.Activity;

/**
 * Created by admin on 2017-05-14.
 */

public interface ReviseModel {
    void uploadFile(String picPath);
    interface ReviseModelListener{
        Activity onGetActivity();
        void uploadPicResult(boolean isOk,String icon);
    }
}
