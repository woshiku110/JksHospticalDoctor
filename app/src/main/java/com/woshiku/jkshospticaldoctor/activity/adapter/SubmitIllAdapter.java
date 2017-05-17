package com.woshiku.jkshospticaldoctor.activity.adapter;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.domain.SubmitIllData;
import com.woshiku.jkshospticaldoctor.activity.view.SquareLayout;
import org.xutils.image.ImageOptions;
import org.xutils.x;
import java.util.List;

/**
 * Created by admin on 2017-05-11.
 */

public class SubmitIllAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<SubmitIllData> illDataList;
    Context context;
    private OnItemClickListener onItemClickListener;
    private OnItemDeleClickListener onItemDeleClickListener;
    private String name;
    public interface OnItemClickListener{
        void onItemClick(SubmitIllData submitIllData,String name);
    }

    public interface OnItemDeleClickListener{
        void onItemDeleClick(SubmitIllData submitIllData,String name);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemDeleClickListener(OnItemDeleClickListener onItemDeleClickListener) {
        this.onItemDeleClickListener = onItemDeleClickListener;
    }

    public SubmitIllAdapter(Context context, List<SubmitIllData> illDataList,String name) {
        this.context = context;
        this.illDataList = illDataList;
        this.name = name;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context,R.layout.item_illdata,null);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolderOne viewHolderOne = (ViewHolderOne)holder;
        final SubmitIllData submitIllData = illDataList.get(position);
        viewHolderOne.squareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null){
                    onItemClickListener.onItemClick(submitIllData,name);
                }
            }
        });
        viewHolderOne.deleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemDeleClickListener != null){
                    onItemDeleClickListener.onItemDeleClick(submitIllData,name);
                }
            }
        });
        if(submitIllData.isAddPhoto()){
            viewHolderOne.imageView.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.img_add_photo));
            viewHolderOne.deleView.setVisibility(View.GONE);
        }else{
            viewHolderOne.deleView.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.compose_delete));
            viewHolderOne.deleView.setVisibility(View.VISIBLE);
            displayImageView(viewHolderOne.imageView,submitIllData.getPath());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(illDataList.get(position).isAddPhoto()){
            return -1;
        }else{
            return 0;
        }
        //return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return illDataList.size();
    }

    class ViewHolderOne extends RecyclerView.ViewHolder{
        SquareLayout squareLayout;
        ImageView imageView;
        ImageView deleView;
        public ViewHolderOne(View itemView) {
            super(itemView);
            squareLayout = (SquareLayout)itemView.findViewById(R.id.item_square_layout);
            imageView = (ImageView)itemView.findViewById(R.id.item_square_layout_image);
            deleView = (ImageView) itemView.findViewById(R.id.item_square_layout_dele);
        }
    }
    /**
     * @param  imagewView 图片
     * @param  path 要显示图片的路径
     */
    private void displayImageView(ImageView imagewView,String path){
        //LogUtil.print(path);
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                .setFailureDrawableId(R.mipmap.img_error)
                .setLoadingDrawableId(R.mipmap.img_default)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .build();
        x.image().bind(imagewView,path,imageOptions);
    }
    /**
     * @param  submitIllDatas 添加一条数据
     * @param pos 要插入的位置
     */
    public void addSingleData(List<SubmitIllData> submitIllDatas,int pos){
        notifyItemChanged(pos-1);
        notifyItemInserted(pos);
        illDataList = submitIllDatas;
    }

    /**
     * @param  submitIllDatas 要显示的数据
     * @param pos 从某个位置更新
     */
    public void addMultiDatas(List<SubmitIllData> submitIllDatas,int pos){
        notifyItemChanged(pos);
        notifyItemRangeInserted(pos+1,submitIllDatas.size() - illDataList.size());
        illDataList = submitIllDatas;
    }

    public void deleSingleData(List<SubmitIllData> submitIllDatas,int pos){
        notifyItemRemoved(pos);
        illDataList = submitIllDatas;
    }

    public String getName() {
        return name;
    }
}
