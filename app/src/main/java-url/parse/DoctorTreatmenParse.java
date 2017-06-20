package parse;

import com.google.gson.Gson;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.urllibrary.domain.Result;

import inter.ResultListener;

/**
 * Created by admin on 2017-05-10.
 */

public class DoctorTreatmenParse {
    public static void doctorTreatmen(Result result, ResultListener resultListener){
        if(result.isSuccess()){
            if(resultListener != null){
                Gson gson = new Gson();
                LogUtil.print("doctor treatment"+result.getMsg());
                Result result1 = gson.fromJson(result.getMsg(),Result.class);
                if(result1.isSuccess()){
                    resultListener.onSuccess(result1.getMsg());
                }else{
                    resultListener.onFail("退号失败!!!");
                }
            }
        }else{
            if(resultListener != null){
                resultListener.onFail("网络连接失败!!!");
            }
        }
    }
}
