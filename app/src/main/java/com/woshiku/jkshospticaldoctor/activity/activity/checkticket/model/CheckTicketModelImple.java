package com.woshiku.jkshospticaldoctor.activity.activity.checkticket.model;

import com.woshiku.jkshospticaldoctor.activity.domain.CheckTicketData;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.jkshospticaldoctor.activity.utils.PinyinComparator;
import com.woshiku.jkshospticaldoctor.activity.utils.ThreadManage;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.url.base.CommonUrl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import inter.ResultListener;
import param.CheckTicketParam;
import param.DoctorCreateOrderParam;
import parse.CheckTicketParse;
import parse.DoctorCreateOrderParse;

/**
 * Created by admin on 2017-05-05.
 */

public class CheckTicketModelImple implements CheckTicketModel{
    CommonUrl commonUrl;
    private String []characters = {"A","B","C","D","E","F","G",
                                    "H","I","J","K","L","M","N",
                                    "O","P","Q","R","S","T","U",
                                    "V","W","X","Y","Z"};
    public CheckTicketModelImple() {
        commonUrl = new CommonUrl();
    }

    @Override
    public void loadData(String yyid,CheckTicketModelListener checkTicketModelListener) {
        loadUrlData(yyid,checkTicketModelListener);
    }

    @Override
    public void submitData(String ids, String names, CheckTicketModelListener checkTicketModelListener) {
        loadUrlTwo(ids,names,checkTicketModelListener);
    }

    private void loadUrlData(final String yyid,final CheckTicketModelListener checkTicketModelListener){
        ThreadManage.getInstance().carry(new Runnable() {
            @Override
            public void run() {
                Result result = commonUrl.loadUrlAsc(CheckTicketParam.checkTicket(yyid));
                CheckTicketParse.checkTicket(result, new ResultListener() {
                    @Override
                    public void onSuccess(Object obj) {
                        List<CheckTicketData> dataList = separateDatas((List<CheckTicketData>)obj);
                        if(dataList.size() == 0){
                            checkTicketModelListener.onLoadNoData();
                        }else{
                            checkTicketModelListener.onLoadOk(dataList);
                        }
                    }

                    @Override
                    public void onFail(Object obj) {
                        checkTicketModelListener.onLoadFail();
                    }
                });
            }
        });
    }

    private void loadUrlTwo(final String ids, final String names, final CheckTicketModelListener checkTicketModelListener){
        ThreadManage.getInstance().carry(new Runnable() {
            @Override
            public void run() {
                checkTicketModelListener.onOpenDialog();
                Result result = commonUrl.loadUrlAsc(DoctorCreateOrderParam.doctorCreateOrder(ids,names));
                DoctorCreateOrderParse.createOrder(result, new ResultListener() {
                    @Override
                    public void onSuccess(Object obj) {
                        checkTicketModelListener.onSubmitPageState(true);
                        checkTicketModelListener.onCloseDialog();
                    }

                    @Override
                    public void onFail(Object obj) {
                        checkTicketModelListener.onSubmitPageState(false);
                        checkTicketModelListener.onCloseDialog();
                    }
                });
            }
        });
    }
    private List<CheckTicketData> tidyData(List<CheckTicketData> dataList){
        for(int i=0;i<dataList.size();i++){
            String cc = new PinyinComparator().getPingYin(dataList.get(i).getName());
            String kk = cc.toCharArray()[0]+"";
            dataList.get(i).setIndexLetter(kk.toUpperCase());//把小写字母变成大写
        }
        return dataList;
    }

    /**
     * 把数据分离开
     * */
    private List<CheckTicketData> separateDatas(List<CheckTicketData> dataList){
        Map<String,List<CheckTicketData>> map = new HashMap<>();
        List<CheckTicketData> newdataList = tidyData(dataList);
        List<CheckTicketData> finalList = new ArrayList<>();
        for(int i=0;i<characters.length;i++){
            List<CheckTicketData> containList = new ArrayList<>();
            for(int j=0;j<newdataList.size();j++){
                if(newdataList.get(j).getIndexLetter().equals(characters[i])){
                    containList.add(newdataList.get(j));//把包含字母的数据添加列表中
                }
            }
            map.put(characters[i],containList);
        }
        for(int i=0;i<characters.length;i++){
            List<CheckTicketData> list = map.get(characters[i]);
            if(list.size()>0){
                finalList.add(new CheckTicketData(true,characters[i]));
                finalList.addAll(list);
            }
        }
        LogUtil.print(finalList.toString());//打印排好顺序的数据
        return finalList;
    }

}
