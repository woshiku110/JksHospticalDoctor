package com.woshiku.urllibrary.domain;



import com.woshiku.urllibrary.common.Global;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/2.
 */
public class CommonUrlData {
    private Map<String,String> map;
    private String baseUrl;
    private String actionUrl;
    private String intent;

    public CommonUrlData() {
        this.baseUrl = Global.baseUrl;
    }

    public CommonUrlData(String actionUrl, Map<String, String> map, String intent) {
        this.baseUrl = Global.baseUrl;
        this.actionUrl = actionUrl;
        this.map = map;
        this.intent = intent;
    }

    public CommonUrlData(String baseUrl, String actionUrl, Map<String, String> map, String intent) {
        this.actionUrl = actionUrl;
        this.baseUrl = baseUrl;
        this.map = map;
        this.intent = intent;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
