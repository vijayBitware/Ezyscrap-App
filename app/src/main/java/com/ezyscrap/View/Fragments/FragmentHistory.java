package com.ezyscrap.View.Fragments;

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
import com.ezyscrap.Controller.AdapterBuyerHistory;
import com.ezyscrap.Model.Login.BuyerReqModel;
import com.ezyscrap.Model.OrderHistory.OrderHistory;
import com.ezyscrap.R;
import com.ezyscrap.webservice.APIRequest;
import com.ezyscrap.webservice.AppConstants;
import com.ezyscrap.webservice.BaseResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by bitware on 28/12/17.
 */

public class FragmentHistory extends Fragment implements APIRequest.ResponseHandler{

    Fragment fragment;
    FragmentManager fm;
    ArrayList<BuyerReqModel> list;
    View view;
    RecyclerView rv_history;
    ImageView imgBack, imgNotification;
    TextView txtHeading, txtNotCount,txt_noRecords;
    RelativeLayout relativeNotCnt;
    AdapterBuyerHistory adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_history,container,false);
        init();
        if (AppUtils.isConnectingToInternet(getContext())){
            callHistoryApi();
        }else {
            AppUtils.toastMsg(getContext(),getResources().getString(R.string.no_internet));
        }
        return view;
    }

    private void callHistoryApi() {
        String url = AppConstants.BASE_URL +"order_history";
        System.out.println("Order History URL > " +url);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", SharedPref.getPreferences().getString(SharedPref.TOKEN, ""));
            Log.e("TAG","Request > " +jsonObject);

            new APIRequest(getContext(),url,jsonObject,this,AppConstants.API_BUYER_ORDER_HISTORY,AppConstants.POST);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        fm = getActivity().getSupportFragmentManager();
        list = new ArrayList<>();
        imgNotification = getActivity().findViewById(R.id.imgNotification);
        imgBack = getActivity().findViewById(R.id.imgBack);
        txtHeading = getActivity().findViewById(R.id.txtHeading);
        relativeNotCnt = getActivity().findViewById(R.id.relativeNotCnt);
        txtNotCount = getActivity().findViewById(R.id.txtNotCount);
        txt_noRecords = view.findViewById(R.id.txt_noRecords);

        txtNotCount.setVisibility(View.GONE);
        relativeNotCnt.setVisibility(View.GONE);
        imgBack.setVisibility(View.VISIBLE);
        imgNotification.setVisibility(View.GONE);
        txtHeading.setText("Order History");

        rv_history = view.findViewById(R.id.rv_history);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv_history.setLayoutManager(layoutManager);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm.popBackStackImmediate();
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        System.out.println("******hstry******");
        txtNotCount.setVisibility(View.GONE);
        relativeNotCnt.setVisibility(View.GONE);
        imgBack.setVisibility(View.VISIBLE);
        imgNotification.setVisibility(View.GONE);
    }

    @Override
    public void onSuccess(BaseResponse response) {
        OrderHistory orderHistory = (OrderHistory) response;
        if (orderHistory.getStatus().equalsIgnoreCase("Success")){
           // AppUtils.toastMsg(getContext(),orderHistory.getDescription());
            txt_noRecords.setVisibility(View.INVISIBLE);
            rv_history.setVisibility(View.VISIBLE);
            adapter = new AdapterBuyerHistory(getActivity(), orderHistory.getScrapData());
            rv_history.setAdapter(adapter);
        }else {
            txt_noRecords.setVisibility(View.VISIBLE);
            rv_history.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onFailure(BaseResponse response) {

    }
}
