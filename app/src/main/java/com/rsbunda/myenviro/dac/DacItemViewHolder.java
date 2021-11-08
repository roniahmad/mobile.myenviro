package com.rsbunda.myenviro.dac;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.io.model.activity.Daily;
import com.rsbunda.myenviro.util.TimeUtils;
import com.rsbunda.myenviro.util.UIUtils;

import java.text.SimpleDateFormat;
import java.util.List;

public class DacItemViewHolder extends RecyclerView.ViewHolder{

    private final TextView mTaskStart, mTaskEnd, mTaskDesc, mTaskDuration;
    private final Context mContext;

    private final Callbacks mCallbacks;

    @Nullable
    private Daily mDaily;

    private List<Daily> mListDaily;

    public DacItemViewHolder(View itemView, Callbacks callbacks, Context context, List<Daily> dailies) {
        super(itemView);

        this.mContext = context;
        this.mCallbacks = callbacks;
        this.mListDaily = dailies;

        mTaskStart = (TextView) itemView.findViewById(R.id.dac_start);
        mTaskEnd = (TextView) itemView.findViewById(R.id.dac_end);
        mTaskDesc = (TextView) itemView.findViewById(R.id.dac_task);
        mTaskDuration = (TextView) itemView.findViewById(R.id.dac_duration);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCallbacks==null || mListDaily ==null){
                    return;
                }
                mCallbacks.onDailyClicked(mDaily);
            }
        });
    }

    public static DacItemViewHolder newInstance(ViewGroup parent, Callbacks callbacks,
                                                Context ctx, List<Daily> dailies){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.dac_item, parent, false);
        return new DacItemViewHolder(itemView, callbacks, ctx, dailies);
    }

    public void bind(@NonNull Daily item){
        mDaily = item;
        final String mStart = new SimpleDateFormat("HH:mm:ss").format(mDaily!=null ? mDaily.getStart():"");
        final String mEnd = new SimpleDateFormat("HH:mm:ss").format(mDaily!=null ? mDaily.getEnd():"");
        final String duration = TimeUtils.formatDuration(mContext, mDaily.getStart().getTime(), mDaily.getEnd().getTime());

        mTaskStart.setText(mStart);
        mTaskEnd.setText(mEnd);
        mTaskDuration.setText(duration);

        UIUtils.setTextMaybeHtml(mTaskDesc, mDaily.getJob());

    }

    public interface Callbacks {
        void onDailyClicked(Daily daily);
    }


}
