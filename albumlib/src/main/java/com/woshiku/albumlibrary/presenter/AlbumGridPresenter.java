package com.woshiku.albumlibrary.presenter;

import com.woshiku.albumlibrary.domain.ImageInfo;
import com.woshiku.albumlibrary.model.AlbumGridModel;
import com.woshiku.albumlibrary.view.entity.AlbumGriView;
import java.util.List;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/8/12.
 */
public class AlbumGridPresenter {
    private AlbumGriView albumGridView;
    private AlbumGridModel albumGridModel;
    public AlbumGridPresenter(AlbumGriView albumGridView){
        this.albumGridView = albumGridView;
        albumGridModel = new AlbumGridModel();
    }
    public void setScreenUpdate(){
        albumGridModel.setEmptyData().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {

                    @Override
                    public void onCompleted() {
                        albumGridView.updateCurrentPics();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Void aVoid) {

                    }
                });
    }
    public void setSelectedPicAmount(List<String> imagesAddr){
        albumGridModel.setTextAmount(imagesAddr).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<String>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<String> s) {
                        albumGridView.updateSelectedPicAmount(s);
                    }
                });
    }
    public void setGridData(List<ImageInfo> mList){
        albumGridModel.setGridData(mList).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ImageInfo>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<ImageInfo> users) {
                        albumGridView.updateGridView(users);
                    }
                });
    }
    public void freshGridView(){
        albumGridModel.freshGridView().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {

                    @Override
                    public void onCompleted() {

                        albumGridView.freshGridView();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Void aVoid) {

                    }
                });
    }
    public void freshSingleView(int pos){
        albumGridModel.freshSingleItem(pos).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {

                    @Override
                    public void onCompleted() {


                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        int pos = Integer.parseInt(s);
                        albumGridView.freshSingleItem(pos);
                    }
                });
    }
}
