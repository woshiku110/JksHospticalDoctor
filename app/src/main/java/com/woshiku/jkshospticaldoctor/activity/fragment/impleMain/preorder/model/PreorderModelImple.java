package com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.preorder.model;

import com.woshiku.jkshospticaldoctor.activity.domain.UndealPreorderData;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.jkshospticaldoctor.activity.utils.ThreadManage;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.url.base.CommonUrl;
import java.util.List;
import inter.ResultListener;
import param.PreorderUndealParam;
import parse.PreorderUndealParse;

/**
 * Created by admin on 2017-04-20.
 */

public class PreorderModelImple implements PreorderModel{
    CommonUrl commonUrl;

    public PreorderModelImple() {
        commonUrl = new CommonUrl();
    }
    /**
     * @desc 处理等处理数据
     * */
    @Override
    public void loadUndealData(PreorderModelListener preorderModelListener) {
        ThreadManage.getInstance().carry(new DealUndealData());
    }

    @Override
    public void loadDealedData(PreorderModelListener preorderModelListener) {

    }

    /**
     * @desc 处理待处理数据
     * */
    class DealUndealData implements Runnable{
        @Override
        public void run() {
            Result result = commonUrl.loadUrlAsc(PreorderUndealParam.preorderUndeal());
            PreorderUndealParse.preorderUndeal(result, new ResultListener() {
                @Override
                public void onSuccess(Object obj) {//解析成功
                    List<UndealPreorderData> undealPreorderDataList = (List<UndealPreorderData>)obj;
                    LogUtil.print(undealPreorderDataList.toString());
                }
                @Override
                public void onFail(Object obj) {//解析失败
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
