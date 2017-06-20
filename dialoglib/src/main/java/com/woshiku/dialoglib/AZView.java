package com.woshiku.dialoglib;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by admin on 2017-05-06.
 */

public class AZView extends LinearLayout{
    private String []characters = {"A","B","C","D","E","F","G",
            "H","I","J","K","L","M","N",
            "O","P","Q","R","S","T","U",
            "V","W","X","Y","Z"};
    private AzClickListener azClickListener;
    public interface AzClickListener{
        void azClick(String cc);
    }
    public AZView(Context context) {
        super(context);
    }

    public AZView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAzClickListener(AzClickListener azClickListener) {
        this.azClickListener = azClickListener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setOrientation(VERTICAL);
        addViews();
    }

    private void addViews(){
        removeAllViews();
        TextView textView;
        for(int i=0;i<characters.length;i++){
            final int pos = i;
            textView = new TextView(getContext());
            textView.setText(characters[i]);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
            textView.setGravity(Gravity.CENTER);
            textView.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            Log.e("kankan",characters[pos]);
                            if(azClickListener != null){
                                azClickListener.azClick(characters[pos]);
                            }
                            break;
                    }
                    return true;
                }
            });
            int size = (int)getContext().getResources().getDimension(R.dimen.check_az_size);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, size);
            addView(textView,lp);
        }
    }
}
