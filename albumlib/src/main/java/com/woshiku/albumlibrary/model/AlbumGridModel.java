package com.woshiku.albumlibrary.model;

import com.woshiku.albumlibrary.domain.ImageInfo;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/8/12.
 */
public class AlbumGridModel {
    public Observable setEmptyData(){
        return Observable.create(new MyEmptySubscriber());
    }
    //创建数字变化观察者
    public Observable setTextAmount(List<String> msg){
        return Observable.create(new MyTextSubscriber(msg));
    }
    //创建list变化观察者
    public Observable setGridData(List<ImageInfo> mList){
        return Observable.create(new MyListSubscriber(mList));
    }
    //刷新单个item
    public Observable freshSingleItem(int pos){
        return Observable.create(new MyMsgSubscriber(pos));
    }
    public Observable freshGridView(){
        return Observable.create(new MyEmptySubscriber());
    }
    //创建Text订阅者
    class MyMsgSubscriber implements Observable.OnSubscribe<String>{
        private int pos;

        public MyMsgSubscriber(int pos) {
            this.pos = pos;
        }

        @Override
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext(pos+"");
        }
    }
    //创建Text订阅者
    class MyEmptySubscriber implements Observable.OnSubscribe<Void>{

        @Override
        public void call(Subscriber<? super Void> subscriber) {
            subscriber.onCompleted();
        }
    }
    //创建Text订阅者
    class MyTextSubscriber implements Observable.OnSubscribe<List<String>>{
        private List<String> msg;

        public MyTextSubscriber(List<String> msg) {
            this.msg = msg;
        }

        @Override
        public void call(Subscriber<? super List<String>> subscriber) {
            if(msg == null){
                subscriber.onError(new Exception("msg == null"));
            }else{
                subscriber.onNext(msg);
                subscriber.onCompleted();
            }
        }
    }
    //创建listview订阅者
    class MyListSubscriber implements Observable.OnSubscribe<List<ImageInfo>>{
        private List<ImageInfo> mList;

        public MyListSubscriber(List<ImageInfo> mList) {
            this.mList = mList;
        }

        @Override
        public void call(Subscriber<? super List<ImageInfo>> subscriber) {
            if(mList == null){
                subscriber.onError(new Exception("mlist == null"));
            }else{
                subscriber.onNext(mList);
            }
        }
    }
}
