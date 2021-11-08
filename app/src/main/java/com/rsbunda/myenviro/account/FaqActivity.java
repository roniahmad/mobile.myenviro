package com.rsbunda.myenviro.account;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.constant.AccountDetailConstant;
import com.rsbunda.myenviro.ui.BaseActivity;

public class FaqActivity extends BaseActivity {
    private WebView mWebView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq_activity);

        setToolbarAsUp(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.finishAfterTransition(FaqActivity.this);
            }
        });

        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar.setProgress(0);
        mProgressBar.setMax(100);

        mWebView = (WebView) findViewById(R.id.faq_web);

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){
                // Do something on page loading started
                // Visible the progressbar
                mProgressBar.setVisibility(View.VISIBLE);
            }

        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress){
                // Update the progress bar with page loading progress
                mProgressBar.setProgress(newProgress);
                if(newProgress == 100){
                    // Hide the progressbar
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        });
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.loadUrl(AccountDetailConstant.FAQ_LINK);

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public Intent getParentActivityIntent() {
        return new Intent(this, AccountActivity.class);
    }

    public static void startFaqActivity(final Activity activity) {
        Intent faqIntent = new Intent(activity,
                FaqActivity.class);
        activity.startActivity(faqIntent);
    }

}
