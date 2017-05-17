package com.woshiku.jkshospticaldoctor.activity.activity.meetdialog;

import android.view.View;
import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.activity.web.WebActivity;
import butterknife.OnClick;

/**
 * Created by admin on 2017-05-04.
 * 会诊活动
 */

public class MeetDialogsisActivity extends WebActivity{

    @Override
    protected void loadChildrenMethod() {

    }

    @Override
    protected void swipeBackCallback() {

    }



    @OnClick({R.id.web_title_return})
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
        }
    }
}
