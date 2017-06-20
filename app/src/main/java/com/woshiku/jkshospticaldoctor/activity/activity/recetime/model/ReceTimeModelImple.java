package com.woshiku.jkshospticaldoctor.activity.activity.recetime.model;

import com.woshiku.jkshospticaldoctor.activity.utils.ThreadManage;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.url.base.CommonUrl;
import inter.ResultListener;
import param.UpdateDateParam;
import parse.UpdateDateParse;

/**
 * Created by admin on 2017-05-15.
 */

public class ReceTimeModelImple implements ReceTimeModel{
    CommonUrl commonUrl;

    public ReceTimeModelImple() {
        commonUrl = new CommonUrl();
    }

    @Override
    public void initPage(ReceTimeModelListener receTimeModelListener) {
        receTimeModelListener.onInitPage();
    }

    @Override
    public void userChooseDate(String date, ReceTimeModelListener receTimeModelListener) {
        ThreadManage.getInstance().carry(new LoadUrlDate(date,receTimeModelListener));
    }

    /**
     * 用于用户选择日期
     */
    private class LoadUrlDate implements Runnable{
        private String chooseDate;
        private ReceTimeModelListener receTimeModelListener;

        public LoadUrlDate(String chooseDate, ReceTimeModelListener receTimeModelListener) {
            this.chooseDate = chooseDate;
            this.receTimeModelListener = receTimeModelListener;
        }

        @Override
        public void run() {
            Result result = commonUrl.loadUrlAsc(UpdateDateParam.updateDate(chooseDate));
            UpdateDateParse.updateDate(result, new ResultListener() {
                @Override
                public void onSuccess(Object obj) {
                    receTimeModelListener.updateState(true,obj);
                }

                @Override
                public void onFail(Object obj) {
                    receTimeModelListener.updateState(false,obj);
                }
            });
        }
    }
}
