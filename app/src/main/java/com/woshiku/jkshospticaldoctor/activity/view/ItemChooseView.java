package com.woshiku.jkshospticaldoctor.activity.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.woshiku.jkshospticaldoctor.R;

/**
 * Created by admin on 2017-04-18.
 */

public class ItemChooseView extends LinearLayout{
    LinearLayout leftLine,rightLine;
    TextView leftText,rightText;
    private boolean isWaitRece = true;
    private UserClickListener userClickListener;

    public ItemChooseView(Context context) {
        super(context);
    }

    public ItemChooseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    interface UserClickListener{
        void userClick(boolean isWait);
    }



    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.item_choose_view,this);
        leftLine = (LinearLayout) findViewById(R.id.item_choose_left);
        rightLine = (LinearLayout)findViewById(R.id.item_choose_right);
        leftText = (TextView)findViewById(R.id.item_choose_left_txt);
        rightText = (TextView)findViewById(R.id.item_choose_right_txt);
        setWaitChecked(true);
        leftLine.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userClickListener != null){
                    userClickListener.userClick(true);
                }
            }
        });
        rightLine.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userClickListener != null){
                    userClickListener.userClick(false);
                }
            }
        });
    }

    private void setWaitChecked(boolean isChecked){
        if(isChecked){
            leftLine.setEnabled(false);//设置left选中
            rightLine.setEnabled(true);
            leftText.setTextColor(ContextCompat.getColor(getContext(),R.color.txt_selected_color));
            rightText.setTextColor(ContextCompat.getColor(getContext(),R.color.txt_unselected_color));
        }else{
            leftLine.setEnabled(true);
            rightLine.setEnabled(false);//设置right选中
        }
    }

    public void setUserClickListener(UserClickListener userClickListener) {
        this.userClickListener = userClickListener;
    }
}
