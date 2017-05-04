package param;

import com.woshiku.urllibrary.domain.CommonUrlData;

import java.util.HashMap;
import java.util.Map;

import common.Global;

/**
 * Created by admin on 2017-04-28.
 */

public class MedicalSearchParam {
    /**
     * @desc 用于药品搜索
     * */
    public static CommonUrlData medicalSearch(String mcorbh){
        String baseUrl = Global.baseUrl;
        String actionUrl = "yuyue/MedicinalSupport_addMedicinal";
        String intent = "MedicalSearch";
        Map<String,String> map = new HashMap<>();
        map.put("mcorbh",mcorbh);
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
}
