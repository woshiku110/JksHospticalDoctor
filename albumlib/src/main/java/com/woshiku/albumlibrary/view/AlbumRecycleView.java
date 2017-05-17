package com.woshiku.albumlibrary.view;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by Administrator on 2016/9/13.
 */
public class AlbumRecycleView extends RecyclerView{
    Context context;
    List<ImageInfo> mList;
    ImageLoaderWrapper loaderWrapper;
    SelectedPhoto selectedPhoto = null;
    ShowAdapter showAdapter;
    AlbumGridPresenter albumGridPresenter;
    ImageLoaderWrapper.DisplayOption displayOption;
    UserLongClick userLongClick;
    public interface UserLongClick{
        void userLongClick(String filePath);
    }
    public AlbumRecycleView(Context context) {
        super(context);
    }

    public AlbumRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void setUserLongClick(UserLongClick userLongClick) {
        this.userLongClick = userLongClick;
    }

    private void init(Context context){
        this.context = context;
        loaderWrapper = ImageLoaderFactory.getLoader();
        displayOption = new ImageLoaderWrapper.DisplayOption();
        displayOption.loadingResId = R.mipmap.img_default;
        displayOption.loadErrorResId = R.mipmap.img_error;
    }
    //设置数据
    public void setData(List<ImageInfo> mList){
        this.mList = mList;
        if(showAdapter == null){
            showAdapter = new ShowAdapter();
            setLayoutManager(new GridLayoutManager(context,3));
            setItemAnimator(new DefaultItemAnimator());
            setAdapter(showAdapter);
        }
    }
    //设置新数据
    public void setNewData(List<ImageInfo> mList){
        this.mList = mList;
        showAdapter = new ShowAdapter();
        setAdapter(showAdapter);
    }
    //单个数据刷新
    public void freshSingleData(int pos){
        showAdapter.notifyItemChanged(pos);
    }

    public void setGridPresenter(AlbumGridPresenter albumGridPresenter){
        this.albumGridPresenter = albumGridPresenter;
    }
    public void setSelectedPhoto(SelectedPhoto selectedPhoto){
        this.selectedPhoto = selectedPhoto;
    }

    class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.MyViewHolder>{

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(context, R.layout.album_grid_item,null);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            ImageInfo imageInfo = mList.get(position);
            //是拍照选项
            if(imageInfo.isTakePhoto()){
                holder.cb.setVisibility(View.INVISIBLE);
                holder.ig.setImageResource(R.mipmap.icon_takephoto);
            }else{
                holder.cb.setVisibility(View.VISIBLE);
                loaderWrapper.displayImage(holder.ig, mList.get(position).getImageFile(), displayOption);
                if(imageInfo.isSelected()){
                    holder.cb.setEnabled(true);
                }else{
                    holder.cb.setEnabled(false);
                }
            }
            holder.suqareLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("result","click"+position);
                    userClick(position);
                }
            });
            holder.suqareLayout.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(userLongClick!=null){
                        userLongClick.userLongClick(mList.get(position).getImageFile().getAbsolutePath());
                    }
                    return false;
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        class MyViewHolder extends ViewHolder{
            SuqareLayout suqareLayout;
            ImageView ig;
            ImageView cb;
            public MyViewHolder(View itemView) {
                super(itemView);
                suqareLayout = (SuqareLayout)itemView.findViewById(R.id.iv_album_suare);
                ig = (ImageView)itemView.findViewById(R.id.iv_album_item);
                cb = (ImageView)itemView.findViewById(R.id.ckb_image_select);
            }
        }
    }
    //拿到被选中的文件
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
            if(count<= Global.maxPicsAmount){
                if(selectedPhoto != null){
                    selectedPhoto.selectedPhotoAmount(getSelectedList(mList));
                }
            }else{
                picSelectData.setIsSelected(false);
                mList.set(pos, picSelectData);
                if(selectedPhoto != null){
                    selectedPhoto.selectedPhotoAmount(getSelectedList(mList));
                }
                Toast.makeText(context, "您选择的图片数量不能超过了" + Global.maxPicsAmount + "张", Toast.LENGTH_SHORT).show();
            }
        }else{
            picSelectData.setIsSelected(false);
            mList.set(pos, picSelectData);
            if(selectedPhoto != null){
                selectedPhoto.selectedPhotoAmount(getSelectedList(mList));
            }
        }
        //刷新单个Item
        albumGridPresenter.freshSingleView(pos);
    }
}
