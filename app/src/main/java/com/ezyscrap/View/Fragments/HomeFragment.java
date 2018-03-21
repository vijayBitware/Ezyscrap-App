package com.ezyscrap.View.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.ezyscrap.AppUtils.SharedPref;
import com.ezyscrap.Controller.CoverFlowAdapter;
import com.ezyscrap.Controller.GameEntity;
import com.ezyscrap.Controller.MyPagerAdapter;
import com.ezyscrap.Model.Login.SellerHomeResponse.SellerHomeResponse;
import com.ezyscrap.R;
import com.ezyscrap.View.Activity.ActivityNotification;
import com.ezyscrap.webservice.APIRequest;
import com.ezyscrap.webservice.AppConstants;
import com.ezyscrap.webservice.BaseResponse;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;
import me.crosswall.lib.coverflow.CoverFlow;
import me.crosswall.lib.coverflow.core.PagerContainer;

public class HomeFragment extends Fragment implements View.OnClickListener, APIRequest.ResponseHandler {

    public String orderNumber, scrapType, scrapQuantity, date;
    public int titleResId;
    Fragment fragment;
    FragmentManager fm;
    String token;
    SharedPref pref;
    View view;
    TextView view_liveScrapBottom, view_soldScrapBottom, txt_added_scrap, txt_latest_scrap, txt_noRecords;
    private FeatureCoverFlow mCoverFlow;
    private CoverFlowAdapter mAdapter;
    private ArrayList<GameEntity> mData = new ArrayList<>(0);
    private TextSwitcher mTitle;
    LinearLayout linearView;
    TextView txtDesc;
    ImageView imgScrap;
    ImageView imgBack, imgNotification;
    TextView txtHeading, txtNotCount;
    RelativeLayout relativeNotCnt;
    //ImageLoader imageLoader;
    ProgressBar progressBar;
    PagerContainer pager_container;
    ViewPager pager;
    CirclePageIndicator indicator;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    int[] sliderImages = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    ArrayList<String> arrImageArray;
    String[][] sliderImageArray;
    ScrollView scroll_home;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        pref = new SharedPref(getActivity());

        init();
        if (SharedPref.getPreferences().getString(SharedPref.NOTIFICATION_COUNTER, "").equalsIgnoreCase("") ||
                SharedPref.getPreferences().getString(SharedPref.NOTIFICATION_COUNTER, "").equalsIgnoreCase("0")) {
            txtNotCount.setText("");
            txtNotCount.setVisibility(View.GONE);
            relativeNotCnt.setVisibility(View.GONE);
            imgNotification.setVisibility(View.VISIBLE);

        } else {
            txtNotCount.setText(SharedPref.getPreferences().getString(SharedPref.NOTIFICATION_COUNTER, ""));
            txtNotCount.setVisibility(View.VISIBLE);
            relativeNotCnt.setVisibility(View.VISIBLE);
            imgNotification.setVisibility(View.VISIBLE);
        }

        imgNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ActivityNotification.class);
                startActivity(i);
            }
        });

        return view;
    }

    private void init() {
        imgNotification = getActivity().findViewById(R.id.imgNotification);
        imgBack = getActivity().findViewById(R.id.imgBack);
        txtHeading = getActivity().findViewById(R.id.txtHeading);
        relativeNotCnt = getActivity().findViewById(R.id.relativeNotCnt);
        txtNotCount = getActivity().findViewById(R.id.txtNotCount);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        pager_container = view.findViewById(R.id.pager_container);
        pager = view.findViewById(R.id.overlap_pager);
        indicator = view.findViewById(R.id.indicator);
        scroll_home = view.findViewById(R.id.scroll_home);

        txtNotCount.setVisibility(View.GONE);
        relativeNotCnt.setVisibility(View.GONE);
        imgBack.setVisibility(View.GONE);
        imgNotification.setVisibility(View.GONE);

        txtHeading.setText("Seller Dashboard");

        txt_added_scrap = (TextView) view.findViewById(R.id.txt_added_scrap);
        txt_latest_scrap = (TextView) view.findViewById(R.id.txt_latest_scrap);
        view_liveScrapBottom = (TextView) view.findViewById(R.id.view_liveScrapBottom);
        view_soldScrapBottom = (TextView) view.findViewById(R.id.view_soldScrapBottom);
        txt_noRecords = view.findViewById(R.id.txt_noRecords);

        view_liveScrapBottom.setBackgroundDrawable(getActivity().getResources().getDrawable(R.color.white));
        view_soldScrapBottom.setBackgroundDrawable(getActivity().getResources().getDrawable(R.color.colorPrimary));

        txt_added_scrap.setOnClickListener(this);
        txt_latest_scrap.setOnClickListener(this);
/////////////////////////////////////////////////////////////
        // imgScrap = (ImageView) view.findViewById(R.id.imgScrap);
        txtDesc = (TextView) view.findViewById(R.id.txtDesc);
        //linearView = (LinearLayout) view.findViewById(R.id.linearView);

        mData.add(new GameEntity("", "", "", "", "", "", "", "", "", ""));
        mAdapter = new CoverFlowAdapter(getActivity());
        mAdapter.setData(mData);
        mCoverFlow = (FeatureCoverFlow) view.findViewById(R.id.coverflow);
        mCoverFlow.setAdapter(mAdapter);

        mCoverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
            @Override
            public void onScrolledToPosition(int position) {

                if (AppConstants.SHOW_DATA) {
                    txtDesc.setText(mData.get(position).scrapDesc);

                    arrImageArray = new ArrayList<>();
                    //sliderImageArray = new String[position][4];
                    if (!mData.get(position).scrapImage1.isEmpty()) {
                        //sliderImageArray = new String[position][1];
                        arrImageArray.add(sliderImageArray[position][0]);
                    }

                    if (!mData.get(position).scrapImage2.isEmpty()) {
                        //sliderImageArray = new String[position][1];
                        arrImageArray.add(sliderImageArray[position][1]);
                    }

                    if (!mData.get(position).scrapImage3.isEmpty()) {
                        //sliderImageArray = new String[position][1];
                        arrImageArray.add(sliderImageArray[position][2]);
                    }

                    if (!mData.get(position).scrapImage4.isEmpty()) {
                        //sliderImageArray = new String[position][1];
                        arrImageArray.add(sliderImageArray[position][3]);
                    }

                    setUpIndicator(arrImageArray);
                    System.out.println("************" + mData.get(position).scrapImage1);
                }
            }

            @Override
            public void onScrolling() {
                //mTitle.setText("");
                txtDesc.setText("");

            }
        });

        webserviceSellerHome();

    }

    private void webserviceSellerHome() {
        String seller_home_url = AppConstants.seller_home_url;
        System.out.println("seller_home Url >" + seller_home_url);
        JSONObject jsonObject = new JSONObject();
        try {
            String token = pref.getString("token", "");
            jsonObject.put("token", token);

            Log.e("TAG", String.valueOf(jsonObject));
            new APIRequest(getActivity(), seller_home_url, jsonObject, this, AppConstants.API_SELLER_HOME, AppConstants.POST);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSuccess(BaseResponse response) {
        AppConstants.SHOW_DATA = true;
        System.out.println("................response..........." + response);
        SellerHomeResponse sellerHomeResponse = (SellerHomeResponse) response;
        System.out.println("#####" + sellerHomeResponse.getStatus().toString());
        mData = new ArrayList<>();
        if (sellerHomeResponse.getStatus().equalsIgnoreCase("success")) {
            //  linearView.setVisibility(View.VISIBLE);
            mCoverFlow.setVisibility(View.VISIBLE);
            AppConstants.LIVE_SCRAP_LIST = new ArrayList<>();
            AppConstants.SOLD_SCRAP_LIST = new ArrayList<>();

            AppConstants.LIVE_SCRAP_LIST = sellerHomeResponse.getLiveScrap();
            AppConstants.SOLD_SCRAP_LIST = sellerHomeResponse.getSoldScrap();
            AppConstants.ARR_SCRAP_TYPE = sellerHomeResponse.getScrapType();

            if (AppConstants.LIVE_SCRAP_LIST.size() != 0) {
                txtDesc.setText(AppConstants.LIVE_SCRAP_LIST.get(0).getDesc());

            }
            if (AppConstants.LIVE_SCRAP_LIST.size() !=0) {
                for (int i = 0; i < AppConstants.LIVE_SCRAP_LIST.size(); i++) {

                    String orderNo = AppConstants.LIVE_SCRAP_LIST.get(i).getOrderId();
                    String scrapType = AppConstants.LIVE_SCRAP_LIST.get(i).getType();
                    String scrapQuan = AppConstants.LIVE_SCRAP_LIST.get(i).getQuantity();
                    String scrapDate = AppConstants.LIVE_SCRAP_LIST.get(i).getDate();
                    String scrapImg1 = AppConstants.LIVE_SCRAP_LIST.get(i).getImage1();
                    String scrapImg2 = AppConstants.LIVE_SCRAP_LIST.get(i).getImage2();
                    String scrapImg3 = AppConstants.LIVE_SCRAP_LIST.get(i).getImage3();
                    String scrapImg4 = AppConstants.LIVE_SCRAP_LIST.get(i).getImage4();
                    String scrapId = AppConstants.LIVE_SCRAP_LIST.get(i).getId();
                    String scrapDesc = AppConstants.LIVE_SCRAP_LIST.get(i).getDesc();

                    mData.add(new GameEntity(orderNo, scrapType, scrapQuan, scrapDate, scrapImg1, scrapImg2, scrapImg3, scrapImg4, scrapId, scrapDesc));
                }
                // mAdapter = new CoverFlowAdapter(this);
                mAdapter.setData(mData);
                //mCoverFlow = (FeatureCoverFlow) findViewById(R.id.coverflow);
                mCoverFlow.setAdapter(mAdapter);

                //For image slider
                arrImageArray = new ArrayList<>();
                sliderImageArray = new String[sellerHomeResponse.getLiveScrap().size()][4];
                if (sellerHomeResponse.getLiveScrap().size() != 0) {
                    for (int i = 0; i < sellerHomeResponse.getLiveScrap().size(); i++) {
                        sliderImageArray[i][0] = sellerHomeResponse.getLiveScrap().get(i).getImage1();
                        sliderImageArray[i][1] = sellerHomeResponse.getLiveScrap().get(i).getImage2();
                        sliderImageArray[i][2] = sellerHomeResponse.getLiveScrap().get(i).getImage3();
                        sliderImageArray[i][3] = sellerHomeResponse.getLiveScrap().get(i).getImage4();
                    }
                    arrImageArray.add(sliderImageArray[0][0]);
                    arrImageArray.add(sliderImageArray[0][1]);
                    arrImageArray.add(sliderImageArray[0][2]);
                    arrImageArray.add(sliderImageArray[0][3]);
                    setUpIndicator(arrImageArray);
                }
            }else {
                mCoverFlow.setVisibility(View.INVISIBLE);
                scroll_home.setVisibility(View.INVISIBLE);
                txt_noRecords.setVisibility(View.VISIBLE);
            }

        } else {
            mCoverFlow.setVisibility(View.INVISIBLE);
            scroll_home.setVisibility(View.INVISIBLE);
            txt_noRecords.setVisibility(View.VISIBLE);
            AppConstants.ARR_SCRAP_TYPE = sellerHomeResponse.getScrapType();
            // Toast.makeText(getActivity(), sellerHomeResponse.getDescription().toLowerCase(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(BaseResponse response) {

    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_coverflow_activity, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.txt_added_scrap:
                view_soldScrapBottom.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                view_liveScrapBottom.setBackgroundColor(getResources().getColor(R.color.white));

                mData = new ArrayList<>();
                if (!(AppConstants.LIVE_SCRAP_LIST.size() == 0)) {
                    mCoverFlow.setVisibility(View.VISIBLE);
                    scroll_home.setVisibility(View.VISIBLE);
                    txt_noRecords.setVisibility(View.INVISIBLE);
                    for (int i = 0; i < AppConstants.LIVE_SCRAP_LIST.size(); i++) {

                        String orderNo = AppConstants.LIVE_SCRAP_LIST.get(i).getOrderId();
                        String scrapType = AppConstants.LIVE_SCRAP_LIST.get(i).getType();
                        String scrapQuan = AppConstants.LIVE_SCRAP_LIST.get(i).getQuantity();
                        String scrapDate = AppConstants.LIVE_SCRAP_LIST.get(i).getDate();
                        String scrapImg1 = AppConstants.LIVE_SCRAP_LIST.get(i).getImage1();
                        String scrapImg2 = AppConstants.LIVE_SCRAP_LIST.get(i).getImage2();
                        String scrapImg3 = AppConstants.LIVE_SCRAP_LIST.get(i).getImage3();
                        String scrapImg4 = AppConstants.LIVE_SCRAP_LIST.get(i).getImage4();
                        String scrapId = AppConstants.LIVE_SCRAP_LIST.get(i).getId();
                        String scrapDesc = AppConstants.LIVE_SCRAP_LIST.get(i).getDesc();

                        System.out.println("*********data***********" + scrapDesc + "**" + scrapImg1 + "**" + scrapImg4);
                        mData.add(new GameEntity(orderNo, scrapType, scrapQuan, scrapDate, scrapImg1, scrapImg2, scrapImg3, scrapImg4, scrapId, scrapDesc));
                    }

                    mAdapter.setData(mData);

                    mCoverFlow.setAdapter(mAdapter);

                    arrImageArray = new ArrayList<>();
                    sliderImageArray = new String[AppConstants.LIVE_SCRAP_LIST.size()][4];

                    for (int i = 0; i < AppConstants.LIVE_SCRAP_LIST.size(); i++) {
                        sliderImageArray[i][0] = AppConstants.LIVE_SCRAP_LIST.get(i).getImage1();
                        sliderImageArray[i][1] = AppConstants.LIVE_SCRAP_LIST.get(i).getImage2();
                        sliderImageArray[i][2] = AppConstants.LIVE_SCRAP_LIST.get(i).getImage3();
                        sliderImageArray[i][3] = AppConstants.LIVE_SCRAP_LIST.get(i).getImage4();
                    }
                    System.out.println("*****************" + sliderImageArray[0][0] + "**" + sliderImageArray[0][1]);

                    System.out.println("******coverflow*******"+mCoverFlow.getScrollPosition());
                    if (!sliderImageArray[mCoverFlow.getScrollPosition()][0].isEmpty()) {

                        arrImageArray.add(sliderImageArray[mCoverFlow.getScrollPosition()][0]);
                    }

                    if (!sliderImageArray[mCoverFlow.getScrollPosition()][1].isEmpty()) {

                        arrImageArray.add(sliderImageArray[mCoverFlow.getScrollPosition()][1]);
                    }

                    if (!sliderImageArray[mCoverFlow.getScrollPosition()][2].isEmpty()) {

                        arrImageArray.add(sliderImageArray[mCoverFlow.getScrollPosition()][2]);
                    }

                    if (!sliderImageArray[mCoverFlow.getScrollPosition()][3].isEmpty()) {

                        arrImageArray.add(sliderImageArray[mCoverFlow.getScrollPosition()][3]);
                    }

                    System.out.println("*****arrImageArray******" + arrImageArray.size());
                    setUpIndicator(arrImageArray);
                } else {
                    mCoverFlow.setVisibility(View.INVISIBLE);
                    scroll_home.setVisibility(View.INVISIBLE);
                    txt_noRecords.setVisibility(View.VISIBLE);
                }

                break;

            case R.id.txt_latest_scrap:
                view_soldScrapBottom.setBackgroundColor(getResources().getColor(R.color.white));
                view_liveScrapBottom.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                mData = new ArrayList<>();
                if (AppConstants.SOLD_SCRAP_LIST.size() != 0) {
                    mCoverFlow.setVisibility(View.VISIBLE);
                    scroll_home.setVisibility(View.VISIBLE);
                    txt_noRecords.setVisibility(View.INVISIBLE);
                    for (int i = 0; i < AppConstants.SOLD_SCRAP_LIST.size(); i++) {

                        String orderNo = AppConstants.SOLD_SCRAP_LIST.get(i).getOrderId();
                        String scrapType = AppConstants.SOLD_SCRAP_LIST.get(i).getType();
                        String scrapQuan = AppConstants.SOLD_SCRAP_LIST.get(i).getQuantity();
                        String scrapDate = AppConstants.SOLD_SCRAP_LIST.get(i).getDate();
                        String scrapImg1 = AppConstants.SOLD_SCRAP_LIST.get(i).getImage1();
                        String scrapImg2 = AppConstants.SOLD_SCRAP_LIST.get(i).getImage2();
                        String scrapImg3 = AppConstants.SOLD_SCRAP_LIST.get(i).getImage3();
                        String scrapImg4 = AppConstants.SOLD_SCRAP_LIST.get(i).getImage4();
                        String scrapId = AppConstants.SOLD_SCRAP_LIST.get(i).getId();
                        String scrapDesc = AppConstants.SOLD_SCRAP_LIST.get(i).getDesc();

                        mData.add(new GameEntity(orderNo, scrapType, scrapQuan, scrapDate, scrapImg1, scrapImg2, scrapImg3, scrapImg4, scrapId, scrapDesc));
                    }

                    mAdapter.setData(mData);

                    mCoverFlow.setAdapter(mAdapter);

                    arrImageArray = new ArrayList<>();
                    sliderImageArray = new String[AppConstants.SOLD_SCRAP_LIST.size()][4];

                    for (int i = 0; i < AppConstants.SOLD_SCRAP_LIST.size(); i++) {
                        sliderImageArray[i][0] = AppConstants.SOLD_SCRAP_LIST.get(i).getImage1();
                        sliderImageArray[i][1] = AppConstants.SOLD_SCRAP_LIST.get(i).getImage2();
                        sliderImageArray[i][2] = AppConstants.SOLD_SCRAP_LIST.get(i).getImage3();
                        sliderImageArray[i][3] = AppConstants.SOLD_SCRAP_LIST.get(i).getImage4();
                    }
                    arrImageArray.add(sliderImageArray[0][0]);
                    arrImageArray.add(sliderImageArray[0][1]);
                    arrImageArray.add(sliderImageArray[0][2]);
                    arrImageArray.add(sliderImageArray[0][3]);
                    setUpIndicator(arrImageArray);
                } else {
                    mCoverFlow.setVisibility(View.INVISIBLE);
                    scroll_home.setVisibility(View.INVISIBLE);
                    txt_noRecords.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private void setUpIndicator(ArrayList<String> arrImageArray) {
        pager = pager_container.getViewPager();
        pager.setAdapter(new MyPagerAdapter(getContext(), arrImageArray));
        pager.setClipChildren(false);
        //
        pager.setOffscreenPageLimit(15);

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
        NUM_PAGES = arrImageArray.size();
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
        AppConstants.image1 = null;
        AppConstants.image2 = null;
        AppConstants.image3 = null;
        AppConstants.image4 = null;
    }
}