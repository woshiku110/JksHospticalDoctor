package com.woshiku.jkshospticaldoctor.activity.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/17.
 */
public class FileUtils {
    //文件目录
    private static String fileDir = "/DoctorPic/pic/";
    private static String tempDir = "/DoctorPic/pic/temppic/";
    private static String demoName = "123.png";
    private static String updateDir = "/DoctorPic";
    private static String pressedPicName = "pressed.png";

    //得到内置sd卡目录
    private static String getSdDir(){
        String str;
        File sdfile = Environment.getExternalStorageDirectory();
        str=sdfile.toString();
        return str;
    }
    //得到根目录
    private static String getRootPath(String mypath){
        String root=getSdDir()+mypath;
        File path=new File(root);
        if(!path.exists()){
            path.mkdirs();
        }
        return root;
    }
    public static String getDefaultImage(){
        return getRootPath(fileDir)+demoName;
    }
    public static String generImageName(){
        return getRootPath(fileDir)+System.currentTimeMillis()+".png";
    }
    /*public static void deleFile(Context context,String addr,BitmapUtils bitmapUtils){
        File file = new File(addr);
        if(file.exists()){
            bitmapUtils.clearDiskCache(addr);
            // 最后通知图库更新
            //context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + addr)));
        }
    }*/
    public static String gengerTempPic(){
        return getRootPath(tempDir)+System.currentTimeMillis()+".png";
    }
    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
    public static void deleTempPics(Context context){
        File file = new File(getRootPath(tempDir));
        deleteDir(file);
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + getRootPath(updateDir))));
    }
    public static String getRootDir(){
        return getRootPath(fileDir);
    }
    public static String getPressedName(int index){
        return getRootDir()+"pressed"+index+".png";
    }
    public static String getUserIconName(){
        return getRootDir()+"icon"+System.currentTimeMillis()+".png";
    }
    public static List<String> getPressedList(int amount){
        List<String> list = new ArrayList<>();
        for(int i=0;i<amount;i++){
            list.add(getPressedName(i));
        }
        return list;
    }
}
