package com.woshiku.jkshospticaldoctor.activity;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.woshiku.jkshospticaldoctor.activity.domain.PermissionData;
import com.woshiku.jkshospticaldoctor.activity.utils.AppManager;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.jkshospticaldoctor.activity.utils.PermissionHelper;
import com.woshiku.waitlibrary.WaitDialog;

import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * Created by Administrator on 2017/4/6.
 */
public abstract class BaseActivity extends AppCompatActivity{
    protected abstract void initViews();//用于初始化继承后activity初始化布局
    protected abstract void swipeBackCallback();//滑动移除时回调
    protected SwipeBackActivityHelper mHelper;
    WaitDialog waitDialog;
    View parentView;
    private PermissionHelper permissionHelper;//权限管理
    PermissionData permissionData;
    /**
     * @desc 用于初始化活动侧滑配置
     * */
    private void initSwipeBack(){
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.setSwipeBackListener(new SwipeBackActivityHelper.SwipeBackListener() {
            @Override
            public void swipeBack() {
                swipeBackCallback();
            }
        });
        mHelper.onActivityCreate();
        setGesture(false);
    }

    /**
     * 初始化状态栏 并且修改状态栏的颜色
     * */
    private void initStatusBar(){
        ViewGroup contentFrameLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        parentView = contentFrameLayout.getChildAt(0);
        if (parentView != null && Build.VERSION.SDK_INT >= 14) {
            parentView.setFitsSystemWindows(true);
        }
    }
    /**
     *用于初始化权限
     */
    private void initPermission(){
        permissionHelper = new PermissionHelper(this);
    }
    public boolean isPermission(){
        return permissionHelper.checkPermission(permissionData.getName());
    }

    public void allowPermission(){
        permissionHelper.permissionsCheck(permissionData.getName(),permissionData.getReturnCode());
    }

    public PermissionData getPermissionData() {
        return permissionData;
    }

    public void setPermissionData(PermissionData permissionData) {
        this.permissionData = permissionData;
    }

    /**
     * @param isok true表示 当前页面可以侧滑关闭 false表示 当前页面不进行侧滑关闭
     * @desc 用于开,关活动的开关
     * */
    public void setGesture(boolean isok){
        mHelper.getSwipeBackLayout().setEnableGesture(isok);
    }
    public void setScrollDirection(int direction){
        mHelper.getSwipeBackLayout().setEdgeTrackingEnabled(direction);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSwipeBack();
        initViews();
        initStatusBar();
        AppManager.getAppManager().addActivity(this);
        initPermission();
        LogUtil.print("create",this.getClass().getName());
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        mHelper.getSwipeBackLayout().scrollToFinishActivity();
    }

    public void toastShort(final String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this,msg,Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void openDialog(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(waitDialog == null){
                    waitDialog = new WaitDialog(BaseActivity.this,parentView);
                }
                waitDialog.showDialog();
            }
        });

    }
    public void closeDialog(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(waitDialog == null){
                    waitDialog = new WaitDialog(BaseActivity.this,parentView);
                }
                waitDialog.closeDialog();
            }
        });
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (com.woshiku.jkshospticaldoctor.activity.utils.Utils.isFastDoubleClick()) {
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.print("create","destory"+this.getClass().getName());
        AppManager.getAppManager().finishActivity(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == permissionData.getReturnCode()){
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                userPassPermission(permissionData);
            } else {
                permissionHelper.startAppSettings();//如果请求失败
                Toast.makeText(this, permissionData.getFailReason(), Toast.LENGTH_SHORT).show();
            }
        }

    }
    protected void userPassPermission(PermissionData permissionData){

    }
}
