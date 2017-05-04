package parse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woshiku.jkshospticaldoctor.activity.domain.MedicalSearchData;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.urllibrary.domain.Result;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import inter.ResultListener;

/**
 * Created by admin on 2017-04-29.
 */

public class MedicalSearchParse {
    public static void medicalSearch(Result result, ResultListener resultListener){
        if(result.isSuccess()){
            parseMessage(result.getMsg(),resultListener);
        }else{
            resultListener.onFail("网络获取失败");
        }
    }
    private static void parseMessage(String msg, ResultListener resultListener){
        Gson gson = new Gson();
        Result result = gson.fromJson(msg,Result.class);
        if(result.isSuccess()){
            resultListener.onSuccess(parseOneMessage(result.getMsg()));
        }else{
            resultListener.onFail("拿取失败");
        }
    }
    private static List<MedicalSearchData> parseOneMessage(String msg){
        List<MedicalSearchData> mList = new ArrayList<>();
        try{
            Type type = new TypeToken<List<String[]>>(){}.getType();
            Gson gson = new Gson();
            List<String[]> strsList = gson.fromJson(msg,type);
            for(String[] strs:strsList){
                mList.add(new MedicalSearchData(strs[0],strs[1],strs[2],strs[3],strs[4],strs[5],strs[6],strs[7]));
            }
        }catch (Exception e){
            LogUtil.print("parse error"+msg);
        }
        return mList;
    }
}
