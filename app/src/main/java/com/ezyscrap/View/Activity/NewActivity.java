package com.ezyscrap.View.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.paytm.pgsdk.PaytmClientCertificate;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.ezyscrap.R;

import java.util.HashMap;
import java.util.Map;

public class NewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        PaytmPGService Service = null;
        Service = PaytmPGService.getStagingService();

                //Service = PaytmPGService.getProductionService();

//Create new order Object having all order information.
        Map<String, String> paramMap = new HashMap<String,String>();
        paramMap.put( "MID" , "PAYTM_MERCHANT_ID");
        paramMap.put( "ORDER_ID" , "ORDER0000000001");
        paramMap.put( "CUST_ID" , "10000988111");
        paramMap.put( "INDUSTRY_TYPE_ID" , "PAYTM_INDUSTRY_TYPE_ID");
        paramMap.put( "CHANNEL_ID" , "WAP");
        paramMap.put( "TXN_AMOUNT" , "1");
        paramMap.put( "WEBSITE" , "PAYTM_WEBSITE");
        paramMap.put( "CALLBACK_URL" , "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID=ORDER0000000001");
        paramMap.put( "EMAIL" , "abc@gmail.com");
        paramMap.put( "MOBILE_NO" , "9999999999");
        paramMap.put( "CHECKSUMHASH" , "w2QDRMgp1/BNdEnJEAPCIOmNgQvsi+BhpqijfM9KvFfRiPmGSt3Ddzw+oTaGCLneJwxFFq5mqTMwJXdQE2EzK4px2xruDqKZjHupz9yXev4=");

//Create Client Certificate object holding the information related to Client Certificate. Filename must be without .p12 extension.
//For example, if suppose client.p12 is stored in raw folder, then filename must be client.
        PaytmClientCertificate Certificate = new PaytmClientCertificate ("password" , "filename" );

        //creating a paytm order object using the hashmap
        PaytmOrder order = new PaytmOrder(paramMap);
//Set PaytmOrder and PaytmClientCertificate Object. Call this method and set both objects before starting transaction.
        Service.initialize(order, Certificate);
//OR
        //Service.initialize(order, null);

//Start the Payment Transaction. Before starting the transaction ensure that initialize method is called.

        Service.startPaymentTransaction(this, true, true, new PaytmPaymentTransactionCallback() {

            @Override
            public void someUIErrorOccurred(String inErrorMessage) {
                Log.d("LOG", "UI Error Occur.");
                Toast.makeText(getApplicationContext(), " UI Error Occur. ", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTransactionResponse(Bundle inResponse) {
                Log.d("LOG", "Payment Transaction : " + inResponse);
                Toast.makeText(getApplicationContext(), "Payment Transaction response "+inResponse.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void networkNotAvailable() {
                Log.d("LOG", "UI Error Occur.");
                Toast.makeText(getApplicationContext(), " UI Error Occur. ", Toast.LENGTH_LONG).show();
            }

            @Override
            public void clientAuthenticationFailed(String inErrorMessage) {
                Log.d("LOG", "UI Error Occur.");
                Toast.makeText(getApplicationContext(), " Severside Error "+ inErrorMessage, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onErrorLoadingWebPage(int iniErrorCode,
                                              String inErrorMessage, String inFailingUrl) {

            }
            @Override
            public void onBackPressedCancelTransaction() {
// TODO Auto-generated method stub
            }

            @Override
            public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                Log.d("LOG", "Payment Transaction Failed " + inErrorMessage);
                Toast.makeText(getBaseContext(), "Payment Transaction Failed ", Toast.LENGTH_LONG).show();
            }

        });
    }

}
