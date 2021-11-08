package com.rsbunda.myenviro.home.service;

import static com.rsbunda.myenviro.util.LogUtils.makeLogTag;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.home.DividerDecoration;
import com.rsbunda.myenviro.home.service.product.ProductAdapter;
import com.rsbunda.myenviro.home.service.product.ProductItemViewHolder;
import com.rsbunda.myenviro.io.model.layanan.Layanan;
import com.rsbunda.myenviro.io.model.layanan.ProductLayanan;
import com.rsbunda.myenviro.ui.widget.RaviDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class ServiceItemViewHolder extends RecyclerView.ViewHolder
    implements DividerDecoration.Divided , ProductItemViewHolder.Callbacks {

    private static final String TAG = makeLogTag(ServiceItemViewHolder.class);

    private final ImageView mImgService;
    private final TextView mTxtServiceName;
    private final TextView mTxtServiceDescription;
    private final ImageButton mBtnMore;
    private final Callbacks mCallbacks;

    private final Context mContext;

    private final RecyclerView mRecyclerViewProduct;

    private List<ProductLayanan> mProductByIdOnly;
    private List<ProductLayanan> mListProducts;

    private ProductAdapter mCertainProductAdapter;

    @Nullable
    private Layanan mLayanan;

    public ServiceItemViewHolder(View itemView, Callbacks callbacks, Context context, List<ProductLayanan> mProducts){
        super(itemView);

        mCallbacks = callbacks;
        mContext = context;
        mListProducts = mProducts;

        mImgService = (ImageView) itemView.findViewById(R.id.service_image);
        mTxtServiceName = (TextView) itemView.findViewById(R.id.service_name);
        mTxtServiceDescription = (TextView) itemView.findViewById(R.id.service_description);
        mBtnMore = (ImageButton) itemView.findViewById(R.id.readme_link);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCallbacks==null || mLayanan==null){
                    return;
                }
                mCallbacks.onServicesClicked(mLayanan);
            }
        });

        mRecyclerViewProduct = (RecyclerView) itemView.findViewById(R.id.list_service);

    }

    public static ServiceItemViewHolder newInstance(ViewGroup parent, Callbacks callbacks, Context context, List<ProductLayanan> list){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.service_item, parent, false);
        return new ServiceItemViewHolder(itemView, callbacks, context, list);
    }

    public void bind(@NonNull Layanan item){
        mLayanan = item;
        mTxtServiceName.setText(mLayanan.getNama());
        mTxtServiceDescription.setText(mLayanan.getDeskripsi());

        final String imgSrv = (mLayanan.getGambar() != null) ? mLayanan.getGambar() : "";
        if (TextUtils.isEmpty(imgSrv)){
            int resImg = R.drawable.ic_envihero;
            mImgService.setImageResource(resImg);
        }else {
            Glide.with(mContext).load(imgSrv).into(mImgService);
        }

        mProductByIdOnly = new ArrayList<>();

        int serviceId = mLayanan.getId();

        if(mListProducts.size()>0){
            for (int i = 0; i< mListProducts.size(); i++){
                int currId = mListProducts.get(i).getIdJenisLayanan();
                if(currId==serviceId){
                    mProductByIdOnly.add(mListProducts.get(i));
                }
            }

        }
        mCertainProductAdapter = new ProductAdapter(mContext, mProductByIdOnly, this.getCallbacks());

        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);

        mRecyclerViewProduct.setLayoutManager(mLayoutManager);
        mRecyclerViewProduct.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewProduct.addItemDecoration(new RaviDividerItemDecoration(mContext,
                LinearLayoutManager.HORIZONTAL, 16));

        mRecyclerViewProduct.setAdapter(mCertainProductAdapter);
    }

    private ProductItemViewHolder.Callbacks getCallbacks(){
        return (ProductItemViewHolder.Callbacks) this;
    }

    @Override
    public void onProductClicked(ProductLayanan product) {

    }

    @Override
    public void onProductShare(ProductLayanan product) {

    }

    @Override
    public void onProductOffer(ProductLayanan product) {

    }

    public interface Callbacks {
        void onServicesClicked(Layanan service);
    }

}
