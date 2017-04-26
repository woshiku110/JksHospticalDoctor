package com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.holdDialog.model;

import com.woshiku.jkshospticaldoctor.activity.domain.HoldDialogData;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.jkshospticaldoctor.activity.utils.ThreadManage;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.url.base.CommonUrl;
import java.util.List;
import inter.ResultListener;
import param.HoldDialogParam;
import parse.HoldDialoglParse;

/**
 * Created by admin on 2017-04-19.
 */

public class HoldDialogModelImple implements HoldDialogModel{
    CommonUrl commonUrl;

    public HoldDialogModelImple() {
        commonUrl = new CommonUrl();
    }

    @Override
    public void loadData(PreorderModelListener preorderModelListener) {
        ThreadManage.getInstance().carry(new DealData(preorderModelListener));
    }
    /**
     * @desc 处理待处理数据
     * */
    class DealData implements Runnable{
        PreorderModelListener preorderModelListener;
        public DealData(PreorderModelListener preorderModelListener) {
            this.preorderModelListener = preorderModelListener;
        }
        @Override
        public void run() {
            Result result = commonUrl.loadUrlAsc(HoldDialogParam.holdDialog());
            HoldDialoglParse.holdDialogParse(result, new ResultListener() {
                @Override
                public void onSuccess(Object obj) {
                    List<HoldDialogData> undealPreorderDataList = (List<HoldDialogData>)obj;
                    if(undealPreorderDataList.size() == 0){
                        preorderModelListener.onLoadNoData(true);
                    }else{
                        preorderModelListener.onLoadOk(true,undealPreorderDataList);
                    }
                }

                @Override
                public void onFail(Object obj) {
                    preorderModelListener.onLoadFail(true);
                    if(obj instanceof String){
                        LogUtil.print((String)obj);
                    }else{
                        LogUtil.print((Exception) obj);
                    }
                }
            });
        }
    }
}
