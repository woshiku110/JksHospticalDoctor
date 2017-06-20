package com.woshiku.albumlibrary.fragment.impleBase;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;
import com.woshiku.albumlibrary.R;
import com.woshiku.albumlibrary.activity.AlbumActivity;
import com.woshiku.albumlibrary.common.Global;
import com.woshiku.albumlibrary.domain.AlbumFolderInfo;
import com.woshiku.albumlibrary.domain.ImageInfo;
import com.woshiku.albumlibrary.fragment.BaseFragment;
import com.woshiku.albumlibrary.presenter.AlbumGridPresenter;
import com.woshiku.albumlibrary.presenter.ImageScannerPresenter;
import com.woshiku.albumlibrary.presenter.ImageScannerPresenterImpl;
import com.woshiku.albumlibrary.view.AlbumRecycleView;
import com.woshiku.albumlibrary.view.AlbumView;
import com.woshiku.albumlibrary.view.SelectedPhoto;
import com.woshiku.albumlibrary.view.entity.AlbumGriView;
import com.woshiku.albumlibrary.view.entity.AlbumViewData;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Administrator on 2016/8/15.
 */
@SuppressLint("ValidFragment")
public class ContentAlbumFragment extends BaseFragment implements AlbumView, AlbumGriView, SelectedPhoto {
    View mView;
    //AblumGridView gridView;
    AlbumRecycleView recycleView;
    ImageScannerPresenter mImageScannerPresenter;
    AlbumGridPresenter albumGridPresenter;
    //扫描拿到图片文件
    List<AlbumFolderInfo> dirInfoList;
    /**
     * Android M 的Runtime Permission特性申请权限用的
     */
    private final static int REQUEST_READ_EXTERNAL_STORAGE_CODE = 1;
    private final static String PACKAGE_URL_SCHEME = "package:";
    @SuppressLint("ValidFragment")
    public ContentAlbumFragment(FragmentActivity mActiviy) {
        super(mActiviy);
    }

    @Override
    public void initDatas() {
        super.initDatas();
        mImageScannerPresenter = new ImageScannerPresenterImpl(ContentAlbumFragment.this);
        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(mActivity, R.string.grant_advice_read_album, Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE_CODE);
        } else {
            //开始扫描图片
            mImageScannerPresenter.startScanImage(mActivity.getApplicationContext(), mActivity.getSupportLoaderManager());
        }
    }

    @Override
    public View initViews() {
        mView = View.inflate(mActivity, R.layout.album_detail_layout,null);
        //gridView = (AblumGridView)mView.findViewById(R.id.gv_album);
        recycleView = (AlbumRecycleView)mView.findViewById(R.id.gv_album);
        //gridView.setSelectedPhoto(ContentAlbumFragment.this);
        recycleView.setSelectedPhoto(ContentAlbumFragment.this);
        //gridView.setOnItemClickListener(ContentAlbumFragment.this);
        //gridView.setOnItemLongClickListener(ContentAlbumFragment.this);
        albumGridPresenter = new AlbumGridPresenter(ContentAlbumFragment.this);
        //gridView.setGridPresenter(albumGridPresenter);
        recycleView.setGridPresenter(albumGridPresenter);
        recycleView.setUserLongClick(new AlbumRecycleView.UserLongClick() {
            @Override
            public void userLongClick(String filePath) {
                AlbumActivity albumActivity = (AlbumActivity)mActivity;
                albumActivity.enterPicPreview(filePath);
            }
        });
        return mView;
    }
    /**
     * 启动系统权限设置界面
     */
    private void startSystemSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + mActivity.getPackageName()));
        startActivity(intent);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_EXTERNAL_STORAGE_CODE) {
            boolean granted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            if (granted) {
                Toast.makeText(mActivity, R.string.grant_permission_success, Toast.LENGTH_SHORT).show();
                mImageScannerPresenter.startScanImage(mActivity.getApplicationContext(), mActivity.getSupportLoaderManager());

            } else {
                showMissingPermissionDialog();//提示对话框
                Toast.makeText(mActivity, R.string.grant_permission_failure, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 显示打开权限提示的对话框
     */
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(R.string.help);
        builder.setMessage(R.string.help_content);

        builder.setNegativeButton(R.string.quit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mActivity, R.string.grant_permission_failure, Toast.LENGTH_SHORT).show();
                mActivity.finish();
            }
        });

        builder.setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startSystemSettings();
                mActivity.finish();
            }
        });

        builder.show();
    }
    public List<AlbumFolderInfo> getAlbumFolderInfo(){

        return dirInfoList;
    }
    @Override
    public void refreshAlbumData(AlbumViewData albumData) {
        try{
            dirInfoList = albumData.getAlbumFolderInfoList();
            //用户默认选择全部图片
            AlbumFolderInfo albumFolderInfo = dirInfoList.get(Global.userSelected);
            List<ImageInfo> imageInfoList = albumFolderInfo.getImageInfoList();
            if(imageInfoList !=null){
                albumGridPresenter.setGridData(imageInfoList);
            }else{
                imageInfoList = new ArrayList<>();
                albumGridPresenter.setGridData(imageInfoList);
            }
        }catch(Exception e){
            albumGridPresenter.setGridData(new ArrayList<ImageInfo>());
        }
    }

    @Override
    public void selectedPhotoAmount(List<String> imagesAddr) {
        //提示有多少张照片被选中
        albumGridPresenter.setSelectedPicAmount(imagesAddr);
    }

    @Override
    public void switchAlbumFolder(AlbumFolderInfo albumFolderInfo) {

    }

    @Override
    public void updateSelectedPicAmount(List<String> imagesAddr) {
        AlbumActivity mainUi = (AlbumActivity)mActivity;
        mainUi.selectedPicAmount(imagesAddr);
    }

    @Override
    public void updateGridView(List<ImageInfo> picSelectedList) {
        //gridView.setData(picSelectedList);
        recycleView.setData(picSelectedList);
    }
    public void setCurrentPics(){
        albumGridPresenter.setScreenUpdate();
    }
    @Override
    public void updateCurrentPics() {
        AlbumFolderInfo albumFolderInfo = dirInfoList.get(Global.userSelected);
        List<ImageInfo> imageInfoList = albumFolderInfo.getImageInfoList();
        //gridView.setNewData(imageInfoList);
        recycleView.setNewData(imageInfoList);
    }

    @Override
    public void freshGridView() {
        //gridView.freshData();
        //recycleView.freshSingleData();
    }
    //刷新单个item(新的)
    @Override
    public void freshSingleItem(int pos) {
        recycleView.freshSingleData(pos);
    }

    //没有必要重新扫描而是更新一下listview
    public void reScanPics(){
        albumGridPresenter.setScreenUpdate();
    }
    /*@Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        gridView.userClick(position);
        ImageInfo imageInfo = (ImageInfo)parent.getAdapter().getItem(position);
        AlbumActivity albumActivity = (AlbumActivity)mActivity;
        //albumActivity.enterEditActivty(imageInfo.getImageFile().getAbsolutePath(), imageInfo.getImageFile().getAbsolutePath());
        albumActivity.enterPicPreview(imageInfo.getImageFile().getAbsolutePath());
    }
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        ImageInfo imageInfo = (ImageInfo)parent.getAdapter().getItem(position);
        AlbumActivity albumActivity = (AlbumActivity)mActivity;
        albumActivity.enterPicPreview(imageInfo.getImageFile().getAbsolutePath());
        return false;
    }*/
}
