package com.woshiku.albumlibrary.view;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.woshiku.albumlibrary.R;
import com.woshiku.albumlibrary.domain.AlbumFolderInfo;
import com.woshiku.albumlibrary.imageloader.ImageLoaderFactory;
import com.woshiku.albumlibrary.imageloader.ImageLoaderWrapper;
import java.util.List;
/**
 * Created by Administrator on 2016/8/16.
 */
public class AlbumListView extends ListView{
    List<AlbumFolderInfo> mList;
    ShowAdapter showAdapter;
    ImageLoaderWrapper loaderWrapper;
    Context context;
    public AlbumListView(Context context) {
        super(context);
    }

    public AlbumListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
    }
    public void initData(Context context){
        this.context = context;
        loaderWrapper = ImageLoaderFactory.getLoader();
    }
    public void setData(List<AlbumFolderInfo> mList){
        this.mList = mList;
        if(showAdapter == null){
            showAdapter = new ShowAdapter();
            setAdapter(showAdapter);
        }else{
            showAdapter.notifyDataSetChanged();
        }
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
                viewHolder = new ViewHolder();
                convertView = View.inflate(context, R.layout.album_view_item,null);
                viewHolder.fileCover = (ImageView)convertView.findViewById(R.id.album_view_cover);
                viewHolder.name = (TextView)convertView.findViewById(R.id.album_view_name);
                viewHolder.amount = (TextView)convertView.findViewById(R.id.album_view_amount);
                viewHolder.fileSelected = (ImageView)convertView.findViewById(R.id.album_view_selected);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder)convertView.getTag();
            }
            ImageLoaderWrapper.DisplayOption displayOption = new ImageLoaderWrapper.DisplayOption();
            displayOption.loadingResId = R.mipmap.img_default;
            displayOption.loadErrorResId = R.mipmap.img_error;
            loaderWrapper.displayImage(viewHolder.fileCover, mList.get(position).getFrontCover(), displayOption);
            viewHolder.name.setText(mList.get(position).getFolderName());
            viewHolder.amount.setText(mList.get(position).getImageInfoList().size()+"张");
            if(mList.get(position).isSelected()){
                viewHolder.fileSelected.setVisibility(View.VISIBLE);
            }else{
                viewHolder.fileSelected.setVisibility(View.INVISIBLE);
            }
            return convertView;
        }
    }
    public class ViewHolder{
        public ImageView fileCover;
        public TextView name;
        public TextView amount;
        public ImageView fileSelected;
    }
}
