package com.woshiku.urllibrary.url.base;


import android.util.Log;

import com.squareup.okhttp.Response;
import com.woshiku.urllibrary.domain.CommonUrlData;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.util.HpUtil;

import java.io.IOException;

/**
 * Created by Administrator on 2016/11/2.
 */
public class CommonUrl extends HpUtil {
    public CommonUrl() {
        super();
    }
    public void loadUrl(CommonUrlData commonUrlData){
        onInternet(commonUrlData.getBaseUrl(),commonUrlData.getActionUrl(),commonUrlData.getMap(),commonUrlData.getIntent(),false);
    }
    public Result loadUrlAsc(CommonUrlData commonUrlData){
        Result result = new Result();
        result.setSuccess(false);
        try {
            Response response = onInternet(commonUrlData.getBaseUrl(),commonUrlData.getActionUrl(),commonUrlData.getMap(),
                    commonUrlData.getIntent(),true);
            result.setMsg(response.body().string());
            result.setSuccess(true);
        } catch (IOException e) {
            result.setSuccess(false);
            result.setMsg(null);
            Log.e("lookat","body err");
        }finally {
            return result;
        }
    }
}
