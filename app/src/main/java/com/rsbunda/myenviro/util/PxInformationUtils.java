package com.rsbunda.myenviro.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.webkit.WebView;

import com.rsbunda.myenviro.R;

public class PxInformationUtils {

    private static final String DIALOG_PXINFO = "dialog_pxinfo";

    public static void showPxInfo(Activity activity){
        FragmentManager fm = activity.getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag(DIALOG_PXINFO);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        new PxInfoDialog().show(ft, DIALOG_PXINFO);
    }

    public static class PxInfoDialog extends DialogFragment {

        public PxInfoDialog() {
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            WebView webView = new WebView(getActivity());
            webView.loadUrl("file:///android_asset/pxfiles.html");

            return new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.title_information)
                    .setView(webView)
                    .setPositiveButton(R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.dismiss();
                                }
                            }
                    )
                    .create();
        }
    }
}
