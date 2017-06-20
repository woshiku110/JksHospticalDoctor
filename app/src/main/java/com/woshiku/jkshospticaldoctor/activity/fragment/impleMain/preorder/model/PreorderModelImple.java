package com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.preorder.model;

import com.woshiku.jkshospticaldoctor.activity.domain.PreorderData;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.jkshospticaldoctor.activity.utils.ThreadManage;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.url.base.CommonUrl;
import java.util.List;
import inter.ResultListener;
import param.PreorderDealedParam;
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
        ThreadManage.getInstance().carry(new DealUndealData(preorderModelListener));
    }
    /**
     * @desc 处理已处理数据
     * */
    @Override
    public void loadDealedData(PreorderModelListener preorderModelListener) {
        ThreadManage.getInstance().carry(new DealDealedData(preorderModelListener));
    }

    /**
     * @desc 处理待处理数据
     * */
    class DealUndealData implements Runnable{
        PreorderModelListener preorderModelListener;
        public DealUndealData(PreorderModelListener preorderModelListener) {
            this.preorderModelListener = preorderModelListener;
        }
        @Override
        public void run() {
            Result result = commonUrl.loadUrlAsc(PreorderUndealParam.preorderUndeal());
            PreorderUndealParse.preorderUndeal(result, new ResultListener() {
                @Override
                public void onSuccess(Object obj) {//解析成功
                    List<PreorderData> undealPreorderDataList = (List<PreorderData>)obj;
                    if(undealPreorderDataList.size() == 0){
                        preorderModelListener.onLoadNoData(true);
                    }else{
                        preorderModelListener.onLoadOk(true,undealPreorderDataList);
                    }
                    LogUtil.print(undealPreorderDataList.toString());
                }
                @Override
                public void onFail(Object obj) {//解析失败
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

    /**
     * @desc 处理已处理数据
     * */
    class DealDealedData implements Runnable{
        PreorderModelListener preorderModelListener;

        public DealDealedData(PreorderModelListener preorderModelListener) {
            this.preorderModelListener = preorderModelListener;
        }

        @Override
        public void run() {
            Result result = commonUrl.loadUrlAsc(PreorderDealedParam.preorderDealed());
            PreorderUndealParse.preorderUndeal(result, new ResultListener() {
                @Override
                public void onSuccess(Object obj) {//解析成功
                    List<PreorderData> dealedPreorderDataList = (List<PreorderData>)obj;
                    if(dealedPreorderDataList.size() == 0){
                        preorderModelListener.onLoadNoData(false);
                    }else{
                        preorderModelListener.onLoadOk(false,dealedPreorderDataList);
                        LogUtil.print(dealedPreorderDataList.toString());
                    }
                }
                @Override
                public void onFail(Object obj) {//解析失败
                    preorderModelListener.onLoadFail(false);
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
