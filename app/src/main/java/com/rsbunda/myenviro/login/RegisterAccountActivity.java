package com.rsbunda.myenviro.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.ui.BaseActivity;
import static com.rsbunda.myenviro.util.LogUtils.makeLogTag;

public class RegisterAccountActivity extends BaseActivity{

    private static final String TAG = makeLogTag(RegisterAccountActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_account_activity);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public static void startRegisterAccountActivity(final Context activity) {
        Intent registerAccountIntent = new Intent(activity,
                RegisterAccountActivity.class);
        registerAccountIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(registerAccountIntent);
    }

}