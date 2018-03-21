package com.ezyscrap.View.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ezyscrap.AppUtils.SharedPref;
import com.ezyscrap.R;

public class ContactUsActivity extends AppCompatActivity {

    ImageView imgBack;
    WebView webView;
    ProgressDialog dialog;
    RelativeLayout rl_topBar;
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        init();
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
        webView = findViewById(R.id.webView);
        rl_topBar = findViewById(R.id.rl_topBar);
        sharedPref = new SharedPref(ContactUsActivity.this);
        String url = "http://ezyscrap.in/contact-us.html";
        dialog = new ProgressDialog(ContactUsActivity.this);
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

        webView.loadUrl(url);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
    }



   /* public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url.startsWith("http:") || url.startsWith("https:")) {
            return false;
        }

        // Otherwise allow the OS to handle it
        else if (url.startsWith("tel:")) {
            Intent tel = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
            startActivity(tel);
            return true;
        } else if (url.startsWith("mailto:")) {
            String body = "Enter your Question, Enquiry or Feedback below:\n\n";
            Intent mail = new Intent(Intent.ACTION_SEND);
            mail.setType("application/octet-stream");
            mail.putExtra(Intent.EXTRA_EMAIL, new String[]{"email address"});
            mail.putExtra(Intent.EXTRA_SUBJECT, "Subject");
            mail.putExtra(Intent.EXTRA_TEXT, body);
            startActivity(mail);
            return true;
        }
        return true;
    }*/
}
