package com.rsbunda.myenviro.dashboard.jos;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rsbunda.myenviro.io.model.sales.Jos;

import java.util.List;

public class JosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Jos> mJos;

    private Context mContext;

    private JosItemViewHolder.Callbacks mCallbacks;

    public JosAdapter(Context context, List<Jos> jos,
                      @NonNull JosItemViewHolder.Callbacks adapterCallbacks){
        this.mJos = jos;
        this.mContext = context;
        this.mCallbacks = adapterCallbacks;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i){
        return JosItemViewHolder.newInstance(parent, mCallbacks, mContext, mJos);
    }

    @Override
    public  void onBindViewHolder(RecyclerView.ViewHolder holder, int pos){
        final Jos item = mJos.get(pos);
        ((JosItemViewHolder) holder).bind((Jos) item);
    }

    @Override
    public int getItemCount(){
        return  mJos.size();
    }

}
