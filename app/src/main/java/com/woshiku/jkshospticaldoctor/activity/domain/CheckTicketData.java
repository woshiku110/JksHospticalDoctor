package com.woshiku.jkshospticaldoctor.activity.domain;

import com.woshiku.jkshospticaldoctor.activity.inter.PageState;

/**
 * Created by admin on 2017-05-05.
 */

public class CheckTicketData extends PageState{
    private String id,paramOne,name,price,instructions,is_select;
    private String indexLetter;
    private boolean isMark = false;//是不是只是字母
    private boolean isChoosed = false;//是不是用户选择

    public CheckTicketData(int pagestate){
        super.pageState = pagestate;
    }

    public CheckTicketData(boolean isMark,String indexLetter) {
        this.isMark = isMark;
        this.indexLetter = indexLetter;
        if(isMark){
            super.pageState = -1;
        }
    }

    public CheckTicketData(String id, String paramOne, String name, String price, String instructions, String is_select) {
        this.id = id;
        this.paramOne = paramOne;
        this.name = name;
        this.price = price;
        this.instructions = instructions;
        this.is_select = is_select;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParamOne() {
        return paramOne;
    }

    public void setParamOne(String paramOne) {
        this.paramOne = paramOne;
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

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getIs_select() {
        return is_select;
    }

    public void setIs_select(String is_select) {
        this.is_select = is_select;
    }

    public String getIndexLetter() {
        return indexLetter;
    }

    public void setIndexLetter(String indexLetter) {
        this.indexLetter = indexLetter;
    }

    public boolean isMark() {
        return isMark;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }

    @Override
    public String toString() {
        return "CheckTicketData{" +
                "id='" + id + '\'' +
                ", paramOne='" + paramOne + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", instructions='" + instructions + '\'' +
                ", is_select='" + is_select + '\'' +
                ", indexLetter='" + indexLetter + '\'' +
                ", isMark=" + isMark +
                '}';
    }
}
