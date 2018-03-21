package com.ezyscrap.View.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.ezyscrap.AppUtils.AppUtils;
import com.ezyscrap.AppUtils.SharedPref;
import com.ezyscrap.Model.Login.Forgot.ForgotPassresponce;
import com.ezyscrap.Model.Login.LoginResponce;
import com.ezyscrap.R;
import com.ezyscrap.webservice.APIRequest;
import com.ezyscrap.webservice.AppConstants;
import com.ezyscrap.webservice.BaseResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bitware on 19/12/17.
 */

public class LoginActivity extends Activity implements View.OnClickListener, APIRequest.ResponseHandler {

    TextView tv_login, tv_forgetPassword, tv_registration;
    EditText edt_email, edt_password;
    String email, password;
    SharedPref sharedPref;
    Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        customColor();
    }

    private void init() {

        tv_login = findViewById(R.id.tv_login);
        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);
        sharedPref = new SharedPref(LoginActivity.this);
        tv_forgetPassword = findViewById(R.id.tv_forgetPassword);
        tv_registration = findViewById(R.id.tv_registration);

        tv_login.setOnClickListener(this);
        tv_forgetPassword.setOnClickListener(this);
        tv_registration.setOnClickListener(this);

       /* if (!sharedPref.getString("user_id", "").equalsIgnoreCase("")) {
            Intent i = new Intent(LoginActivity.this, HomeScreen.class);
            startActivity(i);
        } else {
            //Intent i = new Intent(LoginActivity.this, LoginActivity.class);
            //startActivity(i);

        }*/
    }

    public void customColor() {
        Spannable WordtoSpan = new SpannableString("Dont't have an account? Register");
        WordtoSpan.setSpan(new ForegroundColorSpan(Color.BLUE), 24, 32,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_registration.setText(WordtoSpan);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                //startActivity(new Intent(LoginActivity.this,HomeScreen.class));
                if (AppUtils.isConnectingToInternet(LoginActivity.this)) {
                    callLoginApi();
                } else {
                    AppUtils.toastMsg(LoginActivity.this, getResources().getString(R.string.no_internet));
                }
                break;
            case R.id.tv_forgetPassword:
                if (AppUtils.isConnectingToInternet(LoginActivity.this)) {
                    showForgetPasswordDialog();
                } else {
                    AppUtils.toastMsg(LoginActivity.this, getResources().getString(R.string.no_internet));
                }
                break;
            case R.id.tv_registration:
                if (AppConstants.USER_FLAG.equalsIgnoreCase("Seller")) {
                    startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                } else if (AppConstants.USER_FLAG.equalsIgnoreCase("Buyer")) {
                    startActivity(new Intent(LoginActivity.this, BuyerRegistrationActivity.class));
                }
                break;
        }
    }

    private void callLoginApi() {
        email = edt_email.getText().toString();
        password = edt_password.getText().toString();
        if (!email.isEmpty()) {
            if (email.matches(AppConstants.EMAIL_REGEX)) {
                if (!password.isEmpty()) {
                    String Url = AppConstants.BASE_URL + "login";
                    System.out.println("Login Url >" + Url);
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("email", email);
                        jsonObject.put("pwd", password);
                        jsonObject.put("flag", AppConstants.USER_FLAG);
                        jsonObject.put("device_id", SharedPref.getPreferences().getString(SharedPref.GCMREGID,""));
                        //jsonObject.put("flag", sharedPref.getString("flag", ""));
                        Log.e("TAG", "request > " + jsonObject);

                        new APIRequest(LoginActivity.this, Url, jsonObject, this, AppConstants.API_LOGIN, AppConstants.POST);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    AppUtils.setErrorMsg(edt_password, "Please Enter Password");
                }
            } else {
                AppUtils.setErrorMsg(edt_email, "Please Enter Valid Email");
            }
        } else {
            AppUtils.setErrorMsg(edt_email, "Please Enter Email");
        }
    }

    private void showForgetPasswordDialog() {
        dialog = new Dialog(LoginActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
        dialog.setContentView(R.layout.dialog_forget_password);

        TextView txt_submit = (TextView) dialog.findViewById(R.id.txt_submit);
        TextView txt_cancel = (TextView) dialog.findViewById(R.id.txt_cancel);
        final EditText edt_email = (EditText) dialog.findViewById(R.id.edt_email);

        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        txt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = edt_email.getText().toString();
                if (!email.isEmpty()) {
                    if (email.matches(AppConstants.EMAIL_REGEX)) {
                        callForgetPasswordApi();
                    } else {
                        AppUtils.setErrorMsg(edt_email, "Please Enter Valid Email");
                    }
                } else {
                    AppUtils.setErrorMsg(edt_email, "Please Enter Email");
                }
            }
        });

        //WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        //wmlp.gravity = Gravity.CENTER_HORIZONTAL;
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        //  dialog.getWindow().setLayout((screenWidth / 2) + 400, (screenHeight / 3) + 50);
        //dialog.getWindow().setLayout(920, 700);
    }

    private void callForgetPasswordApi() {
        String Url = AppConstants.BASE_URL + "forgot_password";
        System.out.println("Forget Pass Url >" + Url);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("flag", AppConstants.USER_FLAG);
            //jsonObject.put("flag", sharedPref.getString("flag", ""));
            Log.e("TAG", "Request > " + jsonObject);
            new APIRequest(LoginActivity.this, Url, jsonObject, this, AppConstants.API_FORGETPASS, AppConstants.POST);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(BaseResponse response) {
        if (response.getApiName() == AppConstants.API_LOGIN) {
            LoginResponce responce = (LoginResponce) response;
            if (responce.getStatus().equalsIgnoreCase("Success")) {
                AppUtils.toastMsg(LoginActivity.this, responce.getDescription());
                sharedPref.writeString(SharedPref.USER_ID, responce.getUserInfo().getId());
                sharedPref.writeString(SharedPref.ORG_NAME, responce.getUserInfo().getOrgName());
                sharedPref.writeString(SharedPref.EMAIL, responce.getUserInfo().getEmail());
                sharedPref.writeString(SharedPref.COMPANY_REG_NO, responce.getUserInfo().getCompanyRegNo());
                sharedPref.writeString(SharedPref.PHONE_NO, responce.getUserInfo().getPhoneNo());
                sharedPref.writeString(SharedPref.ADDRESS, responce.getUserInfo().getAddress());
                sharedPref.writeString(SharedPref.CITY, responce.getUserInfo().getCity());
                sharedPref.writeString(SharedPref.ZIP, responce.getUserInfo().getZip());
                sharedPref.writeString(SharedPref.TOKEN, responce.getToken());

                //sharedPref.writeString("isLogin", "yes");
                sharedPref.writeString(SharedPref.USER_FLAG,AppConstants.USER_FLAG);
                if (sharedPref.getString(SharedPref.USER_FLAG, "").equalsIgnoreCase("Buyer")) {
                    sharedPref.writeString(SharedPref.BUYER_NAME, responce.getUserInfo().getName());
                }
                startActivity(new Intent(LoginActivity.this, HomeScreen.class));
            } else {
                AppUtils.toastMsg(LoginActivity.this, responce.getDescription());
            }
        } else if (response.getApiName() == AppConstants.API_FORGETPASS) {
            ForgotPassresponce forgotPassresponce = (ForgotPassresponce) response;
            if (forgotPassresponce.getStatus().equalsIgnoreCase("Success")) {
                dialog.dismiss();
                AppUtils.toastMsg(LoginActivity.this, forgotPassresponce.getDescription());
            } else {
                AppUtils.toastMsg(LoginActivity.this, forgotPassresponce.getDescription());
            }
        }
    }

    @Override
    public void onFailure(BaseResponse response) {

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
       /* android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();*/
       /* if( getIntent().getBooleanExtra("Exit", false)){
            finish();
            return; // add this to prevent from doing unnecessary stuffs
        }*/
       finish();
       Intent i = new Intent(this, ActivityChooser.class);
       startActivity(i);
    }
}
