package com.woshiku.jkshospticaldoctor.activity.activity.checkreception.model;

import com.woshiku.jkshospticaldoctor.activity.domain.CheckReceData;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.jkshospticaldoctor.activity.utils.ThreadManage;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.url.base.CommonUrl;
import java.util.List;
import inter.ResultListener;
import param.CheckReceptionParam;
import parse.CheckReceptionParse;

/**
 * Created by admin on 2017-05-04.
 */

public class CheckReceModelImple implements CheckReceModel{
    CommonUrl commonUrl;

    public CheckReceModelImple() {
        commonUrl = new CommonUrl();
    }

    @Override
    public void loadData(CheckReceModelListener checkReceModelListener) {
        loadUrlData(checkReceModelListener);
    }
    private void loadUrlData(final CheckReceModelListener checkReceModelListener){
        ThreadManage.getInstance().carry(new Runnable() {
            @Override
            public void run() {
                Result result = commonUrl.loadUrlAsc(CheckReceptionParam.checkReception());
                LogUtil.print(result.toString());
                CheckReceptionParse.checkReception(result, new ResultListener() {
                    @Override
                    public void onSuccess(Object obj) {
                        Object[] objects = (Object[])obj;
                        List<CheckReceData> undealList = (List<CheckReceData>)objects[0];
                        List<CheckReceData> dealedList = (List<CheckReceData>)objects[1];
                        if(undealList.size() == 0){
                            checkReceModelListener.onLoadNoData(true);
                        }else{
                            checkReceModelListener.onLoadOk(true,undealList);
                        }
                        if(dealedList.size() == 0){
                            checkReceModelListener.onLoadNoData(false);
                        }else{
                            checkReceModelListener.onLoadOk(false,dealedList);
                        }
                    }

                    @Override
                    public void onFail(Object obj) {
                        checkReceModelListener.onLoadFail(true);
                        checkReceModelListener.onLoadFail(false);
                    }
                });
            }
        });
    }
}
