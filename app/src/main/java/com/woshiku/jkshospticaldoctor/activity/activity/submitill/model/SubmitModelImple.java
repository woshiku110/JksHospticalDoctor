package com.woshiku.jkshospticaldoctor.activity.activity.submitill.model;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import com.google.gson.Gson;
import com.woshiku.jkshospticaldoctor.activity.domain.SubmitIllData;
import com.woshiku.jkshospticaldoctor.activity.utils.CompressFileUtil;
import com.woshiku.jkshospticaldoctor.activity.utils.FileUtils;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.jkshospticaldoctor.activity.utils.ManageThread;
import com.woshiku.jkshospticaldoctor.activity.utils.PicThreadManage;
import com.woshiku.jkshospticaldoctor.activity.utils.ThreadManage;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.url.base.CommonUrl;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import common.Global;
import inter.ResultListener;
import param.SubmitOrderParam;
import parse.SubmitOrderparse;

/**
 * Created by admin on 2017-05-11.
 */

public class SubmitModelImple implements SubmitIllModel{
    private String takePhotoAddr = "";//用户拍照生成图片的地址
    SubmitIllListener submitIllListener;
    private int uploadRecordCount;
    List<SubmitIllData> successList;
    boolean isUploadSuccess = true;
    CommonUrl commonUrl;
    public SubmitModelImple(SubmitIllListener submitIllListener) {
        this.submitIllListener = submitIllListener;
        commonUrl = new CommonUrl();
    }

    /**
     * 拍照
     * */
    @Override
    public void takePhoto() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        openCameraIntent.addCategory("android.intent.category.DEFAULT");
        takePhotoAddr = FileUtils.generImageName();
        File file = new File(takePhotoAddr);
        Uri imageUri = Uri.fromFile(file);
        openCameraIntent .putExtra("android.intent.extras.CAMERA_FACING", 1); // 调用前置摄像头 startActivityForResult(intent, 1);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        submitIllListener.onGetActivity().startActivityForResult(openCameraIntent, Global.ACTION_TAKE_PICTURE);
    }

    /**
     * 拍照是否成功
     * */
    private void photoIsOk(boolean success){
        if(success){
            submitIllListener.onPhotoSuccess(true,takePhotoAddr);
            //拍照成功后加入数据库
            submitIllListener.onGetActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(new File(takePhotoAddr))));
        }else{
            takePhotoAddr = "";
            submitIllListener.onPhotoSuccess(false,takePhotoAddr);
        }
    }

    /**
     * @param requestCode 请求码
     * @param resultCode 结果码
     * @param data 传递的数据
     */
    @Override
    public void dealActivityReturn(int requestCode, int resultCode, Intent data) {
        if(resultCode == submitIllListener.onGetActivity().RESULT_OK){
            switch (requestCode){
                case Global.ACTION_TAKE_PICTURE:
                    if(resultCode == -1){
                        photoIsOk(true);//拍照成功
                    }else{
                        photoIsOk(false);//拍照失败
                    }
                    break;
            }
        }
    }
    /**
     * @param  oneList 数据一
     * @param twoList 数据二
     */
    @Override
    public void compressPics(List<SubmitIllData> oneList, List<SubmitIllData> twoList) {
        List<SubmitIllData> list = new ArrayList<>();
        list.addAll(oneList);
        list.addAll(twoList);
        ManageThread manageThread = ManageThread.getInstance();
        manageThread.createCompressedThread(5);
        new CompressFileUtil(list,manageThread).setCompressResultListener(new CompressFileUtil.CompressResultListener() {
            @Override
            public void compressResult(List<SubmitIllData> dataList, int size, boolean isEq) {
                if(isEq){
                    LogUtil.print("compress finish");
                    submitIllListener.compressedFinish(dataList);
                }
            }
        }).start();
    }

    /**
     * @param picList 要上传的图片列表
     */
    @Override
    public void uploadMultiPics(final List<SubmitIllData> picList) {
        successList = new ArrayList<>();
        uploadRecordCount = picList.size();
        isUploadSuccess = true;
        PicThreadManage.getInstance().carry(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<picList.size();i++){
                    if(isUploadSuccess){
                        uploadSinglePic(Global.uploadUrl,Global.loginReturnData.id,picList.get(i));
                    }else{
                        break;
                    }
                }
            }
        });
    }

    /**
     * @param orderId 订单id
     * @param bls 病历
     * @param cfs 处方
     */
    @Override
    public void submitOrder(final String orderId, final String bls, final String cfs) {
        ThreadManage.getInstance().carry(new Runnable() {
            @Override
            public void run() {
                Result result = commonUrl.loadUrlAsc(SubmitOrderParam.submitOrder(orderId,bls,cfs));
                SubmitOrderparse.submitOrder(result, new ResultListener() {
                    @Override
                    public void onSuccess(Object obj) {
                        submitIllListener.submitOrderResult(true,obj);
                    }

                    @Override
                    public void onFail(Object obj) {
                        submitIllListener.submitOrderResult(false,obj);
                    }
                });
            }
        });
    }

    private void uploadSinglePic(String url, String userId, final SubmitIllData submitData){
        RequestParams params = new RequestParams(url);
        params.setMultipart(true);
        params.addBodyParameter("upload",new File(submitData.getPath()));
        params.addBodyParameter("userId",userId);
        LogUtil.print("upload","上传文件准备123"+submitData.getPath());
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                LogUtil.print("upload"+result);
                Gson gson = new Gson();
                Result rr = gson.fromJson(result,Result.class);
                if(rr.isSuccess()){
                    submitData.setUploadReturnAddress(rr.getMsg());
                    successList.add(submitData);
                    if(successList.size() == uploadRecordCount){//成功上传了所有的图片
                        submitIllListener.uploadPicResult(true,successList);
                    }
                }else{
                    submitIllListener.uploadPicResult(false,null);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                isUploadSuccess = false;
                submitIllListener.uploadPicResult(false,null);
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
