package parse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.urllibrary.domain.Result;

import java.lang.reflect.Type;
import java.util.List;

import inter.ResultListener;

/**
 * Created by admin on 2017-04-19.
 */

public class PreorderUndealParse {
    public static void preorderUndeal(String msg, ResultListener resultListener){
        Gson gson = new Gson();
        Result result = gson.fromJson(msg,Result.class);
        if(result.isSuccess()){
            parseMsg(result.getMsg(),resultListener);
        }else{
            resultListener.onFail("parse fail");
            LogUtil.print("PreorderUndealParse parse fail");
        }
    }

    private static void parseMsg(String msg,ResultListener resultListener){
        Gson gson = new Gson();
        try{
            Result result = gson.fromJson(msg, Result.class);
            if(result.isSuccess()){
                //resultListener.onSuccess();
            }else{
                resultListener.onFail(result.getMsg());
            }
        }catch (Exception e){
            resultListener.onFail(e);
        }
    }

    private static void parseMessages(String msg){
        Gson gson = new Gson();
        Type listType = new TypeToken<List<String[]>>(){}.getType();
        List<String[]> listStr = gson.fromJson(msg,listType);
    }

}
