package com.rsbunda.myenviro;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import static com.rsbunda.myenviro.util.LogUtils.makeLogTag;

public class AppApplication extends AppCompatActivity {

    private static final String TAG = makeLogTag(AppApplication.class);

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }

}
