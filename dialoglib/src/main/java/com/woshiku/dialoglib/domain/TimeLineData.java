package com.woshiku.dialoglib.domain;

/**
 * Created by Administrator on 2016/9/20.
 */
public class TimeLineData {
    private String name;
    private boolean isSelected;
    //0 1 2 0表示开始 1表示中间 2表示结尾
    private int type;

    public TimeLineData() {
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
