package param;

import com.woshiku.urllibrary.domain.CommonUrlData;

import java.util.HashMap;
import java.util.Map;

import common.Global;

/**
 * Created by admin on 2017-05-13.
 */

public class SubmitOrderParam {
    public static CommonUrlData submitOrder(String orderId,String bls,String cfs){
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/MedicinalProcess_submitOrder";
        String intent = "PreorderUndealParam";
        Map<String,String> map = new HashMap<>();
        map.put("token", Global._token);
        map.put("processId",orderId);
        map.put("blms","");
        map.put("bls",bls);
        map.put("cfs",cfs);
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
}
