package com.woshiku.urllibrary.util;


import com.woshiku.urllibrary.domain.Result;
import com.woshiku.urllibrary.inter.CommonUrlListener;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/11/2.
 */
public class HpUtil {
    private OkHttpClient okHttpClient;
    protected CommonUrlListener commonUrlListener;
    public HpUtil(){
        if(okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS);
            okHttpClient =  builder.build();
        }
    }
    public void setCommonUrlListener(CommonUrlListener commonUrlListener) {
        this.commonUrlListener = commonUrlListener;
    }
    protected synchronized Response onInternet(String baseUrl, String actionUrl, Map<String,String> params, String intent, boolean isAsc) throws IOException {
        String recordUrl="";
        recordUrl += baseUrl;
        recordUrl += actionUrl;
        recordUrl += "?";
        FormBody.Builder postDatas = new FormBody.Builder();
        for(Map.Entry<String,String> entry:params.entrySet()){
            postDatas.add(entry.getKey(),entry.getValue());
            recordUrl += entry.getKey()+"="+entry.getValue()+"&";
        }
        BugLog.print(recordUrl);
        Request request = new Request.Builder().url(baseUrl+actionUrl).post(postDatas.build()).build();
        Response response = null;
        if(isAsc){
            response = okHttpClient.newCall(request).execute();
        }else{
            okHttpClient.newCall(request).enqueue(new MyCallBack(intent));
        }
        return response;
    }
    class MyCallBack implements Callback {
        private String intent;

        public MyCallBack(String intent) {
            this.intent = intent;
        }

        @Override
        public void onFailure(Call call, IOException e) {
            Result result = new Result();
            result.setIntent(intent);
            result.setSuccess(false);
            if(commonUrlListener != null){
                commonUrlListener.urlResult(result);
            }
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
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
