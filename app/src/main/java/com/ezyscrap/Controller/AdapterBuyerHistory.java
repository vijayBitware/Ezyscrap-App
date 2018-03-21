package com.ezyscrap.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ezyscrap.AppUtils.AppUtils;
import com.ezyscrap.Model.OrderHistory.ScrapDatum;
import com.ezyscrap.R;
import com.ezyscrap.View.Activity.InvoiceActivity;
import com.ezyscrap.View.Fragments.FragmentOrderDetails;

import java.util.List;

/**
 * Created by bitware on 28/12/17.
 */

public class AdapterBuyerHistory extends RecyclerView.Adapter<AdapterBuyerHistory.DataObjectHolder> {
    Fragment fragment;
    FragmentManager fm;
    List<ScrapDatum> list;
    Activity activity;

    public AdapterBuyerHistory(FragmentActivity activity,List<ScrapDatum> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_buyer_history, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder  {

        TextView txtScrapType, txtOrderNo, txtQty,txtAmnt;
        ImageView imgAddBid, imgAddInterest, imgInvoice;
        LinearLayout linearDetails;

        public DataObjectHolder(View itemView) {
            super(itemView);

            linearDetails= (LinearLayout)itemView.findViewById(R.id.linearDetails);
            txtAmnt = (TextView) itemView.findViewById(R.id.txtAmnt);
            txtScrapType = (TextView) itemView.findViewById(R.id.txtScrapType);
            txtOrderNo = (TextView) itemView.findViewById(R.id.txtOrderNo);
            txtQty = itemView.findViewById(R.id.txtQty);
            imgInvoice = itemView.findViewById(R.id.imgInvoice);
            imgAddBid = (ImageView) itemView.findViewById(R.id.imgAddBid);
            imgAddInterest = (ImageView) itemView.findViewById(R.id.imgAddInterest);


        }

    }

    @Override
    public void onBindViewHolder(AdapterBuyerHistory.DataObjectHolder holder, final int position) {

        holder.txtOrderNo.setText("#"+list.get(position).getOrderId());
        holder.txtScrapType.setText(list.get(position).getType());
        holder.txtAmnt.setText(activity.getResources().getString(R.string.rupee)+round(Double.parseDouble(list.get(position).getTotalAmt()), 2));
        holder.txtQty.setText(list.get(position).getQuantity());


        holder.linearDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm = ((FragmentActivity)activity).getSupportFragmentManager();
                fragment = new FragmentOrderDetails();
                Bundle bundle = new Bundle();
                bundle.putString("orderNo", list.get(position).getOrderId());
                bundle.putString("scrapType", list.get(position).getType());
                bundle.putString("scrapDesc", list.get(position).getDesc());
                bundle.putString("unit", list.get(position).getQuantity());
                bundle.putString("totalAmnt", list.get(position).getTotalAmt());
                bundle.putString("amntPerKg",list.get(position).getBidPerKg());
                bundle.putString("scrapImg", list.get(position).getImage1());
                bundle.putString("date",list.get(position).getDate());
                bundle.putString("paid_amt",list.get(position).getPayedAmt());
                bundle.putString("gst",list.get(position).getGst());
                AppUtils.replaceFragment(fragment,fm,bundle);
            }
        });


        holder.imgInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, InvoiceActivity.class);
                i.putExtra("scrapId", list.get(position).getId());
                activity.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

}
