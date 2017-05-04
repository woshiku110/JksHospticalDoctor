package com.woshiku.jkshospticaldoctor.activity.domain;

import com.woshiku.jkshospticaldoctor.activity.inter.PageState;

/**
 * Created by admin on 2017-05-04.
 * 接诊历史数据
 */

public class ReceHistoryData  extends PageState {
    private String id,name,icon,date,sex,paramOne,paramTwo;
    public ReceHistoryData(int pageState) {
        super.pageState = pageState;
    }
    public ReceHistoryData(String id, String name, String icon, String date, String sex, String paramOne, String paramTwo) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.date = date;
        this.sex = sex;
        this.paramOne = paramOne;
        this.paramTwo = paramTwo;
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

    public String getParamOne() {
        return paramOne;
    }

    public void setParamOne(String paramOne) {
        this.paramOne = paramOne;
    }

    public String getParamTwo() {
        return paramTwo;
    }

    public void setParamTwo(String paramTwo) {
        this.paramTwo = paramTwo;
    }

    @Override
    public String toString() {
        return "ReceHistoryData{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", date='" + date + '\'' +
                ", sex='" + sex + '\'' +
                ", paramOne='" + paramOne + '\'' +
                ", paramTwo='" + paramTwo + '\'' +
                '}';
    }
}
