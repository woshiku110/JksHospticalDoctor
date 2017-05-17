package com.woshiku.albumlibrary.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.woshiku.albumlibrary.R;

/**
 * Created by woshiku on 2016-08-13.
 */
public class AlbumPopWindow extends PopupWindow{
    View view;
    public AlbumPopWindow(Context context,View parent){
        view = View.inflate(context, R.layout.pop_album_show,null);
        view.startAnimation(AnimationUtils.loadAnimation(context,
                R.anim.fade_ins));
        LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.road_line_ll);
        ll_popup.startAnimation(AnimationUtils.loadAnimation(context,
                R.anim.push_bottom_in_2));

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        showAsDropDown(parent, 0, 0);
        //showAtLocation(parent, Gravity., 0, 100);
        update();
    }
    public void closeAlbum(){
        dismiss();
    }
}
