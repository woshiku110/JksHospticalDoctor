package com.woshiku.bottomtabbarlib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by admin on 2017-04-13.
 */

public class MenuItemView extends LinearLayout{
    ImageView icon;
    TextView text;
    public MenuItemView(Context context) {
        super(context);
    }

    public MenuItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.item_bottom_bar,this);
        icon = (ImageView) findViewById(R.id.item_bottom_bar_image);
        text = (TextView) findViewById(R.id.item_bottom_bar_txt);
    }
}
