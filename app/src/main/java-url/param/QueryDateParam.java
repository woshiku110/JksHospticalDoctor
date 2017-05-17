package param;

import com.woshiku.urllibrary.domain.CommonUrlData;
import java.util.HashMap;
import java.util.Map;
import common.Global;

/**
 * Created by admin on 2017-05-15.
 */

public class QueryDateParam {
    public static CommonUrlData queryDate(){
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/DocterPersonalCenter_queryOpenedDate";
        String intent = "QueryDateParam";
        Map<String,String> map = new HashMap<>();
        map.put("token", Global._token);
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
}
