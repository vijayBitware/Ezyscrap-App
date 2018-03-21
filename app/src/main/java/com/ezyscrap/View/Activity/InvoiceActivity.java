package com.ezyscrap.View.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.ezyscrap.AppUtils.SharedPref;
import com.ezyscrap.R;
import com.ezyscrap.webservice.AppConstants;

public class InvoiceActivity extends AppCompatActivity {

    ImageView imgBack;
    WebView webView;
    SharedPref pref;
    Bundle bundle;
    String scrapId;
    ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        init();

    }

    private void init() {
        pref = new SharedPref(this);
        Intent i = getIntent();
        imgBack = findViewById(R.id.imgBack);
        webView = (WebView) findViewById(R.id.webView);
        scrapId = i.getStringExtra("scrapId");
        dialog = new ProgressDialog(InvoiceActivity.this);
        dialog.setMessage("Loading..");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();

        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                dialog.dismiss();
                super.onPageFinished(view, url);

            }

        });

        webView.loadUrl(AppConstants.BASE_URL + "pdf_invoice/" + SharedPref.getPreferences().getString(SharedPref.TOKEN, "")
                + "/" + SharedPref.getPreferences().getString(SharedPref.USER_FLAG, "")
                + "/" + scrapId);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
