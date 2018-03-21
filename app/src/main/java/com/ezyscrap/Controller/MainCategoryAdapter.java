package com.ezyscrap.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.ezyscrap.Model.Login.CategoryDatum;
import com.ezyscrap.View.Fragments.OverlapFragment;

import java.util.List;

/**
 * Created by bitware on 4/9/17.
 */

public class MainCategoryAdapter extends FragmentPagerAdapter {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    List<CategoryDatum> myArrList ;


    public MainCategoryAdapter(FragmentManager fm, List<CategoryDatum> arrayList) {
        super(fm);
        this.myArrList=arrayList;

    }


    @Override
    public Fragment getItem(int position) {
//
        return OverlapFragment.newInstance(position,this.myArrList);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public int getCount() {
        return myArrList.size();
    }

}
