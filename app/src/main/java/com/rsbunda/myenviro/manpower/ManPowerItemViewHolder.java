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

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        ArrayList<String> artist = new ArrayList<String>();
        artist.add("https://akcdn.detik.net.id/community/media/visual/2020/06/23/f549e4a0-20dd-4d57-8d86-fecabe2aabb6_ori.jpeg");
        artist.add("https://awsimages.detik.net.id/community/media/visual/2018/05/07/699f714d-231e-4632-8be7-2181a7622587_43.jpeg");
        artist.add("https://sahabatsicepat.com/file/2021/01/wweer.jpg");
        artist.add("https://assets.pikiran-rakyat.com/crop/0x0:0x0/x/photo/2020/09/25/1850053671.jpg");
        artist.add("https://img.inews.co.id/media/822/files/inews_new/2021/06/24/24__Manganang1_anyar.jpg");

        final String avatar = (StringUtils.isEmpty(mManPower.getAvatar())) ? "" : mManPower.getAvatar() ;

        if(TextUtils.isEmpty(avatar)){
//            int res = R.drawable.ic_envihero;
//            mImgHero.setImageResource(res);
            Random rand = new Random();
            int pos = rand.nextInt(4);

            Glide.with(mContext).load(artist.get(pos)).into(mImgHero);

        } else {
            Glide.with(mContext).load(avatar).into(mImgHero);
        }
    }

    public interface Callbacks {
        void onTeamsClicked(ManPowerDetil jmpd);
    }


}
