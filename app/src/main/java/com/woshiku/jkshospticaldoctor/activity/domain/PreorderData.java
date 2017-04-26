package com.woshiku.jkshospticaldoctor.activity.domain;

import com.woshiku.jkshospticaldoctor.activity.inter.PageState;

/**
 * Created by admin on 2017-04-24.
 */

public class PreorderData extends PageState{
    private String id,name,icon,date,sex,amount;
    private boolean isChoose = false;//是待处理数据还是已接单数据
    private boolean checkState = true;
    public PreorderData(boolean isChoose) {
        this.isChoose = isChoose;
    }

    public PreorderData(boolean isChoose,int pageType){
        this.isChoose = isChoose;
        super.pageState = pageType;
    }
    public PreorderData(boolean isChoose,int pageType,boolean checkState){
        this.isChoose = isChoose;
        this.checkState = checkState;
        super.pageState = pageType;
    }
    public PreorderData(String id, String name, String icon, String date, String sex, String amount, boolean isChoose) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.date = date;
        this.sex = sex;
        this.amount = amount;
        this.isChoose = isChoose;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public void setCheckState(boolean checkState) {
        this.checkState = checkState;
    }

    public boolean isCheckState() {
        return checkState;
    }

    @Override
    public String toString() {
        return "PreorderData{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", date='" + date + '\'' +
                ", sex='" + sex + '\'' +
                ", amount='" + amount + '\'' +
                ", isChoose=" + isChoose +
                '}';
    }
}
