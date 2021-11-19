package com.rsbunda.myenviro.manpower;

import android.content.Context;
import android.text.TextUtils;
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
import com.rsbunda.myenviro.io.model.sales.ManPowerDetil;
import com.rsbunda.myenviro.util.LoginUtils;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class ManPowerItemViewHolder extends RecyclerView.ViewHolder{

    private final TextView mHeroName, mHeroCode, mHeroJabatan;
    private final ImageView mImgHero;
    private final Context mContext;

    private final Callbacks mCallbacks;

    @Nullable
    private ManPowerDetil mManPower;

    private List<ManPowerDetil> mListMP;

    public ManPowerItemViewHolder(View itemView, Callbacks callbacks, Context context, List<ManPowerDetil> jos) {
        super(itemView);

        this.mContext = context;
        this.mCallbacks = callbacks;
        this.mListMP = jos;

        mHeroName = (TextView) itemView.findViewById(R.id.hero_name);
        mHeroCode = (TextView) itemView.findViewById(R.id.hero_nip);
        mHeroJabatan = (TextView) itemView.findViewById(R.id.hero_jabatan);
        mImgHero = (ImageView) itemView.findViewById(R.id.img_hero);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCallbacks==null || mListMP ==null){
                    return;
                }
                mCallbacks.onTeamsClicked(mManPower);
            }
        });
    }

    public static ManPowerItemViewHolder newInstance(ViewGroup parent, Callbacks callbacks,
                                                     Context ctx, List<ManPowerDetil> jos){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.manpower_item, parent, false);
        return new ManPowerItemViewHolder(itemView, callbacks, ctx, jos);
    }

    public void bind(@NonNull ManPowerDetil item){
        mManPower = item;

        mHeroName.setText(mManPower.getName());
        final String nip = String.format(mContext.getString(R.string.your_nip), mManPower.getNip());
        mHeroCode.setText(nip);
//        final String jabatan = String.format(mContext.getString(R.string.your_jabatan), mJmpd.getJabatan());
//        mHeroJabatan.setText(jabatan);
        mHeroJabatan.setText(mManPower.getJabatan());

        final String avatar = (StringUtils.isEmpty(mManPower.getAvatar())) ? "" : mManPower.getAvatar() ;

        if(TextUtils.isEmpty(avatar)){
            int res = R.drawable.anonymous;
            mImgHero.setImageResource(res);

        } else {
            final String baseUrl = LoginUtils.getBaseUrl(mContext);
            Glide.with(mContext).load(baseUrl + avatar).into(mImgHero);
        }
    }

    public interface Callbacks {
        void onTeamsClicked(ManPowerDetil jmpd);
    }


}
