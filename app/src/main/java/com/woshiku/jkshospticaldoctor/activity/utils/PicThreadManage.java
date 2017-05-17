package com.woshiku.jkshospticaldoctor.activity.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/3/3.
 */
public class PicThreadManage {
    private static int maxThreadAmount = 3;
    private static ExecutorService executorService;
    private static PicThreadManage threadManage;
    private PicThreadManage() {

    }

    public static PicThreadManage getInstance(){
        if(threadManage == null){
            threadManage = new PicThreadManage();
            init(maxThreadAmount);
        }
        return threadManage;
    }

    private static void init(int amount){
        maxThreadAmount = amount;
        executorService = Executors.newFixedThreadPool(amount);
    }

    public void setThreadAmount(int amount){
        PicThreadManage.executorService = Executors.newFixedThreadPool(amount);
    }
    public void carry(Runnable runnable){
        executorService.execute(runnable);
    }
}
