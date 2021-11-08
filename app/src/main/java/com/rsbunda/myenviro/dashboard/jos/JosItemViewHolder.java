package com.rsbunda.myenviro.dashboard.jos;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.io.model.sales.Jos;
import com.rsbunda.myenviro.util.TimeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JosItemViewHolder extends RecyclerView.ViewHolder{

    private final TextView mClientName, mClientCode, mJosNumber, mJosDesc;
    private final TextView mClientStart, mClientEnd;
    private final Context mContext;

    private final Callbacks mCallbacks;

    private final LinearLayout mQrcodeContainer;

    //QR Code
    private final int QR_WIDTH = 200;
    private final int QR_HEIGHT = 200;

    @Nullable
    private Jos mJos;

    private List<Jos> mListJos;

    public JosItemViewHolder(View itemView, JosItemViewHolder.Callbacks callbacks, Context context, List<Jos> jos) {
        super(itemView);

        this.mContext = context;
        this.mCallbacks = callbacks;
        this.mListJos = jos;

        mClientName = (TextView) itemView.findViewById(R.id.client_name);
        mClientCode = (TextView) itemView.findViewById(R.id.client_code);
        mJosNumber = (TextView) itemView.findViewById(R.id.client_jos_number);
        mJosDesc = (TextView) itemView.findViewById(R.id.client_jos_desc);

        mClientStart = (TextView) itemView.findViewById(R.id.client_jos_start_date);
        mClientEnd = (TextView) itemView.findViewById(R.id.client_jos_end_date);

        mQrcodeContainer = (LinearLayout) itemView.findViewById(R.id.qrcode_container);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCallbacks==null || mListJos==null){
                    return;
                }
                mCallbacks.onJosClicked(mJos);
            }
        });

    }

    public static JosItemViewHolder newInstance(ViewGroup parent, JosItemViewHolder.Callbacks callbacks,
                                                    Context ctx, List<Jos> jos){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.jos_item, parent, false);
        return new JosItemViewHolder(itemView, callbacks, ctx, jos);
    }

    public void bind(@NonNull Jos item){
        mJos = item;
        final String clienCode = mJos.getClientCode();
        mClientName.setText(mJos.getClientName());
        mClientCode.setText(clienCode);
        mJosNumber.setText(mJos.getJosNumber());
        mJosDesc.setText(mJos.getScopeOfWork());

        String dateS = TimeUtils.formatShortDate(mContext, mJos.getStartDate());
        mClientStart.setText(dateS);

        String dateE = TimeUtils.formatShortDate(mContext, mJos.getEndDate());
        mClientEnd.setText(dateE);

        //create qrcode
        ImageView ivQr = new ImageView(mContext);
        LinearLayout.LayoutParams ivparam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ivparam.setMargins(0,0,0,0);
        ivQr.setLayoutParams(ivparam);
        MultiFormatWriter multiFormatWriter =
                new MultiFormatWriter();
        try {
            // Create new configuration that specifies the error correction
            Map<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

            BitMatrix bitMatrix = multiFormatWriter.encode(clienCode, BarcodeFormat.
                    QR_CODE,QR_WIDTH,QR_HEIGHT, hints);

            BarcodeEncoder barcodeEncoder =
                    new BarcodeEncoder();
            Bitmap bitmapQr = barcodeEncoder.createBitmap(bitMatrix);

            ivQr.setImageBitmap(bitmapQr);

            mQrcodeContainer.addView(ivQr);

        }
        catch (WriterException e) {
            e.printStackTrace();
        }

    }

    public interface Callbacks {
        void onJosClicked(Jos jos);
    }

}
