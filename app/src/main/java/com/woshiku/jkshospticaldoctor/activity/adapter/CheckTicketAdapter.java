package com.woshiku.jkshospticaldoctor.activity.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.domain.CheckTicketData;
import java.util.List;
import static com.woshiku.jkshospticaldoctor.activity.inter.PageState.PageFail;
import static com.woshiku.jkshospticaldoctor.activity.inter.PageState.PageNoData;
import static com.woshiku.jkshospticaldoctor.activity.inter.PageState.PageOk;
import static com.woshiku.jkshospticaldoctor.activity.inter.PageState.PageWaiting;

/**
 * Created by admin on 2017-04-29.
 *@desc 检查单适配器
 */

public class CheckTicketAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<CheckTicketData> medicalSearchDataList;
    Context context;
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onItemClick(CheckTicketData checkTicketData,int pos);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CheckTicketAdapter(Context context, List<CheckTicketData> medicalSearchDataList) {
        this.context = context;
        this.medicalSearchDataList = medicalSearchDataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == -1){
            View view = View.inflate(context,R.layout.item_checkticket_title,null);
            return new TypeOneHolder(view);
        }else if(viewType == PageWaiting){
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
        }else if(viewType == PageOk){
            View view = View.inflate(context,R.layout.item_checkticket_content,null);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new TypeTwoHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof TypeTwoHolder){
            TypeTwoHolder typeTwoHolder = (TypeTwoHolder)holder;
            final CheckTicketData checkTicketData = medicalSearchDataList.get(position);
            if(!TextUtils.isEmpty(checkTicketData.getInstructions())){
                typeTwoHolder.contentView.setText(checkTicketData.getName()+"("+checkTicketData.getInstructions()+")");
            }else{
                typeTwoHolder.contentView.setText(checkTicketData.getName());
            }
            typeTwoHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        onItemClickListener.onItemClick(checkTicketData,position);
                    }
                }
            });
            if(checkTicketData.isChoosed()){
                typeTwoHolder.chooseView.setBackground(ContextCompat.getDrawable(context,R.mipmap.btn_s_pre));
            }else{
                typeTwoHolder.chooseView.setBackground(ContextCompat.getDrawable(context,R.mipmap.btn_s));
            }
        }else if(holder instanceof TypeOneHolder){
            TypeOneHolder typeOneHolder = (TypeOneHolder)holder;
            CheckTicketData checkTicketData = medicalSearchDataList.get(position);
            typeOneHolder.title.setText(checkTicketData.getIndexLetter());
        }
    }

    @Override
    public int getItemCount() {
        return medicalSearchDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        CheckTicketData checkTicketData = medicalSearchDataList.get(position);
        return checkTicketData.getPageState();
    }
    class TypeHolder extends RecyclerView.ViewHolder{
        public TypeHolder(View itemView) {
            super(itemView);
        }
    }
    class TypeOneHolder extends RecyclerView.ViewHolder{
        TextView title;
        public TypeOneHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_check_ticket_title);
        }
    }
    class TypeTwoHolder extends RecyclerView.ViewHolder{
        View chooseView;
        TextView contentView;
        CardView cardView;
        public TypeTwoHolder(View itemView) {
            super(itemView);
            chooseView = itemView.findViewById(R.id.item_check_ticket_choose);
            contentView = (TextView)itemView.findViewById(R.id.item_check_ticket_content);
            cardView = (CardView)itemView.findViewById(R.id.item_check_ticket_cardview);
        }
    }

    public void updateDatas(List<CheckTicketData> updateList){
        compareData(medicalSearchDataList,updateList);
        medicalSearchDataList = updateList;
        notifyItemRangeChanged(1,updateList.size());
    }

    public void updateData(List<CheckTicketData> updateList,int pos){
        medicalSearchDataList = updateList;
        notifyItemChanged(pos);
    }

    private void compareData(List<CheckTicketData> oldDatas,List<CheckTicketData> newDatas){
        int count = oldDatas.size() - newDatas.size();
        if(count>0){
            notifyItemRangeRemoved(newDatas.size(),count);
        }else if(count == 0){

        }else{
            notifyItemRangeInserted(oldDatas.size(),Math.abs(count));
        }
    }
}
