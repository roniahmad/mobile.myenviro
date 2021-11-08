package com.rsbunda.myenviro.report;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rsbunda.myenviro.io.model.cleaning.DailyReport;
import com.rsbunda.myenviro.io.model.cleaning.DailyReportImages;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<DailyReport> mDailyReport;

    private List<DailyReportImages> mDailyReportImages;

    private Context mContext;

    private ReportItemViewHolder.Callbacks mCallbacks;

    public ReportAdapter(Context context, List<DailyReport> daily, List<DailyReportImages> dailyImages,
                         @NonNull ReportItemViewHolder.Callbacks adapterCallbacks){

        this.mDailyReport = daily;
        this.mDailyReportImages = dailyImages;
        this.mContext = context;
        this.mCallbacks = adapterCallbacks;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ReportItemViewHolder.newInstance(parent, mCallbacks, mContext, mDailyReport, mDailyReportImages);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final DailyReport item = mDailyReport.get(position);
        ((ReportItemViewHolder) holder).bind((DailyReport) item);
    }

    @Override
    public int getItemCount() {
        return  mDailyReport.size();
    }

}
