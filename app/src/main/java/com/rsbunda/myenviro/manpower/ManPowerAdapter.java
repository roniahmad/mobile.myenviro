package com.rsbunda.myenviro.manpower;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.rsbunda.myenviro.io.model.sales.ManPowerDetil;

import java.util.List;

public class ManPowerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<ManPowerDetil> mManPower;

    private Context mContext;

    private ManPowerItemViewHolder.Callbacks mCallbacks;

    public ManPowerAdapter(Context context, List<ManPowerDetil> jos,
                           @NonNull ManPowerItemViewHolder.Callbacks adapterCallbacks){
        this.mManPower = jos;
        this.mContext = context;
        this.mCallbacks = adapterCallbacks;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ManPowerItemViewHolder.newInstance(parent, mCallbacks, mContext, mManPower);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ManPowerDetil item = mManPower.get(position);
        ((ManPowerItemViewHolder) holder).bind((ManPowerDetil) item);
    }

    @Override
    public int getItemCount() {
        return  mManPower.size();
    }




}
