package com.woshiku.jkshospticaldoctor.activity.activity.revisepersonalinfo.model;

import com.google.gson.Gson;
import com.woshiku.jkshospticaldoctor.activity.domain.SubmitIllData;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.jkshospticaldoctor.activity.utils.PicThreadManage;
import com.woshiku.urllibrary.domain.Result;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import common.Global;

/**
 * Created by admin on 2017-05-14.
 */

public class ReviseModelImple implements ReviseModel{
    ReviseModelListener reviseModelListener;

    public ReviseModelImple(ReviseModelListener reviseModelListener) {
        this.reviseModelListener = reviseModelListener;
    }

    @Override
    public void uploadFile(final String path) {
        PicThreadManage.getInstance().carry(new Runnable() {
            @Override
            public void run() {
                uploadSinglePic(Global.uploadUrl,Global.loginReturnData.id,path);
            }
        });
    }

    private void uploadSinglePic(String url, String userId,String path){
        RequestParams params = new RequestParams(url);
        params.setMultipart(true);
        params.addBodyParameter("upload",new File(path));
        params.addBodyParameter("userId",userId);
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                LogUtil.print("upload"+result);
                Gson gson = new Gson();
                Result rr = gson.fromJson(result,Result.class);
                if(rr.isSuccess()){
                    reviseModelListener.uploadPicResult(true,rr.getMsg());
                }else{
                    reviseModelListener.uploadPicResult(false,null);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                reviseModelListener.uploadPicResult(false,null);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
