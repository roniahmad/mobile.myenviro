package com.rsbunda.myenviro.about;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.account.AccountActivity;
import com.rsbunda.myenviro.ui.BaseActivity;
import com.rsbunda.myenviro.util.BulletTextUtils;
import com.rsbunda.myenviro.util.UIUtils;

public class AboutActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);

        View mView = findViewById(R.id.about_container);

        TextView mTxtVisionContent = (TextView) mView.findViewById(R.id.about_vision_content);
        UIUtils.setTextMaybeHtml(mTxtVisionContent, getString(R.string.vision_content));

        TextView mTxtMisionContent = (TextView) mView.findViewById(R.id.about_mission_content);
        CharSequence bulletedList = BulletTextUtils.makeBulletList(10, getString(R.string.mission_content_2),
                getString(R.string.mission_content_3),
                getString(R.string.mission_content_4));

        mTxtMisionContent.setText(bulletedList);

        TextView mTxtValuesContent = (TextView) mView.findViewById(R.id.about_values_content);
        UIUtils.setTextMaybeHtml(mTxtValuesContent, getString(R.string.values_content));

        TextView mTxtMottoContent = (TextView) mView.findViewById(R.id.about_motto_content);
        UIUtils.setTextMaybeHtml(mTxtMottoContent, getString(R.string.motto_content));

        setToolbarAsUp(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.finishAfterTransition(AboutActivity.this);
            }
        });

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public Intent getParentActivityIntent() {
        return new Intent(this, AccountActivity.class);
    }

    public static void startAboutActivity(final Activity activity) {
        Intent aboutIntent = new Intent(activity,
                AboutActivity.class);
        activity.startActivity(aboutIntent);
    }

}
