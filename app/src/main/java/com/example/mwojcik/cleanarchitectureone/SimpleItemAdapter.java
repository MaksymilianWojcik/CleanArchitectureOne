package com.example.mwojcik.cleanarchitectureone;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SimpleItemAdapter extends RecyclerView.Adapter<SimpleItemAdapter.MyViewHolder> {

    private List<SimpleItem> dataList;

    public SimpleItemAdapter(){
        dataList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SimpleItem simpleItem = dataList.get(position);
        holder.titleTV.setText(simpleItem.getTitle());
        holder.descriptionTV.setText(simpleItem.getDescription());
        holder.priorityTV.setText(String.valueOf(simpleItem.getPriority()));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setDataList(List<SimpleItem> dataList){
        this.dataList = dataList;
        notifyDataSetChanged(); //nie powinnismy tego uzywac, ale tutaj nie skupiamy sie na recycler viewu
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTV;
        private TextView priorityTV;
        private TextView descriptionTV;

        public MyViewHolder(View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.title_text_view);
            priorityTV = itemView.findViewById(R.id.priority_text_view);
            descriptionTV = itemView.findViewById(R.id.description_text_view);
        }
    }

}
