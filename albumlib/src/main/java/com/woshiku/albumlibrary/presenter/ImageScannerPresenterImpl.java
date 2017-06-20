package com.woshiku.albumlibrary.presenter;

import android.content.Context;
import android.support.v4.app.LoaderManager;
import com.woshiku.albumlibrary.model.ImageScannerModel;
import com.woshiku.albumlibrary.model.ImageScannerModelImpl;
import com.woshiku.albumlibrary.presenter.entity.ImageScanResult;
import com.woshiku.albumlibrary.view.AlbumView;
import com.woshiku.albumlibrary.view.entity.AlbumViewData;
/**
 * 图片扫描Presenter实现类
 * <p/>
 * Created by Clock on 2016/3/21.
 */
public class ImageScannerPresenterImpl implements ImageScannerPresenter {

    private ImageScannerModel mScannerModel;
    private AlbumView mAlbumView;

    public ImageScannerPresenterImpl(AlbumView albumView) {
        mScannerModel = new ImageScannerModelImpl();
        mAlbumView = albumView;
    }

    @Override
    public void startScanImage(final Context context, LoaderManager loaderManager) {
        mScannerModel.startScanImage(context, loaderManager, new ImageScannerModel.OnScanImageFinish() {
            @Override
            public void onFinish(ImageScanResult imageScanResult) {
                if (mAlbumView != null) {
                    AlbumViewData albumData = mScannerModel.archiveAlbumInfo(context, imageScanResult);
                    mAlbumView.refreshAlbumData(albumData);
                }
            }
        });
    }

}
