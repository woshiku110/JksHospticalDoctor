package parse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woshiku.jkshospticaldoctor.activity.domain.CheckReceData;
import com.woshiku.jkshospticaldoctor.activity.domain.CheckSepatorData;
import com.woshiku.urllibrary.domain.Result;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import inter.ResultListener;

/**
 * Created by admin on 2017-05-04.
 */

public class CheckReceptionParse {
    public static void checkReception(Result result, ResultListener resultListener){
        if(result.isSuccess()){
            Result rr = new Gson().fromJson(result.getMsg(),Result.class);
            if(rr.isSuccess()){
                Object[] objects = parseMessage(rr.getMsg());
                Object object = objects;
                resultListener.onSuccess(object);
            }else{
                if(resultListener!=null){
                    resultListener.onFail("parse fail");
                }
            }
        }else{
            if(resultListener!=null){
                resultListener.onFail("parse checkreception fail");
            }
        }
    }
    private static  Object[] parseMessage(String message){
        List<CheckReceData> undealList = new ArrayList<>();
        List<CheckReceData> dealedList = new ArrayList<>();
        Object[] objs = {undealList,dealedList};
        try{
            Gson gson = new Gson();
            CheckSepatorData checkSepatorData = gson.fromJson(message, CheckSepatorData.class);
            undealList = parseOneMessage(checkSepatorData.waitConfirm);
            dealedList = parseOneMessage(checkSepatorData.alreadyConfirm);
            objs[0] = undealList;
            objs[1] = dealedList;
        }catch (Exception e){

        }
        return objs;
    }


    private static  List<CheckReceData> parseOneMessage(String msg){
        List<CheckReceData> checkReceDataList = new ArrayList<>();
        Gson gson = new Gson();
        try{
            Type type = new TypeToken<List<String[]>>(){}.getType();
            List<String[]> mList = gson.fromJson(msg,type);
            for(String[] strs:mList){
                checkReceDataList.add(new CheckReceData(strs[0],strs[1],strs[2],strs[3],strs[4]));
            }
        }catch (Exception e){

        }
        return checkReceDataList;
    }
}
