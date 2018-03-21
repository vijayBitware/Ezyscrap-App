package com.ezyscrap.View.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ezyscrap.AppUtils.AlertClass;
import com.ezyscrap.AppUtils.AppUtils;
import com.ezyscrap.AppUtils.SharedPref;
import com.ezyscrap.Controller.BuyerReqAdapter;
import com.ezyscrap.Model.Login.Buyer.BuyerReqResponse.BuyerReqList;
import com.ezyscrap.Model.Login.BuyerReqModel;
import com.ezyscrap.R;
import com.ezyscrap.View.Activity.ActivityNotification;
import com.ezyscrap.View.Activity.PaytmActivity;
import com.ezyscrap.webservice.APIRequest;
import com.ezyscrap.webservice.AppConstants;
import com.ezyscrap.webservice.BaseResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BuyerRequestFragment extends Fragment implements APIRequest.ResponseHandler {
    SharedPref pref;
    View view;
    RecyclerView recyclerRequest;
    BuyerReqAdapter adapter;
    LinearLayoutManager lm;
    ArrayList<BuyerReqModel> list;
    Bundle bundle;
    ImageView imgBack, imgNotification;
    TextView txtHeading, txtNotCount, txtNoRecords;
    RelativeLayout relativeNotCnt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_buyer_request, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        pref = new SharedPref(getActivity());
        txtNoRecords = view.findViewById(R.id.txtNoRecords);
        if (SharedPref.getPreferences().getString(SharedPref.WALLET_BAL, "").equalsIgnoreCase("") ||
                SharedPref.getPreferences().getString(SharedPref.WALLET_BAL, "").equalsIgnoreCase("null")) {
            pref.writeString(SharedPref.WALLET_BAL, "0");
        }
        System.out.println("*****wall***" + SharedPref.getPreferences().getString(SharedPref.WALLET_BAL, ""));
        imgNotification = getActivity().findViewById(R.id.imgNotification);
        imgBack = getActivity().findViewById(R.id.imgBack);
        txtHeading = getActivity().findViewById(R.id.txtHeading);
        relativeNotCnt = getActivity().findViewById(R.id.relativeNotCnt);
        txtNotCount = getActivity().findViewById(R.id.txtNotCount);

        //txtNotCount.setVisibility(View.VISIBLE);
        //relativeNotCnt.setVisibility(View.VISIBLE);
        imgBack.setVisibility(View.GONE);
        //imgNotification.setVisibility(View.VISIBLE);
        txtHeading.setText("Latest Requests");
        imgNotification.setVisibility(View.VISIBLE);

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

        list = new ArrayList<>();
        lm = new LinearLayoutManager(getActivity());
        recyclerRequest = view.findViewById(R.id.recyclerRequest);
        recyclerRequest.setLayoutManager(lm);
        recyclerRequest.setHasFixedSize(true);

        if (!AppConstants.SCRAP_ID.equalsIgnoreCase("")) {
            AppUtils.replaceFragment(new ScrapReqDetailsFragment(), getFragmentManager(), null);
        } else {

            getBuyerReqList();
        }

    }

    private void getBuyerReqList() {

        if (AppUtils.isConnectingToInternet(getActivity())) {
            String getUserUrl = AppConstants.BASE_URL + "buyer_home_screen";
            System.out.println("buyer_home_screen Url >" + getUserUrl);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("token", SharedPref.getPreferences().getString(SharedPref.TOKEN, ""));
                new APIRequest(getContext(), getUserUrl, jsonObject, this, AppConstants.API_BUYER_REQ, AppConstants.POST);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {

            AlertClass alert = new AlertClass();
            alert.customDialogforAlertMessage(getActivity(), getResources().getString(R.string.no_internet), "Please check your internet connection");

        }
    }

    @Override
    public void onSuccess(BaseResponse response) {

        BuyerReqList res = (BuyerReqList) response;

        if (res.getStatus().equalsIgnoreCase("success")) {
            recyclerRequest.setVisibility(View.VISIBLE);
            txtNoRecords.setVisibility(View.GONE);
            list.clear();

            pref.writeString(SharedPref.REG_FEE, res.getRegFee());

            for (int i = 0; i < res.getScrapData().size(); i++) {
                BuyerReqModel data = new BuyerReqModel();
                data.setOrderNo(res.getScrapData().get(i).getOrderId());
                data.setScrapType(res.getScrapData().get(i).getType());
                data.setUnit(res.getScrapData().get(i).getQuantity());
                data.setScrapId(res.getScrapData().get(i).getId());
                data.setScrapDesc(res.getScrapData().get(i).getDesc());
                data.setApprAmnt(res.getScrapData().get(i).getMinAmt());
                data.setFlag(res.getScrapData().get(i).getFlag());
                data.setScrapImg1(res.getScrapData().get(i).getImage1());
                data.setScrapImg2(res.getScrapData().get(i).getImage2());
                data.setScrapImg3(res.getScrapData().get(i).getImage3());
                data.setScrapImg4(res.getScrapData().get(i).getImage4());
                data.setBidFlag(res.getScrapData().get(i).getBid());
                data.setSoldFlag(res.getScrapData().get(i).getSoldFlag());
                data.setGst(res.getScrapData().get(i).getGst());
                data.setBidAmnt(res.getScrapData().get(i).getBidAmt());

                if (res.getScrapData().get(i).getFlag().equalsIgnoreCase("1")) {
                    data.setInterestflag(true);
                } else {
                    data.setInterestflag(false);
                }

                list.add(data);
            }

            adapter = new BuyerReqAdapter(getActivity(), list);
            recyclerRequest.setAdapter(adapter);

            /*if (res.getRegFee().equalsIgnoreCase("0")) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
                dialog.setContentView(R.layout.popup_registration_fee);

                // set the custom dialog components - text, image and button

                LinearLayout linearAddMoney = (LinearLayout) dialog.findViewById(R.id.linearAddMoney);
                ImageView imgCancel = (ImageView) dialog.findViewById(R.id.imgCancel);

                imgCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println("******click*******");
                        dialog.cancel();
                    }
                });

                linearAddMoney.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();

                        Intent i = new Intent(getActivity(), PaytmActivity.class);
                        i.putExtra("amount", "5000");
                        i.putExtra("orderId", SharedPref.getPreferences().getString(SharedPref.USER_ID, ""));
                        i.putExtra("regFee", "yes");
                        startActivity(i);
                    }
                });

                dialog.setCancelable(false);
                dialog.show();

            }*/

        } else {
            pref.writeString(SharedPref.WALLET_BAL, res.getWalletBalance());
            pref.writeString(SharedPref.REG_FEE, res.getRegFee());

           /* if (res.getRegFee().equalsIgnoreCase("0")) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
                dialog.setContentView(R.layout.popup_registration_fee);

                // set the custom dialog components - text, image and button

                LinearLayout linearAddMoney = (LinearLayout) dialog.findViewById(R.id.linearAddMoney);
                ImageView imgCancel = (ImageView) dialog.findViewById(R.id.imgCancel);

                imgCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println("******click*******");
                        dialog.cancel();
                    }
                });

                linearAddMoney.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();

                        Intent i = new Intent(getActivity(), PaytmActivity.class);
                        i.putExtra("amount", "5000");
                        i.putExtra("regFee", "yes");
                        startActivity(i);
                    }
                });

                dialog.setCancelable(false);
                dialog.show();

            }*/
            recyclerRequest.setVisibility(View.GONE);
            txtNoRecords.setVisibility(View.VISIBLE);
            // Toast.makeText(getActivity(), res.getDescription().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onFailure(BaseResponse response) {

    }

    @Override
    public void onResume() {
        super.onResume();

        AppConstants.INTEREST = false;
    }
}
