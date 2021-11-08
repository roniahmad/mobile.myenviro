package com.rsbunda.myenviro.dac;


import static com.rsbunda.myenviro.util.LogUtils.makeLogTag;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.dashboard.DashboardActivity;
import com.rsbunda.myenviro.ui.BaseActivity;


public class DacActivity extends BaseActivity {

    private static final String TAG = makeLogTag(DacActivity.class);

    private static final String SCREEN_LABEL = "DacActivity";

    private Toolbar mToolbar;

    private TextView mToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dac_activity);
        setToolbarAsUp(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.finishAfterTransition(DacActivity.this);
            }
        });

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void setToolbarTitle(String title){
        mToolbar = getToolbar();
        if(mToolbar!= null){
            mToolbarTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
            if (mToolbarTitle != null) {
                mToolbarTitle.setText(title);
            }
        }
    }

    @Override
    public Intent getParentActivityIntent() {
        return new Intent(this, DashboardActivity.class);
    }

}
