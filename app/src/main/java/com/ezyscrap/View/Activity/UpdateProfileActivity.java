package com.ezyscrap.View.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ezyscrap.AppUtils.AppUtils;
import com.ezyscrap.AppUtils.SharedPref;
import com.ezyscrap.Model.UpdateResponse;
import com.ezyscrap.R;
import com.ezyscrap.webservice.APIRequest;
import com.ezyscrap.webservice.AppConstants;
import com.ezyscrap.webservice.BaseResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Admin on 12/28/2017.
 */

public class UpdateProfileActivity extends AppCompatActivity implements View.OnClickListener, APIRequest.ResponseHandler {

    EditText edtOrgName, edtContactNumber, edtAddress, edtCityName, edtZipCode, edtEmailId;
    Button btnUpdate;
    SharedPref sharedPref;
    String email, name, phNo, address, city, zip;
    ImageView imgBackArrow;
    FragmentManager fragmentManager;
    Dialog dialog;
    LinearLayout linearHd;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        Intent intent = getIntent();
        init();
    }

    private void init() {
        sharedPref = new SharedPref(this);
        linearHd= findViewById(R.id.linearHd);
        edtOrgName = (EditText) findViewById(R.id.edtOrgName);
        edtContactNumber = (EditText) findViewById(R.id.edtContactNumber);
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        edtCityName = (EditText) findViewById(R.id.edtCityName);
        edtZipCode = (EditText) findViewById(R.id.edtZipCode);
        edtEmailId = (EditText) findViewById(R.id.edtEmailId);
//        edtPassword = (EditText) findViewById(R.id.edtPassword);
        imgBackArrow = (ImageView) findViewById(R.id.imgBackArrow);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        setData();
        fragmentManager = getSupportFragmentManager();
        btnUpdate.setOnClickListener(this);
        imgBackArrow.setOnClickListener(this);

        if(SharedPref.getPreferences().getString(SharedPref.USER_FLAG,"").equalsIgnoreCase("buyer"))
        {
            btnUpdate.setBackground(getResources().getDrawable(R.drawable.btn_green));
            linearHd.setBackgroundColor(getResources().getColor(R.color.blue));
        }else
        {
            btnUpdate.setBackground(getResources().getDrawable(R.drawable.btn_bg));
            linearHd.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    private void setData() {
        if (SharedPref.getPreferences().getString(SharedPref.USER_FLAG, "").equalsIgnoreCase("Buyer")) {
            edtOrgName.setText(SharedPref.getPreferences().getString(SharedPref.BUYER_NAME, ""));
        } else {
            edtOrgName.setText(SharedPref.getPreferences().getString(SharedPref.ORG_NAME, ""));
        }
        edtContactNumber.setText(SharedPref.getPreferences().getString(SharedPref.PHONE_NO, ""));
        edtZipCode.setText(SharedPref.getPreferences().getString(SharedPref.ZIP, ""));
        edtEmailId.setText(SharedPref.getPreferences().getString(SharedPref.EMAIL, ""));
        edtAddress.setText(SharedPref.getPreferences().getString(SharedPref.ADDRESS, ""));
        edtCityName.setText(SharedPref.getPreferences().getString(SharedPref.CITY, ""));
        edtZipCode.setText(SharedPref.getPreferences().getString(SharedPref.ZIP, ""));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.btnUpdate:

                if (sharedPref.getPreferences().getString(sharedPref.USER_FLAG, "").equalsIgnoreCase("Buyer")) {
                    updateBuyerProfile();
                } else if (sharedPref.getPreferences().getString(sharedPref.USER_FLAG, "").equalsIgnoreCase("Seller")) {
                    // AppUtils.toastMsg(UpdateProfileActivity.this, getResources().getString(R.string.no_internet));
                    updateSellerProfile();
                }
                break;

            case R.id.imgBackArrow:
                // AppConstants.SHOW_DATA = false;
                //AppUtils.replaceFragment(new ProfileFragment(), getSupportFragmentManager(), null);
                finish();
                break;

        }
    }


    private boolean validation() {
        email = edtEmailId.getText().toString();
       // password = edtPassword.getText().toString();
        name = edtOrgName.getText().toString();
        phNo = edtContactNumber.getText().toString();
        address = edtAddress.getText().toString();
        city = edtCityName.getText().toString();
        zip = edtZipCode.getText().toString();


        if (phNo.equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter phone number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (phNo.length() < 10) {
            Toast.makeText(this, "Please enter valid mobile number ", Toast.LENGTH_SHORT).show();
            return false;
//        } else if (password.equalsIgnoreCase("")) {
//            Toast.makeText(this, "Please Enter password", Toast.LENGTH_SHORT).show();
//            return false;
//        } else if (password.length() < 6) {
//            Toast.makeText(this, "Please Enter min 6 characters password  ", Toast.LENGTH_SHORT).show();
//            return false;

        } else if (email.equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter email address", Toast.LENGTH_SHORT).show();
            return false;
        }
//        } else if (email.matches(AppConstants.EMAIL_REGEX)) {
//            AppUtils.setErrorMsg(edtEmailId, "Please Enter Valid Email");
//            return false;
//        }
        else if (name.equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (address.equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter address", Toast.LENGTH_SHORT).show();
            return false;
        } else if (city.equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter city", Toast.LENGTH_SHORT).show();
            return false;
        } else if (zip.equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter zipcode", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void updateSellerProfile() {
        if (validation()) {
            String Url = AppConstants.BASE_URL + "update_new_seller";
            System.out.println("Update_seller Url >" + Url);
            JSONObject jsonObject = new JSONObject();
            try {

                jsonObject.put("org_name", name);
                jsonObject.put("phone_no", phNo);
                jsonObject.put("address", address);
                jsonObject.put("city", city);
                jsonObject.put("zip", zip);
                jsonObject.put("token", SharedPref.getPreferences().getString(SharedPref.TOKEN, ""));


                Log.e("TAG", "request > " + jsonObject);

                new APIRequest(UpdateProfileActivity.this, Url, jsonObject, this, AppConstants.API_UPDATE_SELLER, AppConstants.POST);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void updateBuyerProfile() {
        if (validation()) {
            String Url = AppConstants.BASE_URL + "update_buyer";
            System.out.println("Update_buyer Url >" + Url);
            JSONObject jsonObject = new JSONObject();
            try {

                jsonObject.put("name", name);
                jsonObject.put("phone_no", phNo);
                jsonObject.put("address", address);
                jsonObject.put("city", city);
                jsonObject.put("zip", zip);
                jsonObject.put("token", SharedPref.getPreferences().getString(SharedPref.TOKEN, ""));

                Log.e("TAG", "request > " + jsonObject);

                new APIRequest(UpdateProfileActivity.this, Url, jsonObject, this, AppConstants.API_UPDATE_BUYER, AppConstants.POST);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    public void onSuccess(BaseResponse response) {
        if (response.getApiName() == AppConstants.API_UPDATE_BUYER) {
            UpdateResponse updateResponse = (UpdateResponse) response;
            if (updateResponse.getStatus().equalsIgnoreCase("success")) {
                AppUtils.toastMsg(this, updateResponse.getDescription());
                sharedPref.writeString(SharedPref.BUYER_NAME,name);
                sharedPref.writeString(SharedPref.EMAIL,email);
                sharedPref.writeString(SharedPref.PHONE_NO,phNo);
                sharedPref.writeString(SharedPref.ADDRESS,address);
                sharedPref.writeString(SharedPref.CITY,city);
                sharedPref.writeString(SharedPref.ZIP, zip);
                finish();
            } else {

                AppUtils.toastMsg(this, updateResponse.getDescription());
            }

        } else {
            UpdateResponse updateResponse = (UpdateResponse) response;
            if (updateResponse.getStatus().equalsIgnoreCase("success")) {
                AppUtils.toastMsg(this, updateResponse.getDescription());
                sharedPref.writeString(SharedPref.ORG_NAME,name);
                sharedPref.writeString(SharedPref.EMAIL,email);
                sharedPref.writeString(SharedPref.PHONE_NO,phNo);
                sharedPref.writeString(SharedPref.ADDRESS,address);
                sharedPref.writeString(SharedPref.CITY,city);
                sharedPref.writeString(SharedPref.ZIP, zip);
                finish();
            } else {
                AppUtils.toastMsg(this, updateResponse.getDescription());
            }
        }
    }

    @Override
    public void onFailure(BaseResponse response) {

    }
}
