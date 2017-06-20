package param;

import com.woshiku.urllibrary.domain.CommonUrlData;

import java.util.HashMap;
import java.util.Map;

import common.Global;

/**
 * Created by admin on 2017-05-16.
 */

public class DefaultAddressParam {
    public static CommonUrlData defaultAddr(){
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/DocterPersonalCenter_getAllDefaultMsg";
        String intent = "DefaultAddress";
        Map<String,String> map = new HashMap<>();
        map.put("token", Global._token);
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
}
