package com.woshiku.urllibrary.url.base;


import com.woshiku.urllibrary.domain.CommonUrlData;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.util.BugLog;
import com.woshiku.urllibrary.util.HpUtil;
import java.io.IOException;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/11/2.
 */
public class CommonUrl extends HpUtil {
    public CommonUrl() {
        super();
    }
    public void loadUrl(CommonUrlData commonUrlData) throws IOException {
        onInternet(commonUrlData.getBaseUrl(),commonUrlData.getActionUrl(),commonUrlData.getMap(),commonUrlData.getIntent(),false);
    }
    public Result loadUrlAsc(CommonUrlData commonUrlData){
        Result result = new Result();
        result.setSuccess(false);
        Response response;
        try {
            response = onInternet(commonUrlData.getBaseUrl(),commonUrlData.getActionUrl(),commonUrlData.getMap(),
                    commonUrlData.getIntent(),true);
            result.setMsg(response.body().string());
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg(null);
        }finally {
            return result;
        }
    }
}
