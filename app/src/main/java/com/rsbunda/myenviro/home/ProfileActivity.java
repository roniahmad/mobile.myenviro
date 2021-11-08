package com.rsbunda.myenviro.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.ui.BaseActivity;
import com.rsbunda.myenviro.util.LoginUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity  extends BaseActivity {

    private LinearLayout mBarcodeContainer;
    private LinearLayout mQrcodeContainer;

    private final int QR_WIDTH = 400;
    private final int QR_HEIGHT = 400;

    private final int BAR_WIDTH = 600;
    private final int BAR_HEIGHT = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.profile_activity);

        mBarcodeContainer = (LinearLayout) findViewById(R.id.barcode_container);
        mQrcodeContainer = (LinearLayout) findViewById(R.id.qrcode_container);

        //create barcode image
        LinearLayout l = new LinearLayout(this);
        l.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        l.setOrientation(LinearLayout.VERTICAL);

        // barcode data
        final String idrm = LoginUtils.getHeroNip(this);
        final String org = LoginUtils.getOrganizationId(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("idrm", idrm);
            jsonObject.put("org", org);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String message = jsonObject.toString();


        // barcode image
        Bitmap bitmap = null;
        ImageView iv = new ImageView(this);

        try {
            bitmap = encodeAsBitmap(idrm, BarcodeFormat.CODE_128, BAR_WIDTH, BAR_HEIGHT);
            iv.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        l.addView(iv);

        //barcode text

        TextView tvIdrm = new TextView(this);
        tvIdrm.setGravity(Gravity.CENTER_HORIZONTAL);
        tvIdrm.setText(idrm);

        l.addView(tvIdrm);

        mBarcodeContainer.addView(l);

        //create qrcode
        LinearLayout q = new LinearLayout(this);
        q.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        q.setOrientation(LinearLayout.VERTICAL);

        ImageView ivQr = new ImageView(this);
        MultiFormatWriter multiFormatWriter =
                new MultiFormatWriter();
        try {
            // Create new configuration that specifies the error correction
            Map<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

            BitMatrix bitMatrix = multiFormatWriter.encode(message, BarcodeFormat.
                    QR_CODE,QR_WIDTH,QR_HEIGHT, hints);

            BarcodeEncoder barcodeEncoder =
                    new BarcodeEncoder();
            Bitmap bitmapQr = barcodeEncoder.createBitmap(bitMatrix);

            ivQr.setImageBitmap(bitmapQr);

            q.addView(ivQr);

            TextView qrIdrm = new TextView(this);
            qrIdrm.setGravity(Gravity.CENTER_HORIZONTAL);
            qrIdrm.setText(idrm);

            q.addView(qrIdrm);

            mQrcodeContainer.addView(q);

        }
        catch (WriterException e) {
            e.printStackTrace();
        }

        setToolbarAsUp(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.finishAfterTransition(ProfileActivity.this);
            }
        });

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }

    /**************************************************************
     * getting from com.google.zxing.client.android.encode.QRCodeEncoder
     *
     * See the sites below
     * http://code.google.com/p/zxing/
     * http://code.google.com/p/zxing/source/browse/trunk/android/src/com/google/zxing/client/android/encode/EncodeActivity.java
     * http://code.google.com/p/zxing/source/browse/trunk/android/src/com/google/zxing/client/android/encode/QRCodeEncoder.java
     */

    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;

    Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
        String contentsToEncode = contents;
        if (contentsToEncode == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contentsToEncode);
        if (encoding != null) {
            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }

    @Override
    public Intent getParentActivityIntent() {
        return new Intent(this, HomeActivity.class);
    }

    public static void startProfileActivity(final Activity activity) {
        Intent aboutIntent = new Intent(activity,
                ProfileActivity.class);
        activity.startActivity(aboutIntent);
    }
}
