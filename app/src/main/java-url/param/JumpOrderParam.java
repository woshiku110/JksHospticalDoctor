package param;

import com.woshiku.urllibrary.domain.CommonUrlData;

import java.util.HashMap;
import java.util.Map;

import common.Global;

/**
 * Created by admin on 2017-05-10.
 */

public class JumpOrderParam {
    public static CommonUrlData jumpOrder(String orderId){
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/AppointmentProcess_docterRetreatment";
        String intent = "JumpOrder";
        Map<String,String> map = new HashMap<>();
        map.put("orderId",orderId);
        map.put("token", Global._token);
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
}
