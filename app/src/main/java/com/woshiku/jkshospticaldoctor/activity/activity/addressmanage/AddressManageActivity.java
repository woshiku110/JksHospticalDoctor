package com.woshiku.jkshospticaldoctor.activity.activity.addressmanage;

import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.activity.web.WebActivity;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import java.util.HashMap;
import java.util.Map;
import butterknife.OnClick;

/**
 * Created by admin on 2017-05-04.
 */

public class AddressManageActivity extends WebActivity{
    Map<String,String> saveMap = new HashMap<>();
    @Override
    protected void loadChildrenMethod() {
        concertText.setText("新增");
        concert_bt.setVisibility(View.VISIBLE);
        webView.addJavascriptInterface(new JsInteration(), "control");
    }
    /**
     * 需要js实现的方法
     * */
    public class JsInteration {
        @JavascriptInterface
        public void test(){

        }
        @JavascriptInterface
        public void setKeyValue(String key,String value){
            saveMap.put(key,value);
            LogUtil.print("set:"+key+"value:"+value);
        }
        @JavascriptInterface
        public String getValue(String key){
            LogUtil.print("get:"+key+"value:"+saveMap.get(key));
            return saveMap.get(key);
        }
    }

    @OnClick({R.id.web_title_return,R.id.web_title_right})
    void userClick(View view){
        switch (view.getId()){
            case R.id.web_title_return:
                if(webView.canGoBack()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            webView.goBack();
                        }
                    });
                }else{
                    scrollToFinishActivity();
                }
                break;
            case R.id.web_title_right:
                String title = concertText.getText().toString();
                LogUtil.print("title："+title);
                if(title.equals("新增")){
                    webView.loadUrl("javascript:addAddress()");
                }else if(title.equals("保存")){
                    LogUtil.print("调用save");
                    webView.loadUrl("javascript:save()");
                }
                break;
        }
    }

    @Override
    public void titleChanged(String title) {
        super.titleChanged(title);
        if(title.equals("地址管理")){
            concertText.setText("新增");
        }else if(title.equals("添加新地址")){
            concertText.setText("保存");
        }else if(title.equals("选择医院")){
            concertText.setText("");
        }
        LogUtil.print(title);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) )
        {
            if(webView.canGoBack()){
                webView.goBack();
            }else{
                scrollToFinishActivity();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void swipeBackCallback() {

    }
}
