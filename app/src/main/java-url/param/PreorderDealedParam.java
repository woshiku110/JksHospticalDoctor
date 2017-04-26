package param;


import com.woshiku.urllibrary.domain.CommonUrlData;

import java.util.HashMap;
import java.util.Map;
import common.Global;

/**
 * Created by admin on 2017-04-19.
 */

public class PreorderDealedParam {
    public static CommonUrlData preorderDealed(){
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/AppointmentSupport_getDocterYjdList";
        String intent = "PreorderDealedParam";
        Map<String,String> map = new HashMap<>();
        map.put("token", Global._token);
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
}
