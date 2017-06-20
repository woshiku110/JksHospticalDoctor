package com.woshiku.jkshospticaldoctor.activity.utils;

import android.graphics.Bitmap;
import android.util.Log;
import com.woshiku.jkshospticaldoctor.activity.domain.SubmitIllData;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Administrator on 2017/1/9.
 */
public class CompressFileUtil {
    private List<SubmitIllData> uploadFileList;
    private List<SubmitIllData> imageList;
    private ManageThread manageThread;
    private int count;
    private Object obj = new Object();
    private CompressResultListener compressResultListener;

    public interface CompressResultListener{
        void compressResult(List<SubmitIllData> dataList,int size, boolean isEq);
    }

    public CompressFileUtil setCompressResultListener(CompressResultListener compressResultListener) {
        this.compressResultListener = compressResultListener;
        return this;
    }

    public CompressFileUtil(List<SubmitIllData> uploadFileList,ManageThread manageThread) {
        this.uploadFileList = uploadFileList;
        this.manageThread = manageThread;
    }

    /**
     * @desc 压缩图片
     * @param picPath 图片的路径
     * @param submitIllData 图片的保存路径
     * @return void
     * */
    private void compressOnePic(String picPath,SubmitIllData submitIllData){
        Bitmap bmp = PicPressUtil.getSmallBitmap(picPath);
        PicPressUtil.saveBitmap(bmp, submitIllData.getPath());
        synchronized (obj){
            count++;
            Log.e("lookat", "imageAddr" + picPath + "\t" + "pressed pic" +submitIllData.getPath()+"\tcount:"+count);
            if(count==imageList.size()){
                if(compressResultListener != null){
                    compressResultListener.compressResult(imageList,count,true);
                    Log.e("lookat", "compress finished");
                }
            }
        }
    }
    public void start(){
        imageList = new ArrayList<>();
        for(SubmitIllData uploadFile:uploadFileList){
            if(!uploadFile.isAddPhoto()){
                imageList.add(uploadFile);
            }
        }
        for(int i=0;i<imageList.size();i++){
            String oldPath = imageList.get(i).getPath();
            imageList.get(i).setPath(FileUtils.getPressedName(i));
            SubmitIllData submitIllData = imageList.get(i);
            manageThread.carryCompressed(new CompressedFile(oldPath,submitIllData));
        }
    }

    class CompressedFile implements Callable<Object>{
        private SubmitIllData compressedName;
        private String uploadFilePath;
        public CompressedFile(String uploadFilePath,SubmitIllData compressedData) {
            this.uploadFilePath = uploadFilePath;
            this.compressedName = compressedData;
        }
        @Override
        public Object call() throws Exception {
            compressOnePic(uploadFilePath,compressedName);
            return null;
        }
    }
}
