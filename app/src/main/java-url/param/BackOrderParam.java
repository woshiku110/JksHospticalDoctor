package param;

import com.woshiku.urllibrary.domain.CommonUrlData;

import java.util.HashMap;
import java.util.Map;

import common.Global;

/**
 * Created by admin on 2017-04-25.
 */

public class BackOrderParam {
    public static CommonUrlData backOrder(String orderId, String ysjjyy){
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/AppointmentProcess_docterBounce";
        String intent = "backOrder";
        Map<String,String> map = new HashMap<>();
        map.put("orderId",orderId);
        map.put("ysjjyy",ysjjyy);
        map.put("token", Global._token);
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
}
