package com.woshiku.albumlibrary.presenter;

import android.util.Log;

import com.woshiku.albumlibrary.domain.AlbumFolderInfo;
import com.woshiku.albumlibrary.model.AlbumListModel;
import com.woshiku.albumlibrary.view.LeftAlbumView;
import java.util.List;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/8/16.
 */
public class AlbumListPresenter {
    private LeftAlbumView leftAlbumView;
    private AlbumListModel albumListModel;
    public AlbumListPresenter(LeftAlbumView leftAlbumView){
        this.leftAlbumView = leftAlbumView;
        albumListModel = new AlbumListModel();
    }
    public void setListData(List<AlbumFolderInfo> dirInfo){
        albumListModel.setListData(dirInfo).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<AlbumFolderInfo>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<AlbumFolderInfo> dirInfo) {

                        leftAlbumView.updateListView(dirInfo);
                    }
                });
    }
}
