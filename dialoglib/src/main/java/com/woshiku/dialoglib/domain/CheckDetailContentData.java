package com.woshiku.dialoglib.domain;

/**
 * Created by Administrator on 2017/2/28.
 */
public class CheckDetailContentData {
    private boolean isSelected;
    private String id,name,desc,price,state,amount;

    public CheckDetailContentData(String name, String price,boolean isSelected) {
        this.name = name;
        this.price = price;
        this.isSelected = isSelected;
    }

    public CheckDetailContentData(String name, String price,String amount,boolean isSelected) {
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.isSelected = isSelected;
    }

    public CheckDetailContentData(String id,String name,String desc, String price,String state) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.desc = desc;
        this.isSelected = true;
        this.state = state;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
