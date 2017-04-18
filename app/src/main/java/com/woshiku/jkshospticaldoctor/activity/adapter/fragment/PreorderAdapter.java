package com.woshiku.jkshospticaldoctor.activity.adapter.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by admin on 2017-04-18.
 */

public class PreorderAdapter extends RecyclerView.Adapter<PreorderAdapter.PreorderHolder>{

    @Override
    public PreorderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(PreorderHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class PreorderHolder extends RecyclerView.ViewHolder{

        public PreorderHolder(View itemView) {
            super(itemView);
        }
    }
}
