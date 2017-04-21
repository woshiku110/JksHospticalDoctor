package param;

import com.woshiku.urllibrary.domain.CommonUrlData;
import java.util.HashMap;
import java.util.Map;
import common.Global;

/**
 * Created by admin on 2017-04-21.
 */

public class LoginParam {
    public static CommonUrlData login(String name,String pass){
        String baseUrl = Global.baseUrl;
        String actionUrl = "upms/docter_login";
        String intent = "LoginParam";
        Map<String,String> map = new HashMap<>();
        map.put("username",name);
        map.put("password",pass);
        return new CommonUrlData(baseUrl,actionUrl,map,intent);
    }
}
