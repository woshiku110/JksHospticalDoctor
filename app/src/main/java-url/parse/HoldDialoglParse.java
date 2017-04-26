package parse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woshiku.jkshospticaldoctor.activity.domain.HoldDialogData;
import com.woshiku.jkshospticaldoctor.activity.domain.PreorderData;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.urllibrary.domain.Result;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import inter.ResultListener;

/**
 * Created by admin on 2017-04-19.
 */

public class HoldDialoglParse {
    public static void holdDialogParse(Result msg, ResultListener resultListener){
        if(msg.isSuccess()){
            parseMsg(msg.getMsg(),resultListener);
        }else{
            resultListener.onFail("parse fail");
            Result result = msg;
            LogUtil.print("PreorderUndealParse parse fail");
        }
    }

    private static void parseMsg(String msg,ResultListener resultListener){
        Gson gson = new Gson();
        try{
            Result result = gson.fromJson(msg, Result.class);
            if(result.isSuccess()){
                resultListener.onSuccess(parseMessages(result.getMsg()));
            }else{
                resultListener.onFail(result.getMsg());
            }
        }catch (Exception e){
            resultListener.onFail(e);
        }
    }
    /**
     * @desc 解析多个个message
     * */
    private static List<HoldDialogData> parseMessages(String msg){
        List<HoldDialogData> mList = new ArrayList<>();
        Gson gson = new Gson();
        Type listType = new TypeToken<List<String[]>>(){}.getType();
        try{
            List<String[]> listStr = gson.fromJson(msg,listType);
            for(String[] strs:listStr){
                mList.add(parseMessage(strs));
            }
        }catch (Exception e){
        }
        return mList;
    }

    /**
     * @desc 解析单个message
     * */
    private static HoldDialogData parseMessage(String[] strs){
        return new HoldDialogData(strs[0],strs[1],strs[2],strs[3],strs[4],strs[5]);
    }
}
