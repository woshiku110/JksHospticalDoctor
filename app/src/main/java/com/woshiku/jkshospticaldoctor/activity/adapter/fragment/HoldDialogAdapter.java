package com.woshiku.jkshospticaldoctor.activity.adapter.fragment;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.domain.HoldDialogData;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;
import java.util.List;
import common.Global;
import static com.woshiku.jkshospticaldoctor.activity.inter.PageState.PageFail;
import static com.woshiku.jkshospticaldoctor.activity.inter.PageState.PageNoData;
import static com.woshiku.jkshospticaldoctor.activity.inter.PageState.PageOk;
import static com.woshiku.jkshospticaldoctor.activity.inter.PageState.PageWaiting;

/**
 * Created by admin on 2017-04-25.
 */

public class HoldDialogAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<HoldDialogData> holdDataList;
    Context context;
    OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onItemClick(HoldDialogData preorderData,int pos);
    }

    public HoldDialogAdapter(Context context, List<HoldDialogData> holdDataList) {
        this.context = context;
        this.holdDataList = holdDataList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == PageWaiting){
            View view = View.inflate(context, R.layout.recycleview_item_waitting,null);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(lp);
            return new TypeHolder(view);
        }else if(viewType == PageFail){
            View view = View.inflate(context,R.layout.recycleview_item_fail,null);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(lp);
            return new TypeHolder(view);
        }else if(viewType == PageNoData){
            View view = View.inflate(context,R.layout.recycleview_item_nodata,null);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(lp);
            return new TypeHolder(view);
        } else if(viewType == PageOk){
            View view = View.inflate(context,R.layout.item_hold_dialog,null);
            TypeHolderOne typeTwoHolder = new TypeHolderOne(view);
            return typeTwoHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof TypeHolderOne){
            final TypeHolderOne twoHolder = (TypeHolderOne)holder;
            final HoldDialogData holdDialogData = holdDataList.get(position);
            twoHolder.name.setText(holdDialogData.getName());
            twoHolder.backBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        onItemClickListener.onItemClick(holdDialogData,position);
                    }
                }
            });
            displayImageView(twoHolder.icon, Global.imagePath+holdDialogData.getIcon());
            if(holdDialogData.getSex().equals("男")){
                twoHolder.sex.setBackground(ContextCompat.getDrawable(context,R.mipmap.ico_man));
            }else {
                twoHolder.sex.setBackground(ContextCompat.getDrawable(context,R.mipmap.ico_woman));
            }
            if(holdDialogData.isBtEnable()){
                twoHolder.backBt.setBackground(ContextCompat.getDrawable(context,R.drawable.shape_bt_border_drawable));
                if(holdDialogData.isReturnDialog()){
                    twoHolder.backBtText.setText("返回诊断");
                }else{
                    twoHolder.backBtText.setText("叫号");
                }
            }else{
                twoHolder.backBtText.setText("叫号");
                twoHolder.backBt.setBackground(ContextCompat.getDrawable(context,R.drawable.shape_bt_unchoose_border_drawable));
                /*twoHolder.backBt.setBackgroundColor(ContextCompat.getColor(context,R.color.item_backorder_disable));*/
            }
        }
    }

    @Override
    public int getItemCount() {
        return holdDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        HoldDialogData preorderData = holdDataList.get(position);
        return preorderData.getPageState();
    }

    private void displayImageView(ImageView imagewView,String path){
        LogUtil.print(path);
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                .setFailureDrawableId(R.mipmap.img_error)
                .setLoadingDrawableId(R.mipmap.img_default)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setCircular(true)
                .build();
        x.image().bind(imagewView,path,imageOptions);
    }

    class TypeHolder extends RecyclerView.ViewHolder{
        public TypeHolder(View itemView) {
            super(itemView);
        }
    }
    class TypeHolderOne extends RecyclerView.ViewHolder{
        ImageView icon;
        View sex;
        TextView name;
        CardView cardView;
        CardView backBt;
        TextView backBtText;
        public TypeHolderOne(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.item_preorder_card);
            icon = (ImageView)itemView.findViewById(R.id.item_preorder_icon);
            sex =  itemView.findViewById(R.id.item_preorder_sex);
            name = (TextView)itemView.findViewById(R.id.item_preorder_name);
            backBt = (CardView)itemView.findViewById(R.id.item_hold_back);
            backBtText = (TextView)itemView.findViewById(R.id.item_hold_back_txt);
        }
    }

    /**
     * @desc 更新界面数据
     * @param updateList 更新的新数据
     * */
    public void updateDatas(List<HoldDialogData> updateList){
        compareData(holdDataList,updateList);
        holdDataList = updateList;
        notifyItemRangeChanged(0,updateList.size());
    }

    public void deleSingeDataWithUpdate(List<HoldDialogData> updateList,int index){
        notifyItemRemoved(index);
        notifyItemRangeChanged(0,updateList.size());
        holdDataList = updateList;
    }

    /**
     * @desc 比较数据
     * @param oldDatas 更新的新数据
     * @param newDatas 以前的数据
     **/
    private void compareData(List<HoldDialogData> oldDatas,List<HoldDialogData> newDatas){
        int count = oldDatas.size() - newDatas.size();
        if(count>0){
            notifyItemRangeRemoved(newDatas.size(),count);
        }else if(count == 0){
        }else{
            notifyItemRangeInserted(oldDatas.size(),Math.abs(count));
        }
    }
}
