package parse;

import com.google.gson.Gson;
import com.woshiku.jkshospticaldoctor.activity.domain.LoginReturnData;
import com.woshiku.jkshospticaldoctor.activity.utils.RdUtil;
import com.woshiku.urllibrary.domain.Result;
import inter.ResultListener;

/**
 * Created by admin on 2017-04-21.
 */

public class LoginParse {
    public static void login(Result result, ResultListener resultListener){
        if(!result.isSuccess()){
            if(resultListener!=null){
                resultListener.onFail("网络获取失败");
            }
        }else{
            parseMessage(result.getMsg(),resultListener);
        }
    }
    private static void parseMessage(String msg,ResultListener resultListener){
        Gson gson = new Gson();
        Result result = gson.fromJson(msg,Result.class);
        if(result.isSuccess()){
            Gson pGson = new Gson();
            LoginReturnData loginReturnData = pGson.fromJson(result.getMsg(),LoginReturnData.class);
            if(resultListener!=null){
                resultListener.onSuccess(loginReturnData);
            }
        }else{
            if(resultListener!=null){
                resultListener.onFail(result.getMsg());
            }
        }
    }
}
