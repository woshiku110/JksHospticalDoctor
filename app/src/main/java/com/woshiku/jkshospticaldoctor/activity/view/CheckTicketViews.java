package com.woshiku.jkshospticaldoctor.activity.view;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.domain.CheckTicketData;
import java.util.List;

/**
 * Created by admin on 2017-05-08.
 */

public class CheckTicketViews {
    LinearLayout outLayout;
    Context context;
    public CheckTicketViews(Context context,LinearLayout outLayout) {
        this.outLayout = outLayout;
        this.context = context;
    }

    public void generViews(List<CheckTicketData> checkTicketDataList){
        outLayout.removeAllViews();
        View innerLayout = View.inflate(context,R.layout.item_confirm_order,null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for(CheckTicketData checkTicketData:checkTicketDataList){
            outLayout.addView(innerLayout,lp);
        }
    }
}
