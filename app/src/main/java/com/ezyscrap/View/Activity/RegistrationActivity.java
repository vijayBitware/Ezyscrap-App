package com.ezyscrap.View.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ezyscrap.AppUtils.AppUtils;
import com.ezyscrap.Model.Login.RegisterResponse;

import com.ezyscrap.R;
import com.ezyscrap.webservice.APIRequest;
import com.ezyscrap.webservice.AppConstants;
import com.ezyscrap.webservice.BaseResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener, APIRequest.ResponseHandler {
    EditText edtOrgName, edtContactNumber, edtAddress, edtCityName, edtZipCode, edtEmailId, edtPassword;
    Button btnRegister;
    TextView txtLoginHere;
    ImageView imgBackArrow;
    String orgName, contactNumber, address, city, zipcode, emailId, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        customColor();
    }

    private void init() {
        txtLoginHere = (TextView) findViewById(R.id.txtLoginHere);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        edtOrgName = (EditText) findViewById(R.id.edtOrgName);
        edtContactNumber = (EditText) findViewById(R.id.edtContactNumber);
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        edtCityName = (EditText) findViewById(R.id.edtCityName);
        edtZipCode = (EditText) findViewById(R.id.edtZipCode);
        edtEmailId = (EditText) findViewById(R.id.edtEmailId);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        imgBackArrow = (ImageView) findViewById(R.id.imgBackArrow);
        btnRegister.setOnClickListener(this);
        txtLoginHere.setOnClickListener(this);
        imgBackArrow.setOnClickListener(this);
    }

    public void customColor() {

        Spannable WordtoSpan = new SpannableString("Already have an account? Login here");
        WordtoSpan.setSpan(new ForegroundColorSpan(Color.BLUE), 25, 35,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtLoginHere.setText(WordtoSpan);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnRegister:
                registerWebservice();
                break;
            case R.id.txtLoginHere:
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.imgBackArrow:
                Intent intent1 = new Intent(RegistrationActivity.this, LoginActivity.class);
                 startActivity(intent1);
                break;
        }
    }

    private boolean isValidEmail(String email) {

        Pattern pattern = Pattern.compile(AppConstants.EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();


    }

    private void registerWebservice() {
       // String network_status = NetworkStatus.checkConnection(RegistrationActivity.this);
        if(!AppUtils.isConnectingToInternet(this))
        {
            /*AlertClass alert = new AlertClass();
            alert.customDialogforAlertMessage(this, getResources().getString(R.string.no_internet), "Please check your internet connection");*/
            AppUtils.toastMsg(RegistrationActivity.this,getResources().getString(R.string.no_internet));
        } else {
            if (validation()) {
                String registerURL = AppConstants.BASE_URL + "register_new_seller";
                System.out.println("register Url >" + registerURL);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("org_name", orgName);
                    jsonObject.put("email", emailId);
                    jsonObject.put("phone_no", contactNumber);
                    jsonObject.put("address", address);
                    jsonObject.put("city", city);
                    jsonObject.put("zip", zipcode);
                    jsonObject.put("password", password);
                    Log.e("TAG", String.valueOf(jsonObject));
                    new APIRequest(RegistrationActivity.this, registerURL, jsonObject, this, AppConstants.API_REGISTRATION, AppConstants.POST);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private boolean validation() {
        orgName = edtOrgName.getText().toString();
        contactNumber = edtContactNumber.getText().toString();
        address = edtAddress.getText().toString();
        city = edtCityName.getText().toString();
        zipcode = edtZipCode.getText().toString();
        emailId = edtEmailId.getText().toString();
        password = edtPassword.getText().toString();

        if (orgName.equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter organization name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (contactNumber.equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter contact number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (contactNumber.length() < 10) {
            Toast.makeText(this, "please Enter valid mobile number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (address.equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter address", Toast.LENGTH_SHORT).show();
            return false;
        } else if (city.equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter city", Toast.LENGTH_SHORT).show();
            return false;
        } else if (zipcode.equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter zipcode", Toast.LENGTH_SHORT).show();
            return false;
        } else if (zipcode.length() < 6) {
            Toast.makeText(this, "please Enter min 6 characters password  ", Toast.LENGTH_SHORT).show();
            return false;
        } else if (emailId.equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter email Id", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isValidEmail(emailId)) {
            edtEmailId.setError("Invalid email");
            return false;
        } else if (password.equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.length() < 6) {
            Toast.makeText(this, "please Enter min 6 characters password  ", Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;
    }

    @Override
    public void onSuccess(BaseResponse response) {
        RegisterResponse registerResponse = (RegisterResponse) response;
        System.out.println("###>>> : " + registerResponse.getDescription().toString());
        if (registerResponse.getStatus().toString().equalsIgnoreCase("success")) {
            Toast.makeText(RegistrationActivity.this, registerResponse.getDescription().toString(), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
        } else {
            Toast.makeText(RegistrationActivity.this, registerResponse.getDescription().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(BaseResponse response) {
       // Toast.makeText(RegistrationActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
    }


}
