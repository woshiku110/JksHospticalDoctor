package com.woshiku.jkshospticaldoctor.activity.activity.recetime.view;

import android.app.Activity;

/**
 * Created by admin on 2017-05-15.
 */

public interface ReceTimeView {
    void onInitPage();
    Activity getActivity();
    void updateDateResult(boolean isOk,Object object);
}
