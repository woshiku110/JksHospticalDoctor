package parse;

import com.google.gson.Gson;
import com.woshiku.urllibrary.domain.Result;

import inter.ResultListener;

/**
 * Created by admin on 2017-05-10.
 */

public class JumpOrderParse {

    public static void jumpOrder(Result result, ResultListener resultListener){
        if(result.isSuccess()){
            if(resultListener != null){
                Gson gson = new Gson();
                Result result1 = gson.fromJson(result.getMsg(),Result.class);
                if(result1.isSuccess()){
                    resultListener.onSuccess(result1.getMsg());
                }else{
                    resultListener.onFail("跳过失败!!!");
                }
            }
        }else{
            if(resultListener != null){
                resultListener.onFail("网络连接失败!!!");
            }
        }
    }
}
