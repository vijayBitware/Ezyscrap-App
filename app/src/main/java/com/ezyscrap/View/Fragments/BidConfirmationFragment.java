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

import com.ezyscrap.R;


public class BidConfirmationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //ImageView imgBack;
    View view;
    FragmentManager fm;
    TextView tvBidId;
    Bundle bundle;
    ImageView imgBack, imgNotification;
    TextView txtHeading, txtNotCount;
    RelativeLayout relativeNotCnt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_bid_confirmation, container, false);
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
        imgBack.setVisibility(View.GONE);
        imgNotification.setVisibility(View.GONE);

        txtHeading.setText("Bid Confirmation");

        bundle = getArguments();
        fm = getActivity().getSupportFragmentManager();
        //imgBack = view.findViewById(R.id.imgBack);
        tvBidId = view.findViewById(R.id.tvBidId);

        tvBidId.setText("#"+bundle.getString("bidId"));

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
        relativeNotCnt.setVisibility(View.GONE);
    }
}
