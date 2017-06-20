package com.woshiku.dialoglib;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import com.woshiku.dialoglib.view.TouchImageView;
import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * Created by Administrator on 2017/2/23.
 */
public class ScaleImagePop extends PopupWindow{
    View view,parent;
    Context context;
    TouchImageView touchImageView;
    String path;
    LinearLayout scaleLineLayout;
    public ScaleImagePop(Context context,View parent,String path){
        this.context = context;
        this.parent = parent;
        this.path = path;
        view = View.inflate(context,R.layout.scale_image_layout,null);
        scaleLineLayout = (LinearLayout)view.findViewById(R.id.scale_relate);
        touchImageView = (TouchImageView)view.findViewById(R.id.scale_touch_view);
        touchImageView.setOnSingleClickListener(new TouchImageView.OnSingleClickListener() {
            @Override
            public void onSingleClick() {
                dismiss();
            }
        });
        displayImageView(touchImageView,path);
        scaleLineLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setAnimationStyle(R.style.mypopwindow_anim_style);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
    }
    public void show(){
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
    }
    private void displayImageView(ImageView imagewView, String path){
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                .setFailureDrawableId(R.mipmap.img_error)
                .setLoadingDrawableId(R.mipmap.img_default)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .build();
        x.image().bind(imagewView,path,imageOptions);
    }
}
