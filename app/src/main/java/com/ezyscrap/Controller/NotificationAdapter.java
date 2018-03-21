package com.ezyscrap.Controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ezyscrap.Model.NotificationModel;
import com.ezyscrap.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by bitwarepc on 06-Oct-17.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    Context context, mContext;
    List<NotificationModel> modellist = new ArrayList<>();

    public NotificationAdapter(Context context, List<NotificationModel> modellist) {

        this.context = context;
        this.modellist = modellist;
        this.mContext = context;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_notification_list, parent, false);

        return new MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NotificationModel data = modellist.get(position);
        holder.txtNotificationMessage.setText(data.getTxtNotificationMessage());
        holder.txtDate.setText(data.getTxtDate());


    }


    @Override
    public int getItemCount() {
        return modellist.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtNotificationMessage,txtDate;
        public MyViewHolder(View view) {
            super(view);

            txtNotificationMessage = (TextView) view.findViewById(R.id.txtNotificationMessage);

            txtDate = (TextView) view.findViewById(R.id.txtDate);


        }


    }
}






