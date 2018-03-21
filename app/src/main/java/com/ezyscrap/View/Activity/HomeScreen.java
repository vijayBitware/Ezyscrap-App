package com.ezyscrap.View.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ezyscrap.AppUtils.AppUtils;
import com.ezyscrap.AppUtils.SharedPref;
import com.ezyscrap.R;
import com.ezyscrap.View.Fragments.AddScrapFragment;
import com.ezyscrap.View.Fragments.BuyerRequestFragment;
import com.ezyscrap.View.Fragments.FragmentWalletSummary;
import com.ezyscrap.View.Fragments.HomeFragment;
import com.ezyscrap.View.Fragments.ProfileFragment;
import com.ezyscrap.webservice.AppConstants;

/**
 * Created by bitware on 20/12/17.
 */

public class HomeScreen extends AppCompatActivity implements View.OnClickListener {

    ImageView iv_home, iv_profile, iv_addScrap, iv_active_profile, iv_active_home;
    SharedPref sharedPref;
    FragmentManager fragmentManager;
    Toolbar toolbar;
    ImageView imgBack, imgNotification;
    TextView txtHeading, txtNotCount;
    RelativeLayout relativeNotCnt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();

        if (sharedPref.getPreferences().getString(sharedPref.USER_FLAG, "").equalsIgnoreCase("Buyer")) {
            AppUtils.replaceFragment(new BuyerRequestFragment(), getSupportFragmentManager(), null);
        } else {
            AppUtils.replaceFragment(new HomeFragment(), getSupportFragmentManager(), null);
        }
    }

    private void init() {
        Intent i = getIntent();
        System.out.println("********"+i.getStringExtra("flag"));
        iv_active_profile = findViewById(R.id.iv_active_profile);
        iv_active_home = findViewById(R.id.iv_active_home);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar ().setDisplayShowTitleEnabled (false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        txtHeading = findViewById(R.id.txtHeading);
        imgBack = findViewById(R.id.imgBack);
        imgNotification = findViewById(R.id.imgNotification);
        imgBack.setVisibility(View.GONE);
        fragmentManager = getSupportFragmentManager();
        iv_home = findViewById(R.id.iv_home);
        iv_profile = findViewById(R.id.iv_profile);
        iv_addScrap = findViewById(R.id.iv_addScrap);
        sharedPref = new SharedPref(HomeScreen.this);

        iv_addScrap.setOnClickListener(this);
        iv_profile.setOnClickListener(this);
        iv_home.setOnClickListener(this);

        imgNotification = findViewById(R.id.imgNotification);
        imgBack = findViewById(R.id.imgBack);
        txtHeading = findViewById(R.id.txtHeading);
        relativeNotCnt = findViewById(R.id.relativeNotCnt);
        txtNotCount = findViewById(R.id.txtNotCount);

        if(SharedPref.getPreferences().getString(SharedPref.USER_FLAG,"").equalsIgnoreCase("buyer")) {
            if (SharedPref.getPreferences().getString(SharedPref.NOTIFICATION_COUNTER, "").equalsIgnoreCase("") ||
                    SharedPref.getPreferences().getString(SharedPref.NOTIFICATION_COUNTER, "").equalsIgnoreCase("null")) {
                sharedPref.writeString(SharedPref.NOTIFICATION_COUNTER, "0");
                txtNotCount.setVisibility(View.VISIBLE);
                relativeNotCnt.setVisibility(View.VISIBLE);
                imgBack.setVisibility(View.GONE);
                imgNotification.setVisibility(View.VISIBLE);
            }
        }else
        {
            txtNotCount.setVisibility(View.GONE);
            relativeNotCnt.setVisibility(View.GONE);
            imgBack.setVisibility(View.GONE);
            imgNotification.setVisibility(View.GONE);
        }

        if (sharedPref.getPreferences().getString(sharedPref.USER_FLAG, "").equalsIgnoreCase("Buyer")) {
            iv_addScrap.setImageResource(R.drawable.icon_buyer);
            toolbar.setBackgroundColor(getResources().getColor(R.color.blue));
        } else {
            iv_addScrap.setImageResource(R.drawable.icon_tab_add);
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }

        /*if(!AppConstants.SCRAP_ID.equalsIgnoreCase(""))
        {
            AppUtils.replaceFragment(new BuyerRequestFragment(), getSupportFragmentManager(), null);
        }*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_addScrap:
                AppConstants.SCRAP_ID = "";
                iv_active_profile.setVisibility(View.GONE);
                iv_active_home.setVisibility(View.GONE);

                AppConstants.SHOW_DATA = false;
                if (sharedPref.getPreferences().getString(sharedPref.USER_FLAG, "").equalsIgnoreCase("Seller")) {
                    AppUtils.replaceFragment(new AddScrapFragment(), getSupportFragmentManager(), null);
                } else {
                    AppUtils.replaceFragment(new FragmentWalletSummary(), getSupportFragmentManager(), null);
                    //AppUtils.replaceFragment(new FragmentHistory(), getSupportFragmentManager(), null);
                }
                //startActivity(new Intent(HomeScreen.this,AddScrapActivity.class));
                break;
            case R.id.iv_profile:
                AppConstants.SCRAP_ID = "";
                iv_active_home.setVisibility(View.GONE);
                iv_active_profile.setVisibility(View.VISIBLE);
                AppConstants.SHOW_DATA = false;
                AppUtils.replaceFragment(new ProfileFragment(), getSupportFragmentManager(), null);
                break;

            case R.id.iv_home:
                AppConstants.SCRAP_ID = "";
                iv_active_home.setVisibility(View.VISIBLE);
                iv_active_profile.setVisibility(View.GONE);
                AppConstants.SHOW_DATA = false;
                if (sharedPref.getPreferences().getString(sharedPref.USER_FLAG, "").equalsIgnoreCase("Buyer")) {
                    AppUtils.replaceFragment(new BuyerRequestFragment(), getSupportFragmentManager(), null);
                } else {
                    AppUtils.replaceFragment(new HomeFragment(), getSupportFragmentManager(), null);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        AppConstants.SCRAP_ID = "";
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount == 1) {
            AppUtils.showExitDialog(HomeScreen.this);
        } else {
            fragmentManager.popBackStackImmediate();
        }
    }

    //receiver to receive chat & notification counterv when activity is open
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (SharedPref.getPreferences().getString(SharedPref.NOTIFICATION_COUNTER, "").equalsIgnoreCase("") || SharedPref.getPreferences().getString(SharedPref.NOTIFICATION_COUNTER, "").equalsIgnoreCase("0")) {
                txtNotCount.setText("");
                txtNotCount.setVisibility(View.GONE);
                relativeNotCnt.setVisibility(View.GONE);
            } else {
                txtNotCount.setText(SharedPref.getPreferences().getString(SharedPref.NOTIFICATION_COUNTER, ""));
                txtNotCount.setVisibility(View.VISIBLE);
                relativeNotCnt.setVisibility(View.VISIBLE);
            }

        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        try {
            this.registerReceiver(mMessageReceiver, new IntentFilter("homeScreen"));

        } catch (Exception e) {
        }
        if (SharedPref.getPreferences().getString(SharedPref.NOTIFICATION_COUNTER, "").equalsIgnoreCase("") ||
                SharedPref.getPreferences().getString(SharedPref.NOTIFICATION_COUNTER, "").equalsIgnoreCase("0")) {
            txtNotCount.setText("");
            txtNotCount.setVisibility(View.GONE);
            relativeNotCnt.setVisibility(View.GONE);
        } else {
            txtNotCount.setText(SharedPref.getPreferences().getString(SharedPref.NOTIFICATION_COUNTER, ""));
            txtNotCount.setVisibility(View.VISIBLE);
            relativeNotCnt.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMessageReceiver);
    }

}
