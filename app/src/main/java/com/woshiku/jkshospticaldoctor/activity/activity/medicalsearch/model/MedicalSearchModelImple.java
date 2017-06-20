package com.woshiku.jkshospticaldoctor.activity.activity.medicalsearch.model;

import com.woshiku.jkshospticaldoctor.activity.domain.MedicalSearchData;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.jkshospticaldoctor.activity.utils.ThreadManage;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.url.base.CommonUrl;
import java.util.List;
import inter.ResultListener;
import param.MedicalSearchParam;
import parse.MedicalSearchParse;

/**
 * Created by admin on 2017-04-28.
 */

public class MedicalSearchModelImple implements MedicalSearchModel{
    CommonUrl commonUrl;
    public MedicalSearchModelImple() {
        commonUrl = new CommonUrl();
    }

    @Override
    public void loadData(String msg,PreorderModelListener preorderModelListener) {
        loadUrlData(msg,preorderModelListener);
    }

    private void loadUrlData(final String msg,final PreorderModelListener preorderModelListener){
        ThreadManage.getInstance().carry(new Runnable() {
            @Override
            public void run() {
                Result result = commonUrl.loadUrlAsc(MedicalSearchParam.medicalSearch(msg));
                MedicalSearchParse.medicalSearch(result, new ResultListener() {
                    @Override
                    public void onSuccess(Object obj) {
                        List<MedicalSearchData> dataList = (List<MedicalSearchData>)obj;
                        if(dataList.size() == 0){
                            preorderModelListener.onLoadNoData();
                        }else{
                            preorderModelListener.onLoadOk(dataList);
                        }
                        LogUtil.print(dataList.toString());
                    }

                    @Override
                    public void onFail(Object obj) {
                        LogUtil.print((String) obj);
                        preorderModelListener.onLoadFail();
                    }
                });
                LogUtil.print(result.toString());
            }
        });
    }
}
