package com.woshiku.jkshospticaldoctor.activity.domain;

import com.woshiku.jkshospticaldoctor.activity.inter.PageState;

/**
 * Created by admin on 2017-04-25.
 *
 */

public class HoldDialogData extends PageState {
    private String id,name,icon,date,sex,amount="",state="";
    private boolean canCall = true;
    private boolean isReturnDialog = false;//是不是可以返回诊断
    private boolean isBtEnable = true;

    public HoldDialogData(int pageState){
        super.pageState = pageState;
    }

    public HoldDialogData(String id, String name, String icon, String date, String sex, String amount) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.date = date;
        this.sex = sex;
        this.amount = amount;
    }

    public HoldDialogData(String id, String name, String icon, String date, String sex, String amount, String state) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.date = date;
        this.sex = sex;
        this.amount = amount;
        this.state = state;
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

    public boolean isCanCall() {
        return canCall;
    }

    public void setCanCall(boolean canCall) {
        this.canCall = canCall;
    }

    public boolean isBtEnable() {
        return isBtEnable;
    }

    public void setBtEnable(boolean btEnable) {
        isBtEnable = btEnable;
    }

    public boolean isReturnDialog() {
        return isReturnDialog;
    }

    public void setReturnDialog(boolean returnDialog) {
        isReturnDialog = returnDialog;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "HoldDialogData{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", date='" + date + '\'' +
                ", sex='" + sex + '\'' +
                ", amount='" + amount + '\'' +
                ", canCall=" + canCall +
                '}';
    }
}
