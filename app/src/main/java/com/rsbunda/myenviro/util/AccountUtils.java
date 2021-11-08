package com.rsbunda.myenviro.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import android.app.DialogFragment;

import com.rsbunda.myenviro.R;

public class AccountUtils {

    public static void showRegistrationDialog(Activity activity) {
        FragmentManager fm = activity.getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog_registration");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        new AccountUtils.RegistrationDialog().show(ft, "dialog_registration");
    }

    public static class RegistrationDialog extends DialogFragment {

        protected View mView;

        public RegistrationDialog() {}

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            mView = getActivity().getLayoutInflater().inflate(R.layout.register_account_info_layout, null);

            TextView mInfoContent = (TextView) mView.findViewById(R.id.info_content);

            UIUtils.setTextMaybeHtml(mInfoContent, getString(R.string.how_to_register_new_account_content));

            return new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.welcome_to_myenviro)
                    .setView(mView)
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
