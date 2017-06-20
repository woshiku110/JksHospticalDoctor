package param;

import com.woshiku.urllibrary.domain.CommonUrlData;

import java.util.HashMap;
import java.util.Map;

import common.Global;

/**
 * Created by admin on 2017-05-09.
 *
 */

public class DoctorCreateOrderParam {
    public static CommonUrlData doctorCreateOrder(String processId, String contents){
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/PhysicalProcess_createOrder";
        String intent = "doctorCreateOrder";
        Map<String,String> map = new HashMap<>();
        map.put("token",Global._token);
        map.put("processId",processId);
        map.put("contents",contents);
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
}
