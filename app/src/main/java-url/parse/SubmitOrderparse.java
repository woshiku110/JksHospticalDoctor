package parse;

import com.google.gson.Gson;
import com.woshiku.urllibrary.domain.Result;
import inter.ResultListener;

/**
 * Created by admin on 2017-05-13.
 */

public class SubmitOrderparse {
    public static void submitOrder(Result result, ResultListener resultListener){
        if(result.isSuccess()){
            Result rr = new Gson().fromJson(result.getMsg(),Result.class);
            if(rr.isSuccess()){
                resultListener.onSuccess(result.getMsg());
            }else{
                resultListener.onFail("失败");
            }
        }else{
            resultListener.onFail("网络获取失败");
        }
    }
}
