package com.ezyscrap.webservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.ezyscrap.AppUtils.AppUtils;
import com.ezyscrap.Model.Login.Buyer.BuyerReqResponse.BidConfirmationRes;
import com.ezyscrap.Model.Login.Buyer.BuyerReqResponse.BuyerInterestResponse;
import com.ezyscrap.Model.Login.Buyer.BuyerReqResponse.BuyerReqList;
import com.ezyscrap.Model.Login.Buyer.BuyerReqResponse.ScrapDetailsResponse;
import com.ezyscrap.Model.Login.Forgot.ForgotPassresponce;
import com.ezyscrap.Model.Login.LoginResponce;
import com.ezyscrap.Model.Login.Notification.NotificationResponse;
import com.ezyscrap.Model.Login.RegisterResponse;
import com.ezyscrap.Model.Login.SellerHomeResponse.SellerHomeResponse;
import com.ezyscrap.Model.OrderHistory.OrderHistory;
import com.ezyscrap.Model.PaymentHistory.PaymentHistory;
import com.ezyscrap.Model.PaytmChecksumResponce;
import com.ezyscrap.Model.UpdateResponse;

import org.json.JSONObject;

/**
 * Created by bitware on 2/9/17.
 */

public class APIRequest extends AppCompatActivity {

    private JSONObject mJsonObject;
    private String mUrl;
    private ResponseHandler responseHandler;
    private int API_NAME;
    private Context mContext;
    ProgressDialog progressDialog;
    BaseResponse baseResponse;

    public APIRequest(Context context, String url, JSONObject jsonObject, ResponseHandler responseHandler, int api, String methodName) {
        this.responseHandler =responseHandler;
        this.API_NAME = api;
        this.mUrl = url;
        this.mJsonObject = jsonObject;
        this.mContext = context;

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Processing..");
        progressDialog.show();
        progressDialog.setCancelable(false);

        if (methodName.equals(AppConstants.GET)) {
            apiGetRequest();
        } else {
            apiGetRequest();
//            apiPostRequest();
        }

    }

    private void apiPostRequest() {
        System.out.println("API Name"+API_NAME);
        System.out.println("jsonObject"+mJsonObject);
        System.out.println("mUrl"+mUrl);
        String REQUEST_TAG = String.valueOf(API_NAME);

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(mUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(" >>> API RESPONSE " + response);
                        setResponseToBody(response);
                        progressDialog.dismiss();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        });

        jsonObjectReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        AppSingleton.getInstance(mContext).addToRequestQueue(jsonObjectReq, REQUEST_TAG);
    }

    private void apiGetRequest() {
        String REQUEST_TAG = String.valueOf(API_NAME);
        System.out.println("API Name"+API_NAME);

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(mUrl,mJsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Response is >> " + response);
                        setResponseToBody(response);
                        progressDialog.dismiss();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error > " +error);
                AppUtils.toastMsg(mContext,"Something went wrong.Please try again later.");
                progressDialog.dismiss();
            }
        });

        jsonObjectReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        AppSingleton.getInstance(mContext).addToRequestQueue(jsonObjectReq, REQUEST_TAG);
    }

    private void setResponseToBody(JSONObject response) {
        Gson gson = new Gson();
        switch (API_NAME) {
            case AppConstants.API_LOGIN:
                baseResponse = gson.fromJson(response.toString(), LoginResponce.class);
                break;
            case AppConstants.API_FORGETPASS:
                baseResponse = gson.fromJson(response.toString(), ForgotPassresponce.class);
                break;
            case AppConstants.API_REGISTRATION:
                baseResponse = gson.fromJson(response.toString(), RegisterResponse.class);
                break;
            case AppConstants.API_CHANGEPASS:
                baseResponse = gson.fromJson(response.toString(), RegisterResponse.class);
                break;
            case AppConstants.API_BUYER_INTEREST:
                baseResponse = gson.fromJson(response.toString(), BuyerInterestResponse.class);
                break;
            case AppConstants.API_BUYER_REQ:
                baseResponse = gson.fromJson(response.toString(), BuyerReqList.class);
                break;
            case AppConstants.API_ADD_BID:
                baseResponse = gson.fromJson(response.toString(), BidConfirmationRes.class);
                break;
            case AppConstants.API_SELLER_HOME:
                baseResponse = gson.fromJson(response.toString(), SellerHomeResponse.class);
                break;
            case AppConstants.API_SCRAP_DETAILS:
                baseResponse = gson.fromJson(response.toString(), ScrapDetailsResponse.class);
                break;
            case AppConstants.API_NOTIFICATION:
                baseResponse = gson.fromJson(response.toString(), NotificationResponse.class);
                break;
            case AppConstants.API_UPDATE_SELLER:
                baseResponse = gson.fromJson(response.toString(),UpdateResponse.class);
                break;
            case AppConstants.API_UPDATE_BUYER:
                baseResponse = gson.fromJson(response.toString(), UpdateResponse.class);
                break;
            case AppConstants.API_BUYER_ORDER_HISTORY:
                baseResponse = gson.fromJson(response.toString(), OrderHistory.class);
                break;
            case AppConstants.API_PAYMENT_HISTORY:
                baseResponse = gson.fromJson(response.toString(), PaymentHistory.class);
                break;

            case AppConstants.API_PAYTM:
                baseResponse = gson.fromJson(response.toString(), PaytmChecksumResponce.class);
                break;

        }
        baseResponse.setApiName(API_NAME);
        responseHandler.onSuccess(baseResponse);
        responseHandler.onFailure(baseResponse);
    }

    public interface ResponseHandler {
        public void onSuccess(BaseResponse response);
        public void onFailure(BaseResponse response);
    }

}
