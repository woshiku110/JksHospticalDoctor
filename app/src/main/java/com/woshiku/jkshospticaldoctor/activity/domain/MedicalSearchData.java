package com.woshiku.jkshospticaldoctor.activity.domain;

import com.woshiku.jkshospticaldoctor.activity.inter.PageState;

/**
 * Created by admin on 2017-04-28.
 */

public class MedicalSearchData extends PageState{
    private String no,medicalName,standard,unit,amount,eleOne,eleTwo,order;

    public MedicalSearchData(int pageState) {
        super.pageState = pageState;
    }

    public MedicalSearchData(String no, String medicalName, String standard, String unit, String amount, String eleOne, String eleTwo, String order) {
        this.no = no;
        this.medicalName = medicalName;
        this.standard = standard;
        this.unit = unit;
        this.amount = amount;
        this.eleOne = eleOne;
        this.eleTwo = eleTwo;
        this.order = order;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getMedicalName() {
        return medicalName;
    }

    public void setMedicalName(String medicalName) {
        this.medicalName = medicalName;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getEleOne() {
        return eleOne;
    }

    public void setEleOne(String eleOne) {
        this.eleOne = eleOne;
    }

    public String getEleTwo() {
        return eleTwo;
    }

    public void setEleTwo(String eleTwo) {
        this.eleTwo = eleTwo;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "MedicalSearchData{" +
                "no='" + no + '\'' +
                ", medicalName='" + medicalName + '\'' +
                ", standard='" + standard + '\'' +
                ", unit='" + unit + '\'' +
                ", amount='" + amount + '\'' +
                ", eleOne='" + eleOne + '\'' +
                ", eleTwo='" + eleTwo + '\'' +
                ", order='" + order + '\'' +
                '}';
    }
}
