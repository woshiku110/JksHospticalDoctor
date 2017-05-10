package com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.holdDialog.presenter;

import com.woshiku.jkshospticaldoctor.activity.domain.HoldDialogData;

/**
 * Created by admin on 2017-04-26.
 */

public interface HoldDialogPresenter {
    void initPage();
    void firstLoadingPage();
    void loadData();//加载待处理数据
    void doctorTreatMent(HoldDialogData holdDialogData);
}
