package com.woshiku.albumlibrary.model;

import com.woshiku.albumlibrary.domain.AlbumFolderInfo;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/8/16.
 */
public class AlbumListModel {
    //创建list变化观察者
    public Observable setListData(List<AlbumFolderInfo> mList){
        return Observable.create(new MyListSubscriber(mList));
    }
    //创建listview订阅者
    class MyListSubscriber implements Observable.OnSubscribe<List<AlbumFolderInfo>>{
        private List<AlbumFolderInfo> mList;

        public MyListSubscriber(List<AlbumFolderInfo> mList) {
            this.mList = mList;
        }

        @Override
        public void call(Subscriber<? super List<AlbumFolderInfo>> subscriber) {
            if(mList == null){
                subscriber.onError(new Exception("mlist == null"));
            }else{
                subscriber.onNext(mList);
            }
        }
    }
}
