package com.woshiku.inputlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
/**
 * Created by Administrator on 2016/12/15.
 */
public class LeftIconInput extends RelativeLayout{
    ImageView icon;
    EditText editText;
    View view;
    //大小
    int iconWidth,iconHeight,textSize,icon_left_margin,icon_right_margin,icon_bottom_margin,line_height;
    //颜色
    int lineBg,textColor,hint_text_color;
    //背景
    int iconBg;
    String hint_text;
    public LeftIconInput(Context context) {
        super(context);
    }
    public LeftIconInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LeftIconInput);
        //大小
        iconWidth = getSize(typedArray,R.styleable.LeftIconInput_icon_width,R.dimen.icon_width);
        iconHeight = getSize(typedArray,R.styleable.LeftIconInput_icon_height, R.dimen.icon_height);
        textSize = getSize(typedArray,R.styleable.LeftIconInput_text_size,R.dimen.text_size);
        icon_left_margin = getSize(typedArray, R.styleable.LeftIconInput_icon_left_margin, R.dimen.icon_left_margin);
        icon_right_margin = getSize(typedArray, R.styleable.LeftIconInput_icon_right_margin, R.dimen.icon_right_margin);
        icon_bottom_margin = getSize(typedArray,R.styleable.LeftIconInput_icon_bottom_margin, R.dimen.icon_bottom_margin);
        line_height = getSize(typedArray, R.styleable.LeftIconInput_line_height, R.dimen.line_height);
        //颜色
        lineBg = getColor(typedArray,R.styleable.LeftIconInput_line_bg,R.color.line_bg);
        textColor = getColor(typedArray,R.styleable.LeftIconInput_text_color, R.color.text_color);
        hint_text_color = getColor(typedArray, R.styleable.LeftIconInput_hint_text_color, R.color.hint_text_color);
        //背景
        iconBg = typedArray.getResourceId(R.styleable.LeftIconInput_icon_bg, R.mipmap.icon_user);
        lineBg = getColor(typedArray,R.styleable.LeftIconInput_line_bg,R.color.line_bg);
        //文本
        hint_text = getText(typedArray,R.styleable.LeftIconInput_hint_text);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.input_left_icon, this);
        icon = (ImageView)findViewById(R.id.left_icon);
        view = findViewById(R.id.line_view);
        editText = (EditText)findViewById(R.id.edit_text);
        LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(iconWidth,iconHeight);
        iconParams.leftMargin = icon_left_margin;
        iconParams.rightMargin = icon_right_margin;
        icon.setLayoutParams(iconParams);
        RelativeLayout.LayoutParams lineViewParam = (RelativeLayout.LayoutParams)view.getLayoutParams();
        lineViewParam.topMargin = icon_bottom_margin;
        editText.setTextColor(textColor);
        editText.setHintTextColor(hint_text_color);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
        icon.setBackgroundResource(iconBg);
        view.setBackgroundColor(lineBg);
        editText.setHint(hint_text);
        editText.setHintTextColor(hint_text_color);
        editText.setTextColor(textColor);
    }
    public void setText(String text){
        editText.setText(text);
    }
    public String getText(){
        return editText.getText().toString();
    }
    private int getSize(TypedArray typedArray,int index,int defaultId){
        int size;
        try{
            size = typedArray.getResourceId(index,-1);
            if(size!=-1){
                return size;
            }else{
                size = (int)typedArray.getDimension(index,getResources().getDimension(defaultId));
                return size;
            }
        }catch (Exception e){
            size = (int)typedArray.getDimension(index,getResources().getDimension(defaultId));
            return size;
        }
    }
    private String getText(TypedArray typedArray,int index){
        int mark;
        try{
            mark = typedArray.getResourceId(index,-1);
            if(mark!=-1){
                return getContext().getResources().getString(mark);
            }else{
                return typedArray.getString(index);
            }
        }catch (Exception e){

        }
        return "";
    }
    private int getColor(TypedArray typedArray,int index,int defaultId){
        int color;
        color = typedArray.getResourceId(index,-1);
        if(color != -1){
            color = getContext().getResources().getColor(index);
        }else{
            color = getContext().getResources().getColor(defaultId);
        }
        return color;
    }
}
