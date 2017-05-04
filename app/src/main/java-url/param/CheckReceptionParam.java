package param;

import com.woshiku.urllibrary.domain.CommonUrlData;

import java.util.HashMap;
import java.util.Map;

import common.Global;

/**
 * Created by admin on 2017-05-04.
 * @desc 核实处方
 */

public class CheckReceptionParam {
    public static CommonUrlData checkReception(){
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/MedicinalSupport_getDoctorEstablishMedicinal";
        String intent = "CheckReception";
        Map<String,String> map = new HashMap<>();
        map.put("token", Global._token);
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
}
