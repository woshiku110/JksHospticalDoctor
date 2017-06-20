package com.woshiku.jkshospticaldoctor.activity.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by admin on 2017-04-17.
 */

public class NoSmoothViewPager extends ViewPager{
    public NoSmoothViewPager(Context context) {
        super(context);
    }

    public NoSmoothViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
