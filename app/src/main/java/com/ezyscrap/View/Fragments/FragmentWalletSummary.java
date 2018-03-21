package com.ezyscrap.View.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ezyscrap.AppUtils.AppUtils;
import com.ezyscrap.AppUtils.SharedPref;
import com.ezyscrap.Controller.PriviousTransAdapter;
import com.ezyscrap.Model.PaymentHistory.PaymentHistory;
import com.ezyscrap.R;
import com.ezyscrap.View.Activity.ActivityNotification;
import com.ezyscrap.webservice.APIRequest;
import com.ezyscrap.webservice.AppConstants;
import com.ezyscrap.webservice.BaseResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bitware on 23/12/17.
 */

public class FragmentWalletSummary extends Fragment implements APIRequest.ResponseHandler{

    TextView txtAddMoney, txtTotalBal;
    View view;
    RecyclerView rv_priviouudTrans;
    Fragment fragment;
    FragmentManager fm;
    ImageView imgBack, imgNotification;
    TextView txtHeading, txtNotCount;
    RelativeLayout relativeNotCnt;
    SharedPref pref;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wallet_summary,container,false);
        init();
        if (AppUtils.isConnectingToInternet(getContext())){
            callPaymentHistoryApi();
        }else {
            AppUtils.toastMsg(getContext(),getResources().getString(R.string.no_internet));
        }
        return view;
    }

    private void init() {
        AppConstants.INTEREST = false;
        pref = new SharedPref(getActivity());
        imgNotification = getActivity().findViewById(R.id.imgNotification);
        imgBack = getActivity().findViewById(R.id.imgBack);
        txtHeading = getActivity().findViewById(R.id.txtHeading);
        relativeNotCnt = getActivity().findViewById(R.id.relativeNotCnt);
        txtNotCount = getActivity().findViewById(R.id.txtNotCount);

        //txtNotCount.setVisibility(View.GONE);
        // relativeNotCnt.setVisibility(View.GONE);
        imgBack.setVisibility(View.GONE);
        imgNotification.setVisibility(View.VISIBLE);

        txtHeading.setText("Wallet Summary");

        if (SharedPref.getPreferences().getString(SharedPref.NOTIFICATION_COUNTER, "").equalsIgnoreCase("") ||
                SharedPref.getPreferences().getString(SharedPref.NOTIFICATION_COUNTER, "").equalsIgnoreCase("0")) {
            txtNotCount.setText("");
            txtNotCount.setVisibility(View.GONE);
            relativeNotCnt.setVisibility(View.GONE);

        } else {
            txtNotCount.setText(SharedPref.getPreferences().getString(SharedPref.NOTIFICATION_COUNTER, ""));
            txtNotCount.setVisibility(View.VISIBLE);
            relativeNotCnt.setVisibility(View.VISIBLE);
            //imgNotification.setVisibility(View.VISIBLE);
        }

        imgNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ActivityNotification.class);
                startActivity(i);
            }
        });

        fm = getActivity().getSupportFragmentManager();
        txtTotalBal = view.findViewById(R.id.txtTotalBal);
        txtAddMoney = view.findViewById(R.id.txtAddMoney);
        rv_priviouudTrans = view.findViewById(R.id.rl_priviousTrans);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv_priviouudTrans.setLayoutManager(layoutManager);


        txtAddMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new AddMoneyFragment();
                AppUtils.replaceFragment(fragment, fm, null);
            }
        });

    }

    private void callPaymentHistoryApi() {
        String url = AppConstants.BASE_URL +"payment_history";
        System.out.println("Payment History URL > " +url);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token",SharedPref.getPreferences().getString(SharedPref.TOKEN,""));
            Log.e("TAG","Request > " +jsonObject);

            new APIRequest(getContext(),url,jsonObject,this,AppConstants.API_PAYMENT_HISTORY,AppConstants.POST);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(BaseResponse response) {
        PaymentHistory paymentHistory = (PaymentHistory) response;
        if (paymentHistory.getStatus().equalsIgnoreCase("Success")){
           // AppUtils.toastMsg(getContext(),paymentHistory.getDescription());
            if(paymentHistory.getBalance().equalsIgnoreCase(""))
            {
                pref.writeString(SharedPref.WALLET_BAL,"0");
            }else
            {
                pref.writeString(SharedPref.WALLET_BAL,paymentHistory.getBalance());
            }

            double amnt = Double.parseDouble(SharedPref.getPreferences().getString(SharedPref.WALLET_BAL,""));
            txtTotalBal.setText(getActivity().getResources().getString(R.string.rupee)+round(amnt, 2));
            System.out.println("******bal*****"+amnt);
            rv_priviouudTrans.setAdapter(new PriviousTransAdapter(getActivity(),paymentHistory.getPaymentData()));
        }else {
            double amnt = Double.parseDouble(SharedPref.getPreferences().getString(SharedPref.WALLET_BAL,""));
            txtTotalBal.setText(getActivity().getResources().getString(R.string.rupee)+round(amnt, 2));
            if(paymentHistory.getBalance().equalsIgnoreCase(""))
            {
                pref.writeString(SharedPref.WALLET_BAL,"0");
            }else
            {
                pref.writeString(SharedPref.WALLET_BAL,paymentHistory.getBalance());
            }
        }
    }

    @Override
    public void onFailure(BaseResponse response) {

    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

}
