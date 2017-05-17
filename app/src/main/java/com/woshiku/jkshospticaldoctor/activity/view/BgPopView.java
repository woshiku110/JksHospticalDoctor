package com.woshiku.jkshospticaldoctor.activity.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.woshiku.jkshospticaldoctor.R;

/**
 * Created by admin on 2017-05-17.
 */

public class BgPopView extends PopupWindow{
    Context context;
    View parent;
    public BgPopView(final Context context,View parent,PopupWindow pp){
        this.context = context;
        this.parent = parent;
        View view = View.inflate(context, R.layout.pop_bg,null);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                Log.e("dismiss","消失");
            }
        });
        if(pp instanceof PhotoView){
            ((PhotoView) pp).showDialog();
        }
        showAtLocation(parent, Gravity.LEFT|Gravity.TOP, 0, 0);
    }
}
