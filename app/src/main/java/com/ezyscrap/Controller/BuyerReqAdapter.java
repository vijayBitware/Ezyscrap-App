package com.ezyscrap.Controller;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ezyscrap.AppUtils.AlertClass;
import com.ezyscrap.AppUtils.AppUtils;
import com.ezyscrap.AppUtils.SharedPref;
import com.ezyscrap.Model.Login.Buyer.BuyerReqResponse.BuyerInterestResponse;
import com.ezyscrap.Model.Login.BuyerReqModel;
import com.ezyscrap.R;
import com.ezyscrap.View.Activity.PaytmActivity;
import com.ezyscrap.View.Fragments.ScrapReqDetailsFragment;
import com.ezyscrap.webservice.APIRequest;
import com.ezyscrap.webservice.AppConstants;
import com.ezyscrap.webservice.BaseResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Admin on 12/21/2017.
 */

public class BuyerReqAdapter extends RecyclerView.Adapter<BuyerReqAdapter.DataObjectHolder> implements APIRequest.ResponseHandler {
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    ArrayList<BuyerReqModel> list;
    Activity activity;
    Dialog dialog;
    String network_status;
    Typeface font;
    SharedPref pref;
    int pos;

    public BuyerReqAdapter(FragmentActivity activity, ArrayList<BuyerReqModel> list) {
        this.activity = activity;
        this.list = list;
    }
    //BackgroundImageLoader imageloader;

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_buyer_req_list, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, final int position) {

        holder.txtScrapType.setText(list.get(position).getScrapType());
        holder.txtOrderNo.setText("#" + list.get(position).getOrderNo());
        holder.txtUnit.setText(list.get(position).getUnit());

        System.out.println("******listttt****" + list.get(position).isInterestflag());
        if (list.get(position).isInterestflag()) {
            holder.imgAddBid.setVisibility(View.VISIBLE);
            holder.imgAddInterest.setVisibility(View.GONE);
        } else {
            holder.imgAddBid.setVisibility(View.GONE);
            holder.imgAddInterest.setVisibility(View.VISIBLE);
        }

        if (list.get(position).getBidFlag().equalsIgnoreCase("0")) {
            holder.linearBidAdded.setVisibility(View.GONE);
            //holder.imgAddInterest.setVisibility(View.GONE);
            holder.imgAddBid.setVisibility(View.VISIBLE);
        } else {
            holder.linearBidAdded.setVisibility(View.VISIBLE);
            //holder.imgAddInterest.setVisibility(View.GONE);
            holder.imgAddBid.setVisibility(View.GONE);
        }

        if (list.get(position).getSoldFlag() == 2) {
            holder.txtSold.setVisibility(View.VISIBLE);
            holder.txtSold.setText("Sold");
            holder.linearBidAdded.setVisibility(View.GONE);
            holder.imgAddInterest.setVisibility(View.GONE);
            holder.imgAddBid.setVisibility(View.GONE);
        } else if (list.get(position).getSoldFlag() == 1) {
            holder.txtSold.setText("Sold to me");
            holder.txtSold.setVisibility(View.VISIBLE);
            holder.linearBidAdded.setVisibility(View.GONE);
            holder.imgAddInterest.setVisibility(View.GONE);
            holder.imgAddBid.setVisibility(View.GONE);
        }else
        {
            holder.txtSold.setVisibility(View.GONE);
        }

        holder.imgAddBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pref = new SharedPref(activity);
               // if(SharedPref.getPreferences().getString(SharedPref.REG_FEE,"").equalsIgnoreCase("1")) {
                    fm = ((FragmentActivity) activity).getSupportFragmentManager();
                    fragment = new ScrapReqDetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("orderNo", list.get(position).getOrderNo());
                    bundle.putString("scrapType", list.get(position).getScrapType());
                    bundle.putString("scrapDesc", list.get(position).getScrapDesc());
                    bundle.putString("unit", list.get(position).getUnit());
                    bundle.putString("apprAmnt", list.get(position).getApprAmnt());
                    bundle.putString("scrapId", list.get(position).getScrapId());
                    bundle.putString("scrapImg1", list.get(position).getScrapImg1());
                    bundle.putString("scrapImg2", list.get(position).getScrapImg2());
                    bundle.putString("scrapImg3", list.get(position).getScrapImg3());
                    bundle.putString("scrapImg4", list.get(position).getScrapImg4());
                    bundle.putBoolean("flag", list.get(position).isInterestflag());
                    bundle.putString("bidFlag", list.get(position).getBidFlag());
                    bundle.putInt("soldFlag", list.get(position).getSoldFlag());
                    bundle.putString("gst", list.get(position).getGst());
                    bundle.putString("bidAmnt", list.get(position).getBidAmnt());
                    AppUtils.replaceFragment(fragment, fm, bundle);
                /*}else
                {
                    final Dialog dialog = new Dialog(activity);
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

                            Intent i = new Intent(activity, PaytmActivity.class);
                            i.putExtra("amount", "5000");
                            i.putExtra("orderId","Reg"+SharedPref.getPreferences().getString(SharedPref.USER_ID,""));
                            i.putExtra("regFee", "yes");
                            activity.startActivity(i);
                        }
                    });

                    dialog.setCancelable(false);
                    dialog.show();
                }*/
            }
        });

        holder.imgAddInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pref = new SharedPref(activity);
               // if(SharedPref.getPreferences().getString(SharedPref.REG_FEE,"").equalsIgnoreCase("1")) {
                    addInterest(position);
                /*}else
                {
                    final Dialog dialog = new Dialog(activity);
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

                            Intent i = new Intent(activity, PaytmActivity.class);
                            i.putExtra("amount", "5000");
                            i.putExtra("orderId","Reg"+SharedPref.getPreferences().getString(SharedPref.USER_ID,""));
                            i.putExtra("regFee", "yes");
                            activity.startActivity(i);
                        }
                    });

                    dialog.setCancelable(false);
                    dialog.show();
                }
*/
            }
        });

        holder.linearDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fm = ((FragmentActivity) activity).getSupportFragmentManager();
                fragment = new ScrapReqDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("orderNo", list.get(position).getOrderNo());
                bundle.putString("scrapType", list.get(position).getScrapType());
                bundle.putString("scrapDesc", list.get(position).getScrapDesc());
                bundle.putString("unit", list.get(position).getUnit());
                bundle.putString("apprAmnt", list.get(position).getApprAmnt());
                bundle.putString("scrapId", list.get(position).getScrapId());
                bundle.putString("scrapImg1", list.get(position).getScrapImg1());
                bundle.putString("scrapImg2", list.get(position).getScrapImg2());
                bundle.putString("scrapImg3", list.get(position).getScrapImg3());
                bundle.putString("scrapImg4", list.get(position).getScrapImg4());
                bundle.putBoolean("flag", list.get(position).isInterestflag());
                bundle.putString("bidFlag", list.get(position).getBidFlag());
                bundle.putInt("soldFlag", list.get(position).getSoldFlag());
                bundle.putString("gst", list.get(position).getGst());
                bundle.putString("bidAmnt", list.get(position).getBidAmnt());
                AppUtils.replaceFragment(fragment, fm, bundle);
            }
        });

    }

    private void addInterest(int position) {
        pos = position;
        System.out.println("*******position******" + position);
        pref = new SharedPref(activity);
        if (AppUtils.isConnectingToInternet(activity)) {
            String getUserUrl = AppConstants.BASE_URL + "add_scrap_interest";
            System.out.println("add_scrap_interest Url >" + getUserUrl);
            JSONObject jsonObject = new JSONObject();
            try {

                jsonObject.put("token", SharedPref.getPreferences().getString(SharedPref.TOKEN, ""));
                jsonObject.put("scrap_id", list.get(position).getScrapId());
                new APIRequest(activity, getUserUrl, jsonObject, this, AppConstants.API_BUYER_INTEREST, AppConstants.POST);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {

            AlertClass alert = new AlertClass();
            alert.customDialogforAlertMessage(activity, activity.getResources().getString(R.string.no_internet), "Check your internet connection");

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onSuccess(BaseResponse response) {
        BuyerInterestResponse responseList = (BuyerInterestResponse) response;
        System.out.println("###>>> : " + responseList.getDescription().toString());
        if (responseList.getStatus().toString().equalsIgnoreCase("success")) {

            System.out.println("*******pos******" + pos);
            BuyerReqModel data = new BuyerReqModel();
            data.setInterestflag(true);
            data.setOrderNo(list.get(pos).getOrderNo());
            data.setScrapType(list.get(pos).getScrapType());
            data.setUnit(list.get(pos).getUnit());
            data.setScrapId(list.get(pos).getScrapId());
            data.setScrapDesc(list.get(pos).getScrapDesc());
            data.setApprAmnt(list.get(pos).getApprAmnt());
            data.setFlag("1");
            data.setSoldFlag(0);
            data.setBidFlag("0");
            data.setGst(list.get(pos).getGst());
            data.setScrapImg1(list.get(pos).getScrapImg1());
            data.setScrapImg2(list.get(pos).getScrapImg2());
            data.setScrapImg3(list.get(pos).getScrapImg3());
            data.setScrapImg4(list.get(pos).getScrapImg4());
            list.set(pos, data);
            notifyDataSetChanged();
            Toast.makeText(activity, responseList.getDescription(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(activity, responseList.getDescription(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onFailure(BaseResponse response) {
        System.out.println("******fail******");
        // Toast.makeText(activity, response.toString(), Toast.LENGTH_SHORT).show();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {

        TextView txtScrapType, txtOrderNo, txtUnit, txtSold;
        ImageView imgAddBid, imgAddInterest;
        LinearLayout linearDetails, linearBidAdded;

        public DataObjectHolder(View itemView) {
            super(itemView);
            linearBidAdded = (LinearLayout) itemView.findViewById(R.id.linearBidAdded);
            linearDetails = (LinearLayout) itemView.findViewById(R.id.linearDetails);
            txtScrapType = (TextView) itemView.findViewById(R.id.txtScrapType);
            txtOrderNo = (TextView) itemView.findViewById(R.id.txtOrderNo);
            txtUnit = (TextView) itemView.findViewById(R.id.txtUnit);
            imgAddBid = (ImageView) itemView.findViewById(R.id.imgAddBid);
            imgAddInterest = (ImageView) itemView.findViewById(R.id.imgAddInterest);
            txtSold = itemView.findViewById(R.id.txtSold);

        }

    }
}
