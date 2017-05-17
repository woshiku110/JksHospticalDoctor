package com.woshiku.jkshospticaldoctor.activity.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 2017/1/6.
 */
public class ManageThread {
    private static ManageThread manageThread;
    private ExecutorService executorService;
    private ExecutorService compressedService;
    private ManageThread(){

    }
    public static ManageThread getInstance(){
        if(manageThread==null){
            manageThread = new ManageThread();
        }
        return manageThread;
    }
    public void createThread(int amount){
        executorService = Executors.newFixedThreadPool(amount);
    }
    public void createCompressedThread(int amount){
        compressedService = Executors.newFixedThreadPool(amount);
    }
    public Future<Object> carry(Callable<Object> call){
        Future<Object> future = executorService.submit(call);
        return future;
    }
    public void carryCompressed(Callable<Object> call){
        compressedService.submit(call);
    }
}
