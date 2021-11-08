package com.rsbunda.myenviro.account;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.rsbunda.myenviro.R;
import com.rsbunda.myenviro.constant.AccountDetailConstant;
import com.rsbunda.myenviro.ui.BaseActivity;

public class TosActivity extends BaseActivity {

    private WebView mWebView;
    private ProgressBar mProgressBar;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tos_activity);

        setToolbarAsUp(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.finishAfterTransition(TosActivity.this);
            }
        });

        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar.setMax(100);

        mWebView = (WebView) findViewById(R.id.tos_web);

        mWebView.setWebViewClient(new WebViewClientTos());

        mWebView.setWebChromeClient(new WebChromeClientTos());

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.loadUrl(AccountDetailConstant.TOS_LINK);

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private class WebViewClientTos extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mProgressBar.setVisibility(View.GONE);
            mProgressBar.setProgress(100);
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.setProgress(0);
        }
    }

    private class WebChromeClientTos extends WebChromeClient {
        public void onProgressChanged(WebView view, int progress) {
            mProgressBar.setProgress(progress);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        else {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public Intent getParentActivityIntent() {
        return new Intent(this, AccountActivity.class);
    }

    public static void startTosActivity(final Activity activity) {
        Intent tosIntent = new Intent(activity,
                TosActivity.class);
        activity.startActivity(tosIntent);
    }

}
