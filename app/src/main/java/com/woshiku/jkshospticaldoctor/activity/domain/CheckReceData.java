package com.woshiku.jkshospticaldoctor.activity.domain;

import com.woshiku.jkshospticaldoctor.activity.inter.PageState;

/**
 * Created by admin on 2017-05-04.
 * @desc 用于处理核实处方
 */

public class CheckReceData extends PageState{
    private String id,icon,name,sex,paramOne;
    private boolean isChoose = false;//是选择界面还是正常的cell界面
    private boolean checkState = true;//是待处理数据还是已接单数据
    public CheckReceData(int pageState) {
        super.pageState = pageState;
    }
    public CheckReceData(boolean isChoose) {
        this.isChoose = isChoose;
    }

    public CheckReceData(boolean isChoose,int pageType){
        this.isChoose = isChoose;
        super.pageState = pageType;
    }
    public CheckReceData(String id, String icon, String name, String sex, String paramOne) {
        this.id = id;
        this.icon = icon;
        this.name = name;
        this.sex = sex;
        this.paramOne = paramOne;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getParamOne() {
        return paramOne;
    }

    public void setParamOne(String paramOne) {
        this.paramOne = paramOne;
    }


    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public boolean isCheckState() {
        return checkState;
    }

    public void setCheckState(boolean checkState) {
        this.checkState = checkState;
    }

    @Override
    public String toString() {
        return "CheckReceData{" +
                "id='" + id + '\'' +
                ", icon='" + icon + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", paramOne='" + paramOne + '\'' +
                '}';
    }
}
