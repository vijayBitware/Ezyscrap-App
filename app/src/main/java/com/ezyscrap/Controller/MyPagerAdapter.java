package com.ezyscrap.Controller;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ezyscrap.R;

import java.util.ArrayList;

/**
 * Created by bitware on 9/1/18.
 */

public class MyPagerAdapter extends PagerAdapter {

    private Context context;
    ArrayList<String> imageArray;

    public MyPagerAdapter(Context context,ArrayList<String> imageArray) {
        this.context = context;
        this.imageArray=imageArray;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_cover, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_cover);
        /*System.out.println(">> path :" + arrSliderImages.get(position).getImagePath());
        aQuery.id(imageView).image(arrSliderImages.get(position).getImagePath());*/
        System.out.println("****pager******"+imageArray.get(position));
        Glide.with(context).load(imageArray.get(position)).into(imageView);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return imageArray.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }
}
