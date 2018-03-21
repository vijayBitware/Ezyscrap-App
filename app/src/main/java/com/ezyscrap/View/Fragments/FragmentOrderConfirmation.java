package com.ezyscrap.View.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ezyscrap.R;

/**
 * Created by bitware on 20/12/17.
 */

public class FragmentOrderConfirmation extends Fragment {

    ImageView imgBack, imgNotification;
    TextView txtHeading, txtNotCount,tv_orderId;
    RelativeLayout relativeNotCnt;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order_success, container, false);
        init();
        tv_orderId.setText("#"+getArguments().getString("order_id"));

        return view;
    }

    private void init() {
        imgNotification = getActivity().findViewById(R.id.imgNotification);
        imgBack = getActivity().findViewById(R.id.imgBack);
        txtHeading = getActivity().findViewById(R.id.txtHeading);
        relativeNotCnt = getActivity().findViewById(R.id.relativeNotCnt);
        txtNotCount = getActivity().findViewById(R.id.txtNotCount);
        tv_orderId = view.findViewById(R.id.tv_orderId);

        txtNotCount.setVisibility(View.GONE);
        relativeNotCnt.setVisibility(View.GONE);
        imgBack.setVisibility(View.GONE);
        imgNotification.setVisibility(View.GONE);
        txtHeading.setText("Order Confirmation");

    }
}
