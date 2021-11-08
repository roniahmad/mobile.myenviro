package com.rsbunda.myenviro.report;

import static com.rsbunda.myenviro.util.LogUtils.makeLogTag;

import android.os.Bundle;
import android.view.View;

import androidx.core.app.ActivityCompat;

import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.ui.BaseActivity;

public class ReportActivity extends BaseActivity {

    private static final String TAG = makeLogTag(ReportActivity.class);

    private static final String SCREEN_LABEL = "REPORT";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_activity);

        setToolbarAsUp(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.finishAfterTransition(ReportActivity.this);
            }
        });

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }

}
