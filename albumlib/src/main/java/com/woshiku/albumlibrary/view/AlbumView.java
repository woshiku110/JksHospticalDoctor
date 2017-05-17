package com.woshiku.albumlibrary.view;


import com.woshiku.albumlibrary.domain.AlbumFolderInfo;
import com.woshiku.albumlibrary.view.entity.AlbumViewData;

/**
 * Created by Clock on 2016/3/19.
 */
public interface AlbumView {

    /**
     * 刷新相册数据信息
     *
     * @param albumData
     */
    public void refreshAlbumData(AlbumViewData albumData);

    /**
     * 切换图片目录
     *
     * @param albumFolderInfo 指定图片目录的信息
     */
    public void switchAlbumFolder(AlbumFolderInfo albumFolderInfo);

}
