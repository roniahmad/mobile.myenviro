package com.rsbunda.myenviro.feedback;

import android.content.Intent;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import android.view.View;

import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.dashboard.DashboardActivity;
import com.rsbunda.myenviro.ui.BaseActivity;

import static com.rsbunda.myenviro.util.LogUtils.makeLogTag;

public class FeedbackActivity  extends BaseActivity {
    private static final String TAG = makeLogTag(FeedbackActivity.class);

    private static final String SCREEN_LABEL = "Feedback";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_activity);
        setToolbarAsUp(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.finishAfterTransition(FeedbackActivity.this);
            }
        });

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public Intent getParentActivityIntent() {
        return new Intent(this, DashboardActivity.class);
    }

}
