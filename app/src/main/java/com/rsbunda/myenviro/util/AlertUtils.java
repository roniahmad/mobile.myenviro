package com.rsbunda.myenviro.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rsbunda.myenviro.R;

public class AlertUtils {

    public static final String SAD = "SAD";

    public static final String SMILE = "SMILE";

    public static final String HAPPY = "HAPPY";

    public static void messageDialog(final Context ctx,
                                     final String title, final String message,
                                     final String emotion){

        final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        final LayoutInflater li = LayoutInflater.from(ctx);
        final View v = li.inflate(R.layout.alert_layout, null);

        TextView txtHeader = (TextView) v.findViewById(R.id.tv_header);
        UIUtils.setTextMaybeHtml(txtHeader, title);
        txtHeader.setTextColor(ctx.getResources().getColor( R.color.colorPrimaryDark));

        ImageView imgHeader = (ImageView) v.findViewById(R.id.img_message);
        if(emotion.equals(SAD)){
            imgHeader.setImageDrawable(ctx.getDrawable(R.drawable.ic_envihero));
            txtHeader.setTextColor(ctx.getResources().getColor( R.color.colorAccent));
        }else if (emotion.equals(SMILE)){
            imgHeader.setImageDrawable(ctx.getDrawable(R.drawable.ic_envihero));
        }else if(emotion.equals(HAPPY)){
            imgHeader.setImageDrawable(ctx.getDrawable(R.drawable.ic_envihero));
        }else{
            imgHeader.setImageDrawable(ctx.getDrawable(R.drawable.ic_envihero));
        }

        TextView txtContent = (TextView) v.findViewById(R.id.tv_content);
        UIUtils.setTextMaybeHtml(txtContent, message);

        builder.setView(v);
//        builder.setTitle(ctx.getString(R.string.information));
        builder.setCancelable(true);
        builder.setPositiveButton(ctx.getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    public static void infoDialog(final Context ctx,
                                     final String title, final String message){

        final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

        builder.setTitle(title);

        builder.setMessage(Html.fromHtml(message));

        builder.setCancelable(false);

        builder.setPositiveButton(ctx.getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();

    }
}
