package com.ezyscrap.Controller;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ezyscrap.AppUtils.AppUtils;
import com.ezyscrap.Model.PaymentHistory.PaymentDatum;
import com.ezyscrap.R;

import java.util.List;

/**
 * Created by bitware on 23/12/17.
 */

public class PriviousTransAdapter extends RecyclerView.Adapter<PriviousTransAdapter.DataObjectHolder> {

    List<PaymentDatum> list;
    Activity activity;
    ImageView imgAddBid, imgAddInterest;

    public PriviousTransAdapter(FragmentActivity activity,List<PaymentDatum> paymentHistory) {
        this.activity = activity;
        this.list = paymentHistory;
    }
    //BackgroundImageLoader imageloader;

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_privious_trans, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, final int position) {

        holder.txt_orderId.setText("Order #"+list.get(position).getOrderId());
        holder.txt_paymentAmt.setText(activity.getResources().getString(R.string.rupee)+round(Double.parseDouble(list.get(position).getTotalAmt()), 2));
        holder.txt_paymentDate.setText(AppUtils.parseDateToddMMyyyy(list.get(position).getDate()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder  {

        TextView txt_paymentAmt,txt_orderId,txt_paymentDate;

        public DataObjectHolder(View itemView) {
            super(itemView);
            txt_paymentAmt = itemView.findViewById(R.id.txt_paymentAmt);
            txt_orderId = itemView.findViewById(R.id.txt_orderId);
            txt_paymentDate = itemView.findViewById(R.id.txt_paymentDate);

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

