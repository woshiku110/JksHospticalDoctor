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
