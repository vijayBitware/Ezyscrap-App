package com.ezyscrap.View.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.ezyscrap.AppUtils.SharedPref;
import com.ezyscrap.Model.PaytmChecksumResponce;
import com.ezyscrap.R;
import com.ezyscrap.webservice.APIRequest;
import com.ezyscrap.webservice.AppConstants;
import com.ezyscrap.webservice.BaseResponse;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * This is the sample app which will make use of the PG SDK. This activity will
 * show the usage of Paytm PG SDK API's.
 **/

public class PaytmActivity extends AppCompatActivity implements APIRequest.ResponseHandler {

    TextView txtHd, txtFee;
    ImageView imgBack;
    SharedPref pref;
    Button btnPayNow;
    String checksumHash;
    String transactionAmnt, regFee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paytm);
        //initOrderId();
        init();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    private void init() {
        Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
        String dateToStr = format.format(curDate);
        txtHd = findViewById(R.id.txtHd);
        txtFee = findViewById(R.id.txtFee);
        imgBack = findViewById(R.id.imgBack);
        btnPayNow = findViewById(R.id.btnPayNow);
        pref = new SharedPref(this);
        Intent i = getIntent();
        transactionAmnt = i.getStringExtra("amount");
        regFee = i.getStringExtra("regFee");

        if(regFee.equalsIgnoreCase("no"))
        {
            txtHd.setText("Wallet Amount : ");
            txtFee.setText(getResources().getString(R.string.rupee)+transactionAmnt);
        }else
        {
            txtHd.setText("Registration Fee : ");
            txtFee.setText(getResources().getString(R.string.rupee)+transactionAmnt);
        }

        AppConstants.ORDER_ID = i.getStringExtra("orderId") + dateToStr;
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callChecksumUrl();
            }
        });




    }

    //This is to refresh the order id: Only for the Sample App's purpose.
    @Override
    protected void onStart() {
        super.onStart();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void callChecksumUrl() {
        String url = AppConstants.generateChecksum;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("MID", AppConstants.M_ID);
            jsonObject.put("ORDER_ID", AppConstants.ORDER_ID);
            AppConstants.CUSTOMER_ID = SharedPref.getPreferences().getString(SharedPref.USER_ID, "");
            jsonObject.put("CUST_ID", AppConstants.CUSTOMER_ID);
            jsonObject.put("INDUSTRY_TYPE_ID", AppConstants.INDUSTRY_TYPE_ID);
            jsonObject.put("CHANNEL_ID", AppConstants.CHANNEL_ID);
            jsonObject.put("TXN_AMOUNT", transactionAmnt);
            jsonObject.put("WEBSITE", AppConstants.WEBSITE);
            //jsonObject.put("MOBILE_NO", AppConstants.MOBILE_NO);
           // jsonObject.put("EMAIL ID", AppConstants.EMAIL);
            jsonObject.put("CALLBACK_URL", AppConstants.CALLBACK_URL+AppConstants.ORDER_ID);
            Log.e("TAG", "Request > " + jsonObject);

            new APIRequest(PaytmActivity.this, url, jsonObject, this, AppConstants.API_PAYTM, AppConstants.POST);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onStartTransaction() {
        PaytmPGService Service = PaytmPGService.getProductionService();
        //Kindly create complete Map and checksum on your server side and then put it here in paramMap.
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("MID", AppConstants.M_ID);
        paramMap.put("ORDER_ID", AppConstants.ORDER_ID);
        AppConstants.CUSTOMER_ID = SharedPref.getPreferences().getString(SharedPref.USER_ID, "");
        paramMap.put("CUST_ID", AppConstants.CUSTOMER_ID);
        paramMap.put("INDUSTRY_TYPE_ID", AppConstants.INDUSTRY_TYPE_ID);
        paramMap.put("CHANNEL_ID", AppConstants.CHANNEL_ID);
        paramMap.put("TXN_AMOUNT", transactionAmnt);
        paramMap.put("WEBSITE", AppConstants.WEBSITE);
        //paramMap.put("MOBILE_NO", AppConstants.MOBILE_NO);
        //paramMap.put("EMAIL ID", AppConstants.EMAIL);
        paramMap.put("CALLBACK_URL", AppConstants.CALLBACK_URL+AppConstants.ORDER_ID);
        paramMap.put("CHECKSUMHASH", checksumHash);

        PaytmOrder Order = new PaytmOrder(paramMap);

        Service.initialize(Order, null);

        Service.startPaymentTransaction(this, true, true,
                new PaytmPaymentTransactionCallback() {

                    @Override
                    public void someUIErrorOccurred(String inErrorMessage) {
                        System.out.println("*****error*****" + inErrorMessage);
                        // Some UI Error Occurred in Payment Gateway Activity.
                        // // This may be due to initialization of views in
                        // Payment Gateway Activity or may be due to //
                        // initialization of webview. // Error Message details
                        // the error occurred.
                    }

                    @Override
                    public void onTransactionResponse(Bundle inResponse) {
                        Log.d("LOG", "Payment Transaction : " + inResponse);
                        System.out.println("********res**********" + inResponse.getString("STATUS"));

                        if (inResponse.getString("STATUS").equalsIgnoreCase("TXN_SUCCESS")) {
                            //parcing result data
                            String CHECKSUMHASH = "", BANKNAME = "", ORDERID = "", TXNAMOUNT = "", TXNDATE = "", TXNID = "";

                            CHECKSUMHASH = inResponse.getString("CHECKSUMHASH");
                            BANKNAME = inResponse.getString("BANKNAME");
                            ORDERID = inResponse.getString("ORDERID");
                            TXNAMOUNT = inResponse.getString("TXNAMOUNT");
                            TXNDATE = inResponse.getString("TXNDATE");
                            TXNID = inResponse.getString("TXNID");

                            System.out.println("*****success2*****");
                            //  startActivity(new Intent(context, DrawerActivity.class));

                            if (regFee.equalsIgnoreCase("yes")) {
                                new BuyBitcoin().execute("{\"token\":\"" + SharedPref.getPreferences().getString(SharedPref.TOKEN, "") + "\",\"ORDERID\":\"" + ORDERID + "\",\"reg_flag\":\"" + "1" + "\",\"flag\":\"" + SharedPref.getPreferences().getString(SharedPref.USER_FLAG, "") + "\"}");

                            } else {
                                new BuyBitcoin().execute("{\"token\":\"" + SharedPref.getPreferences().getString(SharedPref.TOKEN, "") + "\",\"ORDERID\":\"" + ORDERID + "\",\"reg_flag\":\"" + "0" + "\",\"flag\":\"" + SharedPref.getPreferences().getString(SharedPref.USER_FLAG, "") + "\"}");
                            }
                            //new BuyBitcoin().execute("{\"token\":\"" + SharedPref.getPreferences().getString(SharedPref.TOKEN, "") + "\",\"ORDERID\":\"" + ORDERID + "\",\"reg_flag\":\"" + "1" + "\",\"flag\":\"" + SharedPref.getPreferences().getString(SharedPref.USER_FLAG, "") + "\"}");

                            // Response from SURl and FURL

                        } else {
                            if (AppConstants.PAYU_REDIRECT.equalsIgnoreCase("bid")) {
                                finish();
                            } else {
                                finish();
                                startActivity(new Intent(PaytmActivity.this, HomeScreen.class));
                            }
                        }

                        //Toast.makeText(getApplicationContext(), "Payment Transaction response " + inResponse.toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void networkNotAvailable() {
                        System.out.println("*****network*****");
                        // If network is not
                        // available, then this
                        // method gets called.
                    }

                    @Override
                    public void clientAuthenticationFailed(String inErrorMessage) {
                        System.out.println("*****clientAuthenticationFailed*****" + inErrorMessage);
                        // This method gets called if client authentication
                        // failed. // Failure may be due to following reasons //
                        // 1. Server error or downtime. // 2. Server unable to
                        // generate checksum or checksum response is not in
                        // proper format. // 3. Server failed to authenticate
                        // that client. That is value of payt_STATUS is 2. //
                        // Error Message describes the reason for failure.
                    }

                    @Override
                    public void onErrorLoadingWebPage(int iniErrorCode,
                                                      String inErrorMessage, String inFailingUrl) {
                        System.out.println("*****onErrorLoadingWebPage*****" + inErrorMessage);

                    }

                    // had to be added: NOTE
                    @Override
                    public void onBackPressedCancelTransaction() {
                        // TODO Auto-generated method stub

                        if (AppConstants.PAYU_REDIRECT.equalsIgnoreCase("bid")) {

                            finish();
                        } else {

                            finish();
                            startActivity(new Intent(PaytmActivity.this, HomeScreen.class));
                        }
                    }

                    @Override
                    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                        Log.d("LOG", "Payment Transaction Failed " + inErrorMessage);
                        Toast.makeText(getBaseContext(), "Payment Transaction Failed ", Toast.LENGTH_LONG).show();
                    }

                });
    }

    @Override
    public void onSuccess(BaseResponse response) {
        PaytmChecksumResponce checksumResponce = (PaytmChecksumResponce) response;
        System.out.println("*******RESSS*****" + response.toString());
        if (checksumResponce.getStatus().equalsIgnoreCase("success")) {
            //
            Log.e("TAG", "Checksum > " + checksumResponce.getCheckSumArray().getCHECKSUMHASH());
            System.out.println("******checksumHash*****" + checksumResponce.getCheckSumArray().getCHECKSUMHASH());
            checksumHash = checksumResponce.getCheckSumArray().getCHECKSUMHASH();

            onStartTransaction();
        } else {
            Toast.makeText(PaytmActivity.this, checksumResponce.getDescription(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(BaseResponse response) {

    }

    class BuyBitcoin extends AsyncTask<String, Void, String> {

        ProgressDialog p;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(PaytmActivity.this);
            p.setMessage("Please Wait..");
            p.setCancelable(false);
            p.show();
            // dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            System.out.println("*************" + params.toString());
            String result = "";
            com.squareup.okhttp.Response response = null;
            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(120, TimeUnit.SECONDS); // connect timeout
            client.setReadTimeout(120, TimeUnit.SECONDS);
            MediaType JSON = MediaType.parse("application/json;charset=utf-8");
            Log.e("request", params[0]);
            RequestBody body = RequestBody.create(JSON, params[0]);
            String url;

            url = "save_payment_detail";

            System.out.println("****url*******" + url);
            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
                    .url(AppConstants.BASE_URL + url)
                    .post(body)
                    .build();
            try {
                response = client.newCall(request).execute();
                Log.d("response123", String.valueOf(response));
                return response.body().string();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            AppConstants.INTEREST = false;
            System.out.println(">>> Buy bitcoin :" + s);
            p.dismiss();

            if (s != null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String status = jsonObject.getString("status");

                    if (status.equalsIgnoreCase("Success")) {
                        String response_msg = jsonObject.getString("description");
                        Toast.makeText(PaytmActivity.this, response_msg, Toast.LENGTH_SHORT).show();
                        if (regFee.equalsIgnoreCase("no")) {
                            if (SharedPref.getPreferences().getString(SharedPref.WALLET_BAL, "").equalsIgnoreCase("") ||
                                    SharedPref.getPreferences().getString(SharedPref.WALLET_BAL, "").equalsIgnoreCase("null")) {
                                pref.writeString(SharedPref.WALLET_BAL, transactionAmnt);
                            } else {
                                double calAmnt = Double.parseDouble(SharedPref.getPreferences().getString(SharedPref.WALLET_BAL, "")) + Double.parseDouble(transactionAmnt);
                                pref.writeString(SharedPref.WALLET_BAL, String.valueOf(calAmnt));

                            }
                        } else {
                            pref.writeString(SharedPref.REG_FEE, "1");
                        }

                        System.out.println("**********wall bal******" + SharedPref.getPreferences().getString(SharedPref.WALLET_BAL, ""));
                        finish();
                        if (AppConstants.PAYU_REDIRECT.equalsIgnoreCase("bid")) {
                            finish();
                        } else {
                            finish();
                            startActivity(new Intent(PaytmActivity.this, HomeScreen.class));
                        }

                    } else {
                        p.dismiss();
                        // Toast.makeText(LoginActivity.this,response_msg,Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    // Toast.makeText(LoginActivity.this,getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();

                }
            } else {
                p.dismiss();
                //Toast.makeText(LoginActivity.this, "Something went wrong.Please try again", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //finish();
        if (AppConstants.PAYU_REDIRECT.equalsIgnoreCase("bid")) {
            System.out.println("******ifffffffffff******");
            finish();
        } else {
            System.out.println("******elseeeeeee******");
            finish();
            startActivity(new Intent(PaytmActivity.this, HomeScreen.class));
        }
    }
}
