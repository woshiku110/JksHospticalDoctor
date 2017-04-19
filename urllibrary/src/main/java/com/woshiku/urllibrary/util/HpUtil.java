package com.woshiku.urllibrary.util;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.woshiku.urllibrary.common.Global;
import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.inter.CommonUrlListener;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/11/2.
 */
public class HpUtil {
    private OkHttpClient okHttpClient;
    protected CommonUrlListener commonUrlListener;
    public HpUtil(){
        if(okHttpClient == null){
            okHttpClient = new OkHttpClient();
            okHttpClient.setConnectTimeout(Global.timeOutTime, TimeUnit.SECONDS);
        }
    }
    public void setCommonUrlListener(CommonUrlListener commonUrlListener) {
        this.commonUrlListener = commonUrlListener;
    }
    protected synchronized Response onInternet(String baseUrl,String actionUrl,Map<String,String> params,String intent,boolean isAsc){
        String recordUrl="";
        recordUrl+=baseUrl;
        recordUrl+=actionUrl;
        recordUrl+="?";
        FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
        for(Map.Entry<String,String> entry:params.entrySet()){
            formEncodingBuilder.add(entry.getKey(),entry.getValue());
            recordUrl += entry.getKey()+"="+entry.getValue()+"&";
        }
        BugLog.print(intent,recordUrl);
        Request request = new Request.Builder().url(baseUrl+actionUrl).post(formEncodingBuilder.build()).build();
        if(isAsc){
            try {
                return okHttpClient.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }else{
            okHttpClient.newCall(request).enqueue(new MyCallBack(intent));
        }
        return null;
    }
    class MyCallBack implements Callback{
        private String intent;

        public MyCallBack(String intent) {
            this.intent = intent;
        }

        @Override
        public void onFailure(Request request, IOException e) {
            BugLog.print(intent, "error", e);
            Result result = new Result();
            result.setIntent(intent);
            result.setSuccess(false);
            if(commonUrlListener != null){
                commonUrlListener.urlResult(result);
            }
        }

        @Override
        public void onResponse(Response response) throws IOException {
            BugLog.print(intent, response.body().toString());
            Result result = new Result();
            result.setMsg(response.body().string());
            result.setIntent(intent);
            result.setSuccess(true);
            if(commonUrlListener != null){
                commonUrlListener.urlResult(result);
            }
        }
    }
}
