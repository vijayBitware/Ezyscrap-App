package com.ezyscrap.View.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.ezyscrap.AppUtils.SharedPref;
import com.ezyscrap.R;
import com.ezyscrap.webservice.AppConstants;

/**
 * Created by bitware on 19/12/17.
 */

public class ActivityChooser extends Activity implements View.OnClickListener {

    TextView txt_buyer, txt_seller;
    SharedPref pref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);

        init();
    }

    private void init() {
        pref = new SharedPref(ActivityChooser.this);
        txt_buyer = findViewById(R.id.txt_buyer);
        txt_seller = findViewById(R.id.txt_seller);

        txt_buyer.setOnClickListener(this);
        txt_seller.setOnClickListener(this);

        int MyVersion = Build.VERSION.SDK_INT;
        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            System.out.println("***********version*********" + MyVersion);
            if (!checkIfAlreadyhavePermission()) {
                requestForSpecificPermission();
            }
        }

        if (!SharedPref.getPreferences().getString(SharedPref.USER_ID, "").equalsIgnoreCase("")) {
            System.out.println("**************choose*********");
            Intent i = new Intent(this, HomeScreen.class);
            startActivity(i);
        }
    }

    private boolean checkIfAlreadyhavePermission() {
        int result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int result3 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        if (result1 == PackageManager.PERMISSION_GRANTED || result2 == PackageManager.PERMISSION_GRANTED || result3 == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestForSpecificPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //granted
                    //turnOnGps();
                    System.out.println("************granted*********");
                } else {
                    //not granted
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityChooser.this);
                    builder.setTitle("Need Permissions");
                    builder.setMessage("This app needs camera permissions.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            requestForSpecificPermission();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_buyer:
                AppConstants.USER_FLAG = "Buyer";
                //pref.writeString("flag","Buyer");
                //pref.writeString("isFlagSet","yes");
                startActivity(new Intent(ActivityChooser.this, LoginActivity.class));
                finish();
                break;
            case R.id.txt_seller:
                AppConstants.USER_FLAG = "Seller";
                //pref.writeString("flag","Seller");
                //pref.writeString("isFlagSet","yes");
                startActivity(new Intent(ActivityChooser.this, LoginActivity.class));
                finish();
                break;
        }
    }
}
