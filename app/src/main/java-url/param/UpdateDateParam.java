package param;

import com.woshiku.urllibrary.domain.CommonUrlData;

import java.util.HashMap;
import java.util.Map;

import common.Global;

/**
 * Created by admin on 2017-05-15.
 */

public class UpdateDateParam {
    public static CommonUrlData updateDate(String date){
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/DocterPersonalCenter_turnDateState";
        String intent = "UpdateDateParam";
        Map<String,String> map = new HashMap<>();
        map.put("date",date);
        map.put("token", Global._token);
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
}
