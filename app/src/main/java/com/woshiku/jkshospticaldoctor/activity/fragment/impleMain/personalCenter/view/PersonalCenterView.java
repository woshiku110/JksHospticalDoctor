package com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.personalCenter.view;

import android.app.Activity;
import android.widget.TextView;

/**
 * Created by admin on 2017-04-28.
 */

public interface PersonalCenterView {
    void setInitPage();
    void setOpenDialog();
    void setCloseDialog();
    void setShortToast(String msg);
    TextView getIntervalTimeView();
    Activity onGetActivity();
}
