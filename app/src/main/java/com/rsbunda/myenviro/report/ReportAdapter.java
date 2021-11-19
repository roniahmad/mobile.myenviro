package com.rsbunda.myenviro.report;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rsbunda.myenviro.io.model.cleaning.DailyReportDetail;
import com.rsbunda.myenviro.io.model.cleaning.DailyReportImages;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<DailyReportDetail> mDailyReportDetail;

    private List<DailyReportImages> mDailyReportImages;

    private Context mContext;

    private ReportItemViewHolder.Callbacks mCallbacks;

    public ReportAdapter(Context context, List<DailyReportDetail> daily, List<DailyReportImages> dailyImages,
                         @NonNull ReportItemViewHolder.Callbacks adapterCallbacks){

        this.mDailyReportDetail = daily;
        this.mDailyReportImages = dailyImages;
        this.mContext = context;
        this.mCallbacks = adapterCallbacks;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ReportItemViewHolder.newInstance(parent, mCallbacks, mContext, mDailyReportDetail, mDailyReportImages);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final DailyReportDetail item = mDailyReportDetail.get(position);
        ((ReportItemViewHolder) holder).bind((DailyReportDetail) item);
    }

    @Override
    public int getItemCount() {
        return  mDailyReportDetail.size();
    }

}
