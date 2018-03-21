package com.ezyscrap.View.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ezyscrap.AppUtils.SharedPref;
import com.ezyscrap.R;
import com.ezyscrap.webservice.AppConstants;

public class PrivacyPolicyActivity extends AppCompatActivity {

    ImageView imgBack;
    WebView webView;
    ProgressDialog dialog;
    RelativeLayout rl_topBar;
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        init();
        Log.e("TAG","Flag >"+AppConstants.USER_FLAG);
        if (sharedPref.getString(SharedPref.USER_FLAG, "").equalsIgnoreCase("Seller")) {
            rl_topBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else if (sharedPref.getString(SharedPref.USER_FLAG, "").equalsIgnoreCase("Buyer")) {
            rl_topBar.setBackgroundColor(getResources().getColor(R.color.blue));
        }
    }

    private void init() {
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rl_topBar = findViewById(R.id.rl_topBar);
        sharedPref = new SharedPref(PrivacyPolicyActivity.this);
        webView = findViewById(R.id.webView);
        String url = "http://ezyscrap.in/privacy-policy.html";
        dialog =new ProgressDialog(PrivacyPolicyActivity.this);
        dialog.setMessage("Loading..");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();

        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {}

            @Override
            public void onPageFinished(WebView view, String url) {
                dialog.dismiss();
                super.onPageFinished(view,url);

            }

        });

        webView.loadUrl(url);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
    }
}
