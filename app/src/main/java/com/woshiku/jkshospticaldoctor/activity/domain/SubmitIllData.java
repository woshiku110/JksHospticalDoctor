package com.woshiku.jkshospticaldoctor.activity.domain;

/**
 * Created by admin on 2017-05-12.
 */

public class SubmitIllData {
    private boolean isAddPhoto = false;//是不是添加照片
    private String path;//照片路径
    private boolean photoType = false;//false表示病历 true为处方
    private String uploadReturnAddress ="";
    public SubmitIllData(boolean isAddPhoto) {
        this.isAddPhoto = isAddPhoto;
    }

    public SubmitIllData(boolean isAddPhoto, String path) {
        this.isAddPhoto = isAddPhoto;
        this.path = path;
    }

    public SubmitIllData(boolean isAddPhoto, String path, boolean photoType) {
        this.isAddPhoto = isAddPhoto;
        this.path = path;
        this.photoType = photoType;
    }

    public boolean isAddPhoto() {
        return isAddPhoto;
    }

    public void setAddPhoto(boolean addPhoto) {
        isAddPhoto = addPhoto;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isPhotoType() {
        return photoType;
    }

    public String getUploadReturnAddress() {
        return uploadReturnAddress;
    }

    public void setUploadReturnAddress(String uploadReturnAddress) {
        this.uploadReturnAddress = uploadReturnAddress;
    }

    @Override
    public String toString() {
        return "SubmitIllData{" +
                "isAddPhoto=" + isAddPhoto +
                ", path='" + path + '\'' +
                ", photoType=" + photoType +
                '}';
    }
}
