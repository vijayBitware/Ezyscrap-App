package com.ezyscrap.View.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ezyscrap.AppUtils.SharedPref;
import com.ezyscrap.R;
import com.ezyscrap.View.Activity.PaytmActivity;
import com.ezyscrap.webservice.AppConstants;

public class AddMoneyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    TextView txtAvlBal;
    EditText edtAmnt;
    View view;
    Button btnAddMoney;
    ImageView imgBack, imgNotification;
    TextView txtHeading, txtNotCount;
    RelativeLayout relativeNotCnt;
    Fragment fragment;
    FragmentManager fm;
    SharedPref pref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_money,container,false);
        init();
        return view;
    }

    private void init() {
        System.out.println("******init*******");
        pref = new SharedPref(getActivity());
        imgNotification = getActivity().findViewById(R.id.imgNotification);
        imgBack = getActivity().findViewById(R.id.imgBack);
        txtHeading = getActivity().findViewById(R.id.txtHeading);
        relativeNotCnt = getActivity().findViewById(R.id.relativeNotCnt);
        txtNotCount = getActivity().findViewById(R.id.txtNotCount);

        txtNotCount.setVisibility(View.GONE);
        relativeNotCnt.setVisibility(View.GONE);
        imgBack.setVisibility(View.VISIBLE);
        imgNotification.setVisibility(View.GONE);

        txtHeading.setText("Add Money");
        fm = getActivity().getSupportFragmentManager();
        edtAmnt = view.findViewById(R.id.edtAmnt);
        txtAvlBal = view.findViewById(R.id.txtAvlBal);
        btnAddMoney = view.findViewById(R.id.btnAddMoney);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm.popBackStackImmediate();
            }
        });

        btnAddMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtAmnt.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(getActivity(), "Please enter amount",Toast.LENGTH_SHORT).show();

                }else
                {
                    AppConstants.PAYU_REDIRECT = "bid";
                    Intent i = new Intent(getActivity(), PaytmActivity.class);
                    i.putExtra("amount", edtAmnt.getText().toString());
                    i.putExtra("orderId","Ord"+SharedPref.getPreferences().getString(SharedPref.USER_ID,""));
                    i.putExtra("regFee", "No");
                    startActivity(i);
                    //Toast.makeText(getActivity(),"Money added to wallet", Toast.LENGTH_SHORT).show();
                   //int bal = Integer.parseInt(AppConstants.AVAL_BAL) + Integer.parseInt(edtAmnt.getText().toString());
                    //AppConstants.AVAL_BAL = String.valueOf(bal);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        edtAmnt.setText("");
        String amount = SharedPref.getPreferences().getString(SharedPref.WALLET_BAL,"");
        System.out.println("*************amnt***********"+amount);
        txtAvlBal.setText("₹"+round(Double.parseDouble(amount), 2));
        System.out.println("**********resume*******");
        relativeNotCnt.setVisibility(View.GONE);
       if(AppConstants.PAYU_REDIRECT.equalsIgnoreCase("bid"))
        {
            //fm = getActivity().getSupportFragmentManager();
            AppConstants.PAYU_REDIRECT = "";
            //fm.popBackStackImmediate();
        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
