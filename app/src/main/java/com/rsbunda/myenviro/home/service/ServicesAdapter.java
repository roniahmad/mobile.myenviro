package com.rsbunda.myenviro.home.service;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rsbunda.myenviro.io.model.layanan.Layanan;
import com.rsbunda.myenviro.io.model.layanan.ProductLayanan;

import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Layanan> mLayanan;
    private List<ProductLayanan> mProductLayanan;

    private Context mContext;
    private ServiceItemViewHolder.Callbacks mAdapterCallbacks;

    public ServicesAdapter(Context context, List<Layanan> services, List<ProductLayanan> product,
                           @NonNull ServiceItemViewHolder.Callbacks adapterCallbacks){
        this.mContext = context;
        this.mLayanan = services;
        this.mProductLayanan = product;
        this.mAdapterCallbacks = adapterCallbacks;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i){
        return ServiceItemViewHolder.newInstance(parent, mAdapterCallbacks, mContext, mProductLayanan);
    }

    @Override
    public  void onBindViewHolder(RecyclerView.ViewHolder holder, int pos){
        final Layanan item = mLayanan.get(pos);
        ((ServiceItemViewHolder) holder).bind((Layanan) item);
    }

    @Override
    public int getItemCount(){
        return  mLayanan.size();
    }
}
