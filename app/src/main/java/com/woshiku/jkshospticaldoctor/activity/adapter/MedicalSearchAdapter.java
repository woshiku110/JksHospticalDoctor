package com.woshiku.jkshospticaldoctor.activity.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.woshiku.jkshospticaldoctor.R;
import com.woshiku.jkshospticaldoctor.activity.domain.MedicalSearchData;
import com.woshiku.jkshospticaldoctor.activity.utils.KeyBoardUtils;
import java.util.List;
import static com.woshiku.jkshospticaldoctor.activity.inter.PageState.PageFail;
import static com.woshiku.jkshospticaldoctor.activity.inter.PageState.PageNoData;
import static com.woshiku.jkshospticaldoctor.activity.inter.PageState.PageOk;
import static com.woshiku.jkshospticaldoctor.activity.inter.PageState.PageWaiting;

/**
 * Created by admin on 2017-04-29.
 */

public class MedicalSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<MedicalSearchData> medicalSearchDataList;
    Context context;
    private TextChangeListener textChangeListener;
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onItemClick(MedicalSearchData medicalSearchData);
    }
    public interface TextChangeListener{
        void textChange(String text);
    }

    public void setTextChangeListener(TextChangeListener textChangeListener) {
        this.textChangeListener = textChangeListener;
    }

    public MedicalSearchAdapter(Context context, List<MedicalSearchData> medicalSearchDataList) {
        this.context = context;
        this.medicalSearchDataList = medicalSearchDataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == -1){
            View view = View.inflate(context,R.layout.item_medical_search,null);
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
            View view = View.inflate(context,R.layout.item_medical_info,null);
            return new TypeTwoHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof TypeTwoHolder){
            TypeTwoHolder typeTwoHolder = (TypeTwoHolder)holder;
            final MedicalSearchData medicalSearchData = medicalSearchDataList.get(position);
            typeTwoHolder.noView.setText(medicalSearchData.getNo());
            typeTwoHolder.nameView.setText(medicalSearchData.getMedicalName());
            typeTwoHolder.unitView.setText(medicalSearchData.getStandard());
            typeTwoHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        onItemClickListener.onItemClick(medicalSearchData);
                    }
                }
            });
        }else if(holder instanceof TypeOneHolder){
            final TypeOneHolder typeOneHolder = (TypeOneHolder)holder;
            typeOneHolder.searchInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s.toString().length()>0){
                        String msg = s.toString();
                        if(textChangeListener != null){
                            textChangeListener.textChange(msg);
                        }
                    }else{//关闭输入法
                        KeyBoardUtils.closeKeybord(typeOneHolder.searchInput,context);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return medicalSearchDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        MedicalSearchData preorderData = medicalSearchDataList.get(position);
        return preorderData.getPageState();
    }
    class TypeHolder extends RecyclerView.ViewHolder{
        public TypeHolder(View itemView) {
            super(itemView);
        }
    }
    class TypeOneHolder extends RecyclerView.ViewHolder{
        EditText searchInput;
        ImageView searchBt;
        public TypeOneHolder(View itemView) {
            super(itemView);
            searchInput = (EditText) itemView.findViewById(R.id.item_medical_search_input);
            searchBt = (ImageView)itemView.findViewById(R.id.item_medical_search_bt);
        }
    }
    class TypeTwoHolder extends RecyclerView.ViewHolder{
        TextView noView,nameView,unitView;
        CardView cardView;
        public TypeTwoHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.item_medical_card);
            noView = (TextView)itemView.findViewById(R.id.item_medical_no);
            nameView = (TextView)itemView.findViewById(R.id.item_medical_name);
            unitView = (TextView)itemView.findViewById(R.id.item_medical_unit);
        }
    }

    public void updateDatas(List<MedicalSearchData> updateList){
        compareData(medicalSearchDataList,updateList);
        medicalSearchDataList = updateList;
        notifyItemRangeChanged(1,updateList.size());
    }
    private void compareData(List<MedicalSearchData> oldDatas,List<MedicalSearchData> newDatas){
        int count = oldDatas.size() - newDatas.size();
        if(count>0){
            notifyItemRangeRemoved(newDatas.size(),count);
        }else if(count == 0){
        }else{
            notifyItemRangeInserted(oldDatas.size(),Math.abs(count));
        }
    }
}
