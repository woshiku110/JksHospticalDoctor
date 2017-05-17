package com.woshiku.jkshospticaldoctor.activity.activity.revisepersonalinfo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import com.google.gson.Gson;
import com.soundcloud.android.crop.Crop;
import com.woshiku.albumlibrary.activity.AlbumActivity;
import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.activity.revisepersonalinfo.presenter.RevisePresenter;
import com.woshiku.jkshospticaldoctor.activity.activity.revisepersonalinfo.presenter.RevisePresenterImple;
import com.woshiku.jkshospticaldoctor.activity.activity.revisepersonalinfo.view.ReviseView;
import com.woshiku.jkshospticaldoctor.activity.activity.web.WebActivity;
import com.woshiku.jkshospticaldoctor.activity.domain.LoginReturnData;
import com.woshiku.jkshospticaldoctor.activity.domain.PermissionData;
import com.woshiku.jkshospticaldoctor.activity.utils.FileUtils;
import com.woshiku.jkshospticaldoctor.activity.utils.RdUtil;
import com.woshiku.jkshospticaldoctor.activity.view.PhotoView;
import java.io.File;
import java.util.List;
import butterknife.OnClick;
import common.Global;

/**
 * Created by admin on 2017-04-25.
 * @desc 预约接诊活动
 */

public class RevisePersonalInfoActivity extends WebActivity implements ReviseView{
    String takePhotoAddr = "";
    String userCropPath;//用户裁剪地址
    RevisePresenter presenter;
    String isUploadPics = "";
    String urlTitle;
    @Override
    protected void loadChildrenMethod() {
        webView.addJavascriptInterface(new JsInteration(), "control");
        presenter = new RevisePresenterImple(this);
    }

    /**
     * 需要js实现的方法
     * */
    public class JsInteration {
        @JavascriptInterface
        public String getName(){
            return Global.loginReturnData.username;
        }
        @JavascriptInterface
        public String getPhone(){
            return Global.loginReturnData.phone;
        }
        @JavascriptInterface
        public String getQQ(){
            return Global.loginReturnData.email;
        }
        @JavascriptInterface
        public String getPic(){
            return Global.loginReturnData.logo;
        }
        @JavascriptInterface
        public void  iconClicked(){//用户点击了头像
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setPermissionData(new PermissionData(Global.WritePermission,Global.WriteReturnCode,Global.WriteReason));
                    if(isPermission()){
                        setPermissionData(new PermissionData(Global.CameraPermission,Global.CameraReturnCode,Global.CameraReason));
                        if(isPermission()){
                            jumpPop();
                        }else{
                            allowPermission();
                        }
                    }else{
                        allowPermission();
                    }
                }
            });
        }
        @JavascriptInterface
        public String getToken(){
            return Global._token;
        }
        @JavascriptInterface
        public void reviseResult(boolean isOk,String reason,String phone,String email,String img){
            if(isOk){
                closeDialog();
                saveOk(phone,email,img);
                toastShort("保存成功");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        scrollToFinishActivity();
                    }
                });
            }else{
                closeDialog();
                toastShort(reason);
            }
        }

        @JavascriptInterface
        public void revisePassResult(boolean isOk,String reason){//修改密码结果
            toastShort(reason);
        }
    }
    /**
     * @param  phone 手机号
     * @param  email 电子邮件
     * @param  headImg 头像图片
     */
    private void saveOk(String phone ,String email ,String headImg){
        String loginMsg = RdUtil.readData("loginReturn");
        if(!TextUtils.isEmpty(loginMsg)){
            Gson gson = new Gson();
            Global.loginReturnData = gson.fromJson(loginMsg, LoginReturnData.class);
            Global.loginReturnData.phone = phone;
            Global.loginReturnData.email = email;
            Global.loginReturnData.logo = headImg;
            RdUtil.saveData("loginReturn",new Gson().toJson(Global.loginReturnData));
        }
    }
    /**
     * @param  isSucees 是否是成功的
     * @param path 上传图片成功后返回的路径
     */
    @Override
    public void uploadPic(boolean isSucees, String path) {
        if(isSucees){
            isUploadPics = path;
            webView.loadUrl("javascript:"+"save("+"'"+path+"'"+")");
        }else{
            closeDialog();
            toastShort("上传头像失败,请重新上传!!!");
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }
    @OnClick({R.id.web_title_return,R.id.web_title_right})
    void userClick(View view){
        switch (view.getId()){
            case R.id.web_title_right:
                if(urlTitle.equals("个人信息")){
                    if(TextUtils.isEmpty(isUploadPics)){//用户没有上传头像成功
                        if(!TextUtils.isEmpty(userCropPath)){//表示用户修改过头像
                            openDialog();
                            presenter.uploadFile(userCropPath);
                        }else{//用户没有修改过头像
                            openDialog();
                            webView.loadUrl("javascript:save("+"'"+Global.loginReturnData.logo+"'"+")");
                        }
                    }else{//用户上传头像成功
                        openDialog();
                        webView.loadUrl("javascript:save("+"'"+isUploadPics+"'"+")");
                    }
                }else{//修改密码
                    webView.loadUrl("javascript:save()");
                }
                break;
            case R.id.web_title_return:
                if(webView.canGoBack()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            webView.goBack();
                        }
                    });
                }else{
                    scrollToFinishActivity();
                }
                break;
        }
    }


    @Override
    public void titleChanged(String title) {
        super.titleChanged(title);
        urlTitle = title;
        if(title.equals("个人信息")){
            concertText.setText("保存");
            concert_bt.setVisibility(View.VISIBLE);
        }else{
            concertText.setText("修改");
            concert_bt.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 弹出pop用于拍照或选择相册中的图片
     */
    private void jumpPop(){
        new PhotoView(this,concert_bt).addAlbumChooseListener(new PhotoView.AlbumChooseListener() {
            @Override
            public void albumChoose() {//相册选择
                Intent intent = new Intent(RevisePersonalInfoActivity.this, AlbumActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("mode",1);
                intent.putExtras(bundle);
                startActivityForResult(intent, Global.ACTION_ALBUMN_REQUEST);
            }
        }).addAlbumPhotoListener(new PhotoView.AlbumPhotoListener() {
            @Override
            public void albumPhoto() {//拍照片
                takePhoto();
            }
        }).showDialog();
    }

    /**
     * 拍照
     * */
    public void takePhoto() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        openCameraIntent.addCategory("android.intent.category.DEFAULT");
        takePhotoAddr = FileUtils.generImageName();
        File file = new File(takePhotoAddr);
        Uri imageUri = Uri.fromFile(file);
        openCameraIntent .putExtra("android.intent.extras.CAMERA_FACING", 1); // 调用前置摄像头 startActivityForResult(intent, 1);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(openCameraIntent, Global.ACTION_TAKE_PICTURE);
    }

    /**
     * 拍照是否成功
     * */
    private void photoIsOk(boolean success){
        if(success){
            //拍照成功后加入数据库
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(new File(takePhotoAddr))));
            userCropPath = FileUtils.getUserIconName();
            beginCrop(takePhotoAddr,userCropPath);
        }else{
            takePhotoAddr = "";
        }
    }

    /**
     * 裁剪图片
     * */
    protected void beginCrop(String sourcePath,String destPath) {
        Uri source  = Uri.fromFile(new File(sourcePath));
        Uri destination = Uri.fromFile(new File(destPath));
        Crop.of(source, destination).asSquare().start(this);
    }
    public boolean handleCrop(int resultCode, Intent result) {
        if(resultCode == RESULT_OK) {
            Crop.getOutput(result);
            return true;
        } else if (resultCode == Crop.RESULT_ERROR) {
            toastShort(Crop.getError(result).getMessage());
            return false;
        }
        return false;
    }

    @Override
    protected void userPassPermission(PermissionData permissionData) {
        super.userPassPermission(permissionData);
        if(permissionData.getName().equals(Global.WritePermission)){
            setPermissionData(new PermissionData(Global.CameraPermission,Global.CameraReturnCode,Global.CameraReason));
            if(isPermission()){
                jumpPop();
            }else{
                allowPermission();
            }
        }else if(permissionData.getName().equals(Global.CameraPermission)){
            jumpPop();
        }
    }

    /**
     * @param requestCode 请求码
     * @param resultCode 结果码
     * @param data 传递的数据
     */
    public void dealActivityReturn(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case Global.ACTION_TAKE_PICTURE:
                    if(resultCode == -1){
                        photoIsOk(true);//拍照成功
                    }else{
                        photoIsOk(false);//拍照失败
                    }
                    break;
                case Global.ACTION_ALBUMN_REQUEST:
                    List<String> photoList = data.getExtras().getStringArrayList(Global.IMAGE_SELECTED_ADDR);
                    takePhotoAddr =  photoList.get(0);
                    userCropPath = FileUtils.getUserIconName();
                    beginCrop(takePhotoAddr,userCropPath);
                    break;
                case Crop.REQUEST_CROP:
                    if(handleCrop(resultCode, data)){
                        webView.loadUrl("javascript:"+"setHeadImg("+"'"+userCropPath+"'"+")");
                    }
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        dealActivityReturn(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) )
        {
            if(webView.canGoBack()){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        webView.goBack();
                    }
                });
            }else{
                scrollToFinishActivity();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void swipeBackCallback() {

    }
}
