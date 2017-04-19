package com.woshiku.jkshospticaldoctor.activity.adapter.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.domain.UndealPreorderData;
import com.woshiku.jkshospticaldoctor.activity.utils.LogUtil;
import com.woshiku.jkshospticaldoctor.activity.view.ItemChooseView;
import java.util.List;
/**
 * Created by admin on 2017-04-18.
 */

public class PreorderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<UndealPreorderData> undealPreorderDataList;
    Context context;
    OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onItemClick(RecyclerView.ViewHolder holder,int pos);
    }
    public PreorderAdapter(Context context, List<UndealPreorderData> undealPreorderDataList) {
        this.context = context;
        this.undealPreorderDataList = undealPreorderDataList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == 0){
            View view = View.inflate(context,R.layout.item_preorder_choose,null);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            TypeOneHolder typeOneHolder = new TypeOneHolder(view);
            return typeOneHolder;
        }else{
            View view = View.inflate(context,R.layout.item_preorder,null);
            TypeTwoHolder typeTwoHolder = new TypeTwoHolder(view);
            return typeTwoHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof TypeOneHolder){//类型一
            TypeOneHolder oneHolder = (TypeOneHolder)holder;
            oneHolder.itemChooseView.setUserClickListener(new ItemChooseView.UserClickListener() {
                @Override
                public void userClick(boolean isWait) {
                    LogUtil.print("isWait:"+isWait);
                }
            });
        }else{//类型f二
            TypeTwoHolder twoHolder = (TypeTwoHolder)holder;
            twoHolder.date.setText(undealPreorderDataList.get(position).getDate());
        }
    }

    @Override
    public int getItemViewType(int position) {
        UndealPreorderData undealPreorderData = undealPreorderDataList.get(position);
        if(undealPreorderData.isChoose()){
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return undealPreorderDataList.size();
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
        TextView date;
        public TypeTwoHolder(View itemView) {
            super(itemView);
            icon = (ImageView)itemView.findViewById(R.id.item_preorder_icon);
            sex =  itemView.findViewById(R.id.item_preorder_sex);
            date = (TextView)itemView.findViewById(R.id.item_preorder_date);
        }
    }
}
