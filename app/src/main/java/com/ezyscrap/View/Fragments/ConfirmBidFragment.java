package com.ezyscrap.View.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ezyscrap.AppUtils.AlertClass;
import com.ezyscrap.AppUtils.AppUtils;
import com.ezyscrap.AppUtils.SharedPref;
import com.ezyscrap.Model.Login.Buyer.BuyerReqResponse.BidConfirmationRes;
import com.ezyscrap.R;
import com.ezyscrap.webservice.APIRequest;
import com.ezyscrap.webservice.AppConstants;
import com.ezyscrap.webservice.BaseResponse;

import org.json.JSONException;
import org.json.JSONObject;


public class ConfirmBidFragment extends Fragment implements APIRequest.ResponseHandler {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
   // ImageView imgBack;
    TextView tvTotalAmnt, tvConfirmBid;
    View view;
    String scrapId, amount, amntWithGst, amntPerKg, gstAmnt;
    Bundle bundle;
    Fragment fragment;
    FragmentManager fm;
    ImageView imgBack, imgNotification;
    TextView txtHeading, txtNotCount;
    RelativeLayout relativeNotCnt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_confirm_bid, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        imgNotification = getActivity().findViewById(R.id.imgNotification);
        imgBack = getActivity().findViewById(R.id.imgBack);
        txtHeading = getActivity().findViewById(R.id.txtHeading);
        relativeNotCnt = getActivity().findViewById(R.id.relativeNotCnt);
        txtNotCount = getActivity().findViewById(R.id.txtNotCount);

        txtNotCount.setVisibility(View.GONE);
        relativeNotCnt.setVisibility(View.GONE);
        imgBack.setVisibility(View.VISIBLE);
        imgNotification.setVisibility(View.GONE);

        txtHeading.setText("Confirm Bid");

        fm = getActivity().getSupportFragmentManager();
        //imgBack = view.findViewById(R.id.imgBack);
        tvTotalAmnt = view.findViewById(R.id.tvTotalAmnt);
        tvConfirmBid = view.findViewById(R.id.tvConfirmBid);

        bundle = getArguments();
        gstAmnt = bundle.getString("gst");
        amount = bundle.getString("amount");
        amntPerKg = bundle.getString("amountPerKg");
        amntWithGst = bundle.getString("amountWithGst");

        double amnt = Double.parseDouble(amntWithGst);
        tvTotalAmnt.setText(String.valueOf(round(amnt, 2)));
        scrapId = bundle.getString("scrapId");

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm.popBackStackImmediate();
            }
        });

        tvConfirmBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmBid();
            }
        });
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    private void confirmBid() {
        if (AppUtils.isConnectingToInternet(getActivity())) {
            String getUserUrl = AppConstants.BASE_URL + "add_scrap_bidding";
            System.out.println("add_scrap_interest Url >" + getUserUrl);
            JSONObject jsonObject = new JSONObject();
            try {

                jsonObject.put("token", SharedPref.getPreferences().getString(SharedPref.TOKEN, ""));// SharedPref.getPreferences().getString(SharedPref.BUYER_TOKEN, ""));
                jsonObject.put("bid_amt", amntPerKg);
                jsonObject.put("total_amt", amntWithGst);
                jsonObject.put("gst", gstAmnt);
                jsonObject.put("scrap_id", scrapId);
                new APIRequest(getActivity(), getUserUrl, jsonObject, this, AppConstants.API_ADD_BID, AppConstants.POST);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {

            AlertClass alert = new AlertClass();
            alert.customDialogforAlertMessage(getActivity(), getResources().getString(R.string.no_internet), "Check your internet connection");

        }
    }

    @Override
    public void onSuccess(BaseResponse response) {
        BidConfirmationRes res = (BidConfirmationRes) response;

        if (res.getStatus().equalsIgnoreCase("success")) {
            Toast.makeText(getActivity(), res.getDescription().toString(), Toast.LENGTH_SHORT).show();
            String bidId = res.getBid_id();

            fragment = new BidConfirmationFragment();
            Bundle bundle = new Bundle();
            bundle.putString("bidId", bidId);
            AppUtils.replaceFragment(fragment, fm, bundle);


        } else {
            Toast.makeText(getActivity(), res.getDescription().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onFailure(BaseResponse response) {

    }

    @Override
    public void onResume() {
        super.onResume();
        relativeNotCnt.setVisibility(View.GONE);
    }
}
