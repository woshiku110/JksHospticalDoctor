package com.woshiku.albumlibrary.view.entity;

import com.woshiku.albumlibrary.domain.ImageInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/8/12.
 */
public interface AlbumGriView {
    void updateSelectedPicAmount(List<String> imagesAddr);
    void updateGridView(List<ImageInfo> picSelectedList);
    void updateCurrentPics();
    void freshGridView();
    void freshSingleItem(int pos);
}
