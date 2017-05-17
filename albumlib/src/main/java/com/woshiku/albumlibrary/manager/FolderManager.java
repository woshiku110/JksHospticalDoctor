package com.woshiku.albumlibrary.manager;

import android.os.Environment;

import java.io.File;

/**
 * 目录管理器
 * <p/>
 * Created by Clock on 2016/5/28.
 */
public class FolderManager {

    /**
     * 应用程序在SD卡上的主目录名称
     */
    private final static String APP_FOLDER_NAME = "album";

    private FolderManager() {
    }

    /**
     * 获取app在sd卡上的主目录
     *
     * @return 成功则返回目录，失败则返回null
     */
    public static File getAppFolder() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            File appFolder = new File(Environment.getExternalStorageDirectory(), APP_FOLDER_NAME);
            return createOnNotFound(appFolder);

        } else {
            return null;
        }
    }

    /**
     * 创建目录
     *
     * @param folder
     * @return 创建成功则返回目录，失败则返回null
     */
    private static File createOnNotFound(File folder) {
        if (folder == null) {
            return null;
        }

        if (!folder.exists()) {
            folder.mkdirs();
        }

        if (folder.exists()) {
            return folder;
        } else {
            return null;
        }
    }
}
