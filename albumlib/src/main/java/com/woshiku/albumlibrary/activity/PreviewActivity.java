package com.woshiku.albumlibrary.activity;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.woshiku.albumlibrary.R;
import com.woshiku.albumlibrary.activity.base.BaseActivity;
import com.woshiku.albumlibrary.imageloader.ImageLoaderFactory;
import com.woshiku.albumlibrary.imageloader.ImageLoaderWrapper;
import com.woshiku.albumlibrary.utils.StatusBarCompat;
import com.woshiku.albumlibrary.view.TouchImageView;

import java.io.File;

/**
 * Created by Administrator on 2016/8/18.
 */
public class PreviewActivity extends BaseActivity implements View.OnClickListener {
    private TouchImageView dragImageView;
    ImageLoaderWrapper loaderWrapper;
    ImageLoaderWrapper.DisplayOption displayOption;
    public static String filePath;
    ImageView back_bt;
    Context context;
    @Override
    protected void initView() {
        StatusBarCompat.compat(this, this.getResources().getColor(R.color.state_color));
        setContentView(R.layout.picture_preview_layout);
        context = this;
        init(context);
        initViews();
    }

    private void initViews(){
        dragImageView = (TouchImageView) findViewById(R.id.dragImage);
        back_bt = (ImageView)findViewById(R.id.preview_back_bt);
        back_bt.setOnClickListener(PreviewActivity.this);
        File file = new File(filePath);
        if(file.exists()){
            loaderWrapper.displayImage(dragImageView,file,displayOption);
        }else{
            Toast.makeText(context,"你选择的图片不存在!!!",Toast.LENGTH_SHORT).show();
        }
    }
    private void init(Context context){
        getData();
        loaderWrapper = ImageLoaderFactory.getLoader();
        displayOption = new ImageLoaderWrapper.DisplayOption();
        displayOption.loadingResId = R.mipmap.img_default;
        displayOption.loadErrorResId = R.mipmap.img_error;
    };
    private void getData() {
        filePath = getIntent().getStringExtra(filePath);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
