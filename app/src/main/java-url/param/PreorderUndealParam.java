package param;


import com.woshiku.urllibrary.domain.CommonUrlData;
import java.util.HashMap;
import java.util.Map;
import common.Global;

/**
 * Created by admin on 2017-04-19.
 */

public class PreorderUndealParam {
    public static CommonUrlData preorderUndeal(){
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/AppointmentSupport_getDocterDjdList";
        String intent = "PreorderUndealParam";
        Map<String,String> map = new HashMap<>();
        map.put("token", Global._token);
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
}
