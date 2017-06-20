package param;

import com.woshiku.urllibrary.domain.CommonUrlData;

import java.util.HashMap;
import java.util.Map;

import common.Global;

/**
 * Created by admin on 2017-05-05.
 * @desc 检查单参数
 */

public class CheckTicketParam {
    public static CommonUrlData checkTicket(String yyid){
        String baseUrl = Global.baseUrl;
        String actionUrl = "manage/CheckManage_checkList";
        String intent = "CheckTicket";
        Map<String,String> map = new HashMap<>();
        map.put("yyid",yyid);
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
}
