package parse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woshiku.jkshospticaldoctor.activity.utils.DateChangeUtil;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.jkshospticaldoctor.activity.utils.RdUtil;
import com.woshiku.urllibrary.domain.Result;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import inter.ResultListener;

/**
 * Created by admin on 2017-05-15.
 */

public class QueryDateParse {
    public static void queryDate(Result result){
        if(result.isSuccess()){
            Gson gson = new Gson();
            Result rr = gson.fromJson(result.getMsg(),Result.class);
            if(rr.isSuccess()){
                parseDate(rr.getMsg());
                RdUtil.saveData("date",rr.getMsg());//把获取的日期显示
            }else{
                LogUtil.print("query","查询失败");
            }
        }else{
            LogUtil.print("query","失败");
        }
    }

    public static List<String> parseDate(String date){
        List<String> dataList = new ArrayList<>();
        try{
            Type type = new TypeToken<List<String>>(){}.getType();
            Gson gson = new Gson();
            dataList = gson.fromJson(date,type);
        }catch (Exception e){

        }
        return DateChangeUtil.getShowDate(dataList);
    }
}
