package com.woshiku.wheelwidgetslib.widget.adapters;

import android.content.Context;

import com.woshiku.wheelwidgetslib.R;

import java.util.List;

/**
 * Created by Administrator on 2016/12/19.
 * 这里用于测试AbstractWheelTextAdapter的作用
 */
public class TestAdapter extends AbstractWheelTextAdapter{
    private List<String> mList;
    protected TestAdapter(Context context) {
        super(context);
    }
    //可以配置包裹文本的view
    protected TestAdapter(Context context, int itemResource) {
        super(context, itemResource);
    }
    //可以配置包裹文本的view以及文本内容的样式
    protected TestAdapter(Context context, int itemResource, int itemTextResource) {
        super(context, itemResource, itemTextResource);
    }
    /**
     * 使用者可以自己定义滑轮的子item以及内部控制的文本要传文本的ID
     * */
    public TestAdapter(Context context, int itemResource, int itemTextResource,List<String> mList){
        this(context, itemResource, itemTextResource);
        this.mList = mList;
    }
    public TestAdapter(Context context,List<String> mList){
        this(context, R.layout.item_wheelview, R.id.wheel_textview);
        this.mList = mList;
    }
    //拿到具体索引对应数据的值
    @Override
    public CharSequence getItemText(int index) {
        return mList.get(index);
    }
    //共有多少条数据
    @Override
    public int getItemsCount() {
        return mList.size();
    }
    /*public TextView getTextView(int index){

    }*/
    public List<String> getData(){
        return mList;
    }
}
