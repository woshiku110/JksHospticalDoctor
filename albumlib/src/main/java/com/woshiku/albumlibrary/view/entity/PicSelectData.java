package com.woshiku.albumlibrary.view.entity;

import java.io.File;

/**
 * Created by Administrator on 2016/8/12.
 */
public class PicSelectData {
    //图片本身
    private File file;
    //是不是拍照的选项
    private boolean isPhoto;
    //图片是否被选中
    private boolean isSelected;

    public PicSelectData(File file, boolean isPhoto, boolean isSelected) {
        this.file = file;
        this.isPhoto = isPhoto;
        this.isSelected = isSelected;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isPhoto() {
        return isPhoto;
    }

    public void setIsPhoto(boolean isPhoto) {
        this.isPhoto = isPhoto;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
