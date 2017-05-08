package com.woshiku.jkshospticaldoctor.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

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


}
