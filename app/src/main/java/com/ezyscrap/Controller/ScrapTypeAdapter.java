package com.ezyscrap.Controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ezyscrap.R;

import java.util.ArrayList;

/**
 * Created by bitware on 26/12/17.
 */

public class ScrapTypeAdapter extends ArrayAdapter<String> {

        private final LayoutInflater inflater;
        Context context;
        ArrayList<String> arrCategoryList;
        ViewHolder holder;
        TextView tv_title;

        public ScrapTypeAdapter(Context context, int resource, ArrayList<String> arrCategoryList) {
            super(context,resource,arrCategoryList);

            this.context = context;
            this.arrCategoryList=arrCategoryList;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public static class ViewHolder{
            TextView tv_scrapType;
        }

    @Override
    public int getCount() {
        return arrCategoryList.size();
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView==null) {
            convertView = inflater.inflate(R.layout.row_scraptype, null);
            holder = new ViewHolder();

            holder.tv_scrapType = convertView.findViewById(R.id.tv_scrapTitle);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_scrapType.setText(arrCategoryList.get(position));
        return convertView;
    }
}
