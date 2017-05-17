package parse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woshiku.jkshospticaldoctor.activity.domain.AddressData;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.jkshospticaldoctor.activity.utils.RdUtil;
import com.woshiku.urllibrary.domain.Result;

import java.lang.reflect.Type;

/**
 * Created by admin on 2017-05-16.
 */

public class DefaultAddressParse {
    public static void defaultAddr(Result msg){
        if(msg.isSuccess()){
            Gson gson = new Gson();
            Result result = gson.fromJson(msg.getMsg(),Result.class);
            if(result.isSuccess()){
                RdUtil.saveData("address",result.getMsg());
                LogUtil.print("addr",result.getMsg());
            }
        }else{

        }
    }
    public static AddressData getAddressData(String msg){
        AddressData addressData = null;
        try{
            Gson gson = new Gson();
            Type type = new TypeToken<String[]>(){}.getType();
            String[] strs = gson.fromJson(msg,type);
            if(strs[3].equals("1")){
                addressData = new AddressData(strs[0],strs[1],strs[2],true,strs[4]);
            }else {
                addressData = new AddressData(strs[0],strs[1],strs[2],false,strs[4]);
            }

        }catch (Exception e){

        }
        return addressData;
    }
}
