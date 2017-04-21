package com.woshiku.jkshospticaldoctor.activity.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/3/3.
 */
public class ThreadManage {
    private static int maxThreadAmount = 1;
    private static ExecutorService executorService;
    private static ThreadManage threadManage;
    private ThreadManage() {

    }

    public static ThreadManage getInstance(){
        if(threadManage == null){
            threadManage = new ThreadManage();
            init(maxThreadAmount);
        }
        return threadManage;
    }

    private static void init(int amount){
        maxThreadAmount = amount;
        executorService = Executors.newFixedThreadPool(amount);
    }

    public void setThreadAmount(int amount){
        ThreadManage.executorService = Executors.newFixedThreadPool(amount);
    }
    public void carry(Runnable runnable){
        executorService.execute(runnable);
    }
}
