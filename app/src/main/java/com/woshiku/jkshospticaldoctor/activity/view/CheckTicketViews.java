package com.woshiku.jkshospticaldoctor.activity.view;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.woshiku.jkshospticaldoctor.R;

/**
 * Created by admin on 2017-05-08.
 * @desc 检查单显示views
 */

public class CheckTicketViews {
    LinearLayout outLayout;
    Context context;
    public CheckTicketViews(Context context,LinearLayout outLayout) {
        this.outLayout = outLayout;
        this.context = context;
    }

    public void generViews(String[] checkTicketDataList){
        outLayout.removeAllViews();
        for(String checkTicketData:checkTicketDataList){
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            View innerLayout = View.inflate(context,R.layout.item_confirm_order,null);
            TextView textView = (TextView)innerLayout.findViewById(R.id.item_confirm_order_txt);
            textView.setText(checkTicketData);
            outLayout.addView(innerLayout,lp);
        }
    }
}
