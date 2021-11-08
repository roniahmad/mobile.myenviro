package com.rsbunda.myenviro.report;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rsbunda.myenviro.io.model.cleaning.DailyReportImages;

import java.util.List;

public class ReportImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<DailyReportImages> mDailyReport;

    private Context mContext;

    private ReportImageItemViewHolder.Callbacks mCallbacks;


    public ReportImageAdapter(Context context, List<DailyReportImages> daily,
                              @NonNull ReportImageItemViewHolder.Callbacks adapterCallbacks){
        this.mDailyReport = daily;
        this.mContext = context;
        this.mCallbacks = adapterCallbacks;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ReportImageItemViewHolder.newInstance(parent, mCallbacks, mContext, mDailyReport);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final DailyReportImages item = mDailyReport.get(position);
        ((ReportImageItemViewHolder) holder).bind((DailyReportImages) item);
    }

    @Override
    public int getItemCount() {
        return  mDailyReport.size();
    }

}
