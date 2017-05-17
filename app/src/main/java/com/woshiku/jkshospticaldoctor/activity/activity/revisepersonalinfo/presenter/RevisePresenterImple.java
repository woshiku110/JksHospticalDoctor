package com.woshiku.jkshospticaldoctor.activity.activity.revisepersonalinfo.presenter;

import android.app.Activity;

import com.woshiku.jkshospticaldoctor.activity.activity.revisepersonalinfo.model.ReviseModel;
import com.woshiku.jkshospticaldoctor.activity.activity.revisepersonalinfo.model.ReviseModelImple;
import com.woshiku.jkshospticaldoctor.activity.activity.revisepersonalinfo.view.ReviseView;

/**
 * Created by admin on 2017-05-14.
 */

public class RevisePresenterImple implements RevisePresenter,ReviseModel.ReviseModelListener{
    ReviseView reviseView;
    ReviseModel reviseModel;

    public RevisePresenterImple(ReviseView reviseView) {
        this.reviseView = reviseView;
        reviseModel = new ReviseModelImple(this);
    }
    @Override
    public void uploadFile(String picPath) {
        reviseModel.uploadFile(picPath);
    }

    @Override
    public Activity onGetActivity() {
        if(reviseView != null){
            reviseView.getActivity();
        }
        return null;
    }

    @Override
    public void uploadPicResult(boolean isOk, String icon) {
        if(reviseView != null){
            reviseView.uploadPic(isOk,icon);
        }
    }


}
