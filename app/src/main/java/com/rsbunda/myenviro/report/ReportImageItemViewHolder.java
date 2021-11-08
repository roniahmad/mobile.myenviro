package com.rsbunda.myenviro.report;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.io.model.cleaning.DailyReportImages;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.List;

public class ReportImageItemViewHolder extends RecyclerView.ViewHolder{

    private final TextView mImageTimeHint;
    private final ImageView mImgReport;
    private final Context mContext;

    private final Callbacks mCallbacks;

    @Nullable
    private DailyReportImages model;

    private List<DailyReportImages> mListDailyReport;

    public ReportImageItemViewHolder(View itemView, Callbacks callbacks, Context context, List<DailyReportImages> dailies) {
        super(itemView);

        this.mContext = context;
        this.mCallbacks = callbacks;
        this.mListDailyReport = dailies;

        mImgReport = (ImageView) itemView.findViewById(R.id.image_report);
        mImageTimeHint = (TextView) itemView.findViewById(R.id.report_image_time_hint);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCallbacks==null || mListDailyReport ==null){
                    return;
                }
                mCallbacks.onReportImageClicked(model);
            }
        });

        mImgReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCallbacks==null || mListDailyReport ==null){
                    return;
                }
                mCallbacks.onReportImageClicked(model);
            }
        });

    }

    public static ReportImageItemViewHolder newInstance(ViewGroup parent, Callbacks callbacks,
                                                        Context ctx, List<DailyReportImages> dailies){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.report_image_item, parent, false);
        return new ReportImageItemViewHolder(itemView, callbacks, ctx, dailies);
    }

    public void bind(@NonNull DailyReportImages item){
        model = item;

        final String start = new SimpleDateFormat("dd MMM yyyy").format(model !=null ? model.getTglCapture():"");
        final String end = new SimpleDateFormat("HH:mm:ss").format(model !=null ? model.getJamCapture():"");

        final String timeHint = start + " " + end;
        mImageTimeHint.setText(timeHint);

        final String reportImage = (StringUtils.isEmpty(model.getFilename())) ? StringUtils.EMPTY : model.getFilename() ;

        if(reportImage == StringUtils.EMPTY){
            int res = R.drawable.ic_envihero;
            mImgReport.setImageResource(res);
        } else {
            String path = "http://192.168.1.138:8006";
            Glide.with(mContext).load(path+reportImage).into(mImgReport);
        }

    }

    public interface Callbacks {
        void onReportImageClicked(DailyReportImages daily);
    }

}
