package com.ezyscrap.View.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ezyscrap.AppUtils.AppUtils;
import com.ezyscrap.R;

/**
 * Created by bitware on 21/12/17.
 */

public class FragmentOrderDetails extends Fragment implements View.OnClickListener {

    View view;
    ImageView imgBackArrow;
    TextView txtOrderWeight,txtScrapPrice,txtOrderDate,txtBidAmount,txtQty,txtTaxableAmount,txtTotalGst,txtTotal,txt_scrapType,txtNewSeller;
    ImageView imgBack, imgNotification;
    TextView txtHeading, txtNotCount,txtPaidAmount;
    RelativeLayout relativeNotCnt;
    Fragment fragment;
    FragmentManager fm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order_details, container, false);
        init();
        return view;
    }

    private void init() {
        fm = getActivity().getSupportFragmentManager();
        txtOrderWeight = (TextView) view.findViewById(R.id.txtOrderWeight);
        txtScrapPrice = (TextView) view.findViewById(R.id.txtScrapPrice);
        txtOrderDate = (TextView) view.findViewById(R.id.txtOrderDate);
        txtBidAmount = (TextView) view.findViewById(R.id.txtBidAmount);
        txtQty = (TextView) view.findViewById(R.id.txtQty);
        txtTaxableAmount = (TextView) view.findViewById(R.id.txtTaxableAmount);
        txtTotalGst = (TextView) view.findViewById(R.id.txtTotalGst);
        txtTotal = (TextView) view.findViewById(R.id.txtTotal);
        txt_scrapType = view.findViewById(R.id.txt_scrapType);
        txtNewSeller=view.findViewById(R.id.txtNewSeller);
        txtPaidAmount = view.findViewById(R.id.txtPaidAmount);

        imgNotification = getActivity().findViewById(R.id.imgNotification);
        imgBack = getActivity().findViewById(R.id.imgBack);
        txtHeading = getActivity().findViewById(R.id.txtHeading);
        relativeNotCnt = getActivity().findViewById(R.id.relativeNotCnt);
        txtNotCount = getActivity().findViewById(R.id.txtNotCount);


        txtNotCount.setVisibility(View.GONE);
        relativeNotCnt.setVisibility(View.GONE);
        imgBack.setVisibility(View.VISIBLE);
        imgNotification.setVisibility(View.GONE);
        txtHeading.setText("Order Details");
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm.popBackStackImmediate();
            }
        });

        setData();

    }

    private void setData() {
        txtOrderWeight.setText(getArguments().getString("unit"));
        txt_scrapType.setText(getArguments().getString("scrapType"));
        txtScrapPrice.setText(getResources().getString(R.string.rupee)+round(Double.parseDouble(getArguments().getString("totalAmnt")),2));
        txtBidAmount.setText(getResources().getString(R.string.rupee)+getArguments().getString("amntPerKg"));
        txtQty.setText(getArguments().getString("unit"));
        txtTaxableAmount.setText(getResources().getString(R.string.rupee)+getArguments().getString("amount"));
        txtTotal.setText(getResources().getString(R.string.rupee)+round(Double.parseDouble(getArguments().getString("totalAmnt")),2));
        txtNewSeller.setText("#"+getArguments().getString("orderNo"));
        txtOrderDate.setText(AppUtils.formattedDate(getArguments().getString("date")));
        txtTotalGst.setText(getResources().getString(R.string.rupee)+getArguments().getString("gst"));
        txtPaidAmount.setText(getResources().getString(R.string.rupee)+round(Double.parseDouble(getArguments().getString("totalAmnt")),2));
    }

    @Override
    public void onClick(View view) {

    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    @Override
    public void onResume() {
        super.onResume();
        txtNotCount.setVisibility(View.GONE);
        relativeNotCnt.setVisibility(View.GONE);
        imgBack.setVisibility(View.VISIBLE);
        imgNotification.setVisibility(View.GONE);
    }
}
