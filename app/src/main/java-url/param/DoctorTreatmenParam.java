package param;

import com.woshiku.urllibrary.domain.CommonUrlData;

import java.util.HashMap;
import java.util.Map;
import common.Global;

/**
 * Created by admin on 2017-05-10.
 *@desc 叫号
 */

public class DoctorTreatmenParam {
    public static CommonUrlData doctorTreatmen(String orderId){
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/AppointmentProcess_docterTreatment";
        String intent = "doctorTreatmen";
        Map<String,String> map = new HashMap<>();
        map.put("token",Global._token);
        map.put("orderId",orderId);
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
}
