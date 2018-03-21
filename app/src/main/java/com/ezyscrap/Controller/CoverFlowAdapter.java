package com.ezyscrap.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ezyscrap.R;

import java.util.ArrayList;

public class CoverFlowAdapter extends BaseAdapter {
	
	private ArrayList<GameEntity> mData = new ArrayList<>(0);
	private Context mContext;

	public CoverFlowAdapter(Context context) {
		mContext = context;
	}
	
	public void setData(ArrayList<GameEntity> data) {
		mData = data;
	}
	
	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int pos) {
		return mData.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.row_home, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.txtOrderNumber = (TextView) rowView.findViewById(R.id.txtOrderNumber);
			viewHolder.txtScrapType = (TextView) rowView.findViewById(R.id.txtScrapType);
			viewHolder.txtScrapQuantity = (TextView) rowView.findViewById(R.id.txtScrapQuantity);
			viewHolder.txtDate = rowView.findViewById(R.id.txtDate);
			//viewHolder.txtOrderNumber = (TextView) rowView.findViewById(R.id.txtOrderNumber);
           /* viewHolder.image = (ImageView) rowView
                    .findViewById(R.id.image);*/
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();

       // holder.image.setImageResource(mData.get(position).imageResId);
	/*	Picasso.with(mContext)
				.load(mData.get(position).scrapImage)
				.into( holder.image);*/
        holder.txtOrderNumber.setText(mData.get(position).orderNo);
		System.out.println("********order no*******"+mData.get(position).orderNo);
		holder.txtScrapType.setText(mData.get(position).scrapType);
		holder.txtScrapQuantity.setText(mData.get(position).scrapUnit);
		String arr[] = mData.get(position).scrapDate.split(" ");
		holder.txtDate.setText("Date : "+arr[0]);
		return rowView;
	}


    static class ViewHolder {
        public TextView txtOrderNumber, txtScrapType, txtScrapQuantity, txtDate;
        public ImageView image;
    }
}
