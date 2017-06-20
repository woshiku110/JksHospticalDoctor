package com.woshiku.jkshospticaldoctor.activity.fragment.impleMain.holdDialog.model;

import com.woshiku.jkshospticaldoctor.activity.domain.HoldDialogData;
import com.woshiku.jkshospticaldoctor.activity.main.MainActivity;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.jkshospticaldoctor.activity.utils.ThreadManage;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.url.base.CommonUrl;
import java.util.List;
import inter.ResultListener;
import param.DoctorTreatmenParam;
import param.HoldDialogParam;
import parse.DoctorTreatmenParse;
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

    @Override
    public void doctorTreatMent(HoldDialogData holdDialogData, PreorderModelListener preorderModelListener) {
        ThreadManage.getInstance().carry(new DoctorTreatment(holdDialogData,preorderModelListener));
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
    /**
     * @desc 医生叫号
     * */
    private class DoctorTreatment implements Runnable{
        PreorderModelListener preorderModelListener;
        HoldDialogData holdDialogData;
        public DoctorTreatment(HoldDialogData holdDialogData,PreorderModelListener preorderModelListener) {
            this.holdDialogData = holdDialogData;
            this.preorderModelListener = preorderModelListener;
        }

        @Override
        public void run() {
            final MainActivity mainUi = (MainActivity) preorderModelListener.onActivity();
            mainUi.openDialog();
            Result result = commonUrl.loadUrlAsc(DoctorTreatmenParam.doctorTreatmen(holdDialogData.getId()));
            DoctorTreatmenParse.doctorTreatmen(result, new ResultListener() {
                @Override
                public void onSuccess(Object obj) {
                    preorderModelListener.onDoctorTreat(true,holdDialogData);
                    mainUi.closeDialog();
                }

                @Override
                public void onFail(Object obj) {
                    preorderModelListener.onDoctorTreat(false,holdDialogData);
                    mainUi.closeDialog();
                }
            });
        }
    }
}
