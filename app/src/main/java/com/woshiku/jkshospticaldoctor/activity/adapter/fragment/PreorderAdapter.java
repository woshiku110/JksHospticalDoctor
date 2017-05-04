package com.woshiku.jkshospticaldoctor.activity.adapter.fragment;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.domain.PreorderData;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.jkshospticaldoctor.activity.view.ItemChooseView;
import org.xutils.image.ImageOptions;
import org.xutils.x;
import java.util.List;
import common.Global;
import static com.woshiku.jkshospticaldoctor.activity.inter.PageState.PageFail;
import static com.woshiku.jkshospticaldoctor.activity.inter.PageState.PageNoData;
import static com.woshiku.jkshospticaldoctor.activity.inter.PageState.PageOk;
import static com.woshiku.jkshospticaldoctor.activity.inter.PageState.PageWaiting;

/**
 * Created by admin on 2017-04-18.
 */

public class PreorderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<PreorderData> preorderDataList;
    Context context;
    OnItemClickListener onItemClickListener;
    OnChooseListener onChooseListener;

    public interface OnItemClickListener{
        void onItemClick(PreorderData preorderData,int pos);
    }
    public interface OnChooseListener{
        void onChoose(boolean isUndeal);
    }

    public void setOnChooseListener(OnChooseListener onChooseListener) {
        this.onChooseListener = onChooseListener;
    }

    public PreorderAdapter(Context context, List<PreorderData> undealPreorderDataList) {
        this.context = context;
        this.preorderDataList = undealPreorderDataList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == -1){
            View view = View.inflate(context,R.layout.item_preorder_choose,null);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            TypeOneHolder typeOneHolder = new TypeOneHolder(view);
            return typeOneHolder;
        }else if(viewType == PageWaiting){
            View view = View.inflate(context,R.layout.recycleview_item_waitting,null);
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
            View view = View.inflate(context,R.layout.item_preorder,null);
            TypeTwoHolder typeTwoHolder = new TypeTwoHolder(view);
            return typeTwoHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof TypeOneHolder){//类型一
            final TypeOneHolder oneHolder = (TypeOneHolder)holder;
            oneHolder.itemChooseView.setWaitChecked(preorderDataList.get(position).isCheckState());
            oneHolder.itemChooseView.setUserClickListener(new ItemChooseView.UserClickListener() {
                @Override
                public void userClick(boolean isWait) {
                    if(onChooseListener != null){
                        onChooseListener.onChoose(isWait);
                    }
                    LogUtil.print("isWait:"+isWait);
                }
            });
        }else if(holder instanceof TypeTwoHolder){//类型二
            final TypeTwoHolder twoHolder = (TypeTwoHolder)holder;
            twoHolder.date.setText(preorderDataList.get(position).getDate());
            twoHolder.name.setText(preorderDataList.get(position).getName());
            twoHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        onItemClickListener.onItemClick(preorderDataList.get(position),position);
                    }
                }
            });
            displayImageView(twoHolder.icon, Global.imagePath+preorderDataList.get(position).getIcon());
            if(preorderDataList.get(position).getSex().equals("男")){
                twoHolder.sex.setBackground(ContextCompat.getDrawable(context,R.mipmap.ico_man));
            }else {
                twoHolder.sex.setBackground(ContextCompat.getDrawable(context,R.mipmap.ico_woman));
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        PreorderData preorderData = preorderDataList.get(position);
        if(preorderData.isChoose()){
            return -1;
        }else{
            return preorderData.getPageState();
        }
    }

    @Override
    public int getItemCount() {
        return preorderDataList.size();
    }
    class TypeHolder extends RecyclerView.ViewHolder{
        public TypeHolder(View itemView) {
            super(itemView);
        }
    }
    class TypeOneHolder extends RecyclerView.ViewHolder{
        ItemChooseView itemChooseView;
        public TypeOneHolder(View itemView) {
            super(itemView);
            itemChooseView = (ItemChooseView)itemView.findViewById(R.id.item_preorder_choose);
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)itemChooseView.getLayoutParams();
            lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
            lp.height = LinearLayout.LayoutParams.MATCH_PARENT;
            itemChooseView.setLayoutParams(lp);
        }
    }
    class TypeTwoHolder extends RecyclerView.ViewHolder{
        ImageView icon;
        View sex;
        TextView date,name;
        CardView cardView;
        public TypeTwoHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.item_preorder_card);
            icon = (ImageView)itemView.findViewById(R.id.item_preorder_icon);
            sex =  itemView.findViewById(R.id.item_preorder_sex);
            date = (TextView)itemView.findViewById(R.id.item_preorder_date);
            name = (TextView)itemView.findViewById(R.id.item_preorder_name);
        }
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

    public void updateDatas(List<PreorderData> updateList){
        compareData(preorderDataList,updateList);
        preorderDataList = updateList;
        notifyItemRangeChanged(1,updateList.size());
    }
    private void compareData(List<PreorderData> oldDatas,List<PreorderData> newDatas){
        int count = oldDatas.size() - newDatas.size();
        if(count>0){
            notifyItemRangeRemoved(newDatas.size(),count);
        }else if(count == 0){
        }else{
            notifyItemRangeInserted(oldDatas.size(),Math.abs(count));
        }
    }
    public void deleData(List<PreorderData> preorderDatas,int index){
        notifyItemRemoved(index);
        preorderDataList = preorderDatas;
        LogUtil.print("index:"+index);
    }

}
