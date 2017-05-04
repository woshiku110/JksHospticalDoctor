package com.woshiku.jkshospticaldoctor.activity.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import common.Global;

/**
 * Created by Administrator on 2016/4/16.
 */
@SuppressLint("ValidFragment")
public abstract class BaseFragment extends Fragment{
    public FragmentActivity mActivity;
    MyReceBroad myReceBroad;
    protected abstract void dealBroadcastRece(Intent intent);
    @SuppressLint("ValidFragment")
    public BaseFragment(FragmentActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();//初始化数据
        initBroadCast();
    }

    private void initBroadCast(){
        myReceBroad = new MyReceBroad();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Global.mainAction);
        mActivity.registerReceiver(myReceBroad,filter);
        LogUtil.print("register filter");
    }
    public void removeBroadcast(){
        if(myReceBroad != null){
            mActivity.unregisterReceiver(myReceBroad);
        }
    }
    class MyReceBroad extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            dealBroadcastRece(intent);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initViews();
    }
    public abstract View initViews();

    public void initDatas(){

    }
    /**
     * 加载显示图片
     * */
    public void displayImageView(ImageView imagewView, String path){
        LogUtil.print(path);
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                .setFailureDrawableId(R.mipmap.img_error)
                .setLoadingDrawableId(R.mipmap.img_default)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setCircular(true)
                .build();
        x.image().bind(imagewView,path,imageOptions);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //removeBroadcast();
        LogUtil.print("remove fragment");
    }
}
