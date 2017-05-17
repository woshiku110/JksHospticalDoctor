package com.woshiku.jkshospticaldoctor.activity.domain;

/**
 * Created by admin on 2017-05-11.
 * 权限数据
 */

public class PermissionData {
    private String name;//权限名称
    private int returnCode;//权限的返回值
    private boolean isAllowed = false;//有没有被授权
    private boolean isMust = true;//这个用于检测全部权限，如果不是必要通过时，检测全部权限会进行忽略，默认都为true
    private String failReason;
    public PermissionData(String name, int returnCode, String failReason) {
        this.name = name;
        this.returnCode = returnCode;
        this.failReason = failReason;
    }

    public PermissionData(String name, int returnCode, boolean isAllowed, boolean isMust) {
        this.name = name;
        this.returnCode = returnCode;
        this.isAllowed = isAllowed;
        this.isMust = isMust;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public boolean isAllowed() {
        return isAllowed;
    }

    public void setAllowed(boolean allowed) {
        isAllowed = allowed;
    }

    public boolean isMust() {
        return isMust;
    }

    public void setMust(boolean must) {
        isMust = must;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    @Override
    public String toString() {
        return "PermissionData{" +
                "name='" + name + '\'' +
                ", returnCode=" + returnCode +
                ", isAllowed=" + isAllowed +
                ", isMust=" + isMust +
                ", failReason='" + failReason + '\'' +
                '}';
    }
}
