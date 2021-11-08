package com.rsbunda.myenviro.intro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.home.HomeActivity;
import com.rsbunda.myenviro.welcome.WelcomeActivity;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

public class SplashActivity extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/productsans_regular.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

        setContentView(R.layout.splash_activity);

        // Check if the EULA has been accepted; if not, show it.
        if (WelcomeActivity.shouldDisplay(this)) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                Intent splash = new Intent(SplashActivity.this, HomeActivity.class);
                splash.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                splash.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(splash);

                finish();
            }
        }, SPLASH_TIME_OUT);

    }
}
