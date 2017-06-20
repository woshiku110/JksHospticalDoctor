package com.woshiku.albumlibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import com.woshiku.albumlibrary.R;
import com.woshiku.albumlibrary.common.Global;
import com.woshiku.albumlibrary.domain.ImageInfo;
import com.woshiku.albumlibrary.imageloader.ImageLoaderFactory;
import com.woshiku.albumlibrary.imageloader.ImageLoaderWrapper;
import com.woshiku.albumlibrary.presenter.AlbumGridPresenter;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Administrator on 2016/8/12.
 */
public class AblumGridView extends GridView{
    List<ImageInfo> mList;
    Context context;
    ImageLoaderWrapper loaderWrapper;
    //AlbumImageLoader albumImageLoader;
    SelectedPhoto selectedPhoto = null;
    ShowAdapter showAdapter;
    AlbumGridPresenter albumGridPresenter;
    ImageLoaderWrapper.DisplayOption displayOption;
    public AblumGridView(Context context) {
        super(context);
    }

    public AblumGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
    }
    private void initData(Context context){
        this.context = context;
        //albumImageLoader = AlbumImageLoader.getInstance(context);
        loaderWrapper = ImageLoaderFactory.getLoader();
        displayOption = new ImageLoaderWrapper.DisplayOption();
        displayOption.loadingResId = R.mipmap.img_default;
        displayOption.loadErrorResId = R.mipmap.img_error;
    }
    public void setData(List<ImageInfo> mList){
        this.mList = mList;
        if(showAdapter == null){
            showAdapter = new ShowAdapter();
        }
        setAdapter(showAdapter);
    }
    public void freshData(){
        showAdapter.notifyDataSetChanged();
    }
    public void setNewData(List<ImageInfo> mList){
        this.mList = mList;
        showAdapter = new ShowAdapter();
        setAdapter(showAdapter);
    }
    public void setGridPresenter(AlbumGridPresenter albumGridPresenter){
        this.albumGridPresenter = albumGridPresenter;
    }
    public void setSelectedPhoto(SelectedPhoto selectedPhoto){
        this.selectedPhoto = selectedPhoto;
    }
    class ShowAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView == null){
                convertView = View.inflate(context, R.layout.album_grid_item,null);
                viewHolder = new ViewHolder();
                viewHolder.ig = (ImageView)convertView.findViewById(R.id.iv_album_item);
                viewHolder.cb = (ImageView)convertView.findViewById(R.id.ckb_image_select);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder)convertView.getTag();
            }
            //是拍照选项
            if(mList.get(position).isTakePhoto()){
                viewHolder.cb.setVisibility(View.INVISIBLE);
                viewHolder.ig.setImageResource(R.mipmap.icon_takephoto);
            }else{
                viewHolder.cb.setVisibility(View.VISIBLE);
                //albumImageLoader.displayImage(viewHolder.ig,mList.get(position).getImageFile().getAbsolutePath());
                loaderWrapper.displayImage(viewHolder.ig, mList.get(position).getImageFile(), displayOption);
                if(mList.get(position).isSelected()){
                    viewHolder.cb.setEnabled(true);
                }else{
                    viewHolder.cb.setEnabled(false);
                }
            }
            //viewHolder.cb.setOnClickListener(new UserClick(position,viewHolder));
            return convertView;
        }
    }
    public class ViewHolder{
        public ImageView ig;
        public ImageView cb;
    }
    public List<String> getSelectedList(List<ImageInfo> mList){
        List<String> infoList = new ArrayList<>();
        for(int i=0;i<mList.size();i++){
            if(mList.get(i).isSelected()){
                infoList.add(mList.get(i).getImageFile().getAbsolutePath());
            }
        }
        return infoList;
    }
    public void userClick(int pos){
        ImageInfo picSelectData = mList.get(pos);
        boolean isSelected = picSelectData.isSelected();
        isSelected = !isSelected;
        picSelectData.setIsSelected(isSelected);
        if(isSelected){
            int count;
            mList.set(pos, picSelectData);
            count = getSelectedList(mList).size();
            if(count<=Global.maxPicsAmount){
                if(selectedPhoto != null){
                    selectedPhoto.selectedPhotoAmount(getSelectedList(mList));
                }
            }else{
                picSelectData.setIsSelected(false);
                mList.set(pos, picSelectData);
                if(selectedPhoto != null){
                    selectedPhoto.selectedPhotoAmount(getSelectedList(mList));
                }
                Toast.makeText(context, "您选择的图片数量不能超过了" + Global.maxPicsAmount+"张", Toast.LENGTH_SHORT).show();
            }
        }else{
            picSelectData.setIsSelected(false);
            mList.set(pos, picSelectData);
            if(selectedPhoto != null){
                selectedPhoto.selectedPhotoAmount(getSelectedList(mList));
            }
        }
        //刷新后台
        albumGridPresenter.freshGridView();
    }
    /*class UserClick implements OnClickListener{
        private int pos;
        public UserClick(int pos) {
            this.pos = pos;
        }
        @Override
        public void onClick(View v) {
            ImageInfo picSelectData = mList.get(pos);
            boolean isSelected = picSelectData.isSelected();
            isSelected = !isSelected;
            picSelectData.setIsSelected(isSelected);
            if(isSelected){
                int count;
                mList.set(pos, picSelectData);
                count = getSelectedList(mList).size();
                if(count<=Global.maxPicsAmount){
                    if(selectedPhoto != null){
                        selectedPhoto.selectedPhotoAmount(getSelectedList(mList));
                    }
                }else{
                    mList.set(pos, picSelectData);
                    if(selectedPhoto != null){
                        selectedPhoto.selectedPhotoAmount(getSelectedList(mList));
                    }
                    Toast.makeText(context, "您选择的图片数量不能超过了" + Global.maxPicsAmount+"张", Toast.LENGTH_SHORT).show();
                }
            }else{
                picSelectData.setIsSelected(false);
                mList.set(pos, picSelectData);
                if(selectedPhoto != null){
                    selectedPhoto.selectedPhotoAmount(getSelectedList(mList));
                }
            }
            //刷新后台
            albumGridPresenter.freshGridView();
        }
    }*/
}
