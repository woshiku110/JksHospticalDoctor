package com.woshiku.dialoglib.view;

import android.content.Context;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.woshiku.dialoglib.R;

/**
 * Created by Administrator on 2017/2/22.
 */
public class MyCheckBox extends LinearLayout{
    RelativeLayout relateLayout;
    CheckBox view;
    TextView textView;

    public MyCheckBox(Context context) {
        super(context);
    }
    public MyCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MyCheckBox(Context context,String name,boolean isEdit) {
        this(context);
        initViews(name, isEdit, false);
    }
    public MyCheckBox(Context context,String name,boolean isEdit,boolean isHave) {
        this(context);
        initViews(name, isEdit,isHave);
    }
    private void initViews(String name,boolean isEdit,boolean isHave){
        LayoutInflater.from(getContext()).inflate(R.layout.item_my_checkbox, this);
        relateLayout = (RelativeLayout)findViewById(R.id.my_checkbox_relate);
        view = (CheckBox)findViewById(R.id.my_checkbox_view);
        textView = (TextView)findViewById(R.id.my_checkbox_text);
        textView.setText(name);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        textView.setTextColor(ContextCompat.getColor(getContext(),R.color.hospital_txt_color));
        if(isEdit){
            int size = (int)getContext().getResources().getDimension(R.dimen.check_detail_view_item_check_size);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)textView.getLayoutParams();
            params.leftMargin = (int)getContext().getResources().getDimension(R.dimen.check_detail_view_item_check_margin);
            RelativeLayout.LayoutParams viewParam = (RelativeLayout.LayoutParams)view.getLayoutParams();
            viewParam.width = size;
            viewParam.height = size;
            view.setLayoutParams(viewParam);
        }else{
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)textView.getLayoutParams();
            params.leftMargin = 0;
            if(isHave){
                textView.setTextColor(getContext().getResources().getColor(R.color.new_orange_color));
                view.setVisibility(View.INVISIBLE);
            }else{
                view.setVisibility(View.GONE);
            }
        }
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.item_my_checkbox, this);
        relateLayout = (RelativeLayout)findViewById(R.id.my_checkbox_relate);
        view = (CheckBox)findViewById(R.id.my_checkbox_view);
        textView = (TextView)findViewById(R.id.my_checkbox_text);
    }

    public TextView getTextView() {
        return textView;
    }

    public void setText(final String msg){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                textView.setText(msg);
            }
        });
    }

    public String getText(){
        return textView.getText().toString();
    }

    public boolean getCheck(){
        return view.isChecked();
    }

    public CheckBox getCheckBox(){
        return view;
    }
}
