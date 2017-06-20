package common;

import android.Manifest;

import com.woshiku.jkshospticaldoctor.activity.domain.LoginReturnData;

/**
 * Created by admin on 2017-04-19.
 */

public class Global {
    public static String baseUrl = "http://211.159.188.107/jfs1.1/";
    public static String imagePath = "http://211.159.188.107/File/filebed/";
    public static String uploadUrl = "http://211.159.188.107/File/uploadFile";
    /*public static String baseUrl = "http://192.168.0.202/jfs1.1/";
    public static String imagePath = "http://192.168.0.202/File/filebed/";
    public static String uploadUrl = "http://192.168.0.202/File/uploadFile";*/
    public static String _token = "";
    public static String mainAction = "com.woshiku.mainaction";
    public static LoginReturnData loginReturnData;
    public static final int medicalSearchReurn = 20000;//药品搜索返回页面
    public static final int medicalSearchEnter = 20001;//药品搜索进入页面
    public static final int checkTicketEnter = 20002;//检查单进入界面
    public static final int checkTicketReturn = 20003;//检查单进入界面
    public static final int addressManageeEnter = 20004;//进入地址活动
    public static final int addressManageeReturn = 20005;//返回地址
    public static final String checkReceAction = "com.woshiku.checkReceDetailActivity";
    public static final String WritePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final int WriteReturnCode = 10;
    public static final String WriteReason = "请打开存储权限,否则无法使用相应的功能!!!";
    public static final String CameraPermission = Manifest.permission.CAMERA;
    public static final int CameraReturnCode = 11;
    public static final String CameraReason = "请打开拍照权限,否则无法使用相应的功能!!!";
    public static final int ACTION_TAKE_PICTURE = 0x000000;
    public static final int ACTION_ALBUMN_REQUEST = 1000;
    public static final String IMAGE_SELECTED_ADDR = "imagesAddr";
}
