package com.woshiku.jkshospticaldoctor.activity.activity.receptionhistory.model;

import com.woshiku.jkshospticaldoctor.activity.domain.ReceHistoryData;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.jkshospticaldoctor.activity.utils.ThreadManage;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.url.base.CommonUrl;
import java.util.List;
import inter.ResultListener;
import param.ReceptionHistoryParam;
import parse.ReceptionHistoryParse;

/**
 * Created by admin on 2017-04-28.
 * @desc 虽然名字是medicalsearch 内容实则为接诊历史
 */

public class MedicalSearchModelImple implements MedicalSearchModel {
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
                Result result = commonUrl.loadUrlAsc(ReceptionHistoryParam.receptionHistory(msg));
                ReceptionHistoryParse.receptionHistory(result, new ResultListener() {
                    @Override
                    public void onSuccess(Object obj) {
                        List<ReceHistoryData> dataList = (List<ReceHistoryData>)obj;
                        if(dataList.size() == 0){
                            preorderModelListener.onLoadNoData();
                        }else{
                            preorderModelListener.onLoadOk(dataList);
                        }
                        LogUtil.print(dataList.toString());
                    }

                    @Override
                    public void onFail(Object obj) {
                        preorderModelListener.onLoadFail();
                    }
                });
                LogUtil.print(result.toString());
            }
        });
    }
}
