package param;

import com.woshiku.urllibrary.domain.CommonUrlData;

import java.util.HashMap;
import java.util.Map;

import common.Global;

/**
 * Created by admin on 2017-04-25.
 */

public class HoldDialogParam {
    public static CommonUrlData holdDialog(){
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/AppointmentSupport_getDocterWaitingList";
        String intent = "HoldDialog";
        Map<String,String> map = new HashMap<>();
        map.put("token", Global._token);
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
}
