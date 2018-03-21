package com.ezyscrap.View.Fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ezyscrap.AppUtils.AppUtils;
import com.ezyscrap.AppUtils.SharedPref;
import com.ezyscrap.R;

public class AddBidFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    EditText edtMinAmnt;
    TextView tvTotalAmnt, tvPlaceBid;
    View view;
    Bundle bundle;
    Fragment fragment;
    FragmentManager fm;
    String scrapId, minAmnt, minUnit, gst, gstAmnt;
    //ImageView imgBack;
    ImageView imgBack, imgNotification;
    TextView txtHeading, txtNotCount, tvHeading, txtHdminAmnt;
    RelativeLayout relativeNotCnt;
    float totalAmnt;
    double amntWithGst;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_bid, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        imgNotification = getActivity().findViewById(R.id.imgNotification);
        imgBack = getActivity().findViewById(R.id.imgBack);
        txtHdminAmnt = view.findViewById(R.id.txtHdminAmnt);
        txtHeading = getActivity().findViewById(R.id.txtHeading);
        relativeNotCnt = getActivity().findViewById(R.id.relativeNotCnt);
        txtNotCount = getActivity().findViewById(R.id.txtNotCount);
        tvHeading = view.findViewById(R.id.tvHeading);
        txtNotCount.setVisibility(View.GONE);
        relativeNotCnt.setVisibility(View.GONE);
        imgBack.setVisibility(View.VISIBLE);
        imgNotification.setVisibility(View.GONE);

        txtHeading.setText("Place Bid");

        fm = getActivity().getSupportFragmentManager();
        bundle = getArguments();
        scrapId = bundle.getString("scrapId");
        minAmnt = bundle.getString("minAmnt");
        minUnit = bundle.getString("minUnit");
        gst = bundle.getString("gst");

        String arr[] = minAmnt.split(" ");
        System.out.println("*********"+arr[0]);
        if(arr[1].equalsIgnoreCase("kg"))
        {
            txtHdminAmnt.setText("Minimum Amount Per kg For Bid " + getResources().getString(R.string.rupee) + arr[0]);
        }else  if(arr[1].equalsIgnoreCase("ql"))
        {
            txtHdminAmnt.setText("Minimum Amount Per ql For Bid " + getResources().getString(R.string.rupee) + arr[0]);
        }else  if(arr[1].equalsIgnoreCase("ton"))
        {
            txtHdminAmnt.setText("Minimum Amount Per ton For Bid " + getResources().getString(R.string.rupee) + arr[0]);
        }


        tvHeading.setText("Total Bid Amount (Including GST-" + gst + "%" + ")");
        //imgBack = view.findViewById(R.id.imgBack);
        edtMinAmnt = view.findViewById(R.id.edtMinAmnt);
        tvTotalAmnt = view.findViewById(R.id.tvTotalAmnt);
        tvPlaceBid = view.findViewById(R.id.tvPlaceBid);
        tvTotalAmnt.setText(getResources().getString(R.string.rupee) + "0.00");

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm.popBackStackImmediate();
            }
        });

        edtMinAmnt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String amnt = charSequence.toString();
                System.out.println("******amount*********" + amnt);

                if (!amnt.equalsIgnoreCase("")) {
                    totalAmnt = Float.parseFloat(amnt) * Float.parseFloat(minUnit);

                    System.out.println("**********gst*********"+gst);
                    float totalGstAmnt = totalAmnt * Integer.parseInt(gst) / 100;
                    gstAmnt = String.valueOf(totalGstAmnt);
                    amntWithGst = totalAmnt + totalAmnt * Integer.parseInt(gst) / 100;

                    System.out.println("********gst amnt*******" + amntWithGst+"**"+gstAmnt);

                    //tvTotalAmnt.setText(getResources().getString(R.string.rupee) + String.valueOf(totalAmnt));


                    tvTotalAmnt.setText(getResources().getString(R.string.rupee) + String.valueOf(round(amntWithGst, 2)));
                } else {
                    tvTotalAmnt.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        tvPlaceBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation()) {

                    double halfAmnt = amntWithGst;
                    System.out.println("************half********" + halfAmnt);
                    if (halfAmnt >= Double.parseDouble(SharedPref.getPreferences().getString(SharedPref.WALLET_BAL, ""))) {
                        final Dialog dialog = new Dialog(getActivity());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
                        dialog.setContentView(R.layout.popup_addmoney);

                        // set the custom dialog components - text, image and button
                        TextView txtminAmnt = dialog.findViewById(R.id.txtminAmnt);
                        ImageView imgCancel = (ImageView) dialog.findViewById(R.id.imgCancel);
                        LinearLayout linearAddMoney = (LinearLayout) dialog.findViewById(R.id.linearAddMoney);

                        txtminAmnt.setText("(Minimum amount "+getResources().getString(R.string.rupee)+round(halfAmnt, 2)+")");
                        imgCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.cancel();
                            }
                        });

                        linearAddMoney.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.cancel();

                                fragment = new AddMoneyFragment();

                                AppUtils.replaceFragmentWithoutBackstack(fragment, fm, null);
                            }
                        });

                        dialog.setCancelable(false);
                        dialog.show();

                    } else {
                        fragment = new ConfirmBidFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("scrapId", scrapId);
                        bundle.putString("amount", String.valueOf(totalAmnt));
                        bundle.putString("amountPerKg", edtMinAmnt.getText().toString());
                        bundle.putString("amountWithGst", String.valueOf(amntWithGst));
                        bundle.putString("gst", String.valueOf(gstAmnt));

                        AppUtils.replaceFragment(fragment, fm, bundle);
                    }

                }
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


    private boolean validation() {
        String arr[] = minAmnt.split(" ");
        if (edtMinAmnt.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter bid amount", Toast.LENGTH_SHORT).show();
            return false;
        } else if (Integer.parseInt(edtMinAmnt.getText().toString()) < Integer.parseInt(arr[0])) {
            Toast.makeText(getActivity(), "Bid amount should be greater than minimum amount" + "(" + arr[0] + ")", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        relativeNotCnt.setVisibility(View.GONE);
    }
}
