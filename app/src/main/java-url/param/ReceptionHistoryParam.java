package param;

import com.woshiku.urllibrary.domain.CommonUrlData;

import java.util.HashMap;
import java.util.Map;

import common.Global;

/**
 * Created by admin on 2017-04-29.
 */

public class ReceptionHistoryParam {
    /**
     * @desc 用于药品搜索
     * */
    public static CommonUrlData receptionHistory(String mc){
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/AppointmentSupport_getHistoryList";
        String intent = "ReceptionHistory";
        Map<String,String> map = new HashMap<>();
        map.put("mc",mc);
        map.put("token",Global._token);
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
}
