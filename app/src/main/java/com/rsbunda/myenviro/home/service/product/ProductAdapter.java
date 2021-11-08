package com.rsbunda.myenviro.home.service.product;

import android.content.Context;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.rsbunda.myenviro.io.model.layanan.ProductLayanan;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ProductLayanan> mListProduct;
    private Context mContext;
    private ProductItemViewHolder.Callbacks mProductCallbacks;

    public ProductAdapter(Context ctx, List<ProductLayanan> products, ProductItemViewHolder.Callbacks callbacks){
        this.mContext = ctx;
        this.mListProduct = products;
        this.mProductCallbacks = callbacks;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        return ProductItemViewHolder.newInstance(parent, mProductCallbacks, mContext, mListProduct);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ProductLayanan item = mListProduct.get(position);
        ((ProductItemViewHolder) holder).bind((ProductLayanan) item);
    }

    @Override
    public int getItemCount() {
        return mListProduct.size();
    }

}
