package com.woshiku.jkshospticaldoctor.activity.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import com.woshiku.jkshospticaldoctor.R;

/**
 * Created by Administrator on 2016/12/29.
 */
public class PhotoView extends PopupWindow{
    Context context;
    View parent;
    View view;
    LinearLayout pop_desc_choose;
    LinearLayout pop_desc_takephoto;
    LinearLayout pop_desc_cancel;
    AlbumChooseListener albumChooseListener;
    AlbumPhotoListener albumPhotoListener;
    RelativeLayout pop_desc_relate;
    LinearLayout layout;
    public interface AlbumChooseListener{
        void albumChoose();
    }
    public interface AlbumPhotoListener{
        void albumPhoto();
    }
    public PhotoView(final Context context,View parent){
        this.context = context;
        this.parent = parent;
        view = View.inflate(context, R.layout.pop_edit_photo,null);
        layout = (LinearLayout) view.findViewById(R.id.pop_desc_layout);
        pop_desc_relate = (RelativeLayout)view.findViewById(R.id.pop_desc_relate);
        pop_desc_choose = (LinearLayout)view.findViewById(R.id.pop_desc_choose);
        pop_desc_takephoto = (LinearLayout)view.findViewById(R.id.pop_desc_takephoto);
        pop_desc_cancel = (LinearLayout)view.findViewById(R.id.pop_desc_cancel);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        pop_desc_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (albumChooseListener != null) {
                    albumChooseListener.albumChoose();
                }
                closeDialog();
            }
        });
        pop_desc_takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(albumPhotoListener != null){
                    albumPhotoListener.albumPhoto();
                }
                closeDialog();
            }
        });
        pop_desc_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog();
            }
        });
        pop_desc_relate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog();
            }
        });
        view.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if(keyCode == KeyEvent.KEYCODE_BACK)
                    closeDialog();
                return false;
            }
        });
    }
    public PhotoView showDialog(){
        update();
        showAtLocation(parent, Gravity.LEFT|Gravity.TOP, 0, 0);
        //使用AnimationUtils类的静态方法loadAnimation()来加载XML中的动画XML文件
        Animation animation=AnimationUtils.loadAnimation(context, R.anim.popshow_anim);
        layout.startAnimation(animation);
        return PhotoView.this;
    }
    public void closeDialog(){
        Animation animation=AnimationUtils.loadAnimation(context, R.anim.pophidden_anim);
        layout.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {}   //在动画开始时使用

            @Override
            public void onAnimationRepeat(Animation arg0) {}  //在动画重复时使用

            @Override
            public void onAnimationEnd(Animation arg0) {
                dismiss();
            }
        });
    }

    public PhotoView addAlbumChooseListener(AlbumChooseListener albumChooseListener) {
        this.albumChooseListener = albumChooseListener;
        return this;
    }

    public PhotoView addAlbumPhotoListener(AlbumPhotoListener albumPhotoListener) {
        this.albumPhotoListener = albumPhotoListener;
        return this;
    }
}
