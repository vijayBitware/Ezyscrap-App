package com.ezyscrap.View.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ezyscrap.AppUtils.AlertClass;
import com.ezyscrap.AppUtils.AppUtils;
import com.ezyscrap.AppUtils.SharedPref;
import com.ezyscrap.Controller.MyPagerAdapter;
import com.ezyscrap.Model.Login.Buyer.BuyerReqResponse.BuyerInterestResponse;
import com.ezyscrap.Model.Login.Buyer.BuyerReqResponse.ScrapDetailsResponse;
import com.ezyscrap.R;
import com.ezyscrap.View.Activity.PaytmActivity;
import com.ezyscrap.View.MyFirebaseMessagingService;
import com.ezyscrap.webservice.APIRequest;
import com.ezyscrap.webservice.AppConstants;
import com.ezyscrap.webservice.BaseResponse;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.crosswall.lib.coverflow.CoverFlow;
import me.crosswall.lib.coverflow.core.PageItemClickListener;
import me.crosswall.lib.coverflow.core.PagerContainer;

public class ScrapReqDetailsFragment extends Fragment implements APIRequest.ResponseHandler {

    SharedPref pref;
    TextView txtOrderNo, txtScrapType, txtDesc, txtUnit, txtMinAmnt, tvBid, tvInterest;
    // ImageView imgScrap;
    View view;
    Bundle bundle;
    Fragment fragment;
    FragmentManager fm;
    String scrapId, minAmount, minUnit, unit, bidFlag, gst;
    boolean isInterested;
    ImageView imgBack, imgNotification;
    TextView txtHeading, txtNotCount, txtBidAmnt, txtHdAmnt;
    RelativeLayout relativeNotCnt;
    LinearLayout linearBidAmnt;
    PagerContainer pager_container;
    ViewPager pager;
    CirclePageIndicator indicator;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    //int[] sliderImages = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    String[] sliderImag;
    ArrayList<String> sliderImgList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_scrap_req_details, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        System.out.println("*******details******");
        //clears notification from statusbar
        MyFirebaseMessagingService notif = new MyFirebaseMessagingService();
        notif.cancelNotification();
        sliderImgList = new ArrayList<>();
        imgNotification = getActivity().findViewById(R.id.imgNotification);
        imgBack = getActivity().findViewById(R.id.imgBack);
        txtHeading = getActivity().findViewById(R.id.txtHeading);
        relativeNotCnt = getActivity().findViewById(R.id.relativeNotCnt);
        txtNotCount = getActivity().findViewById(R.id.txtNotCount);

        txtNotCount.setVisibility(View.GONE);
        relativeNotCnt.setVisibility(View.GONE);
        imgBack.setVisibility(View.VISIBLE);
        imgNotification.setVisibility(View.GONE);
        txtHeading.setText("Scrap Request Details");

        fm = getActivity().getSupportFragmentManager();
        bundle = getArguments();
        pref = new SharedPref(getActivity());
        txtHdAmnt = view.findViewById(R.id.txtHdAmnt);
        txtBidAmnt = view.findViewById(R.id.txtBidAmnt);
        tvInterest = view.findViewById(R.id.tvInterest);
        tvBid = view.findViewById(R.id.tvBid);
        txtOrderNo = view.findViewById(R.id.txtOrderNo);
        txtScrapType = view.findViewById(R.id.txtScrapType);
        txtDesc = view.findViewById(R.id.txtDesc);
        txtUnit = view.findViewById(R.id.txtUnit);
        tvBid = view.findViewById(R.id.tvBid);
        txtMinAmnt = view.findViewById(R.id.txtMinAmnt);
        linearBidAmnt = view.findViewById(R.id.linearBidAmnt);

        pager_container = view.findViewById(R.id.pager_container);
        pager = view.findViewById(R.id.overlap_pager);
        indicator = view.findViewById(R.id.indicator);

        System.out.println("***********ezyscrap id*******" + AppConstants.SCRAP_ID);
        if (!AppConstants.SCRAP_ID.equalsIgnoreCase("")) {
            getScrapDetails();
        } else {
            System.out.println("*****flag****" + bundle.getBoolean("flag"));
            if (bundle.getBoolean("flag")) {
                tvBid.setVisibility(View.VISIBLE);
                tvInterest.setVisibility(View.GONE);

            } else {
                tvBid.setVisibility(View.GONE);
                tvInterest.setVisibility(View.VISIBLE);
            }

            if (!bundle.getString("scrapImg1").equalsIgnoreCase("")) {
                System.out.println("***********1" + bundle.getString("scrapImg1"));
                sliderImag = new String[1];
                sliderImag[0] = bundle.getString("scrapImg1");
            }

            if (!bundle.getString("scrapImg2").equalsIgnoreCase("")) {
                System.out.println("***********2" + bundle.getString("scrapImg2"));
                sliderImag = new String[2];
                sliderImag[0] = bundle.getString("scrapImg1");
                sliderImag[1] = bundle.getString("scrapImg2");
            }

            if (!bundle.getString("scrapImg3").equalsIgnoreCase("")) {
                System.out.println("***********3" + bundle.getString("scrapImg3"));
                sliderImag = new String[3];
                sliderImag[0] = bundle.getString("scrapImg1");
                sliderImag[1] = bundle.getString("scrapImg2");
                sliderImag[2] = bundle.getString("scrapImg3");
            }

            if (!bundle.getString("scrapImg4").equalsIgnoreCase("")) {
                System.out.println("***********4" + bundle.getString("scrapImg4"));
                sliderImag = new String[4];
                sliderImag[0] = bundle.getString("scrapImg1");
                sliderImag[1] = bundle.getString("scrapImg2");
                sliderImag[2] = bundle.getString("scrapImg3");
                sliderImag[3] = bundle.getString("scrapImg4");
            }

            bidFlag = bundle.getString("bidFlag");
            if (bidFlag.equalsIgnoreCase("1")) {
                tvBid.setVisibility(View.GONE);
                linearBidAmnt.setVisibility(View.VISIBLE);
            }

            if (bundle.getInt("soldFlag") == 1) {
                tvBid.setVisibility(View.GONE);
                linearBidAmnt.setVisibility(View.VISIBLE);
                tvInterest.setVisibility(View.GONE);
            } else if (bundle.getInt("soldFlag") == 2) {
                tvBid.setVisibility(View.GONE);
                tvInterest.setVisibility(View.GONE);
                linearBidAmnt.setVisibility(View.GONE);
            }

            txtBidAmnt.setText(getResources().getString(R.string.rupee) + bundle.getString("bidAmnt"));
            gst = bundle.getString("gst");
            scrapId = bundle.getString("scrapId");
            txtDesc.setText(bundle.getString("scrapDesc"));
            txtOrderNo.setText("#" + bundle.getString("orderNo"));
            txtScrapType.setText(bundle.getString("scrapType"));
            unit = bundle.getString("unit");
            txtUnit.setText(unit);

            minAmount = bundle.getString("apprAmnt");

            String arrUnit[] = minAmount.split(" ");

            txtMinAmnt.setText(getResources().getString(R.string.rupee) + arrUnit[0]);

            String arr[] = unit.split(" ");
            System.out.println("********unit********" + arr[0]);
            System.out.println("********unit********" + arr[1]);
            if (arr[1].equalsIgnoreCase("kg")) {
                txtHdAmnt.setText("Minimum Amount For Bid Per kg");
                minUnit = arr[0];
            } else if (arr[1].equalsIgnoreCase("ql")) {
                txtHdAmnt.setText("Minimum Amount For Bid Per ql");
                float quintal = Float.parseFloat(arr[0]);//Integer.parseInt(arr[0]) * 100;
                minUnit = String.valueOf(quintal);
            } else if (arr[1].equalsIgnoreCase("ton")) {
                txtHdAmnt.setText("Minimum Amount For Bid Per ton");
                float ton = Float.parseFloat(arr[0]);//Integer.parseInt(arr[0]) * 1000;
                minUnit = String.valueOf(ton);
            }
            System.out.println("*****unit*****" + minUnit);

            setUpIndicator();
        }

        tvBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // if (SharedPref.getPreferences().getString(SharedPref.REG_FEE, "").equalsIgnoreCase("1")) {
                    addBid();
                /*} else {
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
                            dialog.cancel();
                        }
                    });
                    linearAddMoney.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();

                            Intent i = new Intent(getActivity(), PaytmActivity.class);
                            i.putExtra("amount", "5000");
                            i.putExtra("orderId","Reg"+SharedPref.getPreferences().getString(SharedPref.USER_ID,""));
                            i.putExtra("regFee", "yes");
                            startActivity(i);
                        }
                    });

                    dialog.setCancelable(false);
                    dialog.show();
                }*/

            }
        });

        tvInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if (SharedPref.getPreferences().getString(SharedPref.REG_FEE, "").equalsIgnoreCase("1")) {
                    addInterest();
                /*} else {
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

            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstants.SCRAP_ID = "";
                fm.popBackStackImmediate();
            }
        });

    }

    private void getScrapDetails() {
        if (AppUtils.isConnectingToInternet(getActivity())) {
            String getUserUrl = AppConstants.BASE_URL + "scrap_data_by_id";
            System.out.println("buyer_home_screen Url >" + getUserUrl);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("token", SharedPref.getPreferences().getString(SharedPref.TOKEN, ""));
                jsonObject.put("flag", SharedPref.getPreferences().getString(SharedPref.USER_FLAG, ""));
                jsonObject.put("scrap_id", AppConstants.SCRAP_ID);
                new APIRequest(getContext(), getUserUrl, jsonObject, this, AppConstants.API_SCRAP_DETAILS, AppConstants.POST);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {

            AlertClass alert = new AlertClass();
            alert.customDialogforAlertMessage(getActivity(), getResources().getString(R.string.no_internet), "Please check your internet connection");

        }
    }

    private void addInterest() {
        pref = new SharedPref(getActivity());
        if (AppUtils.isConnectingToInternet(getActivity())) {
            String getUserUrl = AppConstants.BASE_URL + "add_scrap_interest";
            System.out.println("add_scrap_interest Url >" + getUserUrl);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("token", SharedPref.getPreferences().getString(SharedPref.TOKEN, ""));
                jsonObject.put("scrap_id", scrapId);
                new APIRequest(getActivity(), getUserUrl, jsonObject, this, AppConstants.API_BUYER_INTEREST, AppConstants.POST);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {

            AlertClass alert = new AlertClass();
            alert.customDialogforAlertMessage(getActivity(), getResources().getString(R.string.no_internet), "Check your internet connection");

        }
    }

    private void addBid() {
        fragment = new AddBidFragment();
        Bundle bundle = new Bundle();
        bundle.putString("minUnit", minUnit);
        bundle.putString("scrapId", scrapId);
        bundle.putString("minAmnt", minAmount);
        bundle.putString("gst", gst);
        AppUtils.replaceFragment(fragment, fm, bundle);
    }

    @Override
    public void onSuccess(BaseResponse response) {
        if (response.getApiName() == 113) {
            ScrapDetailsResponse responseList = (ScrapDetailsResponse) response;
            System.out.println("###>>> : " + responseList.getDescription().toString());
            if (responseList.getStatus().toString().equalsIgnoreCase("success")) {

                tvBid.setVisibility(View.GONE);
                tvInterest.setVisibility(View.VISIBLE);

                scrapId = responseList.getScrapData().getId();
                txtDesc.setText(responseList.getScrapData().getDesc());
                txtOrderNo.setText("#" + responseList.getScrapData().getOrderId());
                txtScrapType.setText(responseList.getScrapData().getType());
                unit = responseList.getScrapData().getQuantity();
                txtUnit.setText(unit);
                minAmount = responseList.getScrapData().getMinAmt();
                txtMinAmnt.setText(getResources().getString(R.string.rupee) + minAmount);
                gst = responseList.getScrapData().getGst();
                String arr[] = unit.split("[a-zA-Z]+");
                minUnit = arr[0];
                System.out.println("*****unit*****" + minUnit);

                bidFlag = responseList.getScrapData().getBid();
                if (bidFlag.equalsIgnoreCase("1")) {
                    tvBid.setVisibility(View.GONE);
                    linearBidAmnt.setVisibility(View.VISIBLE);
                }

                if(responseList.getScrapData().getSoldFlag().toString().equalsIgnoreCase("1")) {
                    tvBid.setVisibility(View.GONE);
                    linearBidAmnt.setVisibility(View.VISIBLE);
                    tvInterest.setVisibility(View.GONE);
                } else if (responseList.getScrapData().getSoldFlag().toString().equalsIgnoreCase("2")) {
                    tvBid.setVisibility(View.GONE);
                    tvInterest.setVisibility(View.GONE);
                    linearBidAmnt.setVisibility(View.GONE);
                }

                String arr1[] = unit.split(" ");
                System.out.println("********unit********" + arr1[0]);
                System.out.println("********unit********" + arr1[1]);
                if (arr1[1].equalsIgnoreCase("kg")) {
                    txtHdAmnt.setText("Minimum Amount For Bid Per kg");
                    minUnit = arr1[0];
                } else if (arr1[1].equalsIgnoreCase("ql")) {
                    txtHdAmnt.setText("Minimum Amount For Bid Per ql");
                    float quintal = Float.parseFloat(arr1[0]);//Integer.parseInt(arr[0]) * 100;
                    minUnit = String.valueOf(quintal);
                } else if (arr1[1].equalsIgnoreCase("ton")) {
                    txtHdAmnt.setText("Minimum Amount For Bid Per ton");
                    float ton = Float.parseFloat(arr1[0]);//Integer.parseInt(arr[0]) * 1000;
                    minUnit = String.valueOf(ton);
                }

                String image1 = responseList.getScrapData().getImage1();
                String image2 = responseList.getScrapData().getImage2();
                String image3 = responseList.getScrapData().getImage3();
                String image4 = responseList.getScrapData().getImage4();

                AppConstants.SCRAP_ID = "";

                if (!image1.equalsIgnoreCase("")) {
                    System.out.println("***********1" + image1);
                    sliderImag = new String[1];
                    sliderImag[0] = image1;
                }

                if (!image2.equalsIgnoreCase("")) {
                    System.out.println("***********2" + image2);
                    sliderImag = new String[2];
                    sliderImag[0] =image1;
                    sliderImag[1] = image2;
                }

                if (!image3.equalsIgnoreCase("")) {
                    System.out.println("***********3" + image3);
                    sliderImag = new String[3];
                    sliderImag[0] = image1;
                    sliderImag[1] = image2;
                    sliderImag[2] = image3;
                }

                if (!image4.equalsIgnoreCase("")) {
                    System.out.println("***********4" + image4);
                    sliderImag = new String[4];
                    sliderImag[0] = image1;
                    sliderImag[1] = image2;
                    sliderImag[2] = image3;
                    sliderImag[3] = image4;
                }

                setUpIndicator();
            } else {
                Toast.makeText(getActivity(), responseList.getDescription().toString(), Toast.LENGTH_SHORT).show();
            }
        } else {
            BuyerInterestResponse responseList = (BuyerInterestResponse) response;
            System.out.println("###>>> : " + responseList.getDescription().toString());
            if (responseList.getStatus().toString().equalsIgnoreCase("success")) {

                Toast.makeText(getActivity(), responseList.getDescription().toString(), Toast.LENGTH_SHORT).show();
                AppConstants.INTEREST = true;
                tvInterest.setVisibility(View.GONE);
                tvBid.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(getActivity(), responseList.getDescription().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onFailure(BaseResponse response) {
        //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
    }

    private void setUpIndicator() {

        System.out.println("*****length****" + sliderImag.length);
        for (int i = 0; i < sliderImag.length; i++) {
            sliderImgList.add(sliderImag[i]);
            System.out.println("*********" + sliderImag[i]);
        }

        pager = pager_container.getViewPager();
        pager.setAdapter(new MyPagerAdapter(getContext(), sliderImgList));
        pager.setClipChildren(false);
        //
        pager.setOffscreenPageLimit(15);
        pager_container.setPageItemClickListener(new PageItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // Toast.makeText(ProductDetailsScreen.this,"position:" + position, Toast.LENGTH_SHORT).show();
            }
        });
        // boolean showTransformer = getIntent().getBooleanExtra("showTransformer",true);
        boolean showTransformer = true;
        if (showTransformer) {
            new CoverFlow.Builder()
                    .with(pager)
                    .scale(0.5f)
                    .pagerMargin(-10)
                    .spaceSize(1f)
                    .build();
        } else {
            pager.setPageMargin(30);
        }

        indicator.setViewPager(pager);
        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(5 * density);
        // System.out.println(">>> Size of arr :" + arrSliderImages.size());
        NUM_PAGES = sliderImgList.size();
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                pager.setCurrentItem(currentPage++, true);
            }
        };

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (AppConstants.INTEREST) {
            tvBid.setVisibility(View.VISIBLE);
            tvInterest.setVisibility(View.GONE);
        }
        relativeNotCnt.setVisibility(View.GONE);
    }
}
