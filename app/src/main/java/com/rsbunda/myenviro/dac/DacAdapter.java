package com.rsbunda.myenviro.dac;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rsbunda.myenviro.io.model.activity.Daily;

import java.util.List;


public class DacAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Daily> mDaily;

    private Context mContext;

    private DacItemViewHolder.Callbacks mCallbacks;

    public DacAdapter(Context context, List<Daily> daily,
                      @NonNull DacItemViewHolder.Callbacks adapterCallbacks){
        this.mDaily = daily;
        this.mContext = context;
        this.mCallbacks = adapterCallbacks;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return DacItemViewHolder.newInstance(parent, mCallbacks, mContext, mDaily);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Daily item = mDaily.get(position);
        ((DacItemViewHolder) holder).bind((Daily) item);
    }

    @Override
    public int getItemCount() {
        return  mDaily.size();
    }

}
