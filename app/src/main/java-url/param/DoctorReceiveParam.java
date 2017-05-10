package param;

import com.woshiku.urllibrary.domain.CommonUrlData;

import java.util.HashMap;
import java.util.Map;

import common.Global;

/**
 * Created by admin on 2017-05-09.
 */

public class DoctorReceiveParam {
    public static CommonUrlData doctorReceive(String hopeTime,String treatmentAddr,String waitAddr,String orderId){
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/AppointmentProcess_docterReceive";
        String intent = "doctorReceive";
        Map<String,String> map = new HashMap<>();
        map.put("token",Global._token);
        map.put("time",hopeTime);
        map.put("treatmentAddr",treatmentAddr);
        map.put("waitAddr",waitAddr);
        map.put("orderId",orderId);
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
}
