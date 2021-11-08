package com.rsbunda.myenviro.home;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.util.LoginUtils;

import java.util.HashMap;
import java.util.Map;

public class QrCodeDialog extends DialogFragment {

    private ImageView mPxCode;
    private TextView mHospitalName;
    private TextView mHospitalAddress;

    private static Context mContext;

    private final int WIDTH = 500;
    private final int HEIGHT = 500;

    public static QrCodeDialog newInstance (String qrcode, Context context){
        QrCodeDialog dialog = new QrCodeDialog();

        Bundle args = new Bundle();
        args.putString(HomeConstant.QRCODE, qrcode);
        dialog.setArguments(args);

        mContext = context;
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View v = inflater.inflate(R.layout.qrcode_fragment, null);
        builder.setView(v);
        final String hospitalName = LoginUtils.getOrganizationName(mContext);
        final String hostAddress = LoginUtils.getOrganizationUnitAddress(mContext) +
                " " + LoginUtils.getOrganizationUnitCity(mContext);

        mHospitalName = (TextView) v.findViewById(R.id.hospital_name);
        mHospitalAddress = (TextView) v.findViewById(R.id.hospital_addres);
        mPxCode = (ImageView) v.findViewById(R.id.pxcode);

        mHospitalName.setText(hospitalName);
        mHospitalAddress.setText(hostAddress);

        String pxcode = getArguments().getString(HomeConstant.QRCODE);

        MultiFormatWriter multiFormatWriter =
                new MultiFormatWriter();
        try {
            // Create new configuration that specifies the error correction
            Map<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

            BitMatrix bitMatrix = multiFormatWriter.encode(pxcode, BarcodeFormat.
                    QR_CODE,WIDTH,HEIGHT, hints);

            BarcodeEncoder barcodeEncoder =
                    new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

            mPxCode.setImageBitmap(bitmap);
        }
        catch (WriterException e) {
            e.printStackTrace();
        }

        return builder.create();
    }
}
