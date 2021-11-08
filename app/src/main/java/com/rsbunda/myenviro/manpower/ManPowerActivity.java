package com.rsbunda.myenviro.manpower;

import static com.rsbunda.myenviro.util.LogUtils.makeLogTag;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.core.app.ActivityCompat;

import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.dashboard.DashboardActivity;
import com.rsbunda.myenviro.ui.BaseActivity;

public class ManPowerActivity extends BaseActivity {

    private static final String TAG = makeLogTag(ManPowerActivity.class);

    private static final String SCREEN_LABEL = "ManPowerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manpower_activity);
        setToolbarAsUp(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.finishAfterTransition(ManPowerActivity.this);
            }
        });

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public Intent getParentActivityIntent() {
        return new Intent(this, DashboardActivity.class);
    }
}
