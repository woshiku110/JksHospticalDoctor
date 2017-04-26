package com.woshiku.dialoglib;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;
import com.woshiku.dialoglib.view.MyCheckBox;

/**
 * Created by Administrator on 2017/2/21.
 */
public class BackOrderDialog extends PopupWindow{
    View view,parent;
    Context context;
    LinearLayout topLine;
    MyCheckBox []myCheckBox = new MyCheckBox[5];
    String []strs = {"分诊错误","其他","时间冲突","修改时间","临时有事"};
    private UserChooseListener userChooseListener;
    String msg;
    public interface UserChooseListener{
        void userChoose(String msg);
    }

    public void setUserChooseListener(UserChooseListener userChooseListener) {
        this.userChooseListener = userChooseListener;
    }

    public BackOrderDialog(Context context,View parent){
        this.parent = parent;
        this.context = context;
        view = View.inflate(context, R.layout.layout_back_order,null);
        view.startAnimation(AnimationUtils.loadAnimation(context,
                R.anim.fade_ins));
        topLine = (LinearLayout)view.findViewById(R.id.back_order_top);
        myCheckBox[0] = (MyCheckBox)view.findViewById(R.id.check_one);
        myCheckBox[1] = (MyCheckBox)view.findViewById(R.id.check_two);
        myCheckBox[2] = (MyCheckBox)view.findViewById(R.id.check_three);
        myCheckBox[3] = (MyCheckBox)view.findViewById(R.id.check_four);
        myCheckBox[4] = (MyCheckBox)view.findViewById(R.id.check_five);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        initDatas();
        setContentView(view);
        update();
        showAtLocation(parent, Gravity.LEFT | Gravity.TOP, 0, 0);

    }
    private void initDatas(){
        LinearLayout btOkLine = (LinearLayout)view.findViewById(R.id.bt_sure);
        LinearLayout btCancelLine = (LinearLayout)view.findViewById(R.id.bt_cancel);
        topLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        for(int i=0;i<strs.length;i++){
            final int pos =i;
            myCheckBox[i].setText(strs[i]);
            myCheckBox[i].getCheckBox().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("lookat","pos:"+pos);
                    setSingleCheck(pos,myCheckBox[pos].getCheckBox().isChecked());
                }
            });
        }
        btOkLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg = getTextCheck();
                if(!TextUtils.isEmpty(msg)){
                    if(userChooseListener != null){
                        userChooseListener.userChoose(msg);
                        dismiss();
                    }
                }else{
                    Toast.makeText(context,"请选择一下退单独原因",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btCancelLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
    private void setSingleCheck(int index,boolean isState){
        for(int i=0;i<strs.length;i++){
            if(i != index){
                myCheckBox[i].getCheckBox().setChecked(false);
            }else{
                myCheckBox[i].getCheckBox().setChecked(true);
            }
        }
    }
    private String getTextCheck(){
        String msg = null;
        for(int i=0;i<strs.length;i++){
            if(myCheckBox[i].getCheckBox().isChecked()){
                return myCheckBox[i].getText();
            }
        }
        return msg;
    }
}
