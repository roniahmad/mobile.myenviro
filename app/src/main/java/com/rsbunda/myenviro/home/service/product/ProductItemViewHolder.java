package com.rsbunda.myenviro.home.service.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.io.model.layanan.ProductLayanan;

import java.util.List;

public class ProductItemViewHolder extends RecyclerView.ViewHolder {

    private final TextView mProductName, mProductCp, mEmailCp, mPhoneCp;
    private final ImageView mBtnMore;
    private final Button mBtnOffer;
    private final Context mContext;

    private final Callbacks mCallbacks;

    @Nullable
    private ProductLayanan mProduct;

    private List<ProductLayanan> mListProduct;

    private ProductAdapter mAdapter;

    public ProductItemViewHolder(View itemView, Callbacks callbacks, Context context, List<ProductLayanan> products){

        super(itemView);

        mCallbacks = callbacks;
        mContext = context;
        mListProduct = products;
        mProductName = (TextView) itemView.findViewById(R.id.product_name);
        mProductCp = (TextView) itemView.findViewById(R.id.product_cp);
        mEmailCp = (TextView) itemView.findViewById(R.id.email_cp);
        mPhoneCp = (TextView) itemView.findViewById(R.id.phone_cp);
        mBtnMore = (ImageView) itemView.findViewById(R.id.btn_more);
        mBtnOffer = (Button) itemView.findViewById(R.id.offer_button);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCallbacks==null || mProduct==null){
                    return;
                }
                mCallbacks.onProductClicked(mProduct);
            }
        });

        mBtnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCallbacks==null || mProduct==null){
                    return;
                }

                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(mContext, mBtnMore);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.menu_product, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.schedule_share:
                                mCallbacks.onProductShare(mProduct);
                                return true;
                            default:
                                return false;
                        }
                    }
                });

                popup.show();//showing popup menu

            }
        });

        mBtnOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCallbacks==null || mProduct==null){
                    return;
                }

                mCallbacks.onProductOffer(mProduct);
            }
        });
    }

    public static ProductItemViewHolder newInstance(ViewGroup parent, Callbacks callbacks,
                                                    Context ctx, List<ProductLayanan> products){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.product_item, parent, false);
        return new ProductItemViewHolder(itemView, callbacks, ctx, products);
    }

    public void bind(@NonNull ProductLayanan item){
        mProduct = item;
        mProductName.setText(mProduct.getNama());
        mPhoneCp.setText(mProduct.getTelp());
        mProductCp.setText(mProduct.getNarahubung());
        mEmailCp.setText(mProduct.getEmail());
    }

    public ProductItemViewHolder.Callbacks getCallbacks(){
        return (ProductItemViewHolder.Callbacks) this;
    }

    public interface Callbacks {
        void onProductClicked(ProductLayanan product);
        void onProductShare(ProductLayanan product);
        void onProductOffer(ProductLayanan product);
    }
}
