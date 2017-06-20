package parse;

import com.google.gson.Gson;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.urllibrary.domain.Result;

import inter.ResultListener;

/**
 * Created by admin on 2017-04-25.
 */

public class BackOrderParse {
    public static void backOrder(Result result, ResultListener resultListener){
        LogUtil.print("result:"+result.toString());
        if(!result.isSuccess()){
            if(resultListener!=null){
                resultListener.onFail("网络获取失败");
            }
        }else{
            if(resultListener!=null){
                Gson gson = new Gson();
                Result rr = gson.fromJson(result.getMsg(),Result.class);
                if(rr.isSuccess()){
                    resultListener.onSuccess(rr.getMsg());
                }else{
                    resultListener.onFail("解析失败");
                }
            }
        }
    }
}
