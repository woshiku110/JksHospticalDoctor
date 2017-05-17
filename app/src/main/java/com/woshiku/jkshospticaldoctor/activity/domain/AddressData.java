package com.woshiku.jkshospticaldoctor.activity.domain;

/**
 * Created by admin on 2017-05-16.
 */

public class AddressData {
    private String id,receAddr,holdAddr,hospital;
    private boolean isDefault = false;

    public AddressData() {
    }

    public AddressData(String id, String receAddr, String holdAddr,boolean isDefault, String hospital) {
        this.id = id;
        this.receAddr = receAddr;
        this.holdAddr = holdAddr;
        this.hospital = hospital;
        this.isDefault = isDefault;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReceAddr() {
        return receAddr;
    }

    public void setReceAddr(String receAddr) {
        this.receAddr = receAddr;
    }

    public String getHoldAddr() {
        return holdAddr;
    }

    public void setHoldAddr(String holdAddr) {
        this.holdAddr = holdAddr;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
