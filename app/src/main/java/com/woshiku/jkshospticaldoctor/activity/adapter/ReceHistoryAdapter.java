package com.woshiku.jkshospticaldoctor.activity.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
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
import com.woshiku.jkshospticaldoctor.activity.domain.ReceHistoryData;
import com.woshiku.jkshospticaldoctor.activity.utils.KeyBoardUtils;
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
 * Created by admin on 2017-04-29.
 */

public class ReceHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<ReceHistoryData> medicalSearchDataList;
    Context context;
    private TextChangeListener textChangeListener;
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onItemClick(ReceHistoryData receHistoryData);
    }
    public interface TextChangeListener{
        void textChange(String text);
    }

    public void setTextChangeListener(TextChangeListener textChangeListener) {
        this.textChangeListener = textChangeListener;
    }

    public ReceHistoryAdapter(Context context, List<ReceHistoryData> medicalSearchDataList) {
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
            View view = View.inflate(context,R.layout.item_preorder,null);//用的是预约预诊的item
            return new TypeTwoHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof TypeTwoHolder){
            TypeTwoHolder twoHolder = (TypeTwoHolder)holder;
            final ReceHistoryData medicalSearchData = medicalSearchDataList.get(position);
            twoHolder.date.setText(medicalSearchData.getDate());
            twoHolder.name.setText(medicalSearchData.getName());
            twoHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        onItemClickListener.onItemClick(medicalSearchData);
                    }
                }
            });
            displayImageView(twoHolder.icon, Global.imagePath+medicalSearchData.getIcon());
            if(medicalSearchData.getSex().equals("男")){
                twoHolder.sex.setBackground(ContextCompat.getDrawable(context,R.mipmap.ico_man));
            }else {
                twoHolder.sex.setBackground(ContextCompat.getDrawable(context,R.mipmap.ico_woman));
            }
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

    @Override
    public int getItemViewType(int position) {
        ReceHistoryData preorderData = medicalSearchDataList.get(position);
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

    public void updateDatas(List<ReceHistoryData> updateList){
        compareData(medicalSearchDataList,updateList);
        medicalSearchDataList = updateList;
        notifyItemRangeChanged(1,updateList.size());
    }

    private void compareData(List<ReceHistoryData> oldDatas,List<ReceHistoryData> newDatas){
        int count = oldDatas.size() - newDatas.size();
        if(count>0){
            notifyItemRangeRemoved(newDatas.size(),count);
        }else if(count == 0){
        }else{
            notifyItemRangeInserted(oldDatas.size(),Math.abs(count));
        }
    }
}
